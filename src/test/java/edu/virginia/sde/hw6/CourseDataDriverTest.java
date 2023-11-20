package edu.virginia.sde.hw6;

import edu.virginia.sde.reviews.CourseDataDriver;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class CourseDataDriverTest {
    @Test
    void connect() throws SQLException {
        CourseDataDriver cdd = new CourseDataDriver("LoginDataDriverTester.sqlite");
        cdd.connect();
        cdd.createTable();
        cdd.commit();
    }
    @Test
    void addCourse_empty() throws SQLException {
        CourseDataDriver cdd = new CourseDataDriver("LoginDataDriverTester.sqlite");
        cdd.connect();
//        cdd.addCourse("CS", 3140, "Software Development");
//        cdd.commit();
    }
    @Test
    void addCourse_sameMnemonic() throws SQLException {
        CourseDataDriver cdd = new CourseDataDriver("LoginDataDriverTester.sqlite");
        cdd.connect();
//        cdd.addCourse("CS", 3100, "Data Structures and Algorithms");
//        cdd.commit();
    }
    @Test
    void addCourse_longerThan4Char() throws SQLException {
        CourseDataDriver cdd = new CourseDataDriver("LoginDataDriverTester.sqlite");
        cdd.connect();
        assertThrows(SQLException.class, () -> cdd.addCourse("CSSSS", 3140, "Software Development"));
    }
    @Test
    void addCourse_longerThan4CourseNumber() throws SQLException {
        CourseDataDriver cdd = new CourseDataDriver("LoginDataDriverTester.sqlite");
        cdd.connect();
        assertThrows(SQLException.class, () -> cdd.addCourse("CSSS", 10000, "Software Development"));
    }
    @Test
    void addCourse_0CourseNumber() throws SQLException {
        CourseDataDriver cdd = new CourseDataDriver("LoginDataDriverTester.sqlite");
        cdd.connect();
//        cdd.addCourse("CSSS", 0, "Software Development");
//        cdd.commit();
    }
    @Test
    void addCourse_9999CourseNumber() throws SQLException {
        CourseDataDriver cdd = new CourseDataDriver("LoginDataDriverTester.sqlite");
        cdd.connect();
//        cdd.addCourse("CSSS", 9999, "Software Development");
//        cdd.commit();
    }

}
