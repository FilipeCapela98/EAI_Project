package nl.rug.eai.imagestream.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class StartCommand {

    /**
     * Such as the hashtag
     */
    private String topic;

}
