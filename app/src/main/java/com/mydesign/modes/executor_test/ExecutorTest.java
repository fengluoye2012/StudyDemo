package com.mydesign.modes.executor_test;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 核心线程如何创建的？
 * 核心线程是如何保活的？
 * <p>
 * 线程是是异步的吗？
 * 线程池如何执行同步任务呢？
 */
public class ExecutorTest {

    private final int count = Runtime.getRuntime().availableProcessors();
    private final ExecutorService executorService = Executors.newFixedThreadPool(count);

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 10, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

    public void test(Runnable runnable) {

        byte a = 12;

        int b = Integer.MAX_VALUE;
        executorService.execute(runnable);
        executorService.execute(new MyRunnable());
        executorService.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            //todo
        }
    }
}
