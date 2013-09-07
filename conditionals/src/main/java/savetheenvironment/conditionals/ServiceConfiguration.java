package savetheenvironment.conditionals;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.support.MBeanServerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ReflectionUtils;

import javax.inject.Provider;
import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


@Configuration
@EnableMBeanExport(server = "server")
@PropertySource("/config.properties")
class ServiceConfiguration {

    private static final String
            dataSourceDriverClassPropertyName = "dataSource.driverClass",
            dataSourceUrlPropertyName = "dataSource.url",
            dataSourceUserPropertyName = "dataSource.user",
            dataSourcePasswordPropertyName = "dataSource.password";

    private static ReloadableDataSource reloadableDataSource(final ApplicationContext applicationContext, final Provider<DataSource> dataSourceProvider) {

        final Method reloadMethod = ReflectionUtils.findMethod(
                ReloadableDataSource.class, "reload", String.class, String.class, String.class, int.class);

        final AtomicReference<DataSource> dataSourceAtomicReference = new AtomicReference<DataSource>();

        final ReloadableDataSource reloadableDataSource = new ReloadableDataSource() {
            @ManagedOperation
            @Override
            public void reload(String url, String user, String pw) {
                ensurePropertiesAvailable(applicationContext, url, user, pw, org.h2.Driver.class);
                dataSourceAtomicReference.set(dataSourceProvider.get());
            }
        };

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(DataSource.class);
        proxyFactory.setTarget(reloadableDataSource);
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice(new MethodInterceptor() {

            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                Method methodToInvoke = invocation.getMethod();

                if (methodToInvoke.equals(reloadMethod)) {
                    methodToInvoke.invoke(reloadableDataSource, invocation.getArguments());
                } else {
                    // factory the bean if we need it
                    if (dataSourceAtomicReference.get() == null) {
                        dataSourceAtomicReference.set(dataSourceProvider.get());
                    }
                    // then delegate all subsequent calls to the reference
                    methodToInvoke.invoke(
                            dataSourceAtomicReference.get(),
                            invocation.getArguments());
                }

                return invocation.proceed();
            }
        });
        return (ReloadableDataSource) proxyFactory.getProxy();
    }

    private static void ensurePropertiesAvailable(ApplicationContext ac, String url, String user, String pw, Class<? extends Driver> driverClassName) {
        Environment environment = ac.getEnvironment();
        if (environment instanceof ConfigurableEnvironment) {
            ConfigurableEnvironment configurableEnvironment =
                    (ConfigurableEnvironment) environment;
            Map<String, Object> mapOfProperties = new HashMap<String, Object>();
            mapOfProperties.put(dataSourceDriverClassPropertyName, driverClassName);
            mapOfProperties.put(dataSourcePasswordPropertyName, pw);
            mapOfProperties.put(dataSourceUrlPropertyName, url);
            mapOfProperties.put(dataSourceUserPropertyName, user);

            MapPropertySource mapPropertySource = new MapPropertySource("reloadableDataSourceProperties", mapOfProperties);
            configurableEnvironment.getPropertySources().addLast(mapPropertySource);
        }
    }

    @Bean
    public MBeanServerFactoryBean server() {
        MBeanServerFactoryBean mbeanFactory = new MBeanServerFactoryBean();
        mbeanFactory.setLocateExistingServerIfPossible(true);
        mbeanFactory.setRegisterWithFactory(true);
        return mbeanFactory;
    }

    @Bean
    public CustomerService customerService(JdbcTemplate jdbcTemplate) {
        return new CustomerService(jdbcTemplate);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Scope(value = "prototype")
    public DataSource dataSource(Environment env) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(env.getPropertyAsClass(dataSourceDriverClassPropertyName, Driver.class));
        dataSource.setUrl(env.getProperty(dataSourceUrlPropertyName).trim());
        dataSource.setUsername(env.getProperty(dataSourceUserPropertyName).trim());
        dataSource.setPassword(env.getProperty(dataSourcePasswordPropertyName).trim());
        return dataSource;
    }


}

