package edu.virginia.sde.reviews;

public class Course {

    private final int id;
    private final String mnemonic;
    private final int number;
    private final String title;
    private double average;

    public Course(int id, String mnemonic, int num, String title, double average){
        this.id = id;
        this.mnemonic = mnemonic;
        this.number = num;
        this.title = title;
        this.average = average;
    }

    public int getId() {
        return id;
    }
    public String getMnemonic() {
        return mnemonic;
    }

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public double getAverage() {
        return average;
    }
    public String toString() {
        return "Primary ID: " + id + "\nCourse: " + mnemonic + number + " " + title + " Rating: " + average;
    }
}
