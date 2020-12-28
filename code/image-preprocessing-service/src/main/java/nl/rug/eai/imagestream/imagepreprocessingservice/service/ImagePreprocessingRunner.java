package nl.rug.eai.imagestream.imagepreprocessingservice.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.FilteredTweetImage;
import nl.rug.eai.imagestream.imagepreprocessingservice.gateway.senders.PreprocessImageDataSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Slf4j
public class ImagePreprocessingRunner {


    @Autowired
    PreprocessImageDataSender filteredImageDataSender;

    public void run(String topic) {
        // Process the images
        FilteredTweetImage filteredImage = getFilteredImage(topic);

        // The results should be put in the pipeline queue for further processing
        forwardAnnotatedImage(filteredImage);
    }

    private FilteredTweetImage getFilteredImage(String topic) {
        Gson gson = new Gson();
        FilteredTweetImage preprocessedImageData = gson.fromJson(topic, FilteredTweetImage.class);
        return preprocessedImageData.getUrl().matches(".*(png|jpeg|jpg|format=jpg|format=png|format=jpeg).*")
                ? preprocessedImageData : null;
    }

    private void forwardAnnotatedImage(FilteredTweetImage filteredImage) {
        // Forward the filtered image
        if (filteredImage != null) {
            filteredImageDataSender.send(filteredImage);
        } else {
            log.info("Image discarded");
        }
    }
}
