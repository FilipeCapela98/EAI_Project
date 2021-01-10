package nl.rug.eai.imagestream.twitterstreamingservice.controller.handlers;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.StartCommand;
import nl.rug.eai.imagestream.twitterstreamingservice.controller.StreamController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartCommandHandler {

    @Autowired
    StreamController streamController;

    public void handle(StartCommand startCommand) {
        String topic = startCommand.getTopic();
        log.info("Received Start Command for topic: " + topic);
        this.streamController.start(topic);
    }

}
