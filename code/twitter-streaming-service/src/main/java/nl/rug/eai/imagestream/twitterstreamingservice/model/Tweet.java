package nl.rug.eai.imagestream.twitterstreamingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication @Data @JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {
    // {"data":{"id":"1337295154017955843","text":"RT @giantcat9: BREAKING Rt @Reuters\nCats announce annual winter offensive against Christmas villages despite ongoing preparations for anothâ€¦"},"matching_rules":[{"id":1337295176289513472,"tag":"cat images"}]}
     private TwitterData data;
     private TwitterIncludes includes;
     private List<TwitterMatchingRules> matching_rules;

    private String color;
    private String type;

    public static void main(String[] args) {
        String line = "{\"data\":{\"attachments\":{\"media_keys\":[\"3_1336928965101928449\",\"3_1336928965110374400\"]},\"id\":\"1337293300030558210\",\"text\":\"RT @SHUU_4CHAENG: â€œadopt, donâ€™t shop.â€�\\nRosie really loves dogs.\\nu lucky HANK. u have such a happy and lucky tail. https://t.co/qJxqKF10gG\"},\"includes\":{\"media\":[{\"media_key\":\"3_1336928965101928449\",\"type\":\"photo\",\"url\":\"https://pbs.twimg.com/media/Eo25z3bUwAEHslQ.jpg\"},{\"media_key\":\"3_1336928965110374400\",\"type\":\"photo\",\"url\":\"https://pbs.twimg.com/media/Eo25z3dVoAAOxFW.jpg\"}]},\"matching_rules\":[{\"id\":1337293287166603300,\"tag\":\"dog images\"}]}";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            Tweet tweet = objectMapper.readValue(line, Tweet.class);
        } catch (Exception je) {
            System.out.println("Failed");
        }
    }


}


