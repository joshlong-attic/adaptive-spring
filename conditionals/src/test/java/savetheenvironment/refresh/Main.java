package savetheenvironment.refresh;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Provider;

public class Main {

    static public void main(String[] args) throws Throwable {
        AnnotationConfigApplicationContext annotationConfigApplicationContext
                = new AnnotationConfigApplicationContext(CatConfiguration.class);

        Cat cat = annotationConfigApplicationContext.getBean(Cat.class);
        CatAdmin catAdmin = annotationConfigApplicationContext.getBean(CatAdmin.class);

        catAdmin.updateCat("Mikey");

        RefreshableBeanFactoryBean.Refreshable  refreshableBean
                = (RefreshableBeanFactoryBean.Refreshable )   cat;

        refreshableBean.refresh();

        System.out.println("the cat's name is " + cat.getName());

        catAdmin.updateCat("Josh");
        refreshableBean.refresh();

        System.out.println("the cat's name is " + cat.getName());

    }
}
