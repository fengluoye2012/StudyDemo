package com.mydesign.modes.synchronizedtest.threadcommu;

import android.util.Log;

/**
 * join(): 使所属的线程对象x正常执行run()方法中的任务，而使当前线程z进行无限期的等待
 * 等待线程x销毁后再继续执行线程z后面的代码。具有使线程排队运行的作用；有些类似同步的运行效果；
 * join()和synchronized的区别：
 * join():内部使用wait()方法进行等待；
 * synchronized:使用的是“对象监视器”原理作为同步；
 */

public class ThreadCommuTest {

    private static String TAG = AThread.class.getSimpleName();

    public static void test() {
        //joinTest();
        threadLocalTest();

    }

    private static void threadLocalTest() {
        BThread bThread = new BThread();
        bThread.start();
        try {
            bThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "Main::" + ThreadLocalTool.threadLocal.get());
        for (int i = 0; i < 10; i++) {

            ThreadLocalTool.threadLocal.set("main" + i);
            Log.e(TAG, "Main::" + ThreadLocalTool.threadLocal.get());
        }
    }

    private static void joinTest() {
        AThread aThread = new AThread();
        aThread.start();
        try {
            aThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "join");
    }

}
