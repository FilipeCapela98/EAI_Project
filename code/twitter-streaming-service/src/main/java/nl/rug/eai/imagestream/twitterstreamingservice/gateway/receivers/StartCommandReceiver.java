package nl.rug.eai.imagestream.twitterstreamingservice.gateway.receivers;

import nl.rug.eai.imagestream.commons.model.StartCommand;
import nl.rug.eai.imagestream.twitterstreamingservice.controller.StreamController;
import nl.rug.eai.imagestream.twitterstreamingservice.controller.handlers.StartCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class StartCommandReceiver {

    @Autowired
    StartCommandHandler startCommandHandler;

    @JmsListener(destination = "streaming-service-commands")
    public void receiveStartCommand(StartCommand startCommand) {
        this.startCommandHandler.handle(startCommand);
    }

}
