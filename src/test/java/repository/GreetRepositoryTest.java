package repository;

import com.example.grpc.Greet;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class GreetRepositoryTest extends RepositoryTest{
    private GreetRepository repository;
    @Before
    public void before() {
        super.before();
        repository = new GreetRepository(dbi);
    }

    @Test
    public void shouldSaveValuesToDB() {
        Greet.HelloResponse response = Greet.HelloResponse.newBuilder().setGreeting("Hello world").build();
        repository.saveGreetings(response);

        List<String> greetings = repository.find();

        assertEquals(Arrays.asList("Hello world"), greetings);
    }
}