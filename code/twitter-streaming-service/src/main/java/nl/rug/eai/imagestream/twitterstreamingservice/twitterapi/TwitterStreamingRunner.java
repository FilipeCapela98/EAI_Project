package nl.rug.eai.imagestream.twitterstreamingservice.twitterapi;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.StreamProducerHeartbeatEvent;
import nl.rug.eai.imagestream.twitterstreamingservice.controller.StreamController;
import nl.rug.eai.imagestream.twitterstreamingservice.gateway.senders.StreamProducerHeartbeatEventSender;
import nl.rug.eai.imagestream.twitterstreamingservice.twitterapi.model.TweetMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TwitterStreamingRunner {

    @Autowired
    StreamController streamController;

    @Autowired
    TwitterStreamingUtil twitterStreamingUtil;

    @Autowired
    StreamProducerHeartbeatEventSender streamProducerHeartbeatEventSender;

    @Value("${twitter-streaming-delay-seconds}")
    Integer delaySeconds;

    @Async
    public void run(String topic) {
        // TODO: prevent duplicates
        while(streamController.isRunning(topic)) {
            // Send heartbeat to let manager know we are still processing
            streamProducerHeartbeatEventSender.send(new StreamProducerHeartbeatEvent(
                    topic
            ));

            // Fetch new images from twitter
            LocalDateTime previousUpdate = streamController.getLastProcessTime(topic);
            streamController.updateLastProcessTime(topic);
            List<TweetMedia> tweetMediaList = twitterStreamingUtil.fetchNewImages(topic, previousUpdate);
            tweetMediaList.forEach(tml -> log.info(tml.toString()));

            // The results should be put in the pipeline queue for further processing


            try {
                //noinspection BusyWait
                Thread.sleep(delaySeconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
