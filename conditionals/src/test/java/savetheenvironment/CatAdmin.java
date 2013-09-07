package savetheenvironment;

import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;

import javax.management.Notification;

public class CatAdmin
 implements NotificationPublisherAware
{

    private String name;

    private NotificationPublisher notificationPublisher ;


    public Cat getCat() {
        return new Cat(this.name);
    }


    public void updateCat(String name) {
        this.name = name;
        this. notificationPublisher.sendNotification(new Notification("catChanged", this, 0));

    }
    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.notificationPublisher =  notificationPublisher ;
    }
}
