package savetheenvironment.profiles.mocking;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@ComponentScan
@Configuration
public class ServiceConfiguration {
    public static final String PROFILE_VIDEO_YOUTUBE = "PROFILE_VIDEO_YOUTUBE";
    public static final String PROFILE_VIDEO_MOCK = "PROFILE_VIDEO_MOCK";
}