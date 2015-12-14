package com.mjj.services.activity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mjj.services.IRemoteService;
import com.mjj.services.R;


public class RemoteServiceAvtivity extends AppCompatActivity {

    IRemoteService mIRemoteService;
    private ServiceConnection mConnection = new ServiceConnection() {
        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Following the example above for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service
            mIRemoteService = IRemoteService.Stub.asInterface(service);
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.e("asd", "Service has unexpectedly disconnected");
            mIRemoteService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_service);


    }

    public void start(View v)
    {

    }

    public void stop(View v)
    {

    }
}
