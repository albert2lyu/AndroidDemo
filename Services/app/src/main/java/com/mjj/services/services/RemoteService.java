package com.mjj.services.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.mjj.services.IRemoteService;

/**
 * Created by Administrator on 15-12-14.
 */
public class RemoteService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the interface
        return mBinder;
    }

    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        public int getPid(){
            return 213;
        }
        public void basicTypes(int anInt, long aLong, boolean aBoolean,
                               float aFloat, double aDouble, String aString) {
            // Does nothing
        }
    };
}
