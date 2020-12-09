package nl.rug.eai.imagestream.streammanagementservice.gateway.receivers;

import nl.rug.eai.imagestream.commons.model.StreamProducerHeartbeatEvent;
import nl.rug.eai.imagestream.streammanagementservice.controller.handlers.StreamProducerHeartbeatEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class StreamProducerHeartbeatEventReceiver {

    @Autowired
    StreamProducerHeartbeatEventHandler producerHeartbeatEventHandler;

    @JmsListener(destination = "streaming-service-heartbeats")
    public void receiveProducerHeartbeatEvent(StreamProducerHeartbeatEvent producerHeartbeatEvent) {
        this.producerHeartbeatEventHandler.handle(producerHeartbeatEvent);
    }

}
