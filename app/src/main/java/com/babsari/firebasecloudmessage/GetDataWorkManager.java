package com.babsari.firebasecloudmessage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDataWorkManager extends Worker {
    RetrofitService retrofitService;

    String Sdate, Stime, title;
    int date, time, year, month, day, hour, minute;
    Context context = MainActivity.context;

    AlarmData alarmData;
    ArrayList<AlarmData> alarmDataList;

    AlarmManager alarmManager;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferences_editor;

    int id;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public GetDataWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        id=1;

        Log.e("GetDataWorkManager()", "doWork");

        retrofitService = ApiRequest.getRetrofit().create(RetrofitService.class);
        Call<HabitNetNotification> call = retrofitService.getHabitNetNotification();

        call.enqueue(new Callback<HabitNetNotification>() {
            @Override
            public void onResponse(Call<HabitNetNotification> call, Response<HabitNetNotification> response) {
                HabitNetNotification habitNetNotification = response.body();
                ArrayList<HabitDataNotification> habitDataNotification = habitNetNotification.notification;

                for (int i = 0; i < habitDataNotification.size(); i++) {

                    alarmDataList = new ArrayList<>();

                    ArrayList<HabitDataNotificationSetting> habitDataNotificationSettings = habitDataNotification.get(i).settings;

                    Log.e("GetDataWorkManager", "habitDataNotification.size() = " + habitDataNotification.get(i));

                    for (int j = 0; j < habitDataNotificationSettings.size(); j++) {

                        alarmData = new AlarmData();

                        title = habitDataNotificationSettings.get(j).title;
                        Sdate = habitDataNotificationSettings.get(j).date;
                        Stime = habitDataNotificationSettings.get(j).time;

                        date = Integer.parseInt(Sdate);
                        time = Integer.parseInt(Stime);
                        year = date / 10000;
                        month = date % 10000 / 100 - 1;
                        day = date % 100;
                        hour = time / 100;
                        minute = time % 100;

                        alarmData.setDate(Sdate);
                        alarmData.setTime(Stime);
                        alarmData.setTitle(title);

                        alarmDataList.add(alarmData);

//                        Intent data = new Intent(context, MainActivity.class);
//                        data.putExtra("alarmDataList", alarmDataList);
//                        context.startActivities(new Intent[]{data});

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DATE, day);
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 00);

                        mNow = calendar.getTimeInMillis();
                        mDate = new Date(mNow);

                        Log.e("Calander.DATE", "Calander.DATE " + mFormat.format(mDate) + " / ID = " + id);
                        alarmManager = (AlarmManager) MainActivity.context.getSystemService(MainActivity.context.ALARM_SERVICE);

                        if (alarmManager != null) {
                            Intent intent = new Intent(context, AlarmReceiver.class);
                            intent.putExtra("id", id);
                            intent.putExtra("title", title);
                            intent.putExtra("date", date);
                            intent.putExtra("time", time);

                            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE);

                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

                            Toast.makeText(context, id + "번째 알람이 저장되었습니다.", Toast.LENGTH_LONG).show();
                            id++;
                        }
                    }
                }

                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences_editor = sharedPreferences.edit();

                Gson gson = new Gson();

                Log.e("GetDataWorkManager()", "alarmDataList.toString() = " + alarmDataList.toString());
                String json = gson.toJson(alarmDataList);
                sharedPreferences_editor.putString("alarmData", json);
                sharedPreferences_editor.apply();
                Log.e("GetDataWorkManager()", "sharedPreferences_editor = " + json);

//                Log.e("GetDataWorkManager()", "alarmDataList = " + alarmDataList);

                Intent intent = new Intent("com.babsari.firebasecloudmessage.GET_DATA");
//                intent.putExtra("alarmDataList", alarmDataList);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                call.cancel();
                Log.e("GetDataWorkManager()", "call.cancel");
            }

            @Override
            public void onFailure(Call<HabitNetNotification> call, Throwable t) {
                call.cancel();
            }

        });
        return Result.success();
    }

}