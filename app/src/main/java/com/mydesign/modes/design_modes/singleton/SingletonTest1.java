package com.mydesign.modes.design_modes.singleton;

import android.util.Log;

/**
 * 饿汉式：即使在instance 已经初始化之后，每次都线程同步，效率低，节约资源，用的时候才创建；
 * 希望只有在instance对象在没有创建之前，执行同步代码；instance创建之后，不在执行同步方法；
 */
public class SingletonTest1 {

    private String TAG = SingletonTest1.class.getSimpleName();

    private static SingletonTest1 instance;

    private SingletonTest1() {
    }


    public static synchronized SingletonTest1 getInstance() {
        if (instance == null) {
            instance = new SingletonTest1();
        }
        return instance;
    }

    /*public static SingletonTest1 getInstance() {
        synchronized(SingletonTest1.class){
            if (instance == null) {
                instance = new SingletonTest1();
            }
        }
        return instance;
    }*/

    public void test() {
        Log.e(TAG, TAG);
    }

}
