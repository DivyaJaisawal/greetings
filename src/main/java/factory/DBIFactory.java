package factory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.skife.jdbi.v2.DBI;

public class DBIFactory {
    private static HikariDataSource dataSource = null;

    public DBI create() {
        HikariDataSource dataSource = getDataSource();
        return new DBI(dataSource);
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    private synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            dataSource = createDataSource();
        }
        return dataSource;
    }

    private HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(getDatabaseConnectionUrl());
        config.setLeakDetectionThreshold(60000);
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setMaximumPoolSize(2);
        return new HikariDataSource(config);
    }

    public String getDatabaseConnectionUrl() {
        return String.format("jdbc:postgresql://%s:%s/%s", "127.0.0.1", "5432", "testgrpcdevdb");
    }
}
