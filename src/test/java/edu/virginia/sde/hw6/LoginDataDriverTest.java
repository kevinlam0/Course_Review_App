package edu.virginia.sde.hw6;

import edu.virginia.sde.reviews.LoginDataDriver;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class LoginDataDriverTest {
    @Test
    void constructor() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
    }
    @Test
    void createTables() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
        ldd.createTable();
        ldd.commit();
    }
}
