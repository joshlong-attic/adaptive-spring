package savetheenvironment.profiles.mocking;

import java.util.List;

public interface VideoSearch {
    List<String> lookupVideo(String searchTerm) throws Exception;
}
