package nl.rug.eai.imagestream.databaseadapterservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "ImageJSON")
public class FilteredTweetImage {
    private String identifier;
    private String tag;
    private String identifiedObject;
    private String annotatedImage;
}
