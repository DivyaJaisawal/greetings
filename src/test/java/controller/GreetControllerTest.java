package controller;

import com.example.grpc.Greet;
import io.grpc.stub.StreamObserver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import service.GreetService;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class GreetControllerTest {

    @Mock
    private GreetService greetService;

    @Mock
    private StreamObserver<Greet.HelloResponse> helloResponseStreamObserver;

    private GreetController greetController;

    @Before
    public void setUp() {
        greetController = new GreetController(greetService);
    }


    @Test
    public void testShouldReturnHelloResponseForAGivenRequest() {
        Greet.HelloRequest helloRequest = Greet.HelloRequest.newBuilder()
                .setName("Hello world")
                .build();
        Greet.HelloResponse actualResponse = Greet.HelloResponse.newBuilder()
                .setGreeting(helloRequest.getName())
                .build();
        when(greetService.handleGreet(any())).thenReturn(actualResponse);

        greetController.greeting(helloRequest, helloResponseStreamObserver);
        ArgumentCaptor<Greet.HelloResponse> captor = ArgumentCaptor.forClass(Greet.HelloResponse.class);

        helloResponseStreamObserver.onNext(captor.capture());
        assertEquals(captor.getValue().getGreeting(), actualResponse);
        Mockito.verify(helloResponseStreamObserver, times(1)).onNext(actualResponse);
        Mockito.verify(helloResponseStreamObserver, times(1)).onCompleted();
    }

}
