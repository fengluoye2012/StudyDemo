package com.mydesign.modes.synchronizedtest.locktest;


public class BThread extends Thread {
    private MyService service;

    public BThread(MyService myService) {
        this.service = myService;
    }

    @Override
    public void run() {
        super.run();
//        service.test();
        service.signalTest();
    }
}
