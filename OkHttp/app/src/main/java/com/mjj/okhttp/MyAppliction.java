package com.mjj.okhttp;

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;


public class MyAppliction extends Application {
    private static OkHttpClient okHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        okHttpClient = new OkHttpClient();
    }

    public static OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }
}
