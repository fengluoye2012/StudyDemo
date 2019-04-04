package com.mydesign.modes.main;

import android.util.Log;
import android.widget.TextView;

import com.mydesign.modes.R;
import com.mydesign.modes.base.BaseActivity;

import butterknife.BindView;

public class SecondActivity extends BaseActivity {
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void buildContent() {
        super.setContentView(R.layout.actvity_second);
        Log.e(TAG, "////////////////////////////");
    }

}
