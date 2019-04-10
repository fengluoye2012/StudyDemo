package com.mydesign.modes.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import butterknife.ButterKnife;


public abstract class BaseActivity extends Activity {

    protected Activity act;
    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        buildContent();
        ButterKnife.bind(this);
        initData();
    }

    private void init() {
        act = this;
        TAG = act.getClass().getSimpleName();
        MyActivityManager.getInstance().pushActivity(act);
    }

    private void fullScreen(boolean fullScreen) {
        if (fullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    protected abstract void buildContent();


    protected void initData() {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //必须要调用
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        MyActivityManager.getInstance().popActivity(act);
        super.onDestroy();
    }

}
