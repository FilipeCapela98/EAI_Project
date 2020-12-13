package nl.rug.eai.imagestream.twitterstreamingservice.twitterapi.model;

import java.util.List;

import lombok.Data;

@Data
public class SearchResultsIncludes {
    private List<TweetMedia> media;
}
