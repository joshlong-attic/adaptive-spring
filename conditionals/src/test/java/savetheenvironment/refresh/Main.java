package savetheenvironment.refresh;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SwappableDataSourceConfiguration.class);

        DataSource dataSource = applicationContext.getBean(DataSource.class);

        CustomerService customerService = applicationContext.getBean(CustomerService.class);

        // only has odd records
        updateDataSourceConnectionParams(applicationContext, dataSource , "sa", "", "jdbc:h2:tcp://localhost/~/crm_a", org.h2.Driver.class);
        workWithService(customerService);

        // only has even records
        updateDataSourceConnectionParams(applicationContext, dataSource , "sa", "", "jdbc:h2:tcp://localhost/~/crm_b", org.h2.Driver.class);
        workWithService(customerService);
    }

    public static void workWithService(CustomerService customerService) {
        System.out.println( "----------------------------------------");
        List<Customer> customers = customerService.loadAllCustomers();
        for (Customer customer : customers)
            System.out.println(customer.toString());
    }

    public static void updateDataSourceConnectionParams(ApplicationContext ac, DataSource dataSource, String user, String pw, String url, Class<? extends Driver> driverClass) {
        if (ac.getEnvironment() instanceof ConfigurableEnvironment) {
            ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) ac.getEnvironment();
            Map<String, Object> props = new HashMap<String, Object>();

            props.put(SwappableDataSourceConfiguration.DB_URL, url);
            props.put(SwappableDataSourceConfiguration.DB_PASSWORD, pw);
            props.put(SwappableDataSourceConfiguration.DB_DRIVER_CLASS, driverClass);
            props.put(SwappableDataSourceConfiguration.DB_USER, user);
            configurableEnvironment.getPropertySources().addFirst(new MapPropertySource("databaseConfiguration", props));

            ac.publishEvent(new RefreshEvent(dataSource));
        }
    }


}


