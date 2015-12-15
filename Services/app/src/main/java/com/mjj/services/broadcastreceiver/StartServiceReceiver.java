package com.mjj.services.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mjj.services.services.AlarmService;


public class StartServiceReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i("asd","收到服务启动的广播，线程："+Thread.currentThread().getName());
        //启动服务
        context.startService(new Intent(context, AlarmService.class));
    }
}
