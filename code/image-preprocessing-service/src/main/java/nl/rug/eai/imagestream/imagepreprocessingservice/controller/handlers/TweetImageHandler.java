package nl.rug.eai.imagestream.imagepreprocessingservice.controller.handlers;

import nl.rug.eai.imagestream.commons.model.TweetImage;
import nl.rug.eai.imagestream.imagepreprocessingservice.controller.ImagePreprocessingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TweetImageHandler {

    @Autowired
    ImagePreprocessingController imagePreprocessingController;

    public void handle(TweetImage tweetImage) {
        this.imagePreprocessingController.process(tweetImage);
    }

}
