package nl.rug.eai.imagestream.streammanagementservice.gateway.senders;

import nl.rug.eai.imagestream.commons.model.StartCommand;
import nl.rug.eai.imagestream.commons.model.StopCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class StopCommandSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(StopCommand stopCommand) {
        jmsTemplate.convertAndSend("streaming-service-commands", stopCommand,
                msg -> {
                    msg.setStringProperty("JMSXGroupID", "streamTopic=" + stopCommand.getTopic());
                    return msg;
                });
    }

}
