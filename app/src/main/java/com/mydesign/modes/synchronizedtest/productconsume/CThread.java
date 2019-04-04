package com.mydesign.modes.synchronizedtest.productconsume;

public class CThread extends Thread {

    private C c;

    public CThread(C c) {
        this.c = c;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            c.remove();
        }
    }
}