package com.mydesign.modes.plug.ams_hook;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.mydesign.modes.plug.utils.RefInvoke;


public class HandlerCall implements Handler.Callback {

    private String TAG = HandlerCall.class.getSimpleName();

    private Handler baseHandler;

    HandlerCall(Handler handler) {
        super();

        baseHandler = handler;

    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            //ActivityThread的H的LAUNCH_ACTIVITY为100;
            case 100:
                /*final ActivityClientRecord r = (ActivityClientRecord) msg.obj;

                r.packageInfo = getPackageInfoNoCheck(
                        r.activityInfo.applicationInfo, r.compatInfo);*/

                handleLaunchActivity(msg);
                break;
            default:
                break;
        }
        if (baseHandler != null) {
            baseHandler.handleMessage(msg);
            //必须返回true，否则将继续走ActivityThread的H的handleMessage(),不受代理类控制；
            return true;
        }
        return false;
    }

    //简单起见，直接取出targetActivity;
    private void handleLaunchActivity(Message msg) {
        Log.e(TAG, "handleLaunchActivity");

        Object obj = msg.obj;

        //把替身恢复成真身；
        Intent raw = (Intent) RefInvoke.getFieldObject(obj, "intent");
        if (raw != null) {
            Intent target = raw.getParcelableExtra(IActivityManagerProxy.EXTRA_TARGET_INTENT);
            if (target != null) {
                raw.setComponent(target.getComponent());
            }
        }
    }
}
