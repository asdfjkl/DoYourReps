package com.asdfjkl.doyourreps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.text.format.DateFormat;

public class Excercise {

    public String name = "";
    public int currentLevel; // reps computed from currentLevel
    public int weekStep; //  three steps (=1 Week) per currentLevel
    public int overallCount;
    public int monthCount;
    public int yearCount;
    public String thisYear = "";
    public String thisMonth = "";

    public Excercise(String name) {
        this.name = name;
        this.currentLevel = 0;
        this.weekStep = 0;
        this.overallCount = 0;
        this.monthCount = 0;
        this.yearCount = 0;
        this.thisYear = Excercise.getCurrentYear();
        this.thisMonth = Excercise.getCurrentMonth();
    }

    public void reset() {
        this.currentLevel = 0;
        this.weekStep = 0;
        this.overallCount = 0;
        this.monthCount = 0;
        this.yearCount = 0;
        this.thisYear = Excercise.getCurrentYear();
        this.thisMonth = Excercise.getCurrentMonth();
    }

    public void restore(String s) {
        String items[] = s.split("\\|");
        if(items.length == 8) {
            this.name = items[0];
            this.thisMonth = items[1];
            this.monthCount = Integer.parseInt(items[2]);
            this.thisYear = items[3];
            this.yearCount = Integer.parseInt(items[4]);
            this.overallCount = Integer.parseInt(items[5]);
            this.weekStep = Integer.parseInt(items[6]);
            this.currentLevel = Integer.parseInt(items[7]);
        }
    }

    public String store() {
        String s = "";
        s += this.name + "|";
        s += this.thisMonth + "|" + this.monthCount + "|";
        s += this.thisYear + "|" + this.yearCount + "|";
        s += this.overallCount + "|";
        s += this.weekStep + "|";
        s += this.currentLevel;
        return s;
    }

    @Override
    public String toString() {
        return name;
    }

    public ArrayList<Integer> getCurrentWorkoutSteps() {

        int avg_per_set_one = (int) Math.round(currentLevel * 0.7);
        int overall_one = avg_per_set_one * 5;
        int overall_two = (int) Math.round((avg_per_set_one * 5.0) * 1.05);
        int avg_per_set_two = (int) Math.round(overall_two / 5.0);
        int overall_three = (int) Math.round((avg_per_set_one * 5) * 1.10);
        int avg_per_set_three = (int) Math.round(overall_three / 5);

        ArrayList<Integer> set1 = new ArrayList<>();
        ArrayList<Integer> set2 = new ArrayList<>();
        ArrayList<Integer> set3 = new ArrayList<>();

        set1.add(avg_per_set_one);
        set2.add(avg_per_set_two);
        set3.add(avg_per_set_three);

        set1.add((int) Math.round(avg_per_set_one * 1.15));
        set2.add((int) Math.round(avg_per_set_two * 1.15));
        set3.add((int) Math.round(avg_per_set_three * 1.15));

        set1.add((int) Math.round(avg_per_set_one * 0.8));
        set2.add((int) Math.round(avg_per_set_two * 0.8));
        set3.add((int) Math.round(avg_per_set_three * 0.8));

        set1.add(avg_per_set_one);
        set2.add(avg_per_set_two);
        set3.add(avg_per_set_three);

        set1.add((int) Math.round(avg_per_set_one * 1.05));
        set2.add((int) Math.round(avg_per_set_two * 1.05));
        set3.add((int) Math.round(avg_per_set_three * 1.05));

        if(weekStep == 1) {
            return set1;
        }

        if(weekStep == 2) {
            return set2;
        }

        if(weekStep == 3) {
            return set3;
        }

        return set1;
    }

    public static String getCurrentMonth() {
        Date date = new Date();
        String monthNumber  = (String) DateFormat.format("MM",   date);
        return monthNumber;
    }

    public static String getCurrentYear() {
        Date date = new Date();
        String year = (String) DateFormat.format("yyyy", date);
        return year;
    }

}
