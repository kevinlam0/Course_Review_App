package edu.virginia.sde.reviews;

public class Course {

    private int id;
    private String mnemonic;

    private int num;
    private String title;
    private double average;

    public Course(int id, String mnemonic, int num, String title, double average){
        this.id = id;
        this.mnemonic = mnemonic;
        this.num = num;
        this.title = title;
        this.average = average;
    }

    public int getId() {
        return id;
    }
    public String getMnemonic() {
        return mnemonic;
    }

    public int getNum() {
        return num;
    }

    public String getTitle() {
        return title;
    }

    public double getAverage() {
        return average;
    }
}
