package com.mydesign.modes.synchronizedtest.locktest;


public class ProductThread extends Thread {

    private AlterService service;

    public ProductThread(AlterService service) {
        this.service = service;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
//            service.proMethod();
            service.product();
        }
    }
}
