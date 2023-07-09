package com.example.scheduleapp.model;

import android.os.Parcel;

import java.io.Serializable;

public class Lesson implements Serializable {
    private String date;
    private Less less;

    public Lesson(String date, Less less) {
        this.date = date;
        this.less = less;
    }

    protected Lesson(Parcel in) {
        date = in.readString();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Less getLess() {
        return less;
    }

    public void setLess(Less less) {
        this.less = less;
    }
}
