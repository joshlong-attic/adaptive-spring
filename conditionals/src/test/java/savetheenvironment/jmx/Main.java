package savetheenvironment.jmx;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import savetheenvironment.Cat;
import savetheenvironment.refresh.RefreshableFactoryBean;

public class Main {

    static public void main(String[] args) throws Throwable {
        AnnotationConfigApplicationContext annotationConfigApplicationContext
                = new AnnotationConfigApplicationContext( JmxCatConfiguration.class);

        Cat cat = annotationConfigApplicationContext.getBean(Cat.class);
        JmxCatAdmin catAdmin = annotationConfigApplicationContext.getBean(JmxCatAdmin.class);

        catAdmin.updateCat("Spot");

        RefreshableFactoryBean.Refreshable refreshableBean
                = (RefreshableFactoryBean.Refreshable) cat;

        refreshableBean.refresh();

        System.out.println("the cat's name is " + cat.getName());

        catAdmin.updateCat("Whiskers");
        refreshableBean.refresh();

        System.out.println("the cat's name is " + cat.getName());

        while (true)
            Thread.sleep(1000);

    }
}
