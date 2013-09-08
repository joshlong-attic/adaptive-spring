package savetheenvironment.specialprofiles;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Collection;

public class Main {
    public static final String DEFAULT_PROFILE = "default";
    public static final String QA_PROFILE = "qa";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        ConfigurableEnvironment configurableEnvironment = applicationContext.getEnvironment();

        configurableEnvironment.setActiveProfiles(QA_PROFILE);

        applicationContext.register(BaseConfiguration.class);
        applicationContext.refresh();

        Collection<RegistrationMessage> messages =
                applicationContext.getBeansOfType(RegistrationMessage.class).values();
        System.out.println("the registration messages: " + messages);

    }


}

