package edu.virginia.sde.hw6;

import edu.virginia.sde.reviews.CourseDataDriver;
import edu.virginia.sde.reviews.ReviewDataDriver;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewDataDriverTest {
    @Test
    void connect() throws SQLException {
        ReviewDataDriver cdd = new ReviewDataDriver("LoginDataDriverTester.sqlite");
        cdd.connect();
        cdd.createTable();
        cdd.commit();
    }

    @Test
    void addReview_empty() throws SQLException {
        ReviewDataDriver cdd = new ReviewDataDriver("LoginDataDriverTester.sqlite");
        cdd.connect();
        cdd.addReview(1, "Kevinlam0", 5, "comment");
        cdd.commit();
    }
    @Test
    void addReview_populated() throws SQLException {
        ReviewDataDriver cdd = new ReviewDataDriver("LoginDataDriverTester.sqlite");
        cdd.connect();
        cdd.addReview(1, "kevinlam0", 3, "Cool");
        cdd.commit();
    }


}
