package nl.rug.eai.imagestream.imageanalyzerservice.gateway.senders;

import nl.rug.eai.imagestream.commons.model.AnnotatedImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProcessedImageDataSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(AnnotatedImage annotatedImage) {
        jmsTemplate.convertAndSend("analyzed-images-stream", annotatedImage);
    }

}