package nl.rug.eai.imagestream.imageanalyzerservice.controller.handlers;

import nl.rug.eai.imagestream.commons.model.FilteredTweetImage;
import nl.rug.eai.imagestream.imageanalyzerservice.controller.ImageAnalyzerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilteredTweetHandler {

    @Autowired
    ImageAnalyzerController imageAnalyzerController;

    public void handle(FilteredTweetImage filteredTweetImage) {
        this.imageAnalyzerController.analyze(filteredTweetImage);
    }

}
