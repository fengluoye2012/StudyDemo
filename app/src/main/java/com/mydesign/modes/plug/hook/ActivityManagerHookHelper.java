package com.mydesign.modes.plug.hook;

import android.os.Build;

import com.mydesign.modes.plug.RefInvoke;

import java.lang.reflect.Proxy;

/**
 * 开启Activity的必经流程
 * 1）开启Activity都会调用ActivityManager.getService().startActivity()方法
 * 1.1 getService()方法返回一个IActivityManager对象；IActivityManagerSingleton.get()返回单例对象；
 * 1.2 IActivityManagerSingleton对象是一个final static修饰的变量；
 * <p>
 * 问题：Android Framework源码被各个厂商修改，如果字段各有不同，失败了怎么办？
 */
public class ActivityManagerHookHelper {

    public static void hookActivityManager() throws ClassNotFoundException {

        Object iActivityManager = null;
        if (Build.VERSION.SDK_INT >= 26) {
            Class<?> activityManageClazz = Class.forName("android.app.ActivityManager");
            //获取activityManager中的IActivityManagerSingleton字段
            iActivityManager = RefInvoke.getFieldObject(activityManageClazz, null, "IActivityManagerSingleton");
        } else {
            Class<?> activityManagerNativeClazz = Class.forName("android.app.ActivityManagerNative");
            //获取ActivityManagerNative中的gDefault字段
            iActivityManager = RefInvoke.getFieldObject(activityManagerNativeClazz, null, "gDefault");
        }

        Class<?> singleton = Class.forName("android.util.Singleton");
        //IActivityManagerSingleton是一个Singleton<IActivityManager>对象；获取Singleton中的instance对象；
        Object mInstance = RefInvoke.getFieldObject(singleton, iActivityManager, "mInstance");

        //创建一个对象的代理对象MockClass,然后替换这个字段；
        Class<?> iAMInterface = Class.forName("android.app.IActivityManager");

        //Thread.currentThread().getContextClassLoader();
        Object proxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{iAMInterface}, new IActivityManagerProxy(mInstance));

        //把IActivityManagerSingleton的mInstance字段，修改为proxy;
        RefInvoke.setFieldObject(singleton, iActivityManager, "mInstance", proxy);
    }
}
