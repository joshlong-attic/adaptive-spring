package savetheenvironment.conditionals;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;


public class Main {
    public static void main(String[] arrrImAPirate) throws Throwable {

        ApplicationContext ac = SpringApplication.run(ServiceConfiguration.class);


        while (true)
            Thread.sleep(1000);

    }
}

