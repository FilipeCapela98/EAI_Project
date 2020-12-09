package nl.rug.eai.imagestream.twitterstreamingservice.controller.handlers;

import nl.rug.eai.imagestream.commons.model.StartCommand;
import nl.rug.eai.imagestream.commons.model.StopCommand;
import nl.rug.eai.imagestream.twitterstreamingservice.controller.StreamController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StopCommandHandler {

    @Autowired
    StreamController streamController;

    public void handle(StopCommand stopCommand) {
        String topic = stopCommand.getTopic();
        this.streamController.stop(topic);
    }

}
