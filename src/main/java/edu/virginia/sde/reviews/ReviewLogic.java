package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewLogic {
    private static ReviewDataDriver reviewDataDriver;
    public static void editReview(int course_id, int rating, String comment) throws SQLException {
        String username = Credentials.getUsername();
        reviewDataDriver.connect();
        reviewDataDriver.deleteReview(course_id, username);
        reviewDataDriver.addReview(course_id, username, rating, comment);
        reviewDataDriver.disconnect();
    }

    public static ArrayList<Review> findReviewsByCurrentUser() throws SQLException {
        reviewDataDriver.connect();
        ArrayList<Review> reviews = reviewDataDriver.findReviewsByUsername(Credentials.getUsername());
        reviewDataDriver.disconnect();
        return reviews;
    }
    public static void addReview(String username, int course_id, int rating, String comment) throws SQLException {
        reviewDataDriver.connect();
        reviewDataDriver.addReview(course_id, username, rating, comment);
        reviewDataDriver.disconnect();
    }

    public static void setReviewDataDriver(ReviewDataDriver rdd) {
        reviewDataDriver = rdd;
    }
}
