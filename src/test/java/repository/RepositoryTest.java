package repository;

import org.junit.Before;
import org.junit.BeforeClass;
import org.skife.jdbi.v2.DBI;

public class RepositoryTest {

    protected static DBI dbi;

    @BeforeClass
    public static void beforeClass() {
        dbi = DBSetup.getDBI();
    }

    @Before
    public void before() {
        initializeTestData();
    }

    private void initializeTestData() {
        dbi.withHandle(handle -> {
            handle.createScript("clearTables.sql").execute();
            handle.createScript("seed.sql").execute();
            return null;
        });
    }

}
