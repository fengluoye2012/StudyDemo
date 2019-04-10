package com.mydesign.modes.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mydesign.modes.R;
import com.mydesign.modes.newwidgets.ProgressWebView;
import com.mydesign.modes.plug.hook.ActivityManagerHookHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


public class MyApplication extends Application {

    private static Context context;
    private static ProgressWebView progressWebView;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);



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
