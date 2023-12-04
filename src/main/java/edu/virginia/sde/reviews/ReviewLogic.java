package edu.virginia.sde.reviews;

import java.sql.SQLException;

public class ReviewLogic {
    private static ReviewDataDriver reviewDataDriver;
    public static void editReview(int course_id, int rating, String comment) throws SQLException {
        String username = Credentials.getUsername();
        reviewDataDriver.deleteReview(course_id, username);
        reviewDataDriver.addReview(course_id, username, rating, comment);
    }
}
