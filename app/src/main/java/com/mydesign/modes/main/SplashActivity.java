package com.mydesign.modes.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mydesign.modes.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 1：避免多嵌套一层，因为DecorView 也是一个FrameLayout;
 * 2：换种方式实现倒计时--用RxJava实现；在后面的地方多用RxJava替换现有方式；
 * 3：解决启动页启动时白屏或者黑屏的问题；
 * 因为：如果这个Activity所属的应用还没有在运行，那么系统会为这个Activity所属的应用创建一个进程，
 * 创建进程是需要时间的，从而导致了白屏或黑屏的出现。
 * 解决办法：在theme中设置 windowBackground；
 */
public class SplashActivity extends Activity {

    @BindView(R.id.tv_count_down)
    TextView tvCountDown;
    private Disposable subscribe;

    private final int MAX_TIME = 3;

    private String TAG = SplashActivity.class.getSimpleName();
    private Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = this;
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        //避免多嵌套一层；
        //getWindow().getDecorView().setBackgroundResource(R.drawable.splash);

        //倒计时
        subscribe = Flowable.intervalRange(0, MAX_TIME, 0, 1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                //倒计时
                Log.e(TAG, "倒计时：：" + (MAX_TIME - aLong));
                tvCountDown.setText(String.valueOf(MAX_TIME - aLong));
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "倒计时：：结束");
                gotoMainActivity();
            }
        }).subscribe();
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(act, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    @OnClick(R.id.tv_count_down)
    public void onViewClicked() {
        gotoMainActivity();
    }
}
