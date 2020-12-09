package nl.rug.eai.imagestream.twitterstreamingservice.controller.handlers;

import nl.rug.eai.imagestream.commons.model.StartCommand;
import nl.rug.eai.imagestream.twitterstreamingservice.controller.StreamController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommandHandler {

    @Autowired
    StreamController streamController;

    public void handle(StartCommand startCommand) {
        String topic = startCommand.getTopic();
        this.streamController.start(topic);
    }

}
