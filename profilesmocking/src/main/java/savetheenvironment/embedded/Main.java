package savetheenvironment.embedded;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import savetheenvironment.embedded.video.VideoSearch;

import java.util.List;

public class Main {




    private static Log log = LogFactory.getLog(Main.class);

    static AnnotationConfigApplicationContext runWithApplicationContext() {
        boolean production = true;
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.getEnvironment().setActiveProfiles(production ? ServiceConfiguration.PROFILE_VIDEO_YOUTUBE : ServiceConfiguration.PROFILE_VIDEO_MOCK);
        ac.register(ServiceConfiguration.class);
        ac.refresh();
        return ac;
    }



    public static void main(String[] arrrImAPirate) throws Throwable {
        AnnotationConfigApplicationContext applicationContext = runWithApplicationContext();


       VideoSearch videoSearch = applicationContext.getBean(VideoSearch.class);
        List<String> videoTitles = videoSearch.lookupVideo("Kevin Nilson");

        System.out.println("************** MAIN VIDEO SEARCH RESULTS ************** ") ;

        for(String title:videoTitles){
            System.out.println(title);
        }


    }


}