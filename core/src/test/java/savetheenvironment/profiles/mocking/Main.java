package savetheenvironment.profiles.mocking;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    static AnnotationConfigApplicationContext runWithApplicationContext() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();

        ac.getEnvironment().setActiveProfiles(ServiceConfiguration.PROFILE_VIDEO_YOUTUBE);

        ac.register(ServiceConfiguration.class);
        ac.refresh();
        return ac;
    }

    public static void main(String[] arrrImAPirate) throws Throwable {
        ApplicationContext applicationContext = runWithApplicationContext();

        //showEnvironment(applicationContext);

        //showPropertySource(applicationContext);

       showVideos(applicationContext);
    }

    private static void showPropertySource(ApplicationContext applicationContext) {

        System.out.println();
        System.out.println("************ Property Source ***********");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("db.username", "scott");
        map.put("db.password", "tiger");


        MapPropertySource mapPropertySource = new MapPropertySource("dbConfig", map);

        ((StandardEnvironment) applicationContext.getEnvironment()).getPropertySources().addFirst(mapPropertySource);

        System.out.println("DB Username: " + applicationContext.getEnvironment().getProperty("db.username"));
        System.out.println("DB Password: " + applicationContext.getEnvironment().getProperty("db.password"));

        System.out.println();

        System.out.println("DB Url from @PropertySource: " + applicationContext.getEnvironment().getProperty("db.url"));

        System.out.println();
    }

    private static void showVideos(ApplicationContext applicationContext) throws Exception {
        VideoSearch videoSearch = applicationContext.getBean(VideoSearch.class);
        List<String> videoTitles = videoSearch.lookupVideo("Kevin Nilson");

        System.out.println();
        System.out.println("************** VIDEO SEARCH RESULTS - YOUTUBE ************** ");

        for (String title : videoTitles) {
            System.out.println(title);
        }
    }

    private static void showEnvironment(ApplicationContext applicationContext) {
        System.out.println();
        System.out.println("************ Environment ***********");
        System.out.println("User Dir: " + applicationContext.getEnvironment().getProperty("user.dir"));
        System.out.println();
    }
}