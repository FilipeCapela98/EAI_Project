package nl.rug.eai.imagestream.databaseadapterservice.controller.handlers;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.AnnotatedImage;
import nl.rug.eai.imagestream.databaseadapterservice.controller.AnnotatedImageProcessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AnnotatedImageHandler {

    @Autowired
    AnnotatedImageProcessingController annotatedImageProcessingController;

    public void handle(AnnotatedImage annotatedImage) {
        this.annotatedImageProcessingController.process(annotatedImage);
    }

}
