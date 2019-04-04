package com.mydesign.modes.datastructure;

import android.util.Log;


public class Children extends Father {


    void addItem() {
        Log.e("hh", "我是children类");
        Inner inner = new Inner("feng");
        Inner in = inner.getInner();
        String name = in.name;
        Log.e("hh", name);
    }


    static final class Inner {
        public String te;
        public String name;

        public Inner(String name) {
            this.name = name;
        }

        final Inner getInner() {
            return this;
        }

    }

}
