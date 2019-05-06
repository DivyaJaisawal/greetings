import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

class RequiredConfigurations {

    static Set<String> requiredConfigurations() {
        return new HashSet<>(asList(
                "KAFKA_BROKERS",
                "KAFKA_MESSAGE_COUNT",
                "KAFKA_GROUP_ID_CONFIG",
                "GREET_TOPIC_NAME",
                "KAFKA_OFFSET_RESET_LATEST",
                "OFFSET_RESET_EARLIER"

        ));
    }
}
