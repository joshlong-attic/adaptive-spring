package savetheenvironment.embedded;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static savetheenvironment.embedded.ServiceConfiguration.DEVELOPMENT_PROFILE;
import static savetheenvironment.embedded.ServiceConfiguration.PRODUCTION_PROFILE;

public class Main {

    static void runWithApplicationContext() {
        boolean production = false;
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.getEnvironment().setActiveProfiles(production ? ServiceConfiguration.PRODUCTION_PROFILE : ServiceConfiguration.DEVELOPMENT_PROFILE);
        ac.register(ServiceConfiguration.class);
        ac.refresh();
    }

    static void runWithSpringBoot() {
        SpringApplication.run(ServiceConfiguration.class);
    }

    public static void main(String[] arrrImAPirate) throws Throwable {
//        runWithApplicationContext();
        runWithSpringBoot();
    }

}

class Customer {
    private String firstName, lastName;
    private long id;

    public Customer(String firstName, String lastName, long id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getId() {
        return id;
    }

}

class CustomerService {

    private final RowMapper<Customer> customerRowMapper = new RowMapper<Customer>() {
        @Override
        public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Customer(
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getLong("id")
            );
        }
    };
    private final JdbcTemplate jdbcTemplate;

    public CustomerService(JdbcTemplate jdbcTemplate) {
        assert jdbcTemplate != null : "you must provide a value to the JdbcTemplate";
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> loadAllCustomers() {
        return jdbcTemplate.query("select * from customers", this.customerRowMapper);
    }
}

@EnableTransactionManagement
@ComponentScan
@Configuration
@Import({ProductionDataSourceConfiguration.class, EmbeddedDataSourceConfiguration.class})
class ServiceConfiguration {

    public static final String PRODUCTION_PROFILE = "production";
    public static final String DEVELOPMENT_PROFILE = "test";

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}

@Configuration
@PropertySource("/config.properties")
@Profile({PRODUCTION_PROFILE})
class ProductionDataSourceConfiguration {

    @Bean
    public DataSource dataSource(Environment env) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(env.getPropertyAsClass("dataSource.driverClass", Driver.class));
        dataSource.setUrl(env.getProperty("dataSource.url").trim());
        dataSource.setUsername(env.getProperty("dataSource.user").trim());
        dataSource.setPassword(env.getProperty("dataSource.password").trim());
        return dataSource;
    }
}

@Configuration
@Profile({DEVELOPMENT_PROFILE})
class EmbeddedDataSourceConfiguration {

    private Log log = LogFactory.getLog(getClass());

    @PostConstruct
    public void setupTestProfileImages() throws Exception {
        // do setup to make sure the test environment is setup as required
    }

    @Bean
    public DataSource dataSource() {

        ClassPathResource classPathResource = new ClassPathResource("/crm-schema-h2.sql");

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(classPathResource);

        EmbeddedDatabaseFactoryBean embeddedDatabaseFactoryBean = new EmbeddedDatabaseFactoryBean();
        embeddedDatabaseFactoryBean.setDatabasePopulator(resourceDatabasePopulator);
        embeddedDatabaseFactoryBean.setDatabaseName("crm");
        embeddedDatabaseFactoryBean.setDatabaseType(EmbeddedDatabaseType.H2);
        embeddedDatabaseFactoryBean.afterPropertiesSet();
        return embeddedDatabaseFactoryBean.getObject();
    }

}
