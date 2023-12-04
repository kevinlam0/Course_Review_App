package edu.virginia.sde.reviews;

import edu.virginia.sde.reviews.Exceptions.InvalidCourseException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static java.lang.Math.round;

public class CourseLogic {
    CourseDataDriver courseDataDriver;
    private final String sqlDatabaseFile;
    public CourseLogic(String sqliteFileName) {
        this.sqlDatabaseFile = sqliteFileName;
        this.courseDataDriver = new CourseDataDriver(sqlDatabaseFile);
    }
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
    public double calculateReviewAverage(int courseID) throws SQLException {
        ReviewDataDriver rdd = new ReviewDataDriver(this.sqlDatabaseFile);
        rdd.connect();
        ArrayList<Review> reviews = rdd.findAllReviewsForCourse(courseID);
        rdd.disconnect();
        double total = 0.0;
        int count = 0;
        for (Review review : reviews) {
            total += review.getRating();
            count++;
        }
        total = round(total/count, 2);
        return total;
    }

    //  URL: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
    //  Description: Used to make a method for rounding to two decimal places
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
