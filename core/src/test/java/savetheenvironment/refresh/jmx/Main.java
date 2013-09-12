package savetheenvironment.refresh.jmx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) throws Throwable {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                JmxSwappableDataSourceConfiguration.class);

        while (true)
            Thread.sleep(1000);


    }
}
