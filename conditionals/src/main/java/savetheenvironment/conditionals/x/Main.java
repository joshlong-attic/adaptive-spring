package savetheenvironment.conditionals.x;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import javax.inject.Provider;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    static public void main(String[] args) throws Throwable {
        AnnotationConfigApplicationContext annotationConfigApplicationContext
                = new AnnotationConfigApplicationContext(ServiceConfiguration.class);

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

//@EnableMBeanExport
@Configuration
class ServiceConfiguration {

    @Bean
    public CatAdmin catAdmin() {
        CatAdmin catAdmin = new CatAdmin();
        return catAdmin;
    }

    @Bean
    public Cat refreshableCat(final CatAdmin catAdmin) throws Throwable {
        RefreshableBeanFactoryBean<Cat> catRefreshableBeanFactoryBean =
                new RefreshableBeanFactoryBean<Cat>(new Provider<Cat>() {
                    @Override
                    public Cat get() {
                        return catAdmin.getCat();
                    }
                });
        return catRefreshableBeanFactoryBean.getObject();
    }

}

class CatAdmin {

    private String name;

    public Cat getCat() {
        return new Cat(this.name);
    }

    public void updateCat(String name) {
        this.name = name;
    }
}

class Cat {

    private String name = "Josh";

    public Cat() {
    }

    public Cat(String n) {
        this.name = n;
    }

    public String getName() {
        return this.name;
    }
}

class RefreshableBeanFactoryBean<T> implements FactoryBean<T> {

    private final AtomicReference<T> atomicReference = new AtomicReference<T>();
    private Provider<T> provider;

    public RefreshableBeanFactoryBean(Provider<T> provider) {
        this.provider = provider;
    }

    public RefreshableBeanFactoryBean(T initialRef, Provider<T> provider) {
        this.provider = provider;
        this.atomicReference.set(initialRef);
    }

    protected void updateBeanReference() {
        this.atomicReference.set( this.provider.get());
    }

    @Override
    public T getObject() throws Exception {

        if (null == this.atomicReference.get()) {
            updateBeanReference();
        }

        T t = atomicReference.get();

        ProxyFactory pf = new ProxyFactory(t);
        pf.setProxyTargetClass(true);
        List<Class<?>> listOfClasses = new ArrayList<Class<?>>();
        listOfClasses.add(Refreshable .class);
        listOfClasses.addAll(Arrays.asList(t.getClass().getInterfaces()));

        pf.setInterfaces(listOfClasses.toArray(new Class[listOfClasses.size()]));
        pf.addAdvice(new MethodInterceptor() {

            private final Method refreshMethod =
                    ReflectionUtils.findMethod(Refreshable .class, "refresh");

            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                // if someone externally calls refresh, then rebuild the reference
                if (invocation.getMethod().equals(refreshMethod)) {
                    updateBeanReference();
                    return atomicReference.get();
                } else {
                    // otherwise send the request on through to the underlying object unchanged
                    Object ref = atomicReference.get();
                    Method method = invocation.getMethod();
                    return method.invoke(ref, invocation.getArguments());
                }
            }
        });
        Object o = pf.getProxy();
        return (T) o;
    }

    @Override
    public Class<?> getObjectType() {
        return Object.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public static interface Refreshable <T> {
        T refresh();
    }
}
