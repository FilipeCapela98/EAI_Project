package nl.rug.eai.imagestream.imageanalyzerservice.controller.handlers;

import nl.rug.eai.imagestream.commons.model.ImageCommand;
import nl.rug.eai.imagestream.imageanalyzerservice.controller.ImageAnalyzerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageCommandHandler {

    @Autowired
    ImageAnalyzerController imageAnalyzerController;

    public void handle(ImageCommand imageCommand) {
        String topic = imageCommand.getTopic();
        this.imageAnalyzerController.start(topic);
    }

}
