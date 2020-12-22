package nl.rug.eai.imagestream.commons.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AnnotatedImage {
    private String identifier;
    private String identifiedObject;
    private String annotatedImage;
}
