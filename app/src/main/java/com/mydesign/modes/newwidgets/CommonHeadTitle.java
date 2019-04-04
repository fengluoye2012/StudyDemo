package com.mydesign.modes.newwidgets;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.mydesign.modes.R;


public class CommonHeadTitle extends LinearLayout {

    private SimpleToolbar toolbar;

    public CommonHeadTitle(Context context) {
        this(context, null);
    }

    public CommonHeadTitle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonHeadTitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_title, this);
        toolbar = (SimpleToolbar) view.findViewById(R.id.toolbar);

        toolbar.setLeftTitleClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = (Activity) getContext();
                act.finish();
            }
        });
    }

    public void setTitle(String title){
        toolbar.setMainTitle(title);
    }

}
