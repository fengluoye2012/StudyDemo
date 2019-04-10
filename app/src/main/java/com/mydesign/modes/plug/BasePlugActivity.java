package com.mydesign.modes.plug;

import android.app.Activity;
import android.content.Context;

import com.mydesign.modes.plug.hook.ActivityManagerHookHelper;
import com.mydesign.modes.plug.hook.ActivityThreadHookHelper;

public class BasePlugActivity extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            ActivityManagerHookHelper.hookActivityManager();
            ActivityThreadHookHelper.hookActivityThread();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
