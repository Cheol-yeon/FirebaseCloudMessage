package com.babsari.firebasecloudmessage;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {

    ArrayList<AlarmData> localDataSet;

    public AlarmListAdapter(ArrayList<AlarmData> dataset) {
        localDataSet = dataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView time;
        public TextView date;
        public TextView onButton;
        public TextView offButton;

        LinearLayout toggleButton;


        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            time = (TextView) view.findViewById(R.id.time);
            date = (TextView) view.findViewById(R.id.date);
            onButton = (TextView) view.findViewById(R.id.buttonOn);
            offButton = (TextView) view.findViewById(R.id.buttonOff);
            toggleButton = (LinearLayout) view.findViewById(R.id.toggleButton);

            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onButton.getBackground()!=null) {
                        onButton.setBackground(null);
                        offButton.setBackgroundResource(R.drawable.item_bg_on);
                        onButton.setText("ON");
                        onButton.setTextColor(Color.BLACK);
                        offButton.setTextColor(Color.WHITE);
                    }
                    else if (offButton.getBackground()!=null) {
                        offButton.setBackground(null);
                        onButton.setBackgroundResource(R.drawable.item_bg_on);
                        offButton.setText("OFF");
                        onButton.setTextColor(Color.WHITE);
                        offButton.setTextColor(Color.BLACK);
                    }
                }
            });


        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ConstraintLayout view;

        view = (ConstraintLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.alarm_list,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlarmData alarmData = localDataSet.get(position);

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);

        int date = Integer.parseInt(alarmData.getDate());
        int time = Integer.parseInt(alarmData.getTime());
        int year = date / 10000;
        int month = date % 10000 / 100 - 1;
        int day = date % 100;
        int hour = time / 100;
        int minute = time % 100;

        holder.title.setText(alarmData.getTitle());
        holder.time.setText(hour + " : " + minute);
        holder.date.setText(year + "." + month + "." + day);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DATE,day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        Date mDate2 = new Date(calendar.getTimeInMillis());

        int compare = mDate.compareTo(mDate2);

        if(compare>0) {
            holder.onButton.setBackground(null);
            holder.offButton.setBackgroundResource(R.drawable.item_bg_on);
            holder.offButton.setText("OFF");
            holder.offButton.setTextColor(Color.WHITE);
            holder.onButton.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet==null?0:localDataSet.size();
    }
}
