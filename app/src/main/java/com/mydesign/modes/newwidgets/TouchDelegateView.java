package com.mydesign.modes.newwidgets;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TouchDelegateView extends LinearLayout {

    private TextView view;

    public TouchDelegateView(Context context) {
        this(context, null);
    }

    public TouchDelegateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchDelegateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setTouchDelegateView(TextView view) {
        this.view = view;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if(getTouchDelegate() == null){
            setTouchDelegate(new TouchDelegate(new Rect(getLeft(), getTop(), getRight(), getBottom()), view));
        }
        return super.onTouchEvent(event);
    }
}
