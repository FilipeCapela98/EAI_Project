package nl.rug.eai.imagestream.imageanalyzerservice.gateway.receivers;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.FilteredTweetImage;
import nl.rug.eai.imagestream.imageanalyzerservice.controller.handlers.FilteredTweetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilteredTweetImageReceiver {

    @Autowired
    FilteredTweetHandler filteredTweetHandler;


    @Autowired
    MappingJackson2MessageConverter messageConverter;

    @JmsListener(destination = "filtered_image_stream")
    public void receiveFilteredTweetImage(FilteredTweetImage filteredTweetImage) {
        filteredTweetHandler.handle(filteredTweetImage);
    }

}
