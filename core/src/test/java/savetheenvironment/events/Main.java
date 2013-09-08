package savetheenvironment.events;


import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

public class Main {


    public static void main(String[] args) throws Throwable {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(EventConfiguration.class);

        while (true)
            Thread.sleep(1000);
    }
}

@Component
class Publisher implements ApplicationEventPublisherAware, ApplicationListener<ReplyMessage> {

    private ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(fixedDelay = 1000L)
    public void sayMarco() {
        this.applicationEventPublisher.publishEvent(new ReplyMessage(this));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void onApplicationEvent(ReplyMessage replyMessage) {
        System.out.println("Received reply message: " + replyMessage.getMessage());
    }
}

@ComponentScan
@Configuration
@EnableScheduling
class EventConfiguration {
    @Bean
    TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }
}

class MessageEvent extends ApplicationEvent {
    private String message;

    public MessageEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

class ReplyMessage extends MessageEvent {
    public ReplyMessage(Object source) {
        super(source, "Polo!");
    }
}

class RequestMessage extends MessageEvent {
    public RequestMessage(Object source) {
        super(source, "Marco!");
    }
}

@Component
class MarcoEventListener implements ApplicationListener<MessageEvent> {
    @Override
    public void onApplicationEvent(MessageEvent messageEvent) {
        System.out.println("Somebody said, '" + messageEvent.getMessage() + "'");
    }
}

@Component
class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("Somebody refreshed the ApplicationContext.");
    }
}
