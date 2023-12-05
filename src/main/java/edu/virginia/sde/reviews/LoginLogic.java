package edu.virginia.sde.reviews;

import edu.virginia.sde.reviews.Exceptions.PasswordIncorrectException;
import edu.virginia.sde.reviews.Exceptions.UserAlreadyExistsException;
import edu.virginia.sde.reviews.Exceptions.UserNotFoundException;

import java.sql.SQLException;

public class LoginLogic {

    private static LoginDataDriver loginDataDriver;
    public static void createUser(String username, String password) throws SQLException {
        loginDataDriver.connect();
        if (loginDataDriver.doesUserExist(username)) { throw new UserAlreadyExistsException();}
        loginDataDriver.addUser(username, password);
        loginDataDriver.disconnect();
    }
    public static void isLoginSuccessful(String username, String password) throws SQLException {
        loginDataDriver.connect();
        try {
            loginDataDriver.loginCredentialsIsValid(username, password);
        }
        catch (PasswordIncorrectException e){
            throw new PasswordIncorrectException("");
        }
        catch (UserNotFoundException e) {
            throw new UserNotFoundException("");
        }
        finally {
            loginDataDriver.disconnect();
        }
        Credentials.setUsername(username);
    }
    public static void setLoginDataDriver(LoginDataDriver loginDataDriver) throws SQLException {
        LoginLogic.loginDataDriver = loginDataDriver;
    }
}

