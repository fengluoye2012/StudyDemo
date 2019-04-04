package com.mydesign.modes.newwidgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.OverScroller;
import android.widget.TextView;

import com.mydesign.modes.main.MainAdapter;
import com.mydesign.modes.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//OverScroller/Scroller类的应用
public class OverScrollerActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.listView)
    MyListView listView;
    @BindView(R.id.linearLayout)
    MyLinearLayout linearLayout;

    private OverScroller overScroller;
    private Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = this;
        setContentView(R.layout.activity_over_scroller);
        ButterKnife.bind(this);
        linearLayout.setChild(listView);

        initListeners();
        initData();
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add("条目：：" + i);
        }
        MainAdapter mainAdapter = new MainAdapter(act, list);
        listView.setAdapter(mainAdapter);
    }

    /**
     * scrollBy():内容的滑动，而不是View位置的滑动；
     */
    private void initListeners() {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //第一个参数x表示相对于当前位置横向移动的距离，正值向左移动，负值向右移动，单位是像素。
                // 第二个参数y表示相对于当前位置纵向移动的距离，正值向上移动，负值向下移动，单位是像素。
                // tvOther.scrollBy(0, -tvOther.getHeight() / 4);
                //tvOther.scrollTo(0, tvOther.getHeight()/4);
                //tvOther.invalidate();
            }
        });

    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, OverScrollerActivity.class);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}
