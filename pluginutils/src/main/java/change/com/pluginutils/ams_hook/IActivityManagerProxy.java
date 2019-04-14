package change.com.pluginutils.ams_hook;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import change.com.pluginutils.MyApplication;


public class IActivityManagerProxy implements InvocationHandler {
    private String TAG = IActivityManagerProxy.class.getSimpleName();
    private Object mBase;

    private final static String METHOD_NAME = "startActivity";

    public final static String EXTRA_TARGET_INTENT = "extra_target_intent";

    IActivityManagerProxy(Object object) {
        mBase = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //拦截startActivity()方法；替换参数，将要启动的Activity替换为宿主App中的StubActivity，并且将要开启的Activity作为参数保存在Intent中

        if (METHOD_NAME.equals(method.getName())) {
            replaceActivity(args);
            Log.e(TAG, "拦截到了：：" + method.getName());


            return method.invoke(mBase, args);
        }
        return method.invoke(mBase, args);
    }

    //偷梁换柱（狸猫换太子）
    private void replaceActivity(Object[] args) {

        int index = -1;
        Intent raw;
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Intent) {
                index = i;
                break;
            }
        }

        raw = (Intent) args[index];

        /*String name = StubActivity.class.getName();
        int lastIndex = name.lastIndexOf(".");
        String packageName = name.substring(0,lastIndex);*/

        Intent newIntent = new Intent();
        ComponentName componentName = new ComponentName(MyApplication.getContext().getPackageName(), StubActivity.class.getName());
        newIntent.setComponent(componentName);
        //保存原有信息；
        newIntent.putExtra(EXTRA_TARGET_INTENT, raw);

        //替换掉Intent，欺骗AMS；
        args[index] = newIntent;
    }
}
