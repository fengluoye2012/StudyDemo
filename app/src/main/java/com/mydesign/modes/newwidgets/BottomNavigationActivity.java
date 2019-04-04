package com.mydesign.modes.newwidgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mydesign.modes.R;
import com.mydesign.modes.base.BaseActivity;
import com.orhanobut.logger.Logger;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BottomNavigationActivity extends AppCompatActivity {

    @BindString(R.string.app_name)
    String name;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };


    @BindView(R.id.message)
    TextView mTextMessage;
    private Unbinder bind;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
//        bind = ButterKnife.bind(this);
        bind = ButterKnife.bind(this);


        //设置代理消费View;
       // mTextMessage.setTouchDelegate(new TouchDelegate(new Rect(0, 100, 100, 100), null));


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        NestedScrollView nestedScrollView = new NestedScrollView(this);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, BottomNavigationActivity.class);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }


    @OnClick(R.id.message)
    public void click() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.e("onDestroy-----mTextMessage::::" + mTextMessage.getText().toString());
        //需要调用unbind();
        bind.unbind();

    }
}
