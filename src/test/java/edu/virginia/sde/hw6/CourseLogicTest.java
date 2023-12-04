package edu.virginia.sde.hw6;

import edu.virginia.sde.reviews.CourseDataDriver;
import edu.virginia.sde.reviews.CourseLogic;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseLogicTest {
    @Test
    void calculateReviewAverage() throws SQLException {
        CourseDataDriver cdd = new CourseDataDriver("LoginDataDriverTester.sqlite");
        CourseLogic.setCourseDataDriver(cdd);
        assertEquals(4.00, CourseLogic.calculateReviewAverage(1));

    }
}
