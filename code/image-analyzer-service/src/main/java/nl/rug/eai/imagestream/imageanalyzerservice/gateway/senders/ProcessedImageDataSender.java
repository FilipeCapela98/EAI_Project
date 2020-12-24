package nl.rug.eai.imagestream.imageanalyzerservice.gateway.senders;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.AnnotatedImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProcessedImageDataSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(AnnotatedImage annotatedImage) {
        log.info("Sending data on queue: " + annotatedImage);
        try {
            jmsTemplate.setPubSubDomain(true);
            jmsTemplate.convertAndSend("analyzed-images-stream", annotatedImage);
        } catch (Exception e) {
            log.error(e.toString());
        }

    }

}