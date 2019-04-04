package com.mydesign.modes.service_test;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BindService extends Service {

    private ExecutorService executorService = Executors.newFixedThreadPool(3);
    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public BindService getService() {
            //返回当前对象
            return BindService.this;
        }
    }

    private String TAG = BindService.class.getSimpleName();
    private boolean flag = false;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        flag = true;
        return super.onUnbind(intent);
    }

    public void startThread() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    if (flag) {
                        Log.e(TAG, "结束线程");
                        return;
                    }
                    Log.e(TAG, "线程运行中。。。");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }
}
