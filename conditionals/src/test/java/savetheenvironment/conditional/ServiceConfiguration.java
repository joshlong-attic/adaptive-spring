package savetheenvironment.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static savetheenvironment.conditional.DataSourceConfigurationKeyGenerator.dataSourceFromConfigurationProperties;

/**
 * @author Josh Long
 */
@PropertySource("/config.properties")
@Configuration
public class ServiceConfiguration {

    public static final String POSTGRES = "postgres";
    public static final String H2_DB_NAME = "h2.dbName";

    @Bean(name = POSTGRES)
    @ConditionalOnPropertiesPresent({
            POSTGRES + ".user",
            POSTGRES + ".password",
            POSTGRES + ".driverClass",
            POSTGRES + ".url"
    })
    public DataSource postgres(Environment e) {
        return dataSourceFromConfigurationProperties(POSTGRES, e);
    }

    @Bean
    @ConditionalOnPropertiesPresent(H2_DB_NAME)
    public EmbeddedDatabase h2(Environment e) {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName(e.getProperty(H2_DB_NAME))
                .build();
    }
}

