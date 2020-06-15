package com.example.kitkatcw;

public class Data {
    String Feed_Sleep, Duration, Time,Date, Month, Year;
//Creates Setters and getters for all the rows in the SQLite Database
    public Data(String feed_Sleep,String duration, String time, String date, String month, String year) {
        Feed_Sleep = feed_Sleep;
        Duration = duration;
        Time = time;
        Date = date;
        Month = month;
        Year = year;
    }

    public String getFeed_Sleep() {
        return Feed_Sleep;
    }

    public void setFeed_Sleep(String feed_Sleep) {
        Feed_Sleep = feed_Sleep;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }
}
