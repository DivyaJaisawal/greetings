package service;

import com.example.grpc.Greet;
import kafka.KafkaProducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.GreetRepository;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class GreetServiceTest {
    @Mock
    private GreetRepository greetRepository;

    @Mock
    private KafkaProducer kafkaProducer;

    @Mock
    private KafkaConsumer kafkaConsumer;

    private GreetService greetService;

    @Before
    public void setUp() {
       greetService = new GreetService(greetRepository, kafkaProducer, kafkaConsumer, appConfig);
    }

    @Test
    public void testShouldCallPublishToKafkaTopic() {
        Greet.HelloRequest request = Greet.HelloRequest.newBuilder().setName("Hello world").build();
        Greet.HelloResponse response = Greet.HelloResponse.newBuilder().setGreeting(request.getName()).build();

        greetService.handleGreet(request);

        verify(kafkaProducer, times(1)).publishMessage(response.getGreeting(), response.getGreeting());
    }

    @Test
    public void testShouldCallConsumeKafkaMessageFromTopic() {
        Greet.HelloRequest request = Greet.HelloRequest.newBuilder().setName("Hello world").build();
        Greet.HelloResponse response = Greet.HelloResponse.newBuilder().setGreeting(request.getName()).build();

        greetService.handleGreet(request);

        verify(kafkaConsumer, times(1)).consumeMessage();
    }

    @Test
    public void testShouldCallInsertToDb() {
        Greet.HelloRequest request = Greet.HelloRequest.newBuilder().setName("Hello world").build();
        Greet.HelloResponse response = Greet.HelloResponse.newBuilder().setGreeting(request.getName()).build();

        greetService.handleGreet(request);

        verify(greetRepository, times(1)).saveGreetings(response);
    }
}