package savetheenvironment.embedded.video;


import java.util.List;

public interface VideoSearch {
    public List<String> lookupVideo(String searchTerm) throws Exception;
}
