package com.mjj.okhttpandstetho.app;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MyApplication extends Application {

    public static final boolean DEBUG = true;
    @Override
    public void onCreate() {
        super.onCreate();
        if(DEBUG)
        {
            Stetho.initializeWithDefaults(this);
        }
    }
}
