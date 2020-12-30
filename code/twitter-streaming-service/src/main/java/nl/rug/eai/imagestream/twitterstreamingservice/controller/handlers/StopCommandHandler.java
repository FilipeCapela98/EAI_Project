package nl.rug.eai.imagestream.twitterstreamingservice.controller.handlers;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.StartCommand;
import nl.rug.eai.imagestream.commons.model.StopCommand;
import nl.rug.eai.imagestream.twitterstreamingservice.controller.StreamController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StopCommandHandler {

    @Autowired
    StreamController streamController;

    public void handle(StopCommand stopCommand) {
        String topic = stopCommand.getTopic();
        log.info("Received Stop Command for topic: " + topic);
        this.streamController.stop(topic);
    }

}
