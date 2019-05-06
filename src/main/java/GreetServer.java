import com.gojek.ApplicationConfiguration;
import com.gojek.Figaro;
import controller.GreetController;
import factory.DBIFactory;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import kafka.KafkaProducer;
import repository.GreetRepository;
import service.GreetService;

import java.io.IOException;
import java.util.logging.Logger;

public class GreetServer {
    private static final Logger logger = Logger.getLogger(GreetServer.class.getName());
    private Server server;

    private void start() throws IOException {
        int port = 50051;
        final DBIFactory dbiFactory = new DBIFactory();
        GreetRepository greetRepository = new GreetRepository(dbiFactory.create());
        KafkaProducer produceMessage = new KafkaProducer();
        ApplicationConfiguration appConfig = Figaro.configure(RequiredConfigurations.requiredConfigurations());
        GreetService greetService = new GreetService(greetRepository, produceMessage, appConfig);
        server = ServerBuilder.forPort(port)
                .addService(new GreetController(greetService))
                .build()
                .start();
        logger.info("Server started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                GreetServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
            DBIFactory.close();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final GreetServer server = new GreetServer();
        server.start();
        server.blockUntilShutdown();
    }
}


