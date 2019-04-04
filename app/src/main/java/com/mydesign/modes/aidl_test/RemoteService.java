package com.mydesign.modes.aidl_test;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

public class RemoteService extends Service {

    private String TAG = RemoteService.class.getSimpleName();
    private IRemoteService iRemoteService = new IRemoteService();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "myPid:::"+ Process.myPid());
        Log.e(TAG, "onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return iRemoteService;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.e(TAG, "unbindService");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }
}
