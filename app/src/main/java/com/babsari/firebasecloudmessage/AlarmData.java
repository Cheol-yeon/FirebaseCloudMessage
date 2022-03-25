package com.babsari.firebasecloudmessage;

public class AlarmData {

    private String time;
    private String date;
    private String title;

    public AlarmData() { }

    public AlarmData(String time, String date, String title) {
        this.time = time;
        this.date = date;
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "AlarmData{" +
                "time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
