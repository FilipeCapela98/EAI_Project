package nl.rug.eai.imagestream.twitterstreamingservice.controller;

import org.springframework.stereotype.Component;

@Component
public class StreamController {

    // TODO: Keep track of threads / listener, such that they can be easily stopped from here.

    public void start(String topic) {
        System.out.println("Received request to start stream on: " + topic);
        throw new UnsupportedOperationException();
    }

    public void stop(String topic) {
        System.out.println("Received request to stop stream on: " + topic);
        throw new UnsupportedOperationException();
    }

}
