package com.mydesign.modes.synchronizedtest.locktest;

import android.util.Log;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock类:lock()，unLock()方法分别表示加锁和解锁；成对出现一一对应；lock()方法多，则造成无法解锁；
 * unLock()方法多，则报 IllegalMonitorStateException；
 * 线程间通信：
 * Condition类：await()，signal()/signalAll() 方法对应于 wait(),notify()/notifyAll()方法;
 * await()方法被调用后，会立即释放当前锁对象；
 * await(long time, TimeUnit unit):等待一段时间内，是否有线程对该锁进行唤醒，如果没有唤醒，会自动唤醒；
 * signal()方法被调用之后，执行完当前锁代码块的方法，才释放锁对象；
 * <p>
 * <p>
 * Condition类和synchronized关键字的区别:
 * Condition类是JDK5中出现的技术,使用它有更好的灵活性，比如可以实现多路通知功能，也就是在一个Lock对象
 * 里面可以创建多个Condition(即对象监视器)实例，线程对象可以注册在执行的Condition中，从而可以更有选择
 * 性的进行线程通知，在调度线程上更加灵活。
 * <p>
 * <p>
 * synchronized 相当于整个Lock对象中只有一个单一的Condition对象，所有的线程注册在它一个对象身上，
 * 线程开始notifyAll()时，需要通知所有的Waiting 线程，没有选择权，会出现相当大的效率问题；
 */
public class MyService {
    private String TAG = MyService.class.getSimpleName();
    private Lock lock = new ReentrantLock();
    //多个Condition 可以有选择性的进行线程调度
    private Condition condition = lock.newCondition();
    private Condition conditionA = lock.newCondition();

    public void test() {
        try {
            lock.lock();
            Log.e(TAG, Thread.currentThread().getName() + "," + System.currentTimeMillis() + "，，执行了lock");
            Thread.sleep(3000);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } finally {
            lock.unlock();
        }
    }

    public void awaitTest() {
        try {
            lock.lock();
            Log.e(TAG, Thread.currentThread().getName() + "," + System.currentTimeMillis() + "，，开始await时间");
            condition.await();
            Log.e(TAG, Thread.currentThread().getName() + "," + System.currentTimeMillis() + "，，结束await时间");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } finally {
            lock.unlock();
        }
    }

    public void awaitTestA() {
        try {
            lock.lock();
            Log.e(TAG, Thread.currentThread().getName() + "," + System.currentTimeMillis() + "，conditionA，开始await时间");
            conditionA.await();
            Log.e(TAG, Thread.currentThread().getName() + "," + System.currentTimeMillis() + "，conditionA，结束await时间");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } finally {
            lock.unlock();
        }
    }


    public void signalTest() {
        try {
            lock.lock();
            Log.e(TAG, Thread.currentThread().getName() + "," + System.currentTimeMillis() + "，，开始signal时间");
            Thread.sleep(3000);
            condition.signalAll();
            Log.e(TAG, Thread.currentThread().getName() + "," + System.currentTimeMillis() + "，，结束signal时间");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } finally {
            lock.unlock();
        }
        Log.e(TAG, Thread.currentThread().getName() + "," + System.currentTimeMillis() + "，，signal执行之后");
    }

    public void signalTestA() {
        try {
            lock.lock();
            Log.e(TAG, Thread.currentThread().getName() + "," + System.currentTimeMillis() + "，conditionA，开始signal时间");
            Thread.sleep(3000);
            conditionA.signalAll();
            Log.e(TAG, Thread.currentThread().getName() + "," + System.currentTimeMillis() + "，conditionA，结束signal时间");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } finally {
            lock.unlock();
        }
        Log.e(TAG, Thread.currentThread().getName() + "," + System.currentTimeMillis() + "，conditionA，signal执行之后");
    }


}
