package savetheenvironment.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

import static savetheenvironment.conditional.DataSourceConfigurationKeyGenerator.dataSourceFromConfigurationProperties;

/**
 * @author Josh Long
 */
@PropertySource("/config.properties")
@Configuration
public class ServiceConfiguration {
    public static final String POSTGRES_ROOT = "postgres";

    @Bean
    @ConditionalOnDataSourceConfigurationProperties(POSTGRES_ROOT)
    public DataSource db(Environment e)  {
        return dataSourceFromConfigurationProperties(POSTGRES_ROOT, e);
    }
}
