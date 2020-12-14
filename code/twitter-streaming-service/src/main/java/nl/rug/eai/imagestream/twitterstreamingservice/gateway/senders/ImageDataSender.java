package nl.rug.eai.imagestream.twitterstreamingservice.gateway.senders;

import nl.rug.eai.imagestream.commons.model.StreamProducerHeartbeatEvent;
import nl.rug.eai.imagestream.commons.model.TweetImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ImageDataSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(TweetImage tweetImage) {
        jmsTemplate.convertAndSend("fetched-images-stream", tweetImage);
    }

}
