package savetheenvironment.specialprofiles;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({QaConfiguration.class, PlainConfiguration.class, DefaultConfiguration.class})
public class BaseConfiguration {
}
