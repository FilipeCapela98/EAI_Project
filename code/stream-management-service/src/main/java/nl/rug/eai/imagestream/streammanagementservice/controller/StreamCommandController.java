package nl.rug.eai.imagestream.streammanagementservice.controller;

import nl.rug.eai.imagestream.commons.model.StartCommand;
import nl.rug.eai.imagestream.commons.model.StopCommand;
import nl.rug.eai.imagestream.streammanagementservice.gateway.senders.StartCommandSender;
import nl.rug.eai.imagestream.streammanagementservice.gateway.senders.StopCommandSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StreamCommandController {

    @Autowired
    StartCommandSender startCommandSender;

    @Autowired
    StopCommandSender stopCommandSender;

    public void start(String topic) {
        StartCommand startCommand = new StartCommand(topic);
        startCommandSender.send(startCommand);
    }

    public void stop(String topic) {
        StopCommand stopCommand = new StopCommand(topic);
        stopCommandSender.send(stopCommand);
    }

}
