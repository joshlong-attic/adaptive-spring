package savetheenvironment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import savetheenvironment.embedded.ServiceConfiguration;
import savetheenvironment.embedded.video.VideoSearch;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServiceConfiguration.class})
@ActiveProfiles(ServiceConfiguration.PROFILE_VIDEO_MOCK)


public class MainTest {

    @Autowired
    private VideoSearch videoSearch;

    @Test
    public void testVideoSearch() throws Exception {
        List<String> videoTitles = videoSearch.lookupVideo("Kevin Nilson");

        System.out.println("************** START - TEST VIDEO SEARCH RESULTS ************** ");
        for (String title : videoTitles) {
            System.out.println(title);
        }
        System.out.println("************** END - TEST VIDEO SEARCH RESULTS ************** ");
    }
}
