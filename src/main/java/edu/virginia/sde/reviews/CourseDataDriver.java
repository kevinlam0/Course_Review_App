package edu.virginia.sde.reviews;
import edu.virginia.sde.reviews.Exceptions.InvalidCourseException;

import java.util.ArrayList;
import java.util.Optional;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDataDriver extends DatabaseDriver{

    public CourseDataDriver(String sqliteFileName) { super(sqliteFileName); }
    public void createTable() throws SQLException {
        String query = """
                CREATE TABLE IF NOT EXISTS Courses
                (
                    ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    Mnemonic TEXT(4) CHECK(LENGTH(Mnemonic) <= 4),
                    Course_Number INTEGER(4) CHECK(Course_Number >= 0 AND Course_Number <= 9999),
                    Course_Title TEXT(50) CHECK(LENGTH(Course_Title) <= 50),
                    Rating DECIMAL(3,2),
                    UNIQUE (Mnemonic, Course_Number, Course_Title)
                );
                """;
        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute();
        statement.close();
    }
    public void addCourse(String mnemonic, int courseNumber, String courseTitle) throws SQLException {
        if (mnemonic.length() < 1 || mnemonic.length() > 4) {
            throw new InvalidCourseException("The mnemonic cannot be blank nor longer than four characters");
        }
        if (courseNumber > 9999 || courseNumber < 0) {
            throw new InvalidCourseException("The course number must be a positive 4-digit number");
        }
        if (courseTitle.length() > 50) {
            throw new InvalidCourseException("The course title cannot have more than 50 characters (including spaces).");
        }

        try {
            if (connection.isClosed()) {
                throw new IllegalStateException("Connection is not open");
            }
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Courses(Mnemonic, Course_Number, Course_Title, Rating) values (?, ?, ?, ?)");
            statement.setString(1, mnemonic.toUpperCase());
            statement.setInt(2, courseNumber);
            statement.setString(3, courseTitle);
            statement.setDouble(4, 0.00);
            statement.execute();
            statement.close();
        }
        catch (SQLException e) {
            super.rollback();
            throw e;
        }
    }

    /* Used for querying for the main screen */
    public ArrayList<Course> getAllCourses() throws SQLException{
        if (connection.isClosed()) {
            throw new IllegalStateException("Connection is not open");
        }
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Courses");
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Course> courses = new ArrayList<>();
        while (resultSet.next()) {
            Course course = new Course(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );
            courses.add(course);
        }
        statement.close();
        return courses;
    }

    public Optional<Course> selectCourseByID(int id) throws SQLException{

        if (connection.isClosed()) {
            throw new IllegalStateException("Connection is not open");
        }
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Courses WHERE ID = ?");
        statement.setInt(id, 1);
        ResultSet resultSet = statement.executeQuery();

        if (isEmpty(resultSet)) {
            statement.close();
            return Optional.empty();
        }

        Course course = new Course(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getInt(3),
                resultSet.getString(4),
                resultSet.getDouble(5)
        );

        statement.close();

        return Optional.of(course);
    }

    public ArrayList<Course> searchCourses(String mnemonic, Integer number, String title) throws SQLException{
        if (connection.isClosed()) {
            throw new IllegalStateException("Connection is not open");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM Courses WHERE 1");
        // Needs to be case-insensitive
        if (mnemonic != null){
            mnemonic = mnemonic.toUpperCase();
            sql.append(" AND mnemonic = '").append(mnemonic).append("'");
        }
        if (number != null){
            sql.append(" AND Course_Number = ").append(String.valueOf(number));
        }
        // Needs to be case-insensitive
        if (title != null){
            sql.append(" AND Course_Title LIKE '").append("%").append(title).append("%'").append("COLLATE NOCASE");
        }
        PreparedStatement statement = connection.prepareStatement(String.valueOf(sql));

        ResultSet resultSet = statement.executeQuery();

        ArrayList<Course> courses = new ArrayList<>();
        while (resultSet.next()) {
            Course course = new Course(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );

            courses.add(course);
        }
        statement.close();

        return courses;
    }

    private boolean isEmpty(ResultSet resultSet) throws SQLException {
        return !resultSet.isBeforeFirst() && resultSet.getRow() == 0;
    }
}
