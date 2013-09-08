package savetheenvironment.conditional;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;

/**
 * there has to be a better place for this.
 */
public abstract  class DataSourceConfigurationKeyGenerator {

    public static String urlEnvironmentKey(String r) {
        return key(r, "url");
    }

    public static String userEnvironmentKey(String r) {
        return key(r, "user");
    }

    public static String passwordEnvironmentKey(String r) {
        return key(r, "password");
    }

    public static String driverClassEnvironmentKey(String r) {
        return key(r, "driverClass");
    }

    public static String key(String root, String end) {
        return root + "." + end;
    }

    public static DataSource dataSourceFromConfigurationProperties(String root, Environment e) {
        try {
            String user = e.getProperty(userEnvironmentKey(root)),
                    pw = e.getProperty(passwordEnvironmentKey(root)),
                    url = e.getProperty(urlEnvironmentKey(root));
            Class<? extends Driver> driverClass = e.getPropertyAsClass(driverClassEnvironmentKey(root), Driver.class);
            Driver actualDriverInstance = driverClass.newInstance();
            return new SimpleDriverDataSource(actualDriverInstance, url, user, pw);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
