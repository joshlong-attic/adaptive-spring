package savetheenvironment.refresh.jmx;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.util.Assert;
import savetheenvironment.refresh.RefreshEvent;
import savetheenvironment.refresh.SwappableDataSourceConfiguration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Josh Long
 */
@ManagedResource
public class JmxDataSourceConfigurationEndpoint
        implements ApplicationContextAware {

    private final DataSource dataSource;
    private ConfigurableEnvironment environment;
    private ApplicationContext applicationContext;


    public JmxDataSourceConfigurationEndpoint(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        Assert.isTrue(this.applicationContext.getEnvironment() instanceof ConfigurableEnvironment,
                "you must use an implementation of a ConfigurableEnvironment for this class.");
        this.environment = (ConfigurableEnvironment) this.applicationContext.getEnvironment();
    }

    @ManagedOperation
    public void updateDataSource(String user, String pw, String url) {

        Map<String, Object> propertiesForDataSource = new HashMap<String, Object>();
        propertiesForDataSource.put(SwappableDataSourceConfiguration.DB_URL, url);
        propertiesForDataSource.put(SwappableDataSourceConfiguration.DB_PASSWORD, pw);
        propertiesForDataSource.put(SwappableDataSourceConfiguration.DB_USER, user);

        MapPropertySource mapPropertySource = new MapPropertySource("databaseConfiguration", propertiesForDataSource);

        this.environment.getPropertySources().addFirst(mapPropertySource);
        applicationContext.publishEvent(new RefreshEvent( this.dataSource));
    }

}
