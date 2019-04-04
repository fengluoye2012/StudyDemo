package com.mydesign.modes.design_modes.observer;

/**
 * 观察者模式：
 * 简单来说，就是当对象A 对 对象B进行进行了类似“订阅”关系，当对象B的数据发生改变时，就要通知对象A进行相应
 * 方便解耦，最好的事例EventBus;
 */

public class ObserverClient {

    private static ObserverClient instance;

    public static ObserverClient getInstance() {
        if (instance == null) {
            synchronized (ObserverClient.class) {
                if (instance == null) {
                    instance = new ObserverClient();
                }
            }
        }
        return instance;
    }

    public void test() {

        Integer a = 10;
        Class<? extends Integer> aClass = a.getClass();
        ConcreateObservable observable = new ConcreateObservable();
    }
}
