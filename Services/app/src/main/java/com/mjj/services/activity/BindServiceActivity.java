package com.mjj.services.activity;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mjj.services.R;
import com.mjj.services.services.StartService;

public class BindServiceActivity extends AppCompatActivity{

    private TextView textView;

    private boolean isBind = false;

    private StartService.SimpleBinder simpleBinder;

    private String ActionOfService = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bindservice);
        textView = (TextView) findViewById(R.id.text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionOfService = getIntent().getStringExtra("action");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void BindService(View v)
    {
        if(!isBind){
            Intent intent = new Intent(ActionOfService);
            bindService(intent,sc, Context.BIND_AUTO_CREATE);
        }else{
            textView.setText(textView.getText()+"已经绑定服务\n");
        }

    }

    ServiceConnection sc =  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBind = true;
            textView.setText(textView.getText()+"绑定服务成功！ "+name.toString()+"\n");
            simpleBinder = (StartService.SimpleBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            textView.setText(textView.getText() + "服务异常关闭\n");
        }
    };

    public void log(View v)
    {
        if(isBind){
            textView.setText(textView.getText()+"调用服务接口：10 + 12 = " + simpleBinder.add(10,12) + "\n");
        }else{
            textView.setText(textView.getText()+"还没有绑定服务\n");
        }
    }

    public void unBindService(View v)
    {
        if(isBind){
            unbindService(sc);
            textView.setText(textView.getText() + "解除绑定\n");
            isBind = false;
        }else{
            textView.setText(textView.getText()+"还没有绑定服务\n");
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
