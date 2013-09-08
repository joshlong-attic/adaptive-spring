package savetheenvironment.specialprofiles;

import org.springframework.context.annotation.Bean;

public class PlainConfiguration {
    /*
     * this one is <EM>identical</EM> to the one
     * in <CODE>default</CODE>, so it is never registered. Odd.
     */
    @Bean
    RegistrationMessage registrationMessage() {
        return new RegistrationMessage("plain");
    }

    /**
     * Because the method name is different here, the bean itself is
     * registered with a different bean ID. No problem.
     */
    @Bean
    RegistrationMessage registrationMessageWithADifferentName() {
        return new RegistrationMessage("plain with a different name");
    }
}
