package com.mydesign.modes.synchronizedtest.productconsume;


public class PThread extends Thread {

    private P p;

    public PThread(P p) {
        this.p = p;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            p.add();
        }
    }
}
