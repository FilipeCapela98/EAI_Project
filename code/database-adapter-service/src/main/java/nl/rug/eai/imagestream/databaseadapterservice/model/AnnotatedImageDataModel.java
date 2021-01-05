package nl.rug.eai.imagestream.databaseadapterservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AnnotatedImageDataModel {

    @Id
    private String identifier;
    private String tag;
    private String identifiedObject;
    private String annotatedImage;
}
