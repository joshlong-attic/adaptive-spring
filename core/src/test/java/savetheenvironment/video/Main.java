package savetheenvironment.video;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {

    static AnnotationConfigApplicationContext runWithApplicationContext() {
        boolean production =  false ;
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.getEnvironment().setActiveProfiles(production ? ServiceConfiguration.PROFILE_VIDEO_YOUTUBE : ServiceConfiguration.PROFILE_VIDEO_MOCK);
        ac.register(ServiceConfiguration.class);
        ac.refresh();
        return ac;
    }

    public static void main(String[] arrrImAPirate) throws Throwable {
        ApplicationContext applicationContext = runWithApplicationContext();
        VideoSearch videoSearch = applicationContext.getBean(VideoSearch.class);
        List<String> videoTitles = videoSearch.lookupVideo("Kevin Nilson");

        System.out.println("************** MAIN VIDEO SEARCH RESULTS ************** ");

        for (String title : videoTitles) {
            System.out.println(title);
        }
    }
}