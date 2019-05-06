package service;

import com.example.grpc.Greet;
import com.gojek.ApplicationConfiguration;
import kafka.KafkaProducer;
import repository.GreetRepository;


public class GreetService {
    private GreetRepository greetRepository;
    private KafkaProducer produceMessage;
    private ApplicationConfiguration appConfig;

    public GreetService(GreetRepository greetRepository, KafkaProducer produceMessage, ApplicationConfiguration appConfig) {
        this.greetRepository = greetRepository;
        this.produceMessage = produceMessage;
        this.appConfig = appConfig;
    }

    public Greet.HelloResponse handleGreet(Greet.HelloRequest req) {
        Greet.HelloResponse response = Greet.HelloResponse.newBuilder().setGreeting(req.getName()).build();
        greetRepository.saveGreetings(response);
        produceMessage.publishMessage(response.getGreeting(), response.getGreeting(), appConfig);
        return response;
    }
}
