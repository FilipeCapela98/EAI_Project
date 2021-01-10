package nl.rug.eai.imagestream.streammanagementservice.controller.handlers;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.StreamConsumerHeartbeatEvent;
import nl.rug.eai.imagestream.streammanagementservice.controller.StreamActivityRepositoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class StreamConsumerHeartbeatEventHandler {

    @Autowired
    StreamActivityRepositoryManager streamActivityRepositoryManager;

    public void handle(StreamConsumerHeartbeatEvent consumerHeartbeatEvent) {
        String topic = consumerHeartbeatEvent.getTopic();
        log.info("Received consumer heartbeat for topic: " + topic);
        this.streamActivityRepositoryManager.refreshConsumerActivity(topic);
    }

}
