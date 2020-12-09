package nl.rug.eai.imagestream.streammanagementservice.gateway.receivers;

import nl.rug.eai.imagestream.commons.model.StreamConsumerHeartbeatEvent;
import nl.rug.eai.imagestream.streammanagementservice.controller.handlers.StreamConsumerHeartbeatEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class StreamConsumerHeartbeatEventReceiver {

    @Autowired
    StreamConsumerHeartbeatEventHandler consumerHeartbeatEventHandler;

    @JmsListener(destination = "streaming-service-heartbeats")
    public void receiveConsumerHeartbeatEvent(StreamConsumerHeartbeatEvent consumerHeartbeatEvent) {
        this.consumerHeartbeatEventHandler.handle(consumerHeartbeatEvent);
    }

}
