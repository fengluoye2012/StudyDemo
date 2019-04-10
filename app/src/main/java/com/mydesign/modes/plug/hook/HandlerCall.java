package com.mydesign.modes.plug.hook;


import android.os.Handler;
import android.os.Message;
import android.util.Log;


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
                handleLaunchActivity();
                break;
            default:
                break;
        }
        baseHandler.handleMessage(msg);
        //必须返回true，否则将继续走ActivityThread的H的handleMessage(),不受代理类控制；
        return true;
    }

    private void handleLaunchActivity() {
        Log.e(TAG, "handleLaunchActivity");
    }
}
