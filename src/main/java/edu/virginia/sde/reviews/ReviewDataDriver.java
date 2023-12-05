package edu.virginia.sde.reviews;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        this.commit();
    }

    public ArrayList<Review> findReviewsByUsernameAndCourse(String username, int courseID) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Reviews WHERE Username = ? AND Course_ID = ?");
        statement.setString(1, username);
        statement.setInt(2, courseID);
        ResultSet results = statement.executeQuery();

        ArrayList<Review> reviews = new ArrayList<>();
        while(results.next()){
            Review review = new Review(
                    results.getInt(2),
                    results.getString(3),
                    results.getString(4),
                    results.getString(5),
                    results.getInt(6)
            );
            reviews.add(review);
        }
        statement.close();
        return reviews;

    }
    public ArrayList<Review> findReviewsByUsername(String username) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Reviews WHERE Username = ?");
        statement.setString(1, username);
        ResultSet results = statement.executeQuery();

        ArrayList<Review> reviews = new ArrayList<>();
        while(results.next()){
            Review review = new Review(
                    results.getInt(2),
                    results.getString(3),
                    results.getString(4),
                    results.getString(5),
                    results.getInt(6)
            );
            reviews.add(review);
        }
        statement.close();
        return reviews;

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
            this.commit();
        }
        catch (SQLException e) {
            super.rollback();
            throw e;
        }
    }
    public ArrayList<Review> findAllReviewsForCourse(int courseID) throws SQLException {
        // NEEDS CHECKER TO MAKE SURE THIS ID IS IN THE COURSE
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Reviews WHERE Course_ID = ?");
        statement.setInt(1, courseID);
        ResultSet results = statement.executeQuery();


        ArrayList<Review> reviews = new ArrayList<>();


        while (results.next()) {
            Review review = new Review(
                    results.getInt(2),
                    results.getString(3),
                    results.getString(4),
                    results.getString(5),
                    results.getInt(6)
            );
            reviews.add(review);
        }
        statement.close();
        return reviews;
    }

    public void deleteReview(int courseID, String username) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Reviews WHERE Course_ID = ? AND Username = ?");
        statement.setInt(1, courseID);
        statement.setString(2, username);
        statement.execute();
        statement.close();
        this.commit();
    }

    public void updateReview(int newRating, String newComment, int courseID, String username) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE Reviews SET Time = CURRENT_TIMESTAMP, Review = ?, Rating = ? WHERE Course_ID = ? AND Username = ?");
        statement.setString(1, newComment);
        statement.setInt(2, newRating);
        statement.setInt(3, courseID);
        statement.setString(4, username);
        statement.execute();
        statement.close();
        this.commit();
    }
    private boolean isEmpty(ResultSet resultSet) throws SQLException {
        return !resultSet.isBeforeFirst() && resultSet.getRow() == 0;
    }
}

