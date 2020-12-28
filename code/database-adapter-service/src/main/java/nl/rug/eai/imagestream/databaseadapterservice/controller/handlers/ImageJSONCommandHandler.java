package nl.rug.eai.imagestream.databaseadapterservice.controller.handlers;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.ImageCommand;
import nl.rug.eai.imagestream.databaseadapterservice.controller.ImageJSONController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImageJSONCommandHandler {

    @Autowired
    ImageJSONController imageJSONController;

    public void handle(ImageCommand imageCommand) {
        String topic = imageCommand.getTopic();
        this.imageJSONController.start(topic);
    }

}
