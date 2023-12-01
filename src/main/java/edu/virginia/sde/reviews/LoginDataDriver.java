package edu.virginia.sde.reviews;

import edu.virginia.sde.reviews.Exceptions.PasswordIncorrectException;
import edu.virginia.sde.reviews.Exceptions.UserNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LoginDataDriver extends DatabaseDriver{
    private static final String createUserTableSQL = """
            CREATE TABLE IF NOT EXISTS Users
            (
                Username TEXT primary key,
                Password TEXT
            );
            """;
    public LoginDataDriver(String sqliteFileName) { super(sqliteFileName); }
    public void createTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(createUserTableSQL);
        statement.execute();
        statement.close();
    }
    public Set<String> getAllUsers() throws SQLException {
        if (super.connection.isClosed()) {
            throw new IllegalStateException("The connection is not open. You cannot get the users.");
        }
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users");
        Set<String> users = new HashSet<>();
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String user = resultSet.getString(1);
            users.add(user);
        }
        statement.close();
        return users;
    }
    public void loginCredentialsIsValid(String username, String password) throws SQLException {
        if (connection.isClosed()) {
            throw new IllegalStateException("Connection is not open.");
        }
        // Execute query
        PreparedStatement statement = connection.prepareStatement(("SELECT * FROM Users WHERE Username = ?"));
        statement.setString(1, username);
        ResultSet resultset = statement.executeQuery();

        // If none exists
        if (isEmpty(resultset)) {
            statement.close();
            throw new UserNotFoundException("This username does not exist");
        }

        // Password is incorrect
        String pass = resultset.getString(2);
        if (!pass.equals(password)) {
            throw new PasswordIncorrectException("The password is incorrect");
        }

    }
    public boolean doesUserExist(String username) throws SQLException {
        if (connection.isClosed()) {
            throw new IllegalStateException("Connection is not open");
        }
        PreparedStatement statement = connection.prepareStatement(
                ("SELECT * FROM Users WHERE Username = ?"));
        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();
        return !isEmpty(resultSet);
    }
    private boolean isEmpty(ResultSet resultSet) throws SQLException {
        return !resultSet.isBeforeFirst() && resultSet.getRow() == 0;
    }
    public void addUser(String username, String password) throws SQLException {
        try {
            if (connection.isClosed()) {
                throw new IllegalStateException("Connection is not open");
            }
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Users(Username, Password) values (?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.execute();
            statement.close();
        }
        catch (SQLException e) {
            super.rollback();
            throw e;
        }
    }
}
