package controller;

import com.example.grpc.Greet;
import com.example.grpc.GreetingServiceGrpc;
import io.grpc.stub.StreamObserver;
import service.GreetService;


public class GreetController extends GreetingServiceGrpc.GreetingServiceImplBase {
    private GreetService greetService;

    public GreetController(GreetService greetService) {
        this.greetService = greetService;
    }

    @Override
    public void greeting(Greet.HelloRequest req, StreamObserver<Greet.HelloResponse> responseObserver) {
        Greet.HelloResponse response = greetService.handleGreet(req);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
