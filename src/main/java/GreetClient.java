import com.example.grpc.Greet;
import com.example.grpc.GreetingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GreetClient {


    private static final Logger logger = Logger.getLogger(GreetClient.class.getName());
    private final ManagedChannel channel;
    private final GreetingServiceGrpc.GreetingServiceBlockingStub blockingStub;

    public GreetClient(String host, int port) {


        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build());
    }

    GreetClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = GreetingServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String name) {
        logger.info("Will try to greet " + name + " ...");
        Greet.HelloRequest request = Greet.HelloRequest.newBuilder().setName(name).build();
       // logger.log(Level.WARNING,"check", request);
        Greet.HelloResponse response;
        try {
            response = blockingStub.greeting(request);
        } catch (StatusRuntimeException e) {
        //    logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
      //  logger.info("Greeting: " + response.getGreeting());
    }

    public static void main(String[] args) throws Exception {
        GreetClient client = new GreetClient("localhost", 50051);
        try {
            /* Access a service running on the local machine on port 50051 */
            String user = "hello world";
            if (args.length > 0) {
                user = args[0];
            }
            client.greet(user);
        } finally {
            client.shutdown();
        }
    }
}
