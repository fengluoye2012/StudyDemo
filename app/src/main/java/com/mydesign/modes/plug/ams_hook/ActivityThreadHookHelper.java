package com.mydesign.modes.plug.ams_hook;

import android.os.Handler;

import com.mydesign.modes.plug.utils.RefInvoke;


/**
 * 在Looper中取出Message之后，调用 msg.target.dispatchMessage(msg);msg.target就是Handler,即调用Handler的dispatchMessage();
 * <p>
 * Handler的dispatchMessage()方法；如果mCallback不为null,将走Callback的handleMessage()方法
 * public void dispatchMessage(Message msg) {
 * if (msg.callback != null) {
 * handleCallback(msg);
 * } else {
 * if (mCallback != null) {
 * if (mCallback.handleMessage(msg)) {
 * return;
 * }
 * }
 * handleMessage(msg);
 * }
 * }
 * 该类应该放在插件中；要不然插件中没有宿主Activity 对象的StubActivity;
 */
public class ActivityThreadHookHelper {

    public static void hookActivityThread() {


        //获取ActivityThread对象
        Object activityThread = RefInvoke.getStaticFieldObject("android.app.ActivityThread", "sCurrentActivityThread");
        //获取H对象；
        Handler handler = (Handler) RefInvoke.getFieldObject(activityThread, "mH");

        //给Handler类的mCallback对象复制；
        RefInvoke.setFieldObject("android.os.Handler",handler, "mCallback", new HandlerCall(handler));

    }
}
