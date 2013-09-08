package savetheenvironment.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Driver;

/**
 * @author Josh Long
 */
@PropertySource("/config.properties")
@Configuration
public class ServiceConfiguration {

    // h2 configuration properties
    private final String h2DatabaseName = "h2.dbName";

    // postgres configuration properties
    private final String postgres = "postgres";
    private final String postgresUser = postgres + ".user";
    private final String postgresPassword = postgres + ".password";
    private final String postgresUrl = postgres + ".url";
    private final String postgresDriverClass = postgres + ".driverClass";

    @Bean
    @ConditionalOnPropertiesPresent({
            postgresUser, postgresPassword,
            postgresUrl, postgresDriverClass
    })
    public DataSource postgres(Environment e) {
        try {
            String user = e.getProperty(postgresUser),
                    pw = e.getProperty(postgresPassword),
                    url = e.getProperty(postgresUrl);
            Class<? extends Driver> driverClass = e.getPropertyAsClass(
                    postgresDriverClass, Driver.class);

            Driver actualDriverInstance = driverClass.newInstance();
            return new SimpleDriverDataSource(actualDriverInstance, url, user, pw);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Bean
    @ConditionalOnPropertiesPresent(h2DatabaseName)
    public EmbeddedDatabase h2(Environment e) {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName(e.getProperty(h2DatabaseName))
                .build();
    }
}

