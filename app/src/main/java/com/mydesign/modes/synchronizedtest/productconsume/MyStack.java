package com.mydesign.modes.synchronizedtest.productconsume;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个生产者对应对个消费者 使用 if判断语句会出现假死；用while 就正常；
 */
public class MyStack {

    private List<String> list = new ArrayList<>();

    private String TAG = MyStack.class.getSimpleName();
    private int index;

    public synchronized void add() {
        try {
            while (list.size() > 0) {
                Log.e(TAG, Thread.currentThread().getName() + "，，执行了wait()");
                this.wait();
            }

            list.add(String.valueOf(index));
            Log.e(TAG, Thread.currentThread().getName() + "，，添加了数据,," + index);
            index++;

            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void remove() {
        try {
            while (list.size() < 1) {
                Log.e(TAG, Thread.currentThread().getName() + "，，执行了wait()");
                this.wait();
            }

            String remove = list.remove(0);
            Log.e(TAG, Thread.currentThread().getName() + "，，删除了数据,," + remove);
            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

