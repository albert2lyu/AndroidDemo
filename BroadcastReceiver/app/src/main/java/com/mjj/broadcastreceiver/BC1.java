package com.mjj.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BC1 extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		String s = intent.getStringExtra("msg");
		Log.i("asd", "reveiver1收到消息：" + s + " 线程：" + Thread.currentThread().getName());
//		abortBroadcast();
		Bundle bundle = new Bundle();
		bundle.putString("test","BC1修改后的数据");
//		将修改后的数据继续传递
//		setResultExtras(bundle);
	}

}
