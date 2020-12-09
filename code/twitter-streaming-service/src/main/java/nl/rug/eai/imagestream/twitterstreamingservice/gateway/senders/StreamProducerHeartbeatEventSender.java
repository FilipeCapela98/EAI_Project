package nl.rug.eai.imagestream.twitterstreamingservice.gateway.senders;

import nl.rug.eai.imagestream.commons.model.StartCommand;
import nl.rug.eai.imagestream.commons.model.StreamProducerHeartbeatEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class StreamProducerHeartbeatEventSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(StreamProducerHeartbeatEvent producerHeartbeatEvent) {
        jmsTemplate.convertAndSend("streaming-service-heartbeats", producerHeartbeatEvent,
                msg -> {
                    msg.setStringProperty("JMSXGroupID", "streamTopic=" + producerHeartbeatEvent.getTopic());
                    return msg;
                });
    }

}
