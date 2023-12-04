package edu.virginia.sde.reviews;

import edu.virginia.sde.reviews.Exceptions.UserAlreadyExistsException;

import java.sql.SQLException;

public class LoginLogic {

    private static LoginDataDriver loginDataDriver;
    public static void createUser(String username, String password) throws SQLException {
        if (loginDataDriver.doesUserExist(username)) { throw new UserAlreadyExistsException();}
        loginDataDriver.addUser(username, password);
    }
    public static boolean isLoginSuccessful(String username, String password) throws SQLException {
        loginDataDriver.loginCredentialsIsValid(username, password);
        UserLogic.setUsername(username);
        return true;
    }

    public static void setLoginDataDriver(LoginDataDriver loginDataDriver) throws SQLException {
        LoginLogic.loginDataDriver = loginDataDriver;
        LoginLogic.loginDataDriver.connect();
    }
}

