package edu.virginia.sde.reviews;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LoginDataDriver extends DatabaseDriver{
    private final String createUserTableSQL = """
            CREATE TABLE IF NOT EXISTS Users
            (
                Username TEXT primary key,
                Password TEXT
            );
            """;
    public LoginDataDriver(String sqliteFileName) { super(sqliteFileName); }
    public void createTable() throws SQLException {
        super.connection.prepareStatement(createUserTableSQL);
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

}
