package com.babsari.firebasecloudmessage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

    long mNow = System.currentTimeMillis();
    Date mDate = new Date(mNow);
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private String channelId = "alarm_channel";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent busRouteIntent = new Intent(context, MainActivity.class);

        int getId = intent.getIntExtra("id", 1);
        String getTitle = intent.getStringExtra("title");
        int getDate = intent.getIntExtra("date", 00000000);
        int getTime = intent.getIntExtra("time", 1234);
        Log.e("getId", "getId = " + getId + ", getTitle = " + getTitle + ", getDate = " + getDate + ", getTime = " + getTime);



        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(busRouteIntent);
        PendingIntent busRoutePendingIntent =
                stackBuilder.getPendingIntent( getId , PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context,channelId)
                .setSmallIcon(R.mipmap.ic_launcher).setDefaults(Notification.DEFAULT_ALL)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .setContentTitle(getDate + " / " + getTime)
                .setContentText(getTitle)
                .setContentIntent(busRoutePendingIntent);


        final NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(channelId,"Channel human readable title",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(getId,notificationBuilder.build());
        Log.e("AlarmSetter", "Alarm = " + getId + "번 째 " + getDate + " / " + getTime + "시 알람" + "울림" );
    }


}
