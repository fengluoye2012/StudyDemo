package com.mydesign.modes.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mydesign.modes.R;
import com.mydesign.modes.newwidgets.ProgressWebView;
import com.mydesign.modes.plug.ams_hook.ActivityManagerHookHelper;
import com.mydesign.modes.plug.ams_hook.ActivityThreadHookHelper;
import com.mydesign.modes.plug.class_loader.LoadApkClassLoaderHookerHelper;
import com.mydesign.modes.plug.utils.PluginUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


public class MyApplication extends Application {

    private static Context context;
    private static ProgressWebView progressWebView;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        try {

            String packageName = getPackageName();


            String path = "plugin-release.apk";
            //1)将asset目录下apk复制到data/data/file 文件下；
            PluginUtils.extractAssets(base, path);
            //2）为每个插件创建一个DexClassLoaded;
            LoadApkClassLoaderHookerHelper.hookLoadedApkInActivityThread(getFileStreamPath(path));
            //3)hook APP和ActivityManagerService进程通信的ActivityManager的IActivityManagerService对象；进行替换；
            ActivityManagerHookHelper.hookActivityManager();
            //4）替换为真实要启动的Activity；该步骤在插件中
            ActivityThreadHookHelper.hookActivityThread();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Fresco.initialize(this);
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return true;
            }
        });

        progressWebView = new ProgressWebView(context);
        setTheme(R.style.AppTheme);
    }

    public static Context getContext() {
        return context;
    }

    //全局公用；
    public static ProgressWebView getProgressWebView() {
        if (progressWebView == null) {
            progressWebView = new ProgressWebView(getContext());
        }
        return progressWebView;
    }
}
