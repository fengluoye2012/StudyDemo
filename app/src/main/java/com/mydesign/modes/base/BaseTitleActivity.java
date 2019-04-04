package com.mydesign.modes.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mydesign.modes.R;

import butterknife.BindView;

public abstract class BaseTitleActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_net_err)
    TextView tvNetErr;

    @Override
    protected void buildContent() {
        super.setContentView(R.layout.activity_base_title);
        buildContentView();
    }

    public abstract void buildContentView();


    @Override
    public void setContentView(int layoutResID) {
        View.inflate(act, layoutResID, (ViewGroup) findViewById(R.id.content));
    }


    protected void setTitle(String title) {
        tvNetErr.setVisibility(View.GONE);
        tvTitle.setText(title);
    }

}
