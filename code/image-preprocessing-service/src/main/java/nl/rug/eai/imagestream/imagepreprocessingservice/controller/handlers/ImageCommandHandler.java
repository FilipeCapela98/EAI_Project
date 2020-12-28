package nl.rug.eai.imagestream.imagepreprocessingservice.controller.handlers;

import nl.rug.eai.imagestream.commons.model.ImageCommand;
import nl.rug.eai.imagestream.imagepreprocessingservice.controller.ImagePreprocessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageCommandHandler {

    @Autowired
    ImagePreprocessingController imagePreprocessingController;

    public void handle(ImageCommand imageCommand) {
        String topic = imageCommand.getTopic();
        this.imagePreprocessingController.start(topic);
    }

}
