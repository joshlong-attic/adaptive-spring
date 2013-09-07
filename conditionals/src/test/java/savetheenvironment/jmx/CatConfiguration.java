package savetheenvironment.jmx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.support.MBeanServerFactoryBean;
import savetheenvironment.Cat;
import savetheenvironment.CatAdmin;
import savetheenvironment.refresh.RefreshableBeanFactoryBean;

import javax.inject.Provider;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationListener;
import java.util.Collections;

@Configuration
public class CatConfiguration {

    @Bean
    public MBeanExporter mBeanExporter(final MBeanServer mbeanServer,
                                       final CatAdmin catAdmin,
                                       final Cat refreshable) {
        MBeanExporter exporter = new MBeanExporter();
        exporter.setServer(mbeanServer);
        final String catAdminName = "catAdmin";
        exporter.setBeans(Collections.<String, Object>singletonMap("bean:name=" + catAdminName, catAdmin));
        exporter.setNotificationListenerMappings(Collections.<Object, NotificationListener>singletonMap(catAdminName, new NotificationListener() {
            @Override
            public void handleNotification(Notification notification, Object handback) {
                ((RefreshableBeanFactoryBean.Refreshable)refreshable).refresh();
            }
        }));
        return exporter;
    }

    @Bean
    public MBeanServerFactoryBean mBeanServer() {
        MBeanServerFactoryBean serverFactoryBean = new MBeanServerFactoryBean();
        serverFactoryBean.setLocateExistingServerIfPossible(true);
        serverFactoryBean.setRegisterWithFactory(true);
        return serverFactoryBean;
    }

    @Bean
    public JmxCatAdmin catAdmin() {
        return new JmxCatAdmin();
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
