package com.mjj.services.services;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.mjj.services.broadcastreceiver.StartServiceReceiver;

public class AlarmService extends Service{

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    @Override
    public void onCreate() {

        //定时60秒后
        long time = SystemClock.elapsedRealtime()+60_000;
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intentAlarm = new Intent(this,StartServiceReceiver.class);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, time, PendingIntent.getBroadcast(this, 0, intentAlarm, 0));

        //开启线程
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                android.os.Process.THREAD_PRIORITY_DEFAULT);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

        Log.i("asd", "定时服务：onCreate()");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.i("asd","定时服务：onStartCommand()");
        Toast.makeText(this,"定时服务启动",Toast.LENGTH_SHORT).show();

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        return START_STICKY;
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            long endTime = System.currentTimeMillis() + 10_000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
            //按ID暂停
            stopSelf(msg.arg1);
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("asd", "定时服务：onDestroy()");
        Toast.makeText(this,"定时服务关闭",Toast.LENGTH_SHORT).show();
    }
}
