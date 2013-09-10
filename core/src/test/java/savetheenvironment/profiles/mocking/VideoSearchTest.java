package savetheenvironment.profiles.mocking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.List;


@ContextConfiguration(classes = {ServiceConfiguration.class})
@ActiveProfiles(ServiceConfiguration.PROFILE_VIDEO_MOCK)
@RunWith(SpringJUnit4ClassRunner.class)
public class VideoSearchTest {

    @Inject
    private VideoSearch videoSearch;

    @Test
    public void testVideoSearch() throws Exception {
        Assert.notNull(this.videoSearch, "the videoSearch must not be null.");

        List<String> videoTitles = videoSearch.lookupVideo("Kevin Nilson");

        System.out.println();
        System.out.println("************** VIDEO SEARCH RESULTS - MOCK ************** ");

        for (String title : videoTitles) {
            System.out.println(title);
        }


        org.junit.Assert.assertEquals(2, videoSearch.lookupVideo("Kevin Nilson").size());
    }

}
