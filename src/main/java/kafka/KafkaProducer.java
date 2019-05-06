package kafka;

import com.gojek.ApplicationConfiguration;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.Future;

public class KafkaProducer {

    public void publishMessage(String key, String msg, ApplicationConfiguration configuration) {
        try (Producer<String, String> kafkaProducer = ProducerCreator.createProducer(configuration)) {
            System.out.println("keys" + key);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(configuration.getValueAsString("GREET_TOPIC_NAME"),key, msg);
            Future<RecordMetadata> future = kafkaProducer.send(producerRecord);
            if (future.isDone()) {
                System.out.println("Is done called");
            }
        }
    }
}