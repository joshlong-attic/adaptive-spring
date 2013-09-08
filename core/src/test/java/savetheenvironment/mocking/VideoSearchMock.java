package savetheenvironment.mocking;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Profile({ServiceConfiguration.PROFILE_VIDEO_MOCK})
@Service
public class VideoSearchMock implements VideoSearch {
    @Override
    public List<String> lookupVideo(String searchTerm) throws Exception {
        List<String> results=new ArrayList<String>();
        results.add("HTML5 Testing in All Browsers with Java");
        results.add("GTUG - Interview at Google I/O 2009");
        return results;
    }
}
