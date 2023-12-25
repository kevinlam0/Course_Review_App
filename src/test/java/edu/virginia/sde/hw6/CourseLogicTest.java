package edu.virginia.sde.hw6;

import edu.virginia.sde.reviews.Course;
import edu.virginia.sde.reviews.CourseDataDriver;
import edu.virginia.sde.reviews.CourseLogic;
import edu.virginia.sde.reviews.Exceptions.InvalidCourseException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseLogicTest {
    @Test
    void calculateReviewAverage() throws SQLException {
        CourseDataDriver cdd = new CourseDataDriver("LocalDatabase.sqlite");
        CourseLogic.setCourseDataDriver(cdd);
//        assertEquals(3.67, CourseLogic.calculateReviewAverage(1));
    }
    @Test
    void getCourseByID_existingCourse() throws SQLException {
        CourseDataDriver cdd = new CourseDataDriver("LocalDatabase.sqlite");
        CourseLogic.setCourseDataDriver(cdd);
        Course course = CourseLogic.getCourseByID(1);
//        System.out.println(course);
    }
    @Test
    void getCourseByID_nonexistingCourse() throws SQLException {
        CourseDataDriver cdd = new CourseDataDriver("LocalDatabase.sqlite");
        CourseLogic.setCourseDataDriver(cdd);
//        assertThrows(InvalidCourseException.class, () -> CourseLogic.getCourseByID(4));
    }
    @Test
    void filterCourses() throws SQLException {
        CourseDataDriver cdd = new CourseDataDriver("LocalDatabase.sqlite");
        CourseLogic.setCourseDataDriver(cdd);
//        ArrayList<Course> courses = CourseLogic.filterCoursesBy("", null, "a");
//        courses.forEach(System.out::println);
    }
}
