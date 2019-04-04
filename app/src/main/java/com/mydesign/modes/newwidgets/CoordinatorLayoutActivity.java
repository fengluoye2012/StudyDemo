package com.mydesign.modes.newwidgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mydesign.modes.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * CoordinatorLayout+AppBarLayout+CollapsingToolbarLayout(可折叠) 配合可滑动的View(RecyclerView，NestedScrollView);
 * 的使用
 */
public class CoordinatorLayoutActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private Activity act;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = this;
        setContentView(R.layout.activity_nest_scroll_view);
        bind = ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        String[] titles = {"我的", "你的", "他的"};
        List<Fragment> list = new ArrayList<>();
        list.add(new NestScrollViewFragment());
        list.add(new NestScrollViewFragment());
        list.add(new NestScrollViewFragment());

        FragmentsAdapter fragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager(), list, Arrays.asList(titles));
        viewPager.setAdapter(fragmentsAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CoordinatorLayoutActivity.class);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
