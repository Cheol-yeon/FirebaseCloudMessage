package com.babsari.firebasecloudmessage;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HabitDataNotificationSetting implements Serializable {
    @SerializedName("title")
    String title = "";

    @SerializedName("date")
    String date = ""; // YYYYMMDD

    @SerializedName("time")
    String time = ""; // hhmm

    public HabitDataNotificationSetting() { }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
