package edu.virginia.sde.reviews;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDriver {
    private final String sqliteFile;
    protected Connection connection;
    public DatabaseDriver(String sqliteFileName) { this.sqliteFile = sqliteFileName; }
    public void connect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            throw new IllegalStateException("The connection is already opened.");
        }
        connection = DriverManager.getConnection("jdbc:sqlite:" + this.sqliteFile);
        connection.createStatement().execute("PRAGMA foreign_keys = ON");
        connection.setAutoCommit(false);
    }
    public void commit() throws SQLException { connection.commit(); }
    public void rollback() throws SQLException { connection.rollback(); }
    public void disconnect() throws SQLException { connection.close(); }
}
