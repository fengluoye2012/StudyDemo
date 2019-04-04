package com.mydesign.modes.newwidgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mydesign.modes.main.MainAdapter;
import com.mydesign.modes.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TouchEventActivity extends AppCompatActivity {

    @BindView(R.id.listView)
    MyListView listView;
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.textView)
    TouchDelegateView textView;
    @BindView(R.id.tv_other)
    TextView tvOther;
    private Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = this;
        setContentView(R.layout.activity_touch_event);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {

        scrollView.setScrollChild(listView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //scrollView.setNestedScrollingEnabled(true);
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add("条目：：" + i);
        }
        MainAdapter mainAdapter = new MainAdapter(act, list);
        listView.setAdapter(mainAdapter);

        textView.setTouchDelegateView(tvOther);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e("我是textView");
            }
        });
        tvOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e("我是OtherView");
            }
        });
    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TouchEventActivity.class);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}
