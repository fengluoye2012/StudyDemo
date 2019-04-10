package com.mydesign.modes.plug;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mydesign.modes.R;
import com.mydesign.modes.main.MainActivity;
import com.mydesign.modes.plug.hook.ActivityManagerHookHelper;
import com.mydesign.modes.plug.hook.ActivityThreadHookHelper;

/**
 * DecorView 是一个FrameLayout;
 * 目的：
 * 1）加载插件APK中的资源文件
 * 2）加载插件APK中的类
 * 3）加载插件APK中的Activity,能够正常展示和跳转；
 */
public class PlugActivity extends BasePlugActivity {

    private Activity act;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

    }

    /**
     * startActivity最终会调用 MyActivityManager.getService().startActivity()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = this;
        setContentView(R.layout.activity_plug);

        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }
}
