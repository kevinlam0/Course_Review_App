package edu.virginia.sde.reviews;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Course {

    private final int id;
    private final String mnemonic;
    private final int number;
    private final String title;
    private double average;

    private NumberFormat formatter = new DecimalFormat("#0.00");

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

    public String getNumber() {
        String num = String.valueOf(number);
        while (num.length() < 4){
            num = "0" + num;
        }
        return num;
    }

    public int getRealNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getAverage() {
        if (average < 1)
            return "";
        return formatter.format(average);
    }

    public double getActualAverage(){
        return average;
    }
    public String toString() {
        return "Primary ID: " + id + "\nCourse: " + mnemonic + number + " " + title + " Rating: " + average;
    }
}
