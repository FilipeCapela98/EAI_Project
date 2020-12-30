package nl.rug.eai.imagestream.streammanagementservice.controller.handlers;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.StreamConsumerHeartbeatEvent;
import nl.rug.eai.imagestream.commons.model.StreamProducerHeartbeatEvent;
import nl.rug.eai.imagestream.streammanagementservice.controller.StreamActivityRepositoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class StreamProducerHeartbeatEventHandler {

    @Autowired
    StreamActivityRepositoryManager streamActivityRepositoryManager;

    public void handle(StreamProducerHeartbeatEvent producerHeartbeatEvent) {
        String topic = producerHeartbeatEvent.getTopic();
        log.info("Received producer heartbeat for topic: " + topic);
        this.streamActivityRepositoryManager.refreshProducerActivity(topic);
    }

}
