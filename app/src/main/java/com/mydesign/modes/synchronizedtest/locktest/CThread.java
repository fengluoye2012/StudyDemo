package com.mydesign.modes.synchronizedtest.locktest;


public class CThread extends Thread {

    private MyService service;

    public CThread(MyService myService) {
        this.service = myService;
    }

    @Override
    public void run() {
        super.run();
        service.awaitTestA();
    }
}
