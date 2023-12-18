package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.util.Optional;

public class Review {

    int courseID;
    String username;
    String datetime;
    int rating;
    String comment;

    String courseMnemonic;
    String courseNumber;





    public Review(int courseID, String username, String datetime, String comment, int rating){
        this.courseID = courseID;
        this.username = username;
        this.datetime = datetime;
        this.rating = rating;
        this.comment = comment;
        CourseDataDriver cdd = new CourseDataDriver(Credentials.getSqliteDataName());

        try {
            cdd.connect();
            Course course = (cdd.selectCourseByID(courseID).get());
            courseNumber = course.getNumber();
            courseMnemonic = course.getMnemonic();
            cdd.disconnect();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public int getCourseID() {
        return courseID;
    }

    public int getRating() {
        return rating;
    }

    public String getUsername() {
        return username;
    }

    public String getDatetime() {
        return datetime;
    }
    public String getCourseMnemonic() {
        return courseMnemonic;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getComment() {
        return comment;
    }
    public String toString() {
        return "Review:\n"+courseID + " " + username +" "+datetime+" "+ rating + " " + comment;
    }
}
