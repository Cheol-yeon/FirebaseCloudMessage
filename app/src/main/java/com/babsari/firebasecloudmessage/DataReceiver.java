package com.babsari.firebasecloudmessage;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.core.app.NotificationCompat;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

import com.babsari.firebasecloudmessage.MainActivity;
import com.babsari.firebasecloudmessage.R;

public class DataReceiver extends BroadcastReceiver {

    private Context context;

    long mNow = System.currentTimeMillis();
    Date mDate = new Date(mNow);
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        Log.e("DataReceiver()", "onReceive getValue");
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(GetDataWorkManager.class).build();
        WorkManager.getInstance(this.context).enqueue(oneTimeWorkRequest);

        Log.e("WorkInfo.State.values()", "WorkInfo.State.values() = " + WorkInfo.State.SUCCEEDED.isFinished());

        Log.e("DataReceiver", "DataReceiver's Get Alarm Data = " + mFormat.format(mDate));
    }

}