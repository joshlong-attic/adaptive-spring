package savetheenvironment.refresh;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.ReflectionUtils;

import javax.inject.Provider;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Josh Long
 */
public class RefreshableBeanFactoryBean<T> implements FactoryBean<T> {

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
        this.atomicReference.set(this.provider.get());
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
        listOfClasses.add(Refreshable.class);
        listOfClasses.addAll(Arrays.asList(t.getClass().getInterfaces()));

        pf.setInterfaces(listOfClasses.toArray(new Class[listOfClasses.size()]));
        pf.addAdvice(new MethodInterceptor() {

            private final Method refreshMethod =
                    ReflectionUtils.findMethod(Refreshable.class, "refresh");

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

    public static interface Refreshable<T> {
        T refresh();
    }
}
