package nl.rug.eai.imagestream.databaseadapterservice.repository;

import nl.rug.eai.imagestream.commons.model.AnnotatedImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ImageJSONRepository extends MongoRepository<AnnotatedImage, String> {
}
