package com.mydesign.modes.synchronizedtest.productconsume;

import android.util.Log;

/**
 * wait() 和 notify()/notifyAll()：必须配合Synchronized,用于线程同步中；在wait状态中才能调用notify()方法；
 * wait()调用之后，就立即释放锁；notify()调用之后，不会立即释放锁，而是等同步的代码执行完；
 */
public class ProductConsumeTest {

    private String TAG = ProductConsumeTest.class.getSimpleName();

    private boolean isWait = false;

    private static ProductConsumeTest instance;

    private ProductConsumeTest() {
    }

    public static ProductConsumeTest getInstance() {
        if (instance == null) {
            instance = new ProductConsumeTest();
        }
        return instance;
    }

    public void start() {
        try {
            MyStack myStack = new MyStack();
            P p = new P(myStack);
            C c1 = new C(myStack);
            C c2 = new C(myStack);
            PThread thread = new PThread(p);
            CThread cThread1 = new CThread(c1);
            CThread cThread2 = new CThread(c2);

            thread.setName("生产者");
            cThread1.setName("消费者1");
            cThread2.setName("消费者2");

            thread.start();
            cThread1.start();
            cThread2.start();
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }
}
