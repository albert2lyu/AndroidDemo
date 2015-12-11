package com.mjj.services.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mjj.services.R;

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
        Intent intent = new Intent("com.mjj.services.Action.START_SERVICE");
//      也可以这样 Intent intent = new Intent(this, StartService.class);
        startService(intent);
    }

    public void endFirstService(View v)
    {
        stopService(new Intent("com.mjj.services.Action.START_SERVICE"));
    }

    public void bindService(View v)
    {
        Intent intent = new Intent(this,BindServiceActivity.class);
        intent.putExtra("action","com.mjj.services.Action.BIND_SERVICE");
        startActivity(intent);
    }

    public void startBindService(View v)
    {
        startService(new Intent("com.mjj.services.Action.START_BIND_SERVICE"));
        Intent intent = new Intent(this,BindServiceActivity.class);
        intent.putExtra("action", "com.mjj.services.Action.START_BIND_SERVICE");
        startActivity(intent);
    }

    public void endSecondService(View v)
    {
        stopService(new Intent("com.mjj.services.Action.START_BIND_SERVICE"));
    }

    public void foregroundService(View v)
    {
        startService(new Intent("com.mjj.services.Action.FOREGROUND_SERVICE"));
    }

    public void endForegroundService(View v)
    {
        stopService(new Intent("com.mjj.services.Action.FOREGROUND_SERVICE"));
    }

}
