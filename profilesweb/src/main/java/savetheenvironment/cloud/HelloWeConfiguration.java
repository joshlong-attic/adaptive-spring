package savetheenvironment.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;


interface HelloService {
    public String hello();
}

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class HelloWeConfiguration {
    public static void main(String[] args) throws Throwable {

        ApplicationContextInitializer<AnnotationConfigWebApplicationContext> aci =
                new ApplicationContextInitializer<AnnotationConfigWebApplicationContext>() {

                    public void initialize(AnnotationConfigWebApplicationContext appContext) {

                        String profileSystemProperty = System.getProperty("profile");

                        System.out.println("********** profileSystemProperty=" + profileSystemProperty);

                        String profile;

                        if ("bacon".equalsIgnoreCase(profileSystemProperty))
                            profile = "bacon";
                        else if ("cat".equalsIgnoreCase(profileSystemProperty))
                            profile = "cat";
                        else if ("kitten".equalsIgnoreCase(profileSystemProperty))
                            profile = "kitten";
                        else
                            profile = "lost";


                        System.out.println("********** profile=" + profile);


                        appContext.getEnvironment().setActiveProfiles(profile);
                        appContext.refresh();

                    }
                };

        SpringApplication springApplication = new SpringApplication();
        springApplication.addInitializers(aci);
        springApplication.run(HelloWeConfiguration.class);


    }
}

@Controller
class CloudController {
    private HelloService helloService;

    @Autowired
    CloudController(HelloService hs) {
        this.helloService = hs;
    }

    @RequestMapping("/")
    @ResponseBody
    String renderValue() {
        return helloService.hello();
    }

}

@Profile("bacon")
@Service
class HelloBaconService implements HelloService {
    public String hello() {
        return "bacon is great";
    }
}

@Profile("cat")
@Service
class HelloCatService implements HelloService {
    public String hello() {
        return "Meow";
    }
}

@Profile("kitten")
@Service
class HelloKittenService implements HelloService {
    public String hello() {
        return "Purr";
    }
}

@Profile(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME)
@Service
class HelloLostService implements HelloService {
    @Override
    public String hello() {
        return "I am lost";
    }
}
