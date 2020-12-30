package nl.rug.eai.imagestream.imagepreprocessingservice.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.FilteredTweetImage;
import nl.rug.eai.imagestream.commons.model.TweetImage;
import nl.rug.eai.imagestream.imagepreprocessingservice.gateway.senders.PreprocessImageDataSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ImagePreprocessingRunner {

    @Autowired
    PreprocessImageDataSender filteredImageDataSender;

    @Value("${rate-limit-seconds-per-topic}")
    private int secondsBetweenImage;

    private final Map<String, LocalDateTime> lastProcessedTimes = new HashMap<>();

    public void run(TweetImage tweetImage) {

        // Drop if rate limited
        if(isRateLimited(tweetImage))
            return;

        // Process the images
        FilteredTweetImage filteredImage = getFilteredImage(tweetImage);

        // The results should be put in the pipeline queue for further processing
        forwardAnnotatedImage(filteredImage);
    }

    private boolean isRateLimited(TweetImage tweetImage) {
        // It is rate limited.
        if(lastProcessedTimes.containsKey(tweetImage.getTag())
            && ChronoUnit.SECONDS.between(
                    lastProcessedTimes.get(tweetImage.getTag()), LocalDateTime.now()
        ) <= secondsBetweenImage) {
            log.warn("Image processing request has been rate limited and dropped on topic: " + tweetImage.getTag());
            return true;
        }

        // It is not rate limited
        lastProcessedTimes.put(tweetImage.getTag(), LocalDateTime.now());
        return false;
    }

    private FilteredTweetImage getFilteredImage(TweetImage tweetImage) {
        return tweetImage.getUrl().matches(".*(png|jpeg|jpg|format=jpg|format=png|format=jpeg).*")
                ? new FilteredTweetImage(
                        tweetImage.getIdentifier(),
                tweetImage.getType(),
                tweetImage.getUrl(),
                tweetImage.getTag()
        ) : null;
    }

    private void forwardAnnotatedImage(FilteredTweetImage filteredImage) {
        // Forward the filtered image
        if (filteredImage != null) {
            filteredImageDataSender.send(filteredImage);
        } else {
            log.info("Image discarded");
        }
    }
}
