package savetheenvironment.refresh;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import savetheenvironment.Cat;
import savetheenvironment.CatAdmin;

public class Main {

    static public void main(String[] args) throws Throwable {
        AnnotationConfigApplicationContext annotationConfigApplicationContext
                = new AnnotationConfigApplicationContext(CatConfiguration.class);

        Cat cat = annotationConfigApplicationContext.getBean(Cat.class);
        CatAdmin catAdmin = annotationConfigApplicationContext.getBean(CatAdmin.class);

        catAdmin.updateCat("Spot");

        RefreshableBeanFactoryBean.Refreshable refreshableBean
                = (RefreshableBeanFactoryBean.Refreshable) cat;

        refreshableBean.refresh();

        System.out.println("the cat's name is " + cat.getName());

        catAdmin.updateCat("Whiskers");
        refreshableBean.refresh();

        System.out.println("the cat's name is " + cat.getName());

        while (true)
            Thread.sleep(1000);

    }
}
