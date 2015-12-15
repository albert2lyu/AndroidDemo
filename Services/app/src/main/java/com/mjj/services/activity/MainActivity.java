package com.mjj.services.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mjj.services.R;
import com.mjj.services.services.ForegroundService;
import com.mjj.services.services.StartAndBindService;
import com.mjj.services.services.StartService;

/**
 * 参考博客：http://www.cnblogs.com/newcj/archive/2011/05/30/2061370.html
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View v)
    {
        Intent intent = new Intent(this, StartService.class);
//        //5.0以上启动服务必须是‘显示’的，如果要按活动启动需要设置包名，如下。
//        Intent intent = new Intent();
//        intent.setAction("com.mjj.services.Action.START_SERVICE");
//        intent.setPackage(getPackageName());
        startService(intent);
    }

    public void endFirstService(View v)
    {
        stopService(new Intent(this, StartService.class));
    }

    public void bindService(View v)
    {
        Intent intent = new Intent(this,BindServiceActivity.class);
        intent.putExtra("action","com.mjj.services.Action.BIND_SERVICE");
        startActivity(intent);
    }

    public void startBindService(View v)
    {
        startService(new Intent(this, StartAndBindService.class));
        Intent intent = new Intent(this,BindServiceActivity.class);
        intent.putExtra("action", "com.mjj.services.Action.START_BIND_SERVICE");
        startActivity(intent);
    }

    public void endSecondService(View v)
    {
        stopService(new Intent(this, StartAndBindService.class));
    }

    public void foregroundService(View v)
    {
        startService(new Intent(this, ForegroundService.class));
    }

    public void endForegroundService(View v)
    {
        stopService(new Intent(this, ForegroundService.class));
    }

    public void alarmService(View v)
    {
        //发送广播
        sendBroadcast(new Intent("startAlarmService"));
    }
}
