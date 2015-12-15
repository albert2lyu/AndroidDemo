package com.mjj.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BC2 extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		String s = intent.getStringExtra("msg");
		Log.i("asd", "reveiver2收到消息："+s+" 线程：" + Thread.currentThread().getName());
		//拦截广播
//		abortBroadcast();
		//获取别的广播修改后的数据
		Bundle bundle = getResultExtras(true);
		String s2 = bundle.getString("test");
		Log.i("asd", "得到的处理结果是：" + s2);
	}

}
