package edu.virginia.sde.reviews;

public class Review {

    int courseID;
    String username;
    String datetime;
    int rating;
    String comment;

    public Review(int courseID, String username, String datetime, int rating, String comment){
        this.courseID = courseID;
        this.username = username;
        this.datetime = datetime;
        this.rating = rating;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }
    public String toString() {
        return courseID + " " + username +" "+datetime+" "+ rating + " " + comment;
    }
}
