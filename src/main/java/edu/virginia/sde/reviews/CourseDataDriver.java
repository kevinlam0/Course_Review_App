package edu.virginia.sde.reviews;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseDataDriver extends DatabaseDriver{

    public CourseDataDriver(String sqliteFileName) { super(sqliteFileName); }
    public void createTable () throws SQLException {
        String query = """
                CREATE TABLE IF NOT EXISTS Courses
                (
                    ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    Mnemonic TEXT(4) CHECK(column1 >= 0 AND column1 <= 100),
                    Course_Number INTEGER(4) CHECK(column1 >= 0 AND column1 < 9999),
                    Course_Title TEXT(50),
                    UNIQUE (Mnemonic, Course_Number, Course_Title)
                );
                """;
        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute();
        statement.close();
    }
    public void addCourse(String mnemonic, int courseNumber, String courseTitle) throws SQLException {
        try {
            if (connection.isClosed()) {
                throw new IllegalStateException("Connection is not open");
            }
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Courses(Mnemonic, Course_Number, Course_Title) values (?, ?, ?)");
            statement.setString(1, mnemonic);
            statement.setInt(2, courseNumber);
            statement.setString(3, courseTitle);
            statement.execute();
            statement.close();
        }
        catch (SQLException e) {
            super.rollback();
            throw e;
        }
    }
}
