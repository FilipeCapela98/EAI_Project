package nl.rug.eai.imagestream.twitterstreamingservice.controller;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.twitterstreamingservice.twitterapi.TwitterStreamingRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class StreamController {

    @Autowired
    TwitterStreamingRunner twitterStreamingRunner;

    private final List<String> runningProcessors;

    public StreamController() {
        this.runningProcessors = new ArrayList<>();
    }

    public synchronized void start(String topic) {
        log.info("Received request to start stream on: " + topic);

        if(isRunning(topic)) {
            log.warn("Stream on topic " + topic + " is already running. Not starting another runner.");
            return;
        }

        log.info("Starting ingestion on topic " + topic);
        runningProcessors.add(topic);
        twitterStreamingRunner.run(topic);
    }

    public synchronized void stop(String topic) {
        System.out.println("Received request to stop stream on: " + topic);

        if(!isRunning(topic)) {
            log.warn("Tried to stop stream on topic " + topic + ", but there was no processor running.");
        }

        log.info("Stopping ingestion from Twitter on topic: " + topic);
        runningProcessors.remove(topic);
    }

    public boolean isRunning(String topic) {
        return this.runningProcessors.contains(topic);
    }

}
