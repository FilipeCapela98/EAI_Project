package nl.rug.eai.imagestream.twitterstreamingservice.gateway;

import nl.rug.eai.imagestream.commons.model.StartCommand;
import nl.rug.eai.imagestream.twitterstreamingservice.controller.StreamController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class StartCommandReceiver {

    @Autowired
    StreamController streamController;

    @JmsListener(destination = "streaming-service-commands")
    public void receiveStartCommand(StartCommand startCommand) {
        this.streamController.start(startCommand.getTopic());
    }

}
