package com.mjj.okhttpandstetho.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mjj.okhttpandstetho.R;
import com.mjj.okhttpandstetho.model.ZhiHu;
import com.mjj.okhttpandstetho.okhttp.OkHttpCallBack;
import com.mjj.okhttpandstetho.okhttp.OkHttpUtil;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    String url = "http://news-at.zhihu.com/api/4/story/4232852/long-comments";

    public void test(View v)
    {
        OkHttpUtil.doGet(url,callBack);
    }

    OkHttpCallBack<ZhiHu> callBack = new OkHttpCallBack<ZhiHu>() {

        @Override
        public void onResponse(ZhiHu response) {
            Log.i("asd","response size:"+response.getComments().size());
        }
    };



}
