package com.mjj.broadcastreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private LocalBroadcastManager localBroadcastManager;
    private BC1 Broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        动态注册
//      IntentFilter intentfilter = new IntentFilter("BC_One");
//		BC2 bc2 = new BC2();
//		registerReceiver(bc2, intentfilter);

        //动态注册 本地 广播接收器
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(Broadcast = new BC1(),new IntentFilter("BC_Local"));
    }

    public void doClick(View v){
        switch (v.getId()) {
            case R.id.send1://发送一条普通广播
                Intent intent = new Intent();
                intent.putExtra("msg", "这是一条普通广播");
                intent.setAction("BC_One");
                sendBroadcast(intent);
                break;

            case R.id.send2://发送一条有序广播
                Intent intent2 = new Intent();
                intent2.putExtra("msg", "这是一条有序广播");
                intent2.setAction("BC_One");
                sendOrderedBroadcast(intent2, null);
                break;
            case R.id.send3://发送一条异步广播(一个奇怪的广播，不要轻易使用)
                //异步广播很神奇，先发出广播再注册广播接收器都可以收到
                Intent intent3 = new Intent();
                intent3.putExtra("msg", "这是一条异步广播");
                intent3.setAction("BC_Three");
                sendStickyBroadcast(intent3);
                //动态注册
                IntentFilter intentfilter = new IntentFilter("BC_Three");
                BC3 bc3 = new BC3();
                registerReceiver(bc3, intentfilter);
                break;
            case R.id.send4:
                Intent intent4 = new Intent();
                intent4.putExtra("msg", "这是一条本地普通广播");
                intent4.setAction("BC_Local");
                localBroadcastManager.sendBroadcast(intent4);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //动态注册必须解除注册
//		unregisterReceiver(receiver);

        localBroadcastManager.unregisterReceiver(Broadcast);
    }




}
