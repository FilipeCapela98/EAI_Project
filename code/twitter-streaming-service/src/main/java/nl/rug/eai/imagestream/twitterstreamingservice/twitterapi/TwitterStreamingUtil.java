package nl.rug.eai.imagestream.twitterstreamingservice.twitterapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.twitterstreamingservice.twitterapi.model.SearchResultsWrapper;
import nl.rug.eai.imagestream.twitterstreamingservice.twitterapi.model.TweetMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public List<TweetMedia> fetchNewImages(String topic, LocalDateTime lastProcessTime) {
        SearchResultsWrapper searchResultsWrapper = fetchNewImagesRequest(topic, lastProcessTime);

        if(searchResultsWrapper.getIncludes() == null) {
            log.warn("Response returned from Twitter API does not have includes at endpoint " + getEndpoint(topic, lastProcessTime));
            return new ArrayList<>();
        }

        if(searchResultsWrapper.getIncludes().getMedia() == null) {
            log.warn("Response returned from Twitter API does not have media at endpoint " + getEndpoint(topic, lastProcessTime));
            return new ArrayList<>();
        }

        return searchResultsWrapper.getIncludes().getMedia().stream()
                .filter(tweetMedia -> (tweetMedia.getPreview_image_url() != null || tweetMedia.getUrl() != null))
                .collect(Collectors.toList());
    }

    public SearchResultsWrapper fetchNewImagesRequest(String topic, LocalDateTime lastProcessTime) {
        // Set up the endpoint
        String endpoint = getEndpoint(topic, lastProcessTime);
        HttpEntity<?> request = new HttpEntity<>(getHeaders());

        ResponseEntity<SearchResultsWrapper> responseEntity;
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        try {
            responseEntity = restTemplate.exchange(
                    endpoint,
                    HttpMethod.GET,
                    request,
                    SearchResultsWrapper.class
            );
        } catch (Exception e) {
            log.error("Something went wrong during a request to: " + endpoint);
            throw e;
        }

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

    private String getEndpoint(String topic, LocalDateTime lastProcessTime) {
        String query = (topic + " has:images -is:retweet");

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("https://api.twitter.com/2/tweets/search/recent")
                .queryParam("expansions", "attachments.media_keys")
                .queryParam("media.fields", "url,preview_image_url");
                //.queryParam("query", query);

        // If this is not the first request, we need to add a start_time filter
        if(lastProcessTime != null && !lastProcessTime.equals(LocalDateTime.MIN)) {
            uriComponentsBuilder = uriComponentsBuilder.queryParam("start_time",
                    lastProcessTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z");
        }

        return uriComponentsBuilder.toUriString()
                + "&query=" + query;
    }

}