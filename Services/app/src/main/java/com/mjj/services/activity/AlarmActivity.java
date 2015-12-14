package com.mjj.services.activity;

import android.app.AlarmManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + 10_000;
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, PendingIntent.getBroadcast());
    }

    public void start(View v)
    {

    }

    public void stop(View v)
    {

    }


}
