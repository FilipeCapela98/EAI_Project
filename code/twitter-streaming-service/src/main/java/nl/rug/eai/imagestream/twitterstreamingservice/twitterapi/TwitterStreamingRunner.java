package nl.rug.eai.imagestream.twitterstreamingservice.twitterapi;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.twitterstreamingservice.controller.StreamController;
import nl.rug.eai.imagestream.twitterstreamingservice.twitterapi.model.TweetMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TwitterStreamingRunner {

    @Autowired
    StreamController streamController;

    @Autowired
    TwitterStreamingUtil twitterStreamingUtil;

    @Async
    public void run(String topic) {
        // TODO: prevent duplicates
        while(streamController.isRunning(topic)) {
            // TODO: Send heartbeat

            List<TweetMedia> tweetMediaList = twitterStreamingUtil.fetchNewImages(topic);
            tweetMediaList.forEach(tml -> log.info(tml.toString()));

            // TODO: put in queue


            try {
                //noinspection BusyWait
                Thread.sleep(15 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
