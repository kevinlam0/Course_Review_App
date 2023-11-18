package edu.virginia.sde.reviews;

import java.sql.SQLException;

public class LoginDataDriver extends DatabaseDriver{
    private final String createUserTableSQL = """
            CREATE TABLE IF NOT EXISTS Users
            (
                Username TEXT primary key,
                Password TEXT
            );
            """;
    public LoginDataDriver(String sqliteFileName) {
        super(sqliteFileName);
    }
    public void createTable() throws SQLException {
        super.connection.prepareStatement(createUserTableSQL);
    }

}
