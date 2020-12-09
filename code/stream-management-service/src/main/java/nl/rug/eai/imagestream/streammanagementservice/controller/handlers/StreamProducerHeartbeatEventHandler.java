package nl.rug.eai.imagestream.streammanagementservice.controller;

import nl.rug.eai.imagestream.commons.model.StreamConsumerHeartbeatEvent;
import nl.rug.eai.imagestream.commons.model.StreamProducerHeartbeatEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StreamProducerHeartbeatEventHandler {

    @Autowired
    StreamActivityRepositoryManager streamActivityRepositoryManager;

    public void handle(StreamProducerHeartbeatEvent producerHeartbeatEvent) {
        String topic = producerHeartbeatEvent.getTopic();
        this.streamActivityRepositoryManager.refreshProducerActivity(topic);
    }

}
