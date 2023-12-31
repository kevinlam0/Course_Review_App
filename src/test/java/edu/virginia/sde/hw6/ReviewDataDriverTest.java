package edu.virginia.sde.hw6;

import edu.virginia.sde.reviews.Course;
import edu.virginia.sde.reviews.CourseDataDriver;
import edu.virginia.sde.reviews.Review;
import edu.virginia.sde.reviews.ReviewDataDriver;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewDataDriverTest {
    @Test
    void connect() throws SQLException {
        ReviewDataDriver cdd = new ReviewDataDriver("LocalDatabase.sqlite");
        cdd.connect();
        cdd.createTable();
    }

    @Test
    void addReview_empty() throws SQLException {
        ReviewDataDriver cdd = new ReviewDataDriver("LocalDatabase.sqlite");
        cdd.connect();
//        cdd.addReview(1, "Kevinlam0", 5, "comment");
    }
    @Test
    void addReview_populated() throws SQLException {
        ReviewDataDriver cdd = new ReviewDataDriver("LocalDatabase.sqlite");
        cdd.connect();
//        cdd.addReview(1, "yamom", 3, "Cool");
    }
    @Test
    void findAllReviewForCourse_existingCourse() throws SQLException {
        ReviewDataDriver cdd = new ReviewDataDriver("LocalDatabase.sqlite");
        cdd.connect();
        ArrayList<Review> courses = cdd.findAllReviewsForCourse(1);
        courses.forEach(System.out::println);
    }
    @Test
    void findAllReviewForCourse_nonexistingCourse() throws SQLException {
        ReviewDataDriver cdd = new ReviewDataDriver("LocalDatabase.sqlite");
        cdd.connect();
//        assertThrows(RuntimeException.class, () -> cdd.findAllReviewsForCourse(2));
    }

    @Test
    void deleteReview_test() throws SQLException {
        ReviewDataDriver cdd = new ReviewDataDriver("LocalDatabase.sqlite");
        cdd.connect();
//        cdd.deleteReview(1, "yamom");
    }


}
