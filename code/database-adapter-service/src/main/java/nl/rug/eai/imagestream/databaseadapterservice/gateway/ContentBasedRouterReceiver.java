package nl.rug.eai.imagestream.databaseadapterservice.gateway;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.ImageCommand;
import nl.rug.eai.imagestream.databaseadapterservice.controller.handlers.ImageJSONCommandHandler;
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
    ImageJSONCommandHandler imageJSONCommandHandler;

    @Autowired
    MappingJackson2MessageConverter messageConverter;

    @JmsListener(destination = "analyzed-images-stream")
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
        if (object instanceof ImageCommand)
            imageJSONCommandHandler.handle((ImageCommand) object);
        else
            throw new UnsupportedOperationException("No handler found for " + object.getClass().toString());
    }

}