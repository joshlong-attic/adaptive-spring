package savetheenvironment.mocking;

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Profile({ServiceConfiguration.PROFILE_VIDEO_YOUTUBE})
@Service
public class VideoSearchYouTube implements VideoSearch {

    //http://gdata.youtube.com/feeds/api/videos?alt=json&q=Kevin Nilson
    public List<String> lookupVideo(String searchTerm) throws IOException, ServiceException {
        List<String> results = new ArrayList<String>();
        YouTubeService service = new YouTubeService("savetheenvironment");

        YouTubeQuery query = new YouTubeQuery(new URL("http://gdata.youtube.com/feeds/api/videos"));
        query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);


        query.setFullTextQuery(searchTerm);

        query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);

        VideoFeed videoFeed = service.query(query, VideoFeed.class);

        for (VideoEntry videoEntry : videoFeed.getEntries()) {
            results.add(videoEntry.getTitle().getPlainText());
        }

        return results;
    }


}
