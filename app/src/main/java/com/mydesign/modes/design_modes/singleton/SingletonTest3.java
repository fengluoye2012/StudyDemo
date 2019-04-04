package com.mydesign.modes.design_modes.singleton;

import android.util.Log;

/**
 *
 */

public class SingletonTest3 {

    private String TAG = SingletonTest3.class.getSimpleName();


    private SingletonTest3() {
    }

    public static SingletonTest3 getInstance() {
        return SingleTonHolder.instance;
    }

    static class SingleTonHolder {
        public static SingletonTest3 instance = new SingletonTest3();
    }

    public void test() {
        Log.e(TAG, TAG);
    }

}
