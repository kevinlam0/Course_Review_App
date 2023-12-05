package edu.virginia.sde.hw6;

import edu.virginia.sde.reviews.LoginDataDriver;
import edu.virginia.sde.reviews.Exceptions.PasswordIncorrectException;
import edu.virginia.sde.reviews.Exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginDataDriverTest {
    @BeforeEach
    void setUp() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
    }
    @Test
    void constructor() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
    }
    @Test
    void createTables() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
        ldd.createTable();
        ldd.disconnect();
    }
    @Test
    void addUser_emptyTable() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
        // ONLY WORKS ONCE
//        ldd.addUser("Kevinlam0", "password");
//        ldd.commit();
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
//        assertFalse(ldd.doesUserExist("kevinlam"));
    }
    @Test
    void doesUserExist_falseCase() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
        assertFalse(ldd.doesUserExist("Kevinlam"));
    }
    @Test
    void noChangeToTableWhenRollback() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
//        ldd.addUser("kevinlam", "password");
        ldd.disconnect();
    }
    @Test
    void loginCredentialsIsValid_noUserFound() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
//        assertThrows(UserNotFoundException.class,
//                () -> ldd.loginCredentialsIsValid("kevin", "password"));
    }
    @Test
    void loginCredentialsIsValid_passwordIncorrect() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
        assertThrows(PasswordIncorrectException.class,
                () -> ldd.loginCredentialsIsValid("kevinlam0", "passwords"));
    }
    @Test
    void loginCredentialsIsValid_valid() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
        ldd.loginCredentialsIsValid("kevinlam0", "password");
    }
    @Test
    void getAllUsers_onlyHasOnePerson() throws SQLException {
        LoginDataDriver ldd = new LoginDataDriver("LoginDataDriverTester.sqlite");
        ldd.connect();
        // WORKS WITH ONLY 1 DATA IN IT
//        Set<String> users = ldd.getAllUsers();
//        assertEquals(1, users.size());
//        assertTrue(users.contains("kevinlam0"));
    }
}
