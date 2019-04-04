package com.mydesign.modes.synchronizedtest.locktest;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现A B 轮流循环打印的原理：线程通信；A 线程打印了A 之后，唤醒B 线程；A,B线程会被CPU 随机选择运行；
 * 如果选中 A线程，则调用wait()/await()方法，放弃当前线程对同一个对象锁的持有;如果B 线程被CPU选择执行;
 * 正常执行，以此类推；
 * 实现方式有两种：1）synchronized关键字，Object类中的 wait()，notify()/notifyAll() 方法；
 * 2）Lock类 lock(),unLock()方法,Condition类await()，signal()signalAll()方法；
 */
public class AlterService {

    private String TAG = AlterService.class.getSimpleName();
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private boolean haValue = false;

    //打印完之后，如果当前方法再次被执行，调用await()，
    public void proMethod() {
        try {
            lock.lock();
            while (haValue) {
                condition.await();
            }
            Log.e(TAG, "AA");
            haValue = true;
            condition.signalAll();
            Thread.sleep(300);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //打印完之后，如果当前方法再次被执行，调用await()，
    public void consumeMethod() {
        try {
            lock.lock();
            while (!haValue) {
                condition.await();
            }
            Log.e(TAG, "BB");
            haValue = false;
            condition.signalAll();
            Thread.sleep(300);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void product() {
        synchronized (this) {
            try {
                while (haValue) {
                    wait();
                }
                Log.e(TAG, "A");
                haValue = true;
                notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void comstume() {
        synchronized (this) {
            try {
                while (!haValue) {
                    wait();
                }
                Log.e(TAG, "B");
                haValue = false;
                notifyAll();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    //timer 内部 调用TimerThread； TimerTask 是 Runnable；
    public void timerTest() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };
        timer.schedule(timerTask, 2000);
    }


}
