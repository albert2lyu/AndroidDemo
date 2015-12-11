package com.mjj.services.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.mjj.services.R;
import com.mjj.services.activity.BindServiceActivity;

/**
 * Created by Administrator on 15-12-11.
 */
public class ForegroundService extends StartService {

    @Override
    public void onCreate() {
        super.onCreate();

        Intent notificationIntent = new Intent(this, BindServiceActivity.class);
        notificationIntent.putExtra("action", "com.mjj.services.Action.FOREGROUND_SERVICE");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification =  new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("前台服务测试")
                .setContentText("前台服务测试")
                .setContentIntent(pendingIntent)
                .build();

        startForeground(100, notification);
    }
}
