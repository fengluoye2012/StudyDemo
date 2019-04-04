package com.mydesign.modes.synchronizedtest.locktest;


public class AThread extends Thread {

    private MyService service;

    public AThread(MyService myService) {
        this.service = myService;
    }

    @Override
    public void run() {
        super.run();
//        service.test();
        service.awaitTest();
    }
}
