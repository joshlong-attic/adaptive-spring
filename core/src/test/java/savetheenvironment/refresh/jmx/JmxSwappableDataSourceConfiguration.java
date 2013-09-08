package savetheenvironment.refresh.jmx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import savetheenvironment.refresh.SwappableDataSourceConfiguration;

import javax.sql.DataSource;

@Configuration
@EnableMBeanExport
@Import(SwappableDataSourceConfiguration.class)
public class JmxSwappableDataSourceConfiguration {
    @Bean
    public JmxDataSourceConfigurationEndpoint dataSourceConfigurationEndpoint(DataSource dataSource) {
        return new JmxDataSourceConfigurationEndpoint(dataSource);
    }
}
