package nl.rug.eai.imagestream.imagepreprocessingservice.gateway.receivers;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.TweetImage;
import nl.rug.eai.imagestream.imagepreprocessingservice.controller.handlers.TweetImageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
@Slf4j
public class TweetImageReceiver {

    @Autowired
    TweetImageHandler tweetImageHandler;

    @JmsListener(destination = "fetched-images-stream")
    public void receiveGenericMessage(TweetImage tweetImage) {
        tweetImageHandler.handle(tweetImage);
    }

}
