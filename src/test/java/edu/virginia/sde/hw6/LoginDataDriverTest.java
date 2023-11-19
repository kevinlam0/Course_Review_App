package edu.virginia.sde.hw6;

import edu.virginia.sde.reviews.LoginDataDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginDataDriverTest {
    @BeforeEach
    void setUp() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
    }
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
    @Test
    void addUser_emptyTable() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
        ldd.addUser("kevinlam0", "password");
        ldd.commit();
    }
    @Test
    void doesUserExist_true() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
        assertTrue(ldd.doesUserExist("kevinlam0"));
    }
    @Test
    void doesUserExist_false() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
        assertFalse(ldd.doesUserExist("kevinlam"));
    }
    @Test
    void doesUserExist_falseCase() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
        assertFalse(ldd.doesUserExist("Kevinlam"));
    }
}
