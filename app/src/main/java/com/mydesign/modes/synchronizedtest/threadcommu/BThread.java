package com.mydesign.modes.synchronizedtest.threadcommu;

import android.util.Log;

public class BThread extends Thread {
    private String TAG = BThread.class.getSimpleName();

    @Override
    public void run() {
        super.run();

        Log.e(TAG, "BThread::" + ThreadLocalTool.threadLocal.get());
        for (int i = 0; i < 10; i++) {
            ThreadLocalTool.threadLocal.set("BThread"+i);
            Log.e(TAG, "BThread:" + ThreadLocalTool.threadLocal.get());
        }
    }
}
