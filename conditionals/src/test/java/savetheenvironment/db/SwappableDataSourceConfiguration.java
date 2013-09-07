package savetheenvironment.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import savetheenvironment.refresh.RefreshableFactoryBean;

import javax.inject.Provider;
import javax.sql.DataSource;
import java.sql.Driver;

@EnableTransactionManagement
@Configuration
@PropertySource("/config.properties")
public class SwappableDataSourceConfiguration {

    public static final String DB_URL = "db.url";
    public static final String DB_DRIVER_CLASS = "db.driverClass";
    public static final String DB_USER = "db.user";
    public static final String DB_PASSWORD = "db.password";

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public CustomerService customerService(JdbcTemplate jdbcTemplate) {
        return new CustomerService(jdbcTemplate);
    }

    @Bean
    public DataSource dataSourceRefreshableBeanFactoryBean(final Environment environment) throws Exception {
        RefreshableFactoryBean<DataSource> ds = new RefreshableFactoryBean<DataSource>(new Provider<DataSource>() {
            @Override
            public DataSource get() {
                try {
                    String url = environment.getProperty(DB_URL);
                    String pw = environment.getProperty(DB_PASSWORD);
                    String user = environment.getProperty(DB_USER);
                    Class<? extends Driver> driverClass = environment.getPropertyAsClass(DB_DRIVER_CLASS, Driver.class);
                    Driver newInstance = driverClass.newInstance();
                    return new SimpleDriverDataSource(newInstance, url, user, pw);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
        return ds.getObject();
    }


}
