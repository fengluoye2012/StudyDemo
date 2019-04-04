package com.mydesign.modes.synchronizedtest;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程的三种方式：Thread，Runnable，（Callable（有返回值，AsyncTask中有用）配合 furtask ）
 */
public class SynchronizedTest {

    private static String TAG = SynchronizedTest.class.getSimpleName();
    private static SynchronizedTest instance = null;

    private SynchronizedTest() {
    }

    public static SynchronizedTest getInstance() {
        if (instance == null) {
            instance = new SynchronizedTest();
        }
        return instance;
    }

    class MyThread1 extends Thread {
        private ServiceTest service;

        public MyThread1(ServiceTest serviceTest) {
            service = serviceTest;
        }

        @Override
        public void run() {
            service.deal();
            service.synchronizedDeal();
            service.deal2();
        }
    }

    class MyThread2 extends Thread {
        private ServiceTest service;

        public MyThread2(ServiceTest serviceTest) {
            service = serviceTest;
        }

        @Override
        public void run() {
            service.deal();
            service.synchronizedDeal();
            service.deal2();
        }
    }

//    private class ServiceTest {
//        private void deal2() {
//            Log.e(TAG, Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "deal222方法 执行了");
//        }
//
//        private void deal() {
//            Log.e(TAG, Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "deal111方法 执行了");
//        }
//
//        private synchronized void synchronizedDeal() {
//            Log.e(TAG, Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "开始执行了");
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Log.e(TAG, Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "执行结束了");
//        }
//    }

    /**
     * 运行结果
     * Thread1在1533278888126deal111方法 执行了
     * Thread1在1533278888126开始执行了
     * Thread2在1533278888126deal111方法 执行了
     * Thread1在1533278891126执行结束了
     * Thread1在1533278891127deal222方法 执行了
     * Thread2在1533278891128开始执行了
     * Thread2在1533278894129执行结束了
     * Thread2在1533278894129deal222方法 执行了
     * <p>
     * synchronized修饰一般方式时：加锁的方法，同一时间，只能有一个方法执行；其他线程可以执行非加锁方法；
     * 问题：为什么加锁方法下面的方法无法执行呢？
     * 是因为其他线程在等待锁，无法获取当前对象锁，无法往下执行；
     */


    private static class ServiceTest {
        private void deal2() {
            Log.e(TAG, Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "deal222方法 执行了");
        }

        private void deal() {
            Log.e(TAG, Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "deal111方法 执行了");
        }

        private static void synchronizedDeal() {
            Log.e(TAG, Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "synchronizedDeal");
            synchronized (ServiceTest.class) {
                Log.e(TAG, Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "开始执行了");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "执行结束了");
            }
        }
    }

    /**
     * 静态方法 Synchronized(class) 类锁：锁代码块线程同步，非代码块部分是异步的；
     *
     * 线程1在1533282712466deal111方法 执行了
     *线程1在1533282712466synchronizedDeal
     *线程1在1533282712466开始执行了
     *线程2在1533282712466deal111方法 执行了
     *线程2在1533282712467synchronizedDeal
     *线程1在1533282715467执行结束了
     *线程1在1533282715467deal222方法 执行了
     *线程2在1533282715469开始执行了
     *线程2在1533282718470执行结束了
     *线程2在1533282718474deal222方法 执行了
     */


    /**
     * 1）多线程在非加锁状态下，多个线程同时执行，在不考虑线程不安全的情况下，提高了执行效率，比单线程节省时间；
     * 2）
     */
    public void startThread() {

        ServiceTest serviceTest = new ServiceTest();

        MyThread1 thread1 = new MyThread1(serviceTest);
        MyThread2 thread2 = new MyThread2(serviceTest);
        thread1.setName("线程1");
        thread2.setName("线程2");

        Log.e(TAG, "///////////////////////////////////////////");

        thread1.start();
        thread2.start();

    }

    public void executorTest(Runnable runnable) {
        Executors.newFixedThreadPool(100).execute(runnable);
    }
}
