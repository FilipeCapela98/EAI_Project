package nl.rug.eai.imagestream.streammanagementservice.controller;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.StartCommand;
import nl.rug.eai.imagestream.commons.model.StopCommand;
import nl.rug.eai.imagestream.streammanagementservice.gateway.senders.StartCommandSender;
import nl.rug.eai.imagestream.streammanagementservice.gateway.senders.StopCommandSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class StreamCommandController {

    @Autowired
    StartCommandSender startCommandSender;

    @Autowired
    StopCommandSender stopCommandSender;

    public void start(String topic) {
        log.info("Sending Start Stream command for topic: " + topic);
        StartCommand startCommand = new StartCommand(topic);
        startCommandSender.send(startCommand);
    }

    public void stop(String topic) {
        log.info("Sending Stop Stream command for topic: " + topic);
        StopCommand stopCommand = new StopCommand(topic);
        stopCommandSender.send(stopCommand);
    }

}
