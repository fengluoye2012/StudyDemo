package com.mydesign.modes.design_modes.singleton;

import android.util.Log;

/**
 * 懒汉死，在不需要的情况下，就已经创建出来了，浪费资源；
 * 线程安全
 */

public class SingletonTest {

    private String TAG = SingletonTest.class.getSimpleName();

    private static SingletonTest instance = new SingletonTest();

    private SingletonTest() {
    }

    public static SingletonTest getInstance() {
        return instance;
    }

    public void test() {
        Log.e(TAG, TAG);
    }

}
