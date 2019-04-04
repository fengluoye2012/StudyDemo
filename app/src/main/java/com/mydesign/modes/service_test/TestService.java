package com.mydesign.modes.service_test;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestService extends Service {

    private boolean flag;

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    private String TAG = TestService.class.getSimpleName();
    private int startId;


    //在onCreate()方法之前运行；
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");

        this.startId = startId;
        startThread();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startThread() {

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
    public IBinder onBind(Intent intent) {
        throw null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    /**
     * 1：为什么Service在onDestroy之后，线程依然在执行呢？？？  如果是在Activity中呢
     * 可以设置flag，在onDestroy中改变flag，终止线程。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        executorService.shutdownNow();
        flag = true;
        //stopSelf(startId);
        Log.e(TAG, "onDestroy");

    }
}
