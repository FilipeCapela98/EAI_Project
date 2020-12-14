package nl.rug.eai.imagestream.twitterstreamingservice.twitterapi;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.StreamProducerHeartbeatEvent;
import nl.rug.eai.imagestream.commons.model.TweetImage;
import nl.rug.eai.imagestream.twitterstreamingservice.controller.StreamController;
import nl.rug.eai.imagestream.twitterstreamingservice.gateway.senders.ImageDataSender;
import nl.rug.eai.imagestream.twitterstreamingservice.gateway.senders.StreamProducerHeartbeatEventSender;
import nl.rug.eai.imagestream.twitterstreamingservice.twitterapi.model.TweetMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TwitterStreamingRunner {

    @Autowired
    StreamController streamController;

    @Autowired
    TwitterStreamingUtil twitterStreamingUtil;

    @Autowired
    StreamProducerHeartbeatEventSender streamProducerHeartbeatEventSender;

    @Autowired
    ImageDataSender imageDataSender;

    @Value("${twitter-streaming-delay-seconds}")
    Integer delaySeconds;

    @Async
    public void run(String topic) {
        while(streamController.isRunning(topic)) {
            // Send heartbeat to let manager know we are still processing
            streamProducerHeartbeatEventSender.send(new StreamProducerHeartbeatEvent(
                    topic
            ));

            // Fetch new images from twitter
            List<TweetMedia> tweetMediaList = getTweetMedia(topic);

            // The results should be put in the pipeline queue for further processing
            tweetMediaList.forEach(this::forwardTweetMedia);

            try {
                //noinspection BusyWait
                Thread.sleep(delaySeconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<TweetMedia> getTweetMedia(String topic) {
        LocalDateTime previousUpdate = streamController.getLastProcessTime(topic);
        streamController.updateLastProcessTime(topic);
        return twitterStreamingUtil.fetchNewImages(topic, previousUpdate);
    }

    private void forwardTweetMedia(TweetMedia tweetMedia) {
        // 2 fields can be a URL. Grab the one that is there.
        String url = tweetMedia.getPreview_image_url();
        if (tweetMedia.getUrl() != null)
            url = tweetMedia.getUrl();

        // Forward it
        if (url != null && url.length() > 0) {
            imageDataSender.send(new TweetImage(
                    tweetMedia.getMedia_key(),
                    tweetMedia.getType(),
                    url
            ));
        }
    }
}
