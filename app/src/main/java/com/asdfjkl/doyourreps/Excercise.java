package com.asdfjkl.doyourreps;

public class Excercise {

    public String name;
    public int overallReps;

    public Excercise(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
