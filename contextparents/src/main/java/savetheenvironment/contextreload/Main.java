package savetheenvironment.contextreload;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

public class Main {

    public static void main(String[] args) {

        // create a context with a single bean and obtain a reference to that bean
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AConfiguration.class);
        A original = context.getBean(A.class);

        // create a child context with a single bean and obtain a reference to that bean
        AnnotationConfigApplicationContext childContext = new AnnotationConfigApplicationContext();
        childContext.setParent(context);
        childContext.register(BConfiguration.class);
        childContext.refresh();

        B anotherBean = childContext.getBean(B.class) ;
        Assert.notNull( anotherBean);

        // then, access A (which came to us from the original context)
        A fromParentedContext = childContext.getBean(A.class);
        Assert.isTrue(fromParentedContext == original); // its the same as the one we first took out

        // destroy child context
        childContext.destroy();
        childContext.close();

        // no more B, but A still works...
        Assert.isTrue(context.getBean(A.class) == original);
    }

}

@Configuration
class AConfiguration {
    @Bean
    public A a() {
        return new A();
    }
}

@Configuration
class BConfiguration {
    @Bean
    public B b() {
        return new B();
    }
}

class A {
    A() {
        System.out.println("Hello from A!");
    }
}

class B {
    B() {
        System.out.println("Hello from B!");
    }
}