package nl.rug.eai.imagestream.streammanagementservice.controller.handlers;

import nl.rug.eai.imagestream.commons.model.StreamConsumerHeartbeatEvent;
import nl.rug.eai.imagestream.streammanagementservice.controller.StreamActivityRepositoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class StreamConsumerHeartbeatEventHandler {

    @Autowired
    StreamActivityRepositoryManager streamActivityRepositoryManager;

    public void handle(StreamConsumerHeartbeatEvent consumerHeartbeatEvent) {
        String topic = consumerHeartbeatEvent.getTopic();
        this.streamActivityRepositoryManager.refreshConsumerActivity(topic);
    }

}
