package edu.virginia.sde.hw6;

import edu.virginia.sde.reviews.ReviewDataDriver;
import edu.virginia.sde.reviews.ReviewLogic;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class ReviewLogicTest {
    @Test
    void addReview() throws SQLException {
        ReviewDataDriver rdd = new ReviewDataDriver("LocalDatabase.sqlite");
        ReviewLogic.setReviewDataDriver(rdd);
//        ReviewLogic.addReview("yamom", 1, 4, "meh");
    }
}
