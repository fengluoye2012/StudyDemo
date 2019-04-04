package com.mydesign.modes.newwidgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.mydesign.modes.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * recyclerView+Header的方式，Header中包含WebView；在WebView的OnPageFinish()回调之后，再填充adapter的数据；
 * 点击滑动到指定位置：https://blog.csdn.net/shanshan_1117/article/details/78780137?utm_source=blogxgwz3
 * 利用了 WebView的缓存和预加载机制；（https://blog.csdn.net/carson_ho/article/details/71402764； ）
 * <p>
 * 如何记录WebView的滑动位置？？？
 */
public class TouTiaoDetailActivity extends AppCompatActivity {

    @BindView(R.id.commonHeadTitle)
    CommonHeadTitle commonHeadTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.textView)
    TextView textView;
    private Activity act;
    private TTHeaderView headerView;
    private NestScrollViewAdapter adapter;
    private TextView footer;

    private List<String> list;
    private boolean clickButtom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = this;
        setContentView(R.layout.activity_tou_tiao_detail);
        ButterKnife.bind(this);

        initView();
        initListener();
        headerView.loadUrl("https://apps-test.youhaodongxi.com/merchandise/queryPageDetail/987");
    }

    private int scrollY = 0;
    private boolean hasChanged;
    private int scrollWebY = 0;

    private void initListener() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //最后一个可见的item;
                int position = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(recyclerView.getChildCount() - 1));
                if (position < 1) {
                    scrollWebY += dy;
                }

                scrollY += dy;
                if (scrollY >= 500 && !hasChanged) {
                    commonHeadTitle.setTitle("风落叶2012");
                    hasChanged = true;
                }

                if (scrollY < 500 && hasChanged) {
                    commonHeadTitle.setTitle("");
                    hasChanged = false;
                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll && RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mShouldScroll = false;
                    smoothMoveToPosition(recyclerView, mToPosition);
                }
            }

        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!clickButtom) {
                    smoothMoveToPosition(1);
                    clickButtom = true;
                } else {
                    smoothMoveToPosition(0);
                    recyclerView.smoothScrollBy(0, 1000);
                    clickButtom = false;
                }
            }
        });

        headerView.setMyWebViewPageFinish(new TTHeaderView.WebViewPageFinish() {
            @Override
            public void webViewPageFinish() {
                setData();
            }
        });
    }


    private void initView() {
        commonHeadTitle.setTitle("");

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NestScrollViewAdapter(null);
        headerView = new TTHeaderView(act);
        footer = new TextView(act);
        footer.setText("我是尾布局");
        adapter.setHeaderView(headerView);
        recyclerView.setAdapter(adapter);

    }

    public void setData() {
        Logger.e("setData");
        adapter.addData(getList());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (headerView != null) {
            headerView.destroyView();
            headerView = null;
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TouTiaoDetailActivity.class);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public List<String> getList() {
        if (list == null) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }

        for (int i = 0; i < 100; i++) {
            list.add("" + i);
        }
        return list;
    }

    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;

    private void smoothMoveToPosition(int position) {
        if (position != -1) {
            smoothMoveToPosition(recyclerView, position);
        } else {
            smoothMoveToPosition(recyclerView, position + 1);
        }
    }

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);

        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }


}
