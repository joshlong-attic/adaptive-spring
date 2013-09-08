package savetheenvironment.refresh.jmx;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class Main {
    public static void main(String[] args) throws Throwable {
        ApplicationContext applicationContext = SpringApplication.run(
                JmxSwappableDataSourceConfiguration.class);

        while (true)
            Thread.sleep(1000);


    }
}
