package com.babsari.firebasecloudmessage;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class HabitNetNotification implements Serializable {
    @SerializedName("notification")
    ArrayList<HabitDataNotification> notification = new ArrayList<>();

    @Override
    public String toString() {
        return "{" +
                "notification=" + notification +
                '}';
    }
}
