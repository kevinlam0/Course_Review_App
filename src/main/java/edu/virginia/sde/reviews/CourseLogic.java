package edu.virginia.sde.reviews;

import edu.virginia.sde.reviews.Exceptions.InvalidCourseException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CourseLogic {
    CourseDataDriver courseDataDriver;
    public CourseLogic(String sqliteFileName) { this.courseDataDriver = new CourseDataDriver(sqliteFileName);}
    public void addCourse(String mnemonic, int courseNumber, String courseTitle) throws SQLException {
        if (mnemonic.length() < 2 || mnemonic.length() > 4) {
            throw new InvalidCourseException("The mnemonic cannot be blank nor longer than four characters");
        }
        String courseNumberString = String.valueOf(courseNumber);
        if (courseNumberString.length() != 4) {
            throw new InvalidCourseException("The course number needs to be exactly 4-digits");
        }
        if (courseNumber > 9999 || courseNumber < 0) {
            throw new InvalidCourseException("The course number must be a positive 4-digit number");
        }
        if (courseTitle.length() > 50) {
            throw new InvalidCourseException("The course title cannot have more than 50 characters (including spaces).");
        }
        courseDataDriver.addCourse(mnemonic.toUpperCase(), courseNumber, courseTitle);
    }
    public ArrayList<Course> getAllCourses() throws SQLException {
        return courseDataDriver.getAllCourses();
    }
    public Optional<Course> getCourseByID(int id) throws SQLException {
        return courseDataDriver.selectCourseByID(id);
    }
    public ArrayList<Course> filterCoursesBy (String mnemonic, Integer courseNumber, String courseTitle) throws SQLException {
        return courseDataDriver.searchCourses(mnemonic, courseNumber, courseTitle);
    }

}
