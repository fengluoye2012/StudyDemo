package com.mydesign.modes.newwidgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2018/11/5.
 */

public class MyTextView extends TextView {
    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e("TextView 的点击事件");
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Logger.e("TextView的onTouchEvent");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Logger.e("TextView接收到了down事件");
        }
        return super.onTouchEvent(event);
    }
}
