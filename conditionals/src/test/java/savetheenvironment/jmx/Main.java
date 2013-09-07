package savetheenvironment.jmx;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import savetheenvironment.Cat;
import savetheenvironment.refresh.RefreshableBeanFactoryBean;

public class Main {

    static public void main(String[] args) throws Throwable {
        AnnotationConfigApplicationContext annotationConfigApplicationContext
                = new AnnotationConfigApplicationContext( CatConfiguration.class);

        Cat cat = annotationConfigApplicationContext.getBean(Cat.class);
        JmxCatAdmin catAdmin = annotationConfigApplicationContext.getBean(JmxCatAdmin.class);

        catAdmin.updateCat("Mikey");

        RefreshableBeanFactoryBean.Refreshable refreshableBean
                = (RefreshableBeanFactoryBean.Refreshable) cat;

        refreshableBean.refresh();

        System.out.println("the cat's name is " + cat.getName());

        catAdmin.updateCat("Josh");
        refreshableBean.refresh();

        System.out.println("the cat's name is " + cat.getName());

        while (true)
            Thread.sleep(1000);

    }
}
