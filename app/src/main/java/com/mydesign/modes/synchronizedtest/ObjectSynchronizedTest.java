package com.mydesign.modes.synchronizedtest;

import android.util.Log;

public class ObjectSynchronizedTest {

    private static ObjectSynchronizedTest instance;

    private ObjectSynchronizedTest() {

    }

    public static ObjectSynchronizedTest getInstance() {
        if (instance == null) {
            instance = new ObjectSynchronizedTest();
        }
        return instance;
    }

    /**
     * synchronized(X) 和 Synchronized(this) 效果一样，都是对象锁
     * 锁代码块：非加锁部分异步执行，加锁部分同步执行；
     * <p>
     * Thread-9,,1533526985033start
     * Thread-10,,1533526985033hello World
     * Thread-10,,1533526985033start
     * Thread-9,,1533526988034我经过锁了
     * Thread-9,,1533526988035方法结束了
     * Thread-9,,1533526988035hello World
     * Thread-10,,1533526991037我经过锁了
     * Thread-10,,1533526991038方法结束了
     * static + synchronized:Java.class 文件所对应的类锁和 synchronized(X)对象锁 是两个不同的锁，相互之前不互斥；不同线程可异步访问这两个方法；
     */
    static class MyServer {
        private static String TAG = MyServer.class.getSimpleName();
        private Object object = new Object();

        private void doSomething() {
            Log.e(TAG, Thread.currentThread().getName() + ",," + System.currentTimeMillis() + "start");
            synchronized (object) {
                try {
                    Thread.sleep(3000);
                    Log.e(TAG, Thread.currentThread().getName() + ",," + System.currentTimeMillis() + "我经过锁了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.e(TAG, Thread.currentThread().getName() + ",," + System.currentTimeMillis() + "方法结束了");
        }

        private static void logHello() {
            synchronized (MyServer.class) {
                try {
                    Log.e(TAG, Thread.currentThread().getName() + ",," + System.currentTimeMillis() + "hello World");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    class ThreadA extends Thread {
        private MyServer server;

        public ThreadA(MyServer server) {
            this.server = server;
        }

        @Override
        public void run() {
            super.run();
            server.doSomething();
            server.logHello();
        }
    }

    class ThreadB extends Thread {
        private MyServer server;

        public ThreadB(MyServer server) {
            this.server = server;
        }

        @Override
        public void run() {
            super.run();
            server.logHello();
            server.doSomething();

        }
    }

    public void start() {
        MyServer server = new MyServer();
        ThreadA threadA = new ThreadA(server);
        ThreadB threadB = new ThreadB(server);

        threadA.start();
        threadB.start();
    }

}
