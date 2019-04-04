package com.mydesign.modes.newwidgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ScrollView;

//ListView 播放视频，当播放的Item不在可见的item范围内，就释放掉资源；
public class MyScrollView extends ScrollView {

    private View scrollChild;
    private boolean listViewButtom;
    private float lastY;
    private float lastTouchY;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /**
     * 1：为什么requestDisallowInterceptTouchEvent(true)无效呢？
     * 要放在dispatchTouchEvent()方法中，在调用super.dispatchTouchEvent()前；
     * 不允许父View拦截touch event;onInterceptTouchEvent()方法不在调用；
     * requestDisallowInterceptTouchEvent(true);
     * <p>
     * 2：如何在ListView滑到滑到最底部的时候，滑动ListView把事件让ScrollView来处理呢？？
     * 3：在ScrollView已经不可再滑动了，滑动ScrollView,ListView也一起下来？
     *
     * @param ev
     * @return
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();//获取手指的位置相对于左上角的位置；
                float disY = moveY - lastY;
                lastY = moveY;
                //Logger.e("disY::::" + disY);
                if (disY > 0) {//手指向下滑动；
                    requestDisallowInterceptTouchEvent(true);
                } else {//手指向上滑动
                    //如果ListView已经滑动到底部，就容许父类拦截，这个时候滑动事件就不会由ListView消费了；
                    if (scrollChild instanceof AbsListView) {
                        if (isListViewBottom((AbsListView) scrollChild)) {
                            requestDisallowInterceptTouchEvent(false);
                        } else {
                            requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    public void setScrollChild(ListView listView) {
        scrollChild = listView;
    }

    public boolean isListViewTop(AbsListView absListView) {
        if (absListView.getAdapter() != null && absListView.getCount() > 0 && absListView.getFirstVisiblePosition() == 0) {
            View childAt = absListView.getChildAt(0);
            //getTop有可能永远大于0；
            return childAt.getTop() - absListView.getPaddingTop() == 0;
        }
        return false;
    }

    //判断滑动到ListView的底部
    public boolean isListViewBottom(AbsListView absListView) {
        if (absListView.getAdapter() != null && absListView.getChildCount() > 0 && absListView.getLastVisiblePosition() == absListView.getAdapter().getCount() - 1) {
            //getChildAt(index):0<= index <getChildCount():getChildCount()表示有效的Item树量；
            return absListView.getChildAt(absListView.getChildCount() - 1).getBottom() == absListView.getMeasuredHeight();
        }
        return false;
    }

    //在ScrollView已经不可再滑动了,就不在拦截事件了；onTouchEvent
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(ev);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
