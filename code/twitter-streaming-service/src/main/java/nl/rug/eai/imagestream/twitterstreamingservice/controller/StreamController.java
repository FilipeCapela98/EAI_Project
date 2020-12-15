package nl.rug.eai.imagestream.twitterstreamingservice.controller;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.twitterstreamingservice.twitterapi.TwitterStreamingRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class StreamController {

    @Autowired
    TwitterStreamingRunner twitterStreamingRunner;

    private final Map<String, LocalDateTime> runningProcessors;

    public StreamController() {
        this.runningProcessors = new ConcurrentHashMap<>();
    }

    public synchronized void start(String topic) {
        log.info("Received request to start stream on: " + topic);

        if(isRunning(topic)) {
            log.warn("Stream on topic " + topic + " is already running. Not starting another runner.");
            return;
        }

        log.info("Starting ingestion on topic " + topic);
        runningProcessors.put(topic, LocalDateTime.MIN);

        // This is called asynchronously in a new thread due to the @Async.
        // It terminated if the topic is removed from the list of running processors in this class
        twitterStreamingRunner.run(topic);
    }

    public synchronized void stop(String topic) {
        log.info("Received request to stop stream on: " + topic);

        if(!isRunning(topic)) {
            log.warn("Tried to stop stream on topic " + topic + ", but there was no processor running.");
        }

        log.info("Stopping ingestion from Twitter on topic: " + topic);
        runningProcessors.remove(topic);
    }

    public boolean isRunning(String topic) {
        return this.runningProcessors.containsKey(topic);
    }

    public void updateLastProcessTime(String topic) {
        this.runningProcessors.replace(topic, LocalDateTime.now());
    }

    public LocalDateTime getLastProcessTime(String topic) {
        return this.runningProcessors.get(topic);
    }
}
