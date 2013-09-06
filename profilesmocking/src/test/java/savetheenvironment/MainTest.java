package savetheenvironment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import savetheenvironment.embedded.Main;
import savetheenvironment.embedded.ServiceConfiguration;

import java.util.List;


//TODO load spring context and call main.videoSearch.lookupVideo("Kevin Nilson");
@RunWith(JUnit4.class)
public class MainTest {

    private static Log log = LogFactory.getLog(MainTest.class);

    @Test
    public void testVideoSearch() throws Exception {
        boolean production = false;
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.getEnvironment().setActiveProfiles(production ? ServiceConfiguration.PROFILE_VIDEO_YOUTUBE : ServiceConfiguration.PROFILE_VIDEO_MOCK);
        ac.register(ServiceConfiguration.class);
        ac.refresh();
        Main main = ac.getBean(Main.class);


        List<String> videoTitles = main.getVideoSearch().lookupVideo("Kevin Nilson");

        System.out.println("************** TEST VIDEO SEARCH RESULTS ************** ") ;
        for (String title : videoTitles) {
            System.out.println(title);
        }
    }
}
