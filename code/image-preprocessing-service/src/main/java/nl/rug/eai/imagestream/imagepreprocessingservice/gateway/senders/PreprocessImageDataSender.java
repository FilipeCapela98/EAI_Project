package nl.rug.eai.imagestream.imagepreprocessingservice.gateway.senders;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.FilteredTweetImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PreprocessImageDataSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(FilteredTweetImage filteredImage) {
        log.info("Sending data on queue: " + filteredImage);
        try {
            jmsTemplate.convertAndSend("filtered_image_stream", filteredImage);
        } catch (Exception e) {
            log.error(e.toString());
        }

    }

}