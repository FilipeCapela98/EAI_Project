package nl.rug.eai.imagestream.databaseadapterservice.gateway;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.AnnotatedImage;
import nl.rug.eai.imagestream.databaseadapterservice.controller.handlers.AnnotatedImageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AnnotatedImageReceiver {

    @Autowired
    AnnotatedImageHandler annotatedImageHandler;

    @JmsListener(destination = "analyzed-images-stream")
    public void receiveGenericMessage(AnnotatedImage annotatedImage) {
        annotatedImageHandler.handle(annotatedImage);
    }

}