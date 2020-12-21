package nl.rug.eai.imagestream.streammanagementservice.gateway.senders;

import nl.rug.eai.imagestream.commons.model.StartCommand;
import nl.rug.eai.imagestream.streammanagementservice.controller.StreamActivityRepositoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class StartCommandSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    StreamActivityRepositoryManager streamActivityRepositoryManager;

    public void send(StartCommand startCommand) {
        jmsTemplate.convertAndSend("streaming-service-commands", startCommand,
                msg -> {
                    msg.setStringProperty("JMSXGroupID", "streamTopic=" + startCommand.getTopic());
                    return msg;
                });
        streamActivityRepositoryManager.refreshStartCommand(startCommand.getTopic());
    }

}
