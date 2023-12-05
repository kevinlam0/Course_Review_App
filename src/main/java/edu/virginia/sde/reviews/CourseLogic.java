package edu.virginia.sde.reviews;

import edu.virginia.sde.reviews.Exceptions.InvalidCourseException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static java.lang.Math.round;

public class CourseLogic {
    private static CourseDataDriver courseDataDriver;
    private static ReviewDataDriver reviewDataDriver;
    private static int currentCourse;

    public static int getCurrentCourseId() {
        return currentCourse;
    }

    public static void setCurrentCourse(int currentCourse) {
        CourseLogic.currentCourse = currentCourse;
    }

    public static void addCourse(String mnemonic, int courseNumber, String courseTitle) throws SQLException {
        String[] words = mnemonic.split(" ");
        if (words.length > 1) {
            throw new InvalidCourseException("The mnemonic cannot have a space.");
        }
        if (mnemonic.length() < 2 || mnemonic.length() > 4) {
            throw new InvalidCourseException("The mnemonic cannot be blank nor longer than four characters");
        }

        if (courseNumber > 9999 || courseNumber < 0) {
            throw new InvalidCourseException("The course number must be a positive 4-digit number");
        }
        if (courseTitle.length() > 50) {
            throw new InvalidCourseException("The course title cannot have more than 50 characters (including spaces).");
        }
        if (courseTitle.length() == 0) {
            throw new InvalidCourseException("The course title cannot be blank.");
        }
        try {
            courseDataDriver.connect();
            courseDataDriver.addCourse(mnemonic.toUpperCase(), courseNumber, courseTitle);
        }
        catch (SQLException e) { e.printStackTrace(); }
        catch (InvalidCourseException e) { throw new InvalidCourseException(e.getMessage()); }
        finally { courseDataDriver.disconnect(); }
    }
    public static ArrayList<Course> getAllCourses() throws SQLException {
        courseDataDriver.connect();
        ArrayList<Course> courses = courseDataDriver.getAllCourses();
        courseDataDriver.disconnect();
        return courses;
    }
    public static Course getCourseByID(int id) throws SQLException {
        courseDataDriver.connect();
        Optional<Course> course = courseDataDriver.selectCourseByID(id);
        courseDataDriver.disconnect();
        if (course.isEmpty()) {throw new InvalidCourseException("There is no course with this ID");}
        return course.get();
    }

    public static ArrayList<Course> filterCoursesBy (String mnemonic, Integer courseNumber, String courseTitle) throws SQLException {
        if (mnemonic.strip().equals("")) {mnemonic = null;}
        if (mnemonic != null) {
            if (mnemonic.strip().length() > 4) {
                throw new InvalidCourseException("You cannot have a mnemonic longer than four characters.");
            }
        }

        if (courseNumber == 0) { courseNumber = null; }
        else if (courseNumber < 0) {throw new InvalidCourseException("You cannot have a course number of negative value.");}

        if (courseTitle.strip().equals("")) { courseTitle = null; }
        courseDataDriver.connect();
        ArrayList<Course> courses = courseDataDriver.searchCourses(mnemonic, courseNumber, courseTitle);
        courseDataDriver.disconnect();
        return courses;
    }
    public static double calculateReviewAverage(int courseID) throws SQLException {
        ReviewDataDriver rdd = new ReviewDataDriver(Credentials.getSqliteDataName());
        rdd.connect();
        ArrayList<Review> reviews = rdd.findAllReviewsForCourse(courseID);
        rdd.disconnect();
        double total = 0.0;
        int count = 0;
        for (Review review : reviews) {
            total += review.getRating();
            count++;
        }
        if (count == 0) {return 0;}
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
    public static void setCourseDataDriver(CourseDataDriver courseDataDriver) throws SQLException {
        CourseLogic.courseDataDriver = courseDataDriver;

//        CourseLogic.courseDataDriver.connect();
    }
    public static void setReviewDataDriver(ReviewDataDriver reviewDataDriver) throws SQLException {
        CourseLogic.reviewDataDriver = reviewDataDriver;

//        CourseLogic.courseDataDriver.connect();
    }

    public static ArrayList<Review> getAllReviews() throws SQLException {
        reviewDataDriver.connect();
        ArrayList<Review> ret = reviewDataDriver.findAllReviewsForCourse(currentCourse);
        reviewDataDriver.disconnect();
        return ret;
    }

    public static Course getCurrentCourse() throws SQLException {
        courseDataDriver.connect();
        Course course = courseDataDriver.selectCourseByID(currentCourse).get();
        courseDataDriver.disconnect();
        return course;
    }

    public static void addReviewToCourse(int rating, String comment) throws SQLException {
        reviewDataDriver.connect();
        reviewDataDriver.addReview(currentCourse, Credentials.getUsername(),rating, comment);
        reviewDataDriver.disconnect();
        double avg = CourseLogic.calculateReviewAverage(currentCourse);
        courseDataDriver.connect();
        courseDataDriver.updateCourseAverage(currentCourse, avg);
        courseDataDriver.disconnect();
    }

    public static ArrayList<Review> getCurrentReview() throws SQLException {
        reviewDataDriver.connect();
        ArrayList<Review> reviews = reviewDataDriver.findReviewsByUsernameAndCourse(Credentials.getUsername(), currentCourse);
        reviewDataDriver.disconnect();
        return reviews;
    }

    public static void editCurrentReview(int newRating, String newComment) throws SQLException {
        reviewDataDriver.connect();
        reviewDataDriver.updateReview(newRating, newComment, currentCourse, Credentials.getUsername());
        reviewDataDriver.disconnect();
        double avg = CourseLogic.calculateReviewAverage(currentCourse);
        courseDataDriver.connect();
        courseDataDriver.updateCourseAverage(currentCourse, avg);
        courseDataDriver.disconnect();
    }


}
