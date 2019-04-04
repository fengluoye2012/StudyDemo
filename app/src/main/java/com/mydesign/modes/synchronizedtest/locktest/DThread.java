package com.mydesign.modes.synchronizedtest.locktest;


public class DThread extends Thread {

    private MyService service;

    public DThread(MyService myService) {
        this.service = myService;
    }

    @Override
    public void run() {
        super.run();
        service.signalTestA();
    }
}
