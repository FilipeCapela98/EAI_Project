package nl.rug.eai.imagestream.databaseadapterservice.service;

import com.google.gson.Gson;
import nl.rug.eai.imagestream.databaseadapterservice.gateway.senders.ImageJSONDataSender;
import nl.rug.eai.imagestream.databaseadapterservice.model.FilteredTweetImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseAdapterRunner {

    @Autowired
    ImageJSONDataSender imageJSONDataSender;

    public void run(String topic) {

        // Fetch new images from queue
        FilteredTweetImage imageJSON = getImageJSON(topic);

        // The results should be put in the pipeline queue for further processing
        sendToMongo(imageJSON);
    }

    private FilteredTweetImage getImageJSON(String topic) {
        Gson gson = new Gson();
        return gson.fromJson(topic, FilteredTweetImage.class);
    }

    private void sendToMongo(FilteredTweetImage imageJSON) {
        imageJSONDataSender.send(imageJSON);
    }
}
