package edu.virginia.sde.hw6;

import edu.virginia.sde.reviews.CourseLogic;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseLogicTest {
    @Test
    void calculateReviewAverage() throws SQLException {
        CourseLogic courseLogic = new CourseLogic("LoginDataDriverTester.sqlite");
        assertEquals(4.00, courseLogic.calculateReviewAverage(1));

    }
}
