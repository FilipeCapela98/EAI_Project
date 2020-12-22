package nl.rug.eai.imagestream.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ImageCommand {

    /**
     * Such as the hashtag
     */
    private String topic;

}
