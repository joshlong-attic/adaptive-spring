package savetheenvironment.jmx;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import savetheenvironment.CatAdmin;

import javax.management.Notification;

@ManagedResource
public class JmxCatAdmin extends CatAdmin
        implements NotificationPublisherAware {
    private NotificationPublisher notificationPublisher;

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

    @Override
    @ManagedOperation
    public void updateCat(String name) {
        super.updateCat(name);
        this.notificationPublisher.sendNotification(new Notification("catChanged", this, 0));

    }
}
