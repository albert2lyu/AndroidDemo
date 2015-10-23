package com.mjj.okhttp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String URL = "http://news-at.zhihu.com/api/4/news/latest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void doGet(View v) {
        notInUIThread(URL);
    }

    /**
     * 异步的请求
     * @param url
     */
    private void notInUIThread(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = MyAppliction.getOkHttpClient().newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("asd", "onFailure");
                //非UI线程
            }
            @Override
            public void onResponse(Response response) throws IOException {
                //非UI线程
                Log.i("asd", "onResponse");
                Log.i("asd", "data:" + response.body().string());

            }
        });
    }

    /**
     * 非异步的请求(存在坑,应该不是超过5秒的问题)
     * @param url
     */
    public void inUIThread(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = MyAppliction.getOkHttpClient().newCall(request).execute();

        Log.i("asd", "data:" + response.body().string());
    }

    public void doPost(View v){


        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("username","张鸿洋");

        Request request = new Request.Builder()
                .url(URL)
                .post(builder.build())
                .build();
        MyAppliction.getOkHttpClient().newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.i("asd", "data:" + response.body().string());
            }
        });

    }



}
