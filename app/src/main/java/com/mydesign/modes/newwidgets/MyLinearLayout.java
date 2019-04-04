package com.mydesign.modes.newwidgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.mydesign.modes.utils.CommonUtils;
import com.orhanobut.logger.Logger;

/**
 * Scroller或者OverScroller:在UP中调用startScroll()自动滑动剩下距离；可用于回弹，滑动等效果；滑动效果类似于ObjectAnimator;
 * scrollTo,ScrollBy用于内容的滑动
 */
public class MyLinearLayout extends LinearLayout {

    private OverScroller overScroller;
    private float lastMoveY;
    private float downY;
    private int oldX;
    private int oldY;
    private int slop;
    private View scrollChild;
    private int scrollTotal;
    private int maxOverScroll;
    private float lastMoveX;


    public MyLinearLayout(Context context) {
        this(context, null);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        overScroller = new OverScroller(getContext());
        scrollTotal = CommonUtils.dipToPixels(100);
        maxOverScroll = CommonUtils.dipToPixels(100);
    }

    public void setChild(AbsListView listView) {
        scrollChild = listView;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        float disY = 0;
        float disX = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastMoveY = event.getY();
                lastMoveX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                disY = event.getY() - lastMoveY;
                disX = event.getX() - lastMoveX;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }


        //disY<0:向上滑动；disY>0:向下滑动；
        //往上滑动；
        if (getScrollY() >= 0 - maxOverScroll && getScrollY() < scrollTotal && disY < 0 && Math.abs(disY) - Math.abs(disX) > slop) {
            Logger.e("disY::" + disY + ",,getScrollY()::" + getScrollY() + ",,total::" + scrollTotal);
            Logger.e("MyLinearLayout 拦截了");
            return true;
        }

        //往下滑动
        if (getScrollY() > -maxOverScroll && getScrollY() <= scrollTotal && disY > 0 && Math.abs(disY) - Math.abs(disX) > slop && isTopListView()) {
            Logger.e("disY::" + disY + ",,getScrollY()::" + getScrollY() + ",,total::" + scrollTotal);
            Logger.e("MyLinearLayout 拦截了");
            return true;
        }

        return super.onInterceptTouchEvent(event);
    }


    //由于没有考虑到速率等问题，滑动过程中有卡顿的感觉；
    //不把放宽具体的滑动距离+Scroller/OverScroller可以有回弹的效果；
    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // Logger.e("MyLinearLayout:::TouchEvent::" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                lastMoveY = event.getY();
                downY = lastMoveY;
                break;
            case MotionEvent.ACTION_MOVE:
                float curY = event.getY();
                float curX = event.getX();
                //disY<0:向上滑动；disY>0:向下滑动；
                float disY = curY - lastMoveY;
                float disX = curX - lastMoveX;
                lastMoveY = curY;


                //最大值--0；向上滑动  满足最小滑动距离；
                if (getScrollY() >= 0 - maxOverScroll && getScrollY() < scrollTotal && disY < 0 && Math.abs(disY) - Math.abs(disX) > slop) {
                    //要判断滑动dis之后是否超过total;
                    if (getScrollY() + (-disY) > scrollTotal) {
                        scrollBy(0, scrollTotal - getScrollY());
                    } else {
                        scrollBy(0, -(int) disY);
                    }
                    return true;
                }

                //0--最大值；向下滑动  满足最小滑动距离；
                if (getScrollY() > -maxOverScroll && getScrollY() <= scrollTotal && disY > 0 && Math.abs(disY) - Math.abs(disX) > slop) {
                    //要判断滑动dis之后，是否小于0；
                    if (getScrollY() - disY + maxOverScroll < 0) {
                        scrollBy(0, -(getScrollY() + maxOverScroll));
                    } else {
                        scrollBy(0, -(int) disY);
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                //松手时，getScrollY()》一半就往上滑动，否则就恢复原来的位置
                int upDx = getScrollY() / (float) scrollTotal > 0.5 ? scrollTotal - getScrollY() : 0 - getScrollY();

                int dy = getScrollY() < 0 ? -getScrollY() : upDx;
                overScroller.startScroll(0, getScrollY(), 0, dy);//dy：偏移量
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.getCurrX(), overScroller.getCurrY());
            postInvalidate();
        }
    }

    public boolean isTopListView() {
        if (scrollChild instanceof AbsListView) {
            AbsListView scrollChild = (AbsListView) this.scrollChild;
            if (scrollChild.getAdapter() != null && scrollChild.getAdapter().getCount() > 0
                    && scrollChild.getFirstVisiblePosition() == 0 && scrollChild.getChildAt(0).getTop() - scrollChild.getPaddingTop() == 0) {
                return true;
            }
        }
        return false;
    }
}
