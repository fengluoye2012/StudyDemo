package com.mydesign.modes.design_modes.proxy.d_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 具体的InvocationHandler类
 *
 * @param <T>
 */
public class SpecificInvocationHandler<T> implements InvocationHandler {

    private T target;

    public SpecificInvocationHandler(T target) {
        this.target = target;
    }

    /**
     * proxy:　　指代我们所代理的那个真实对象
     * method:　　指代的是我们所要调用真实对象的某个方法的Method对象
     * args:　　指代的是调用真实对象某个方法时接受的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //代理方法执行前
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    private void after() {

    }

    private void before() {

    }
}
