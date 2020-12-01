package nl.rug.eai.imagestream.twitterstreamingservice.controller;

import org.springframework.stereotype.Component;

@Component
public class StreamController {

    public void start(String topic) {
        System.out.println("Received request to start stream on: " + topic);
    }

}
