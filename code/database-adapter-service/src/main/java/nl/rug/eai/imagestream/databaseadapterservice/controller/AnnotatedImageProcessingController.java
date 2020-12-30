package nl.rug.eai.imagestream.databaseadapterservice.controller;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.AnnotatedImage;
import nl.rug.eai.imagestream.databaseadapterservice.service.DatabaseAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AnnotatedImageProcessingController {

    @Autowired
    DatabaseAdapterService databaseAdapterRunner;

    public synchronized void process(AnnotatedImage annotatedImage) {
        log.info("Received request to store image on topic: " + annotatedImage.getTag());

        // This is called asynchronously in a new thread due to the @Async.
        // It terminated if the topic is removed from the list of running processors in this class
        databaseAdapterRunner.run(annotatedImage);
    }

}
