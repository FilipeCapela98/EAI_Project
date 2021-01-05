package nl.rug.eai.imagestream.databaseadapterservice.service;

import nl.rug.eai.imagestream.commons.model.AnnotatedImage;
import nl.rug.eai.imagestream.databaseadapterservice.model.AnnotatedImageDataModel;
import nl.rug.eai.imagestream.databaseadapterservice.repository.ImageJSONRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseAdapterService {

    @Autowired
    ImageJSONRepository imageJSONRepository;

    AnnotatedImageDataModel annotatedImageDataModel;

    public void run(AnnotatedImage annotatedImage) {
        // The results should be put in the pipeline queue for further processing
        annotatedImageDataModel = new AnnotatedImageDataModel(annotatedImage.getIdentifier(), annotatedImage.getTag(),
                annotatedImage.getIdentifiedObject(), annotatedImage.getAnnotatedImage());
        sendToMongo(annotatedImage);
    }

    private void sendToMongo(AnnotatedImage annotatedImage) {
        imageJSONRepository.save(annotatedImage);
    }
}
