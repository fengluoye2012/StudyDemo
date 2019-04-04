package com.mydesign.modes.newwidgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.TextView;

import com.mydesign.modes.R;
import com.orhanobut.logger.Logger;

/**
 * 侧滑删除:适用于普通View，ListView，RecyclerView;
 */
public class SideSlipDeleteView extends LinearLayout {

    private OverScroller scroller;
    private int touchSlop;
    private float lastY;
    private float lastX;
    private TextView rightView;
    private float lastDownX;

    public SideSlipDeleteView(Context context) {
        this(context, null);
    }

    public SideSlipDeleteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideSlipDeleteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
        //Scroller scroller = new Scroller(getContext());
        scroller = new OverScroller(getContext());
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    //View加载完成；
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //获取需要滑出的View的宽度
        rightView = (TextView) findViewById(R.id.tv_right);
    }

    /**
     * 问题0：侧滑删除 放在ListView中，为什么只接受了Down,Move和UP 都没有接收到？？
     * 因为AbsTouchEvent的onTouchEvent返回true，如果down事件不被SlipDeleteView消费，就会被AbsListView的OnTouchEvent()消费，
     * Move,Up都会直接分发到AbsListView；不会再往传递了；所有SlipDeleteView的onTouchEvent()返回true；
     * <p>
     * 问题一：
     * 但是如果在SlipDeleteView的OnTouchEvent()直接返回true，ListView的条目点击事件就会被没有了；该如何解决？？？
     * 只能用SlipDeleteView的点击事件替换ListView的条目点击事件；
     * <p>
     * 问题二：
     * SlipDeleteView的点击事件冲突？？
     * 那就在Up中重写performClick()，会调用setOnClickListener()监听；
     * <p>
     * 问题三：如何只滑出一个View？？
     * move 将上一个已经滑出的Item时，关闭；Open的时候，记录当前的SlipDeleteView；关闭的时候，将其制null;
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = ev.getX();
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //disX>0 右滑；disX<0 左滑；
                float disX = ev.getX() - lastX;
                float disY = ev.getY() - lastY;

                //判断是X轴滑动还是Y轴滑动；X轴滑动就拦截；
                //Logger.e("disX::" + disX + ",,disY::" + disY + ",,dix::" + (disX - disY) + ",,,getScrollX()::" + getScrollX());

                //左滑
                if (Math.abs(disX) - Math.abs(disY) > touchSlop && disX < 0 && getScrollX() < getRightWidth()) {
                    return true;
                }
                //右滑
                if (Math.abs(disX) - Math.abs(disY) > touchSlop && disX > 0 && getScrollX() > 0) {
                    //Logger.e("onInterceptTouchEvent  action::" + ev.getAction());
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    //onTouchEvent 返回true 表示消费该事件；
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = ev.getX();
                lastY = ev.getY();

                lastDownX = lastX;
                if (listener != null) {
                    listener.slipDeleteMove(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //disX>0 右滑；disX<0 左滑；
                float disX = ev.getX() - lastX;
                float disY = ev.getY() - lastY;
                lastX = ev.getX();
                lastY = ev.getY();

                //左滑
                if (Math.abs(disX) - Math.abs(disY) > touchSlop && disX < 0 && getScrollX() < getRightWidth()) {

                    if (getScrollX() + (-disX) > getRightWidth()) {//临界值
                        //正值向左移动，负值向右移动
                        scrollBy(getRightWidth() - getScrollX(), 0);
                    } else {
                        scrollBy(-(int) disX, 0);
                    }
                }

                //右滑
                if (Math.abs(disX) - Math.abs(disY) > touchSlop && disX > 0 && getScrollX() > 0) {
                    if (getScrollX() - disX < 0) {
                        scrollBy(getScrollX(), 0);
                    } else {
                        scrollBy(-(int) disX, 0);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                float upX = ev.getX();
                //左滑
                if (upX - lastDownX < 0) {
                    slipLeft();
                }
                //右滑
                if (upX - lastDownX > 0) {
                    slipIn();
                }

                //避免滑动事件和点击事件冲突；
                if (Math.abs(upX - lastDownX) < touchSlop && Math.abs(upX - lastDownX) < touchSlop) {
                    //执行setOnClickListener()方法；
                    //Logger.e("调用performClick()");
                    performClick();
                }
                break;
        }
        return true;
    }

    //右滑 如果滑出超过75%；全部滑出；否者隐藏；
    private void slipIn() {
        boolean isShow = getScrollX() / (float) getRightWidth() > 0.75;
        int dx = isShow ? getRightWidth() - getScrollX() : 0 - getScrollX();
        setOpenOrClose(isShow);
        scroller.startScroll(getScrollX(), getScrollY(), dx, 0);
        invalidate();
    }

    //左滑 如果滑出超过25%；全部滑出；否者隐藏
    private void slipLeft() {
        boolean isShow = getScrollX() / (float) getRightWidth() > 0.25;
        int dx = isShow ? getRightWidth() - getScrollX() : 0 - getScrollX();
        setOpenOrClose(isShow);
       // Logger.e("全部显示：：" + isShow + ",, getScrollX() " + getScrollX() + ",,dx ::" + dx);
        scroller.startScroll(getScrollX(), getScrollY(), dx, 0);
        invalidate();
    }

    public void closeSlipDelete() {
        scroller.startScroll(getScrollX(), getScrollY(), 0 - getScrollX(), 0);
        invalidate();//不能少
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
           // Logger.e("scroller.getCurrX()::" + scroller.getCurrX());
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    public int getRightWidth() {
        return rightView.getMeasuredWidth();
    }

    public void setOpenOrClose(boolean isShow) {
        if (isShow) {
            if (listener != null) {
                listener.slipDeleteOpen(this);
            }
        } else {
            if (listener != null) {
                listener.slipDeleteClose(this);
            }
        }
    }

    //同时只有一个item滑出；可以很好的代码分离
    public interface OnSlipDeleteStateChangeListener {
        void slipDeleteOpen(SideSlipDeleteView sideSlipDeleteView);

        void slipDeleteClose(SideSlipDeleteView sideSlipDeleteView);

        void slipDeleteMove(SideSlipDeleteView sideSlipDeleteView);
    }

    OnSlipDeleteStateChangeListener listener;

    public void setSlipDeleteStateChangeListener(OnSlipDeleteStateChangeListener listener) {
        this.listener = listener;
    }
}
