package savetheenvironment.specialprofiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static savetheenvironment.specialprofiles.Main.QA_PROFILE;

@Configuration
@Profile(QA_PROFILE)
public class QaConfiguration {
    /**
     * We need to be sure to rename this because - as often as not - the
     * bean will get masked by the bean defined in {@link savetheenvironment.specialprofiles.PlainConfiguration the plain configuration class}.
     */
    @Bean
    RegistrationMessage qaRegistrationName() {
        return new RegistrationMessage(QA_PROFILE);
    }
}
