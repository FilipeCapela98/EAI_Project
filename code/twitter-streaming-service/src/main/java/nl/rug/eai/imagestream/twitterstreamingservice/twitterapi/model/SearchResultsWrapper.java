package nl.rug.eai.imagestream.twitterstreamingservice.twitterapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data @JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultsWrapper {
     private SearchResultsIncludes includes;
}


