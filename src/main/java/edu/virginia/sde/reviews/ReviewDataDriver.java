package edu.virginia.sde.reviews;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReviewDataDriver extends DatabaseDriver{

    public ReviewDataDriver(String sqliteFileName) {super(sqliteFileName);}
    public void createTable() throws SQLException {
        String query = """
                CREATE TABLE IF NOT EXISTS Reviews
                (
                    ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    Course_ID Integer,
                    Username Text,
                    Time DATETIME,
                    Review TEXT,
                    Rating INTEGER, 
                    FOREIGN KEY (Course_ID) REFERENCES Courses(ID) ON DELETE CASCADE,
                    FOREIGN KEY (Username) REFERENCES Users(Username) ON DELETE CASCADE,
                    UNIQUE (Course_ID, Username)
                );
                """;
        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute();
        statement.close();
    }


    public void addReview(int courseID, String username, int rating, String comment) throws SQLException {
        try {
            if (connection.isClosed()) {
                throw new IllegalStateException("Connection is not open");
            }
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Reviews(Course_ID, Username, Time, Review, Rating) values (?, ?, CURRENT_TIMESTAMP, ?, ?)");
            statement.setInt(1, courseID);
            statement.setString(2, username);
            statement.setString(3, comment);
            statement.setInt(4, rating);
            statement.execute();
            statement.close();
        }
        catch (SQLException e) {
            super.rollback();
            throw e;
        }
    }
}

