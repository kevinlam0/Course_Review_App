package edu.virginia.sde.reviews;

import java.sql.SQLException;

public class LoginLogic {

    private LoginDataDriver loginDataDriver;
    public LoginLogic(String sqliteFileName) {this.loginDataDriver = new LoginDataDriver(sqliteFileName);}
    public void createUser(String username, String password) throws SQLException {
        if (loginDataDriver.doesUserExist(username)) { throw new UserAlreadyExistsException();}
        loginDataDriver.addUser(username, password);
    }
    public boolean isLoginSuccessful(String username, String password) throws SQLException {
        loginDataDriver.loginCredentialsIsValid(username, password);
        UserLogic.setUsername(username);
        return true;
    }
}

