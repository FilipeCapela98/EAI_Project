package nl.rug.eai.imagestream.databaseadapterservice.repository;

import nl.rug.eai.imagestream.databaseadapterservice.model.FilteredTweetImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ImageJSONRepository extends MongoRepository<FilteredTweetImage, String> {
}
