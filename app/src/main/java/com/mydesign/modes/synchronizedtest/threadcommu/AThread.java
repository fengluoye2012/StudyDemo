package com.mydesign.modes.synchronizedtest.threadcommu;

import android.util.Log;

public class AThread extends Thread {
    private String TAG = AThread.class.getSimpleName();

    @Override
    public void run() {
        super.run();
        try {
            Log.e(TAG, "开始执行");
            Thread.sleep(5000);
            Log.e(TAG, "结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
