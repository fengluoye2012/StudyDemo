package com.mydesign.modes.synchronizedtest.locktest;

import android.util.Log;

public class LockTest {

    private static LockTest lockTest;
    private String TAG = MyService.class.getSimpleName();

    public static LockTest getInstance() {
        if (lockTest == null) {
            lockTest = new LockTest();
        }
        return lockTest;
    }

    public void test() {
        Log.e(TAG, "///////////");
        MyService myService = new MyService();
        AThread aThread = new AThread(myService);
        BThread bThread = new BThread(myService);
        CThread cThread = new CThread(myService);
        DThread dThread = new DThread(myService);
        aThread.setName("A线程");
        bThread.setName("B线程");
        cThread.setName("C线程");
        dThread.setName("D线程");

        aThread.start();
        cThread.start();

        bThread.start();
        dThread.start();
    }

    public void testLock() {
        AlterService service = new AlterService();
        ProductThread productThread = new ProductThread(service);
        ConsumeThread consumeThread = new ConsumeThread(service);
        productThread.start();
        consumeThread.start();
    }
}
