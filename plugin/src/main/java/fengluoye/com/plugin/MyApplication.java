package fengluoye.com.plugin;

import android.app.Application;
import android.content.Context;

import fengluoye.com.plugin.helper.ActivityThreadHookHelper;

public class MyApplication extends Application {

    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        ActivityThreadHookHelper.hookActivityThread();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
