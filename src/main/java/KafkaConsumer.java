import com.gojek.ApplicationConfiguration;
import com.gojek.Figaro;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumer {
    public static void main(String[] args) {

        ApplicationConfiguration configuration = Figaro.configure(RequiredConfigurations.requiredConfigurations());

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, configuration.getValueAsString("KAFKA_BROKERS"));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, configuration.getValueAsString("KAFKA_GROUP_ID_CONFIG"));

        System.out.println("Subscribed to topic " + configuration.getValueAsString("GREET_TOPIC_NAME"));
        KafkaConsumer kafkaConsumer = new KafkaConsumer();
        kafkaConsumer.consumeMessage(configuration, props);
    }

    public void consumeMessage(ApplicationConfiguration configuration, Properties properties) {
       org.apache.kafka.clients.consumer.KafkaConsumer consumer = new org.apache.kafka.clients.consumer.KafkaConsumer(properties);
        consumer.subscribe(Arrays.asList(configuration.getValueAsString("GREET_TOPIC_NAME")));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Supplier id= " +
                       record.key() +
                        " Supplier  Name = " + record.value() + "offset = " + record.offset());
            }
        }
    }
}