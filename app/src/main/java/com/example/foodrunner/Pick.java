package com.example.foodrunner;

public class Pick {
    String date;
    String time;

    public Pick(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public Pick() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
