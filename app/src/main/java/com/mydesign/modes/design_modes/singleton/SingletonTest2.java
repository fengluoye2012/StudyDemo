package com.mydesign.modes.design_modes.singleton;

import android.util.Log;

/**
 * 双重加锁：线程安全,按需加载；只有在极特殊情况下才会线程不安全；
 * 类的创建过程：如 instance = new SingletonTest();
 * <p>
 * 1：:给SingletonTest的实例分配内存；
 * 2：调用SingletonTest()对象的构造函数，初始化成员字段；
 * 3：将instance对象指向分配的内存空间（此时instance对象不在为null）；
 * <p>
 * JVM 编译器允许处理器乱序执行，执行顺序会变成1-3-2；如果3 执行完成，2 没有执行之前，
 * 此时切换到B线程，此时instance 已经不在是null，B线程取回之间用，就会报错；
 * <p>
 * JDK 1.5 之后 加入volatile关键字（原子可见性），不允许处理器乱序执行；
 */

public class SingletonTest2 {

    private static String TAG = SingletonTest2.class.getSimpleName();

    private volatile static SingletonTest2 instance;

    private SingletonTest2() {
    }

    /**
     * 静态方法 + Synchronized（class）：表示对当前类进行持锁，不是当前类同一时间只有一个线程访问的意思；只是一种锁而已；
     *
     * @return
     */
    public static SingletonTest2 getInstance() {
        //避免在instance创建之后，不必要的同步
        if (instance == null) {
            Log.e(TAG, Thread.currentThread().getName() + ",," + System.currentTimeMillis() + "第一个非空判断");
            //一个线程获取锁对象之后，此时有部分线程，在这个阻塞
            synchronized (SingletonTest1.class) {
                Log.e(TAG, Thread.currentThread().getName() + ",," + System.currentTimeMillis() + "synchronized");
                //判断是否为null，如果为null的话，创建instance对象，当前线程创建完对象之后，释放锁；
                // 其他线程获得锁，执行此行代码，（不考虑JVM编译器允许处理器乱序执行的情况下）instance对象已经创建，不在创建instance对象；
                // 第二个非空判断防止多个instance对象被创建；
                if (instance == null) {
                    Log.e(TAG, Thread.currentThread().getName() + ",," + System.currentTimeMillis() + "第二个非空判断");
                    instance = new SingletonTest2();
                }
            }
        }
        return instance;
    }

    /**
     * 运行结果
     * 线程0,,1533284326409第一个非空判断
     * 线程2,,1533284326410第一个非空判断
     * 线程4,,1533284326410第一个非空判断
     * <p>
     * 线程2,,1533284326410synchronized
     * <p>
     * 线程2,,1533284326410第二个非空判断
     * <p>
     * 线程3,,1533284326410第一个非空判断
     * 线程1,,1533284326410第一个非空判断
     * 线程4,,1533284326411synchronized
     * 线程0,,1533284326411synchronized
     * 线程3,,1533284326411synchronized
     * 线程1,,1533284326411synchronized
     * <p>
     * 线程2,,1533284327411SingletonTest2
     * 线程4,,1533284327411SingletonTest2
     * 线程0,,1533284327411SingletonTest2
     * 线程5,,1533284327411SingletonTest2
     * 线程3,,1533284327412SingletonTest2
     * 线程1,,1533284327412SingletonTest2
     * 线程6,,1533284327412SingletonTest2
     * 线程7,,1533284327412SingletonTest2
     * 线程8,,1533284327413SingletonTest2
     * 线程9,,1533284327413SingletonTest2
     */

    public void test() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e(TAG, Thread.currentThread().getName() + ",," + System.currentTimeMillis() + TAG);
    }

}
