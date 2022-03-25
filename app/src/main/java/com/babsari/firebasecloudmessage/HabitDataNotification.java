package com.babsari.firebasecloudmessage;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class HabitDataNotification implements Serializable {
    @SerializedName("childId")
    String childId = "";
    @SerializedName("settings")
    ArrayList<HabitDataNotificationSetting> settings = new ArrayList<>();

    @Override
    public String toString() {
        return "{" +
                "childId='" + childId + '\'' +
                ", settings=" + settings +
                '}';
    }
}
