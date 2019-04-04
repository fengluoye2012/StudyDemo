package com.mydesign.modes.synchronizedtest.locktest;


public class ConsumeThread extends Thread {

    private AlterService service;

    public ConsumeThread(AlterService service) {
        this.service = service;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
//            service.consumeMethod();
            service.comstume();
        }
    }
}
