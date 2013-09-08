package savetheenvironment.conditional;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;


public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ServiceConfiguration.class);
        int sizeOfDataSources = applicationContext.getBeansOfType(DataSource.class).size();
        System.out.println("Found " + sizeOfDataSources + " DataSource(s).");
    }
}


