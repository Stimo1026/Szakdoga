package com.application.appnimal;

public class Walk {

    private String date;
    private String length;
    private String petName;

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    private int steps;
    private int meters;

    public Walk() {
    }

    public Walk(String date, String petName, String length, int steps, int meters) {
        this.date = date;
        this.length = length;
        this.steps = steps;
        this.meters = meters;
        this.petName = petName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getMeters() {
        return meters;
    }

    public void setMeters(int meters) {
        this.meters = meters;
    }
}
