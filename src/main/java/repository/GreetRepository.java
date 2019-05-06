package repository;

import com.example.grpc.Greet;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.util.StringColumnMapper;

import java.util.List;

public class GreetRepository {
    DBI dbi;
    private static final String INSERT_INTO_GREET = "insert into greet (message) VALUES (:message)";
    private static final String SELECT_QUERY = "SELECT * FROM greet";

    public GreetRepository(DBI dbi) {
        this.dbi = dbi;
    }

    public void saveGreetings(Greet.HelloResponse response) {
        dbi.withHandle(handle -> {
            handle.createStatement(INSERT_INTO_GREET)
                    .bind("message", response.getGreeting())
                    .execute();
            return null;
        });
    }

    public List<String> find() {
        return dbi.withHandle(handle -> handle.createQuery(SELECT_QUERY)
                .map(StringColumnMapper.INSTANCE)
                .list()
        );
    }
}
