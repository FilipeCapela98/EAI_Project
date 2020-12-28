package nl.rug.eai.imagestream.databaseadapterservice.gateway.senders;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.databaseadapterservice.model.FilteredTweetImage;
import nl.rug.eai.imagestream.databaseadapterservice.repository.ImageJSONRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImageJSONDataSender {

    @Autowired
    ImageJSONRepository imageJSONRepository;

    public void send(FilteredTweetImage filteredTweetImage){
        imageJSONRepository.save(new FilteredTweetImage("3","PNG",
                "https://pbs.twimg.com/media/EpOMW12XUAQXnrx.jpg","cat"));
        imageJSONRepository.save(new FilteredTweetImage("4","PNG",
                "https://pbs.twimg.com/media/Ep4KOCYUwAI3DVY?format=jpg&name=large","dog"));
    }

}
