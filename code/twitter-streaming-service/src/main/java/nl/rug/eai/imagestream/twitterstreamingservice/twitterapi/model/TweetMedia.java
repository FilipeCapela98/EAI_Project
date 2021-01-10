package nl.rug.eai.imagestream.twitterstreamingservice.twitterapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data @JsonIgnoreProperties(ignoreUnknown = true)
public class TweetMedia {
    private String media_key;
    private String type;
    private String url;
    private String preview_image_url;
}
