package com.mydesign.modes.main;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mydesign.modes.R;
import com.mydesign.modes.aidl_test.AIDLTestActivity;
import com.mydesign.modes.design_modes.build.BuilderClient;
import com.mydesign.modes.design_modes.observer.ObserverClient;
import com.mydesign.modes.design_modes.singleton.SingletonTest2;
import com.mydesign.modes.base.BaseTitleActivity;
import com.mydesign.modes.datastructure.ArithmeticTest;
import com.mydesign.modes.datastructure.ListTest;
import com.mydesign.modes.installAPP.InstallAppActivity;
import com.mydesign.modes.newwidgets.NewWidgetsTest;
import com.mydesign.modes.okhttp.OKHttpTest;
import com.mydesign.modes.retrofit.RetrofitUtils;
import com.mydesign.modes.service_test.ServiceTestActivity;
import com.mydesign.modes.synchronizedtest.ObjectSynchronizedTest;
import com.mydesign.modes.synchronizedtest.SynchronizedTest;
import com.mydesign.modes.synchronizedtest.locktest.LockTest;
import com.mydesign.modes.synchronizedtest.threadcommu.ThreadCommuTest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnItemClick;

import static com.mydesign.modes.synchronizedtest.threadcommu.ThreadCommuTest.test;


public class MainActivity extends BaseTitleActivity {

    @BindString(R.string.app_name)
    public String name;
    @BindView(R.id.listView)
    ListView listView;

    private int index;


    @Override
    public void buildContentView() {
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void initData() {
        super.initData();

        List<String> list = new ArrayList<>();
        list.add("单例模式");
        list.add("Builder模式");
        list.add("synchronized关键字");
        list.add("线程间通信wait");
        list.add("lock锁");
        list.add("数据结构");
        list.add("算法");
        list.add("Android新控件");
        list.add("设计模式");
        list.add("OkHttp");
        list.add("Retrofit");
        list.add("Service");
        list.add("AIDL Test");
        list.add("安装APK文件");
        list.add("Android新控件111");
        list.add("Android新控件111");
        list.add("Android新控件111");
        list.add("Android新控件111");
        list.add("Android新控件111");
        list.add("Android新控件111");

        MainAdapter mainAdapter = new MainAdapter(act, list);

        listView.setAdapter(mainAdapter);
        listView.setStackFromBottom(true);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mainAdapter.setOnSlipDeleteViewItemClickListener(new MainAdapter.OnSlipDeleteViewItemClick() {
            @Override
            public void onItemClick(int pos) {
                dealItemClick(pos);
            }
        });
    }

    private void dealItemClick(int pos) {
        switch (pos) {
            case 0:
                singletonTest();
                break;
            case 1:
                builderTest();
                break;
            case 2:
                synchronizedTest();
                break;
            case 3:
                multiThread();
                break;
            case 4:
//                LockTest.getInstance().test();
                LockTest.getInstance().testLock();
                break;
            case 5:
                ListTest.getInstance().test();
                break;
            case 6:
                ArithmeticTest.getInstance().test();
                break;

            case 7:
                NewWidgetsTest.getInstance().test();
                break;
            case 8:
                EventBus.getDefault().postSticky(new MessageEvent(System.currentTimeMillis() + "postSticky"));
                designMode();
                break;

            case 9:
                OKHttpTest.get();
                break;
            case 10:
                RetrofitUtils.getInstance().getRxJava2();

            case 11:
                startActivity(new Intent(this, ServiceTestActivity.class));
                break;
            case 12:
                startActivity(new Intent(this, AIDLTestActivity.class));
                break;
            case 13:
                startActivity(new Intent(this, InstallAppActivity.class));
                break;
        }
    }

    private void doWhileTest() {
        List<String> dalist = new ArrayList<>();
        dalist.add("1....");
        dalist.add("2.....");
        dalist.add("3.....");
        String str;
        do {
            if (dalist.size() == 0) {
                Log.e(TAG, "dalist.size() == 0");
                return;
            }
            str = dalist.get(0);
            Log.e(TAG, "str:::" + str);
            if (str.equals("1....")) {
                dalist.remove(0);
                str = "";
                continue;
            }
        } while (TextUtils.isEmpty(str));
        Log.e(TAG, "do while 结束");
    }

    private void designMode() {
        //ProxyClient.getInstance().test();
        ObserverClient.getInstance().test();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 10)
    public void onMessageEvent(MessageEvent event) {


        MessageEvent stickyEvent = EventBus.getDefault().getStickyEvent(MessageEvent.class);


    }


    @OnItemClick(R.id.listView)
    public void onItemClickListener(int pos) {
        dealItemClick(pos);
    }

    private void glideTest() {
        ImageView imageView = new ImageView(act);
        RequestOptions options = new RequestOptions().error(R.drawable.ic_launcher_background).centerCrop().placeholder(R.drawable.ic_launcher_background);
        Glide.with(act).load("").apply(options).into(imageView);

    }

    private void synchronizedTest() {
        SynchronizedTest.getInstance().startThread();
        Log.e(TAG, "////////////////////////////");
        ObjectSynchronizedTest.getInstance().start();
    }

    private void multiThread() {
//        Log.e("MyList", "////////////////////////////");
//        ProductConsumeTest.getInstance().start();

        Log.e("AThread", "////////////////////////////");
        test();
    }

    private void singletonTest() {
//        SingletonTest.getInstance().test();
//        SingletonTest1.getInstance().test();

        Log.e("hh", "////////////////////////////");
        int i = 0;
        while (i < 10) {
            MyThread myThread = new MyThread();
            myThread.setName("线程" + i);
            myThread.start();
            i++;
        }

//        SingletonTest2.getInstance().test();
    }

    private void builderTest() {
        BuilderClient.getInstance().builderTest();

    }


    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            SingletonTest2.getInstance().test();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
