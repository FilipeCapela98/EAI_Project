package nl.rug.eai.imagestream.streammanagementservice.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class StreamActivityRepository {

    private Map<String, LocalDateTime> streamProducerActivity;
    private Map<String, LocalDateTime> streamConsumerActivity;
    private Map<String, LocalDateTime> lastStreamStartCommand;

    private final int START_COMMAND_COOLDOWN_SECONDS = 60;
    private final int STREAM_CONSIDERED_INACTIVE_AFTER_SECONDS = 60;

    public StreamActivityRepository() {
        this.streamProducerActivity = new ConcurrentHashMap<>();
        this.streamConsumerActivity = new ConcurrentHashMap<>();
        this.lastStreamStartCommand = new ConcurrentHashMap<>();
    }

    public void refreshConsumer(String topic) {
        streamConsumerActivity.put(topic, LocalDateTime.now());
    }

    public void refreshProducer(String topic) {
        streamProducerActivity.put(topic, LocalDateTime.now());
    }

    public void refreshStartCommand(String topic) {
        lastStreamStartCommand.put(topic, LocalDateTime.now());
    }

    public boolean isStreamBeingProduced(String topic) {
        // FALSE if no start command has ever been executed.
        if(!lastStreamStartCommand.containsKey(topic))
            return false;

        // TRUE if start command recently sent.
        if(ChronoUnit.SECONDS.between(lastStreamStartCommand.get(topic), LocalDateTime.now()) <=
                START_COMMAND_COOLDOWN_SECONDS)
            return true;

        // Otherwise, it depends on whether the last heartbeat was within the threshold.
        return streamProducerActivity.containsKey(topic) &&
                ChronoUnit.SECONDS.between(streamProducerActivity.get(topic), LocalDateTime.now())
                        <= STREAM_CONSIDERED_INACTIVE_AFTER_SECONDS;
    }

    public boolean isStreamBeingConsumed(String topic) {
        // FALSE if it was never consumed in the first place
        if(!streamConsumerActivity.containsKey(topic))
            return false;

        // Otherwise, it depends on whether the last heartbeat was within the threshold.
        return ChronoUnit.SECONDS.between(streamConsumerActivity.get(topic), LocalDateTime.now()) <=
                STREAM_CONSIDERED_INACTIVE_AFTER_SECONDS;
    }

}
