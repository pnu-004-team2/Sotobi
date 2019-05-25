package com.example.kimgyutae.sotobi;

import android.graphics.drawable.Drawable;

public class ListViewItem {

    private String timeStr ;
    private String howStr ;
    private String mpStr ;
    private String pointStr ;

    public void setTime(String time) {
        timeStr = time ;
    }
    public void setHow(String how) {
        howStr = how ;
    }
    public void setMp(String mp) {
        mpStr = mp ;
    }
    public void setpoint(String point) {
        pointStr = point ;
    }

    public String getTime() {
        return timeStr;
    }
    public String getHow() {
        return howStr;
    }
    public String getMp() {
        return mpStr;
    }
    public String getpoint() {
        return pointStr;
    }

}