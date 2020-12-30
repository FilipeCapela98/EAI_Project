package nl.rug.eai.imagestream.imagepreprocessingservice.controller;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.TweetImage;
import nl.rug.eai.imagestream.imagepreprocessingservice.service.ImagePreprocessingRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImagePreprocessingController {

    @Autowired
    ImagePreprocessingRunner imagePreprocessingRunner;

    public void process(TweetImage tweetImage) {
        log.info("Received request to preprocess image on topic: " + tweetImage.getTag());
        imagePreprocessingRunner.run(tweetImage);
    }
}
