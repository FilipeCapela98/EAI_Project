package nl.rug.eai.imagestream.twitterstreamingservice.twitterapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.twitterstreamingservice.twitterapi.model.SearchResultsWrapper;
import nl.rug.eai.imagestream.twitterstreamingservice.twitterapi.model.TweetMedia;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/*
 * Sample code to demonstrate the use of the Filtered Stream endpoint
 * */
@Service
@Slf4j
public class TwitterStreamingUtil {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestTemplate restTemplate;

    private String bearerToken;

    public TwitterStreamingUtil() {
        this.bearerToken = "AAAAAAAAAAAAAAAAAAAAAFR3JwEAAAAAEquyLtEZhuvH7jVLCzrba2Wearo%3DhIcSZNPXNWb3ZOrf2CcexWQbQgNPzhp7MInFZNsjihbzY9KRFe";
    }

    public List<TweetMedia> fetchNewImages(String topic) {
        SearchResultsWrapper searchResultsWrapper = fetchNewImagesRequest(topic);

        if(searchResultsWrapper.getIncludes() == null) {
            log.warn("Response returned from Twitter API does not have includes at endpoint " + getEndpoint(topic));
            return new ArrayList<>();
        }

        if(searchResultsWrapper.getIncludes().getMedia() == null) {
            log.warn("Response returned from Twitter API does not have media at endpoint " + getEndpoint(topic));
            return new ArrayList<>();
        }

        return searchResultsWrapper.getIncludes().getMedia().stream()
                .filter(tweetMedia -> (tweetMedia.getPreview_image_url() != null || tweetMedia.getUrl() != null))
                .collect(Collectors.toList());
    }

    public SearchResultsWrapper fetchNewImagesRequest(String topic) {
        // Set up the endpoint
        String endpoint = getEndpoint(topic);
        HttpEntity<?> request = new HttpEntity<>(getHeaders());

        ResponseEntity<SearchResultsWrapper> responseEntity = restTemplate.exchange(
                endpoint,
                HttpMethod.GET,
                request,
                SearchResultsWrapper.class
        );

        log.error(responseEntity.toString());

        if(!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.error("Something went wrong during a request to: " + endpoint);
            log.error("Failed response: " + responseEntity.getBody());
        } else {
            log.info("GET-request to " + endpoint + " was performed successfully.");
        }

        return responseEntity.getBody();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(this.bearerToken);
        return httpHeaders;
    }

    private String getEndpoint(String topic) {
        String query = topic + " has:images -is:retweet";
        return "https://api.twitter.com/2/tweets/search/recent?expansions=attachments.media_keys" +
                "&media.fields=url,preview_image_url&query=" + topic + " has:images -is:retweet";
    }

}