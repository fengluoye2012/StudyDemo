package com.mydesign.modes.plug.hook;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class IActivityManagerProxy implements InvocationHandler {
    private String TAG = IActivityManagerProxy.class.getSimpleName();
    private Object mBase;

    private final String METHOD_NAME = "startActivity";

    IActivityManagerProxy(Object object) {
        mBase = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (METHOD_NAME.equals(method.getName())) {
            Log.e(TAG, "拦截到了：：" + method.getName());
            return method.invoke(mBase, args);
        }
        return method.invoke(mBase, args);
    }
}
