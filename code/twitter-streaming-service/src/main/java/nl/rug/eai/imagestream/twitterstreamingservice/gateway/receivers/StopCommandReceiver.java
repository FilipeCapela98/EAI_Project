package nl.rug.eai.imagestream.twitterstreamingservice.gateway.receivers;

import nl.rug.eai.imagestream.commons.model.StartCommand;
import nl.rug.eai.imagestream.commons.model.StopCommand;
import nl.rug.eai.imagestream.twitterstreamingservice.controller.handlers.StopCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class StopCommandReceiver {

    @Autowired
    StopCommandHandler stopCommandHandler;

    @JmsListener(destination = "streaming-service-commands")
    public void receiveStopCommand(StopCommand stopCommand) {
        this.stopCommandHandler.handle(stopCommand);
    }

}
