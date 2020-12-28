package nl.rug.eai.imagestream.databaseadapterservice.controller;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.databaseadapterservice.service.DatabaseAdapterRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImageJSONController {

    @Autowired
    DatabaseAdapterRunner databaseAdapterRunner;

    public synchronized void start(String topic) {
        log.info("Received request to start stream on: " + topic);

        // This is called asynchronously in a new thread due to the @Async.
        // It terminated if the topic is removed from the list of running processors in this class
        databaseAdapterRunner.run(topic);
    }

}
