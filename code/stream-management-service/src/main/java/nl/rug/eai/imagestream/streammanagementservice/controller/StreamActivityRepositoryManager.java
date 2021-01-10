package nl.rug.eai.imagestream.streammanagementservice.controller;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.streammanagementservice.model.StreamActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class StreamActivityRepositoryManager {

    @Autowired
    StreamCommandController streamCommandController;

    private final StreamActivityRepository streamActivityRepository = new StreamActivityRepository();

    public void refreshConsumerActivity(String topic) {
        this.streamActivityRepository.refreshConsumer(topic);

        // If the stream has not started yet, kickstart it
        if(!streamActivityRepository.isStreamBeingProduced(topic)) {
            streamCommandController.start(topic);
        }
    }

    public void refreshProducerActivity(String topic) {
        this.streamActivityRepository.refreshProducer(topic);

        // If the stream is producing, but there are no consumers, we can safely close the stream.
        if(!streamActivityRepository.isStreamBeingConsumed(topic)) {
            streamCommandController.stop(topic);
        }
    }

    public void refreshStartCommand(String topic) {
        this.streamActivityRepository.refreshStartCommand(topic);
    }

}
