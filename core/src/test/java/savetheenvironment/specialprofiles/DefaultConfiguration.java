package savetheenvironment.specialprofiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static savetheenvironment.specialprofiles.Main.DEFAULT_PROFILE;


@Configuration
@Profile(DEFAULT_PROFILE)
public class DefaultConfiguration {
    @Bean
    RegistrationMessage registrationMessage() {
        return new RegistrationMessage(DEFAULT_PROFILE);
    }
}
