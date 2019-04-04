package com.mydesign.modes.newwidgets;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import com.orhanobut.logger.Logger;

public class MyListView extends ListView {

    public MyListView(Context context) {
        this(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Logger.e("MyListView  onTouchEvent ::" + ev.getAction() + "::" + super.onTouchEvent(ev));
        return super.onTouchEvent(ev);
    }
}
