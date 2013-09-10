package savetheenvironment.profiles.mocking;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {

    static AnnotationConfigApplicationContext runWithApplicationContext() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();

        ac.getEnvironment().setActiveProfiles(ServiceConfiguration.PROFILE_VIDEO_YOUTUBE );

        ac.register(ServiceConfiguration.class);
        ac.refresh();
        return ac;
    }

    public static void main(String[] arrrImAPirate) throws Throwable {
        ApplicationContext applicationContext = runWithApplicationContext();
        VideoSearch videoSearch = applicationContext.getBean(VideoSearch.class);
        List<String> videoTitles = videoSearch.lookupVideo("Kevin Nilson");

        System.out.println();
        System.out.println("************** VIDEO SEARCH RESULTS - YOUTUBE ************** ");

        for (String title : videoTitles) {
            System.out.println(title);
        }
    }
}