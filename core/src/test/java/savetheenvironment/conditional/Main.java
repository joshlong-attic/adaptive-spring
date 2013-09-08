package savetheenvironment.conditional;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.util.Collection;


public class Main {
    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(ServiceConfiguration.class);

        Collection<DataSource> coll = applicationContext.getBeansOfType(DataSource.class).values();
        System.out.println("Found " + coll.size() + " DataSource(s).");


    }
}


