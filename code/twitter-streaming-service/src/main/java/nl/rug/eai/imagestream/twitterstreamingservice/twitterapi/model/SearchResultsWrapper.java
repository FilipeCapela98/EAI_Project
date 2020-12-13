package nl.rug.eai.imagestream.twitterstreamingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Data @JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {
    // {"data":{"id":"1337295154017955843","text":"RT @giantcat9: BREAKING Rt @Reuters\nCats announce annual winter offensive against Christmas villages despite ongoing preparations for anothâ€¦"},"matching_rules":[{"id":1337295176289513472,"tag":"cat images"}]}
     private TwitterData data;
     private TwitterIncludes includes;
     private List<TwitterMatchingRules> matching_rules;

    private String color;
    private String type;
}


