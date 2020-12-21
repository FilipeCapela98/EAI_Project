package nl.rug.eai.imagestream.streammanagementservice.gateway;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.StartCommand;
import nl.rug.eai.imagestream.commons.model.StopCommand;
import nl.rug.eai.imagestream.commons.model.StreamConsumerHeartbeatEvent;
import nl.rug.eai.imagestream.commons.model.StreamProducerHeartbeatEvent;
import nl.rug.eai.imagestream.streammanagementservice.controller.handlers.StreamConsumerHeartbeatEventHandler;
import nl.rug.eai.imagestream.streammanagementservice.controller.handlers.StreamProducerHeartbeatEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
@Slf4j
public class ContentBasedRouterReceiver {

    @Autowired
    StreamConsumerHeartbeatEventHandler streamConsumerHeartbeatEventHandler;

    @Autowired
    StreamProducerHeartbeatEventHandler streamProducerHeartbeatEventHandler;

    @Autowired
    MappingJackson2MessageConverter messageConverter;

    @JmsListener(destination = "streaming-service-heartbeats")
    public void receiveGenericMessage(Message message) {
        Object object = null;
        try {
            object = messageConverter.fromMessage(message);
        } catch (JMSException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unable to parse message to object: " + message.toString());
        }
        forwardToHandler(object);
    }

    private void forwardToHandler(Object object) {
        if(object instanceof StreamConsumerHeartbeatEvent)
            streamConsumerHeartbeatEventHandler.handle((StreamConsumerHeartbeatEvent) object);
        else if(object instanceof StreamProducerHeartbeatEvent)
            streamProducerHeartbeatEventHandler.handle((StreamProducerHeartbeatEvent) object);
        else
            throw new UnsupportedOperationException("No handler found for " + object.getClass().toString());
    }

}
