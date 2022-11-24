package com.asdfjkl.doyourreps;

public class Excercise {

    public String name;
    public int currentLevel; // reps computed from currentLevel
    public int currentStep; //  three steps (=1 Week) per currentLevel
    public int overallCount;
    public int monthCount;
    public int yearCount;

    public Excercise(String name) {
        this.name = name;
        this.currentLevel = 0;
        this.currentStep = 0;
        this.overallCount = 0;
        this.monthCount = 0;
        this.yearCount = 0;
    }

    @Override
    public String toString() {
        return name;
    }

}
