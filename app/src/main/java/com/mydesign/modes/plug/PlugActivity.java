package com.mydesign.modes.plug;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mydesign.modes.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * DecorView 是一个FrameLayout;
 * 目的：
 * 1）加载插件APK中的资源文件
 * 2）加载插件APK中的类
 * 3）加载插件APK中的Activity,能够正常展示和跳转；
 */
public class PlugActivity extends BasePlugActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_startActivity)
    TextView tvStartActivity;
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
        ButterKnife.bind(this);
    }

    @OnClick({R.id.textView, R.id.tv_startActivity})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.textView) {



        } else if (view.getId() == R.id.tv_startActivity) {
            //开启Activity最后都是将参数组成ComponentName对象+Bundle；
            Intent intent = new Intent();
            //intent.setClassName("fengluoye.com.plugin", "fengluoye.com.plugin.PluginTestActivity");
            intent.setClassName("com.mydesign.modes", "com.mydesign.modes.plug.LoginActivity");
            //intent.putExtra()
            startActivity(intent);
        }
    }

}
