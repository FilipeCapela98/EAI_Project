package nl.rug.eai.imagestream.imageanalyzerservice.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.commons.model.AnnotatedImage;
import nl.rug.eai.imagestream.commons.model.FilteredTweetImage;
import nl.rug.eai.imagestream.imageanalyzerservice.gateway.senders.ProcessedImageDataSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImageAnalyzerRunner {

    @Autowired
    CloudVisionServiceUtil cloudVisionServiceUtil;

    @Autowired
    ProcessedImageDataSender processedImageDataSender;

    public void run(String topic) {
        // Process the images
        AnnotatedImage annotatedImage = getAnalyzedImage(topic);

        // The results should be put in the pipeline queue for further processing
        forwardAnnotatedImage(annotatedImage);
    }

    private AnnotatedImage getAnalyzedImage(String topic) {
        Gson gson = new Gson();
        FilteredTweetImage preprocessedImageData = gson.fromJson(topic, FilteredTweetImage.class);
        return cloudVisionServiceUtil.processImage(preprocessedImageData.getIdentifier(),preprocessedImageData.getTag(), preprocessedImageData.getUrl());
    }

    private void forwardAnnotatedImage(AnnotatedImage annotatedImage) {
        // Forward the annotated image
        if (!annotatedImage.getAnnotatedImage().isEmpty() && !annotatedImage.getIdentifiedObject().isEmpty()) {
            processedImageDataSender.send(annotatedImage);
        }
    }
}
