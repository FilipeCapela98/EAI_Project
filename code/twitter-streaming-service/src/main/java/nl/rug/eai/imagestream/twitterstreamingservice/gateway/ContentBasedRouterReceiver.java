package nl.rug.eai.imagestream.twitterstreamingservice.gateway;

import ch.qos.logback.classic.pattern.MessageConverter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.StartCommand;
import nl.rug.eai.imagestream.commons.model.StopCommand;
import nl.rug.eai.imagestream.twitterstreamingservice.controller.handlers.StartCommandHandler;
import nl.rug.eai.imagestream.twitterstreamingservice.controller.handlers.StopCommandHandler;
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
    StartCommandHandler startCommandHandler;

    @Autowired
    StopCommandHandler stopCommandHandler;

    @Autowired
    MappingJackson2MessageConverter messageConverter;

    @JmsListener(destination = "streaming-service-commands")
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
        if(object instanceof StartCommand)
            startCommandHandler.handle((StartCommand) object);
        else if(object instanceof StopCommand)
            stopCommandHandler.handle((StopCommand) object);
        else
            throw new UnsupportedOperationException("No handler found for " + object.getClass().toString());
    }

}
