package kafka;

import com.gojek.ApplicationConfiguration;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
public class ProducerCreator {
    public static Producer<String, String> createProducer(ApplicationConfiguration configuration) {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configuration.getValueAsString("KAFKA_BROKERS"));
        props.put(ProducerConfig.CLIENT_ID_CONFIG, configuration.getValueAsString("CLIENT_ID"));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, configuration.getValueAsInt("MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION"));
        props.put(ProducerConfig.RETRIES_CONFIG, configuration.getValueAsInt("RETRIES_CONFIG"));
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, configuration.getValueAsInt("REQUEST_TIMEOUT_MS_CONFIG"));
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, configuration.getValueAsInt("RETRY_BACKOFF_MS_CONFIG"));
        //props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());
        return new KafkaProducer<>(props);
    }


}