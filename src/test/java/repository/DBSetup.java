package repository;

import factory.DBIFactory;
import org.flywaydb.core.Flyway;
import org.skife.jdbi.v2.DBI;

public class DBSetup {
    private static DBI dbi;

    static {
        if (dbi == null) {
            initializeDB();
        }
    }

    private static void initializeDB() {

        final DBIFactory dbiFactory = new DBIFactory();
        dbi = dbiFactory.create();
        runFlywayMigration();
    }

    private static void runFlywayMigration() {
        Flyway flyway = new Flyway();
        String url = String.format("jdbc:postgresql://%s:%s/%s", "127.0.0.1", "5432", "testgrpctestdb");
        flyway.setDataSource(url,"postgres", "postgres");
        flyway.setSchemas("flyway-schema-test-db");
        flyway.migrate();
    }

    public static DBI getDBI() {
        return dbi;
    }


}
