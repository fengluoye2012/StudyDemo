package com.mydesign.modes.service_test;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mydesign.modes.R;

import java.io.File;

public class ServiceTestActivity extends Activity implements View.OnClickListener {

    private String TAG = ServiceTestActivity.class.getSimpleName();
    private BindService bindService;
    private String ACTION = "sendBroadcast";
    private Intent intent;
    private Activity act;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected");
            bindService = ((BindService.LocalBinder) service).getService();
            bindService.startThread();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected");

        }
    };
    private MyReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = this;
        setContentView(R.layout.activity_service_test);

        register();


        TextView textView = (TextView) findViewById(R.id.textView);
        TextView tv_stop = (TextView) findViewById(R.id.tv_stop);
        TextView tv_bind = (TextView) findViewById(R.id.tv_bind);
        TextView tv_unbind = (TextView) findViewById(R.id.tv_unbind);
        TextView tv_send = (TextView) findViewById(R.id.tv_send);
        textView.setOnClickListener(this);
        tv_stop.setOnClickListener(this);
        tv_bind.setOnClickListener(this);
        tv_unbind.setOnClickListener(this);
        tv_send.setOnClickListener(this);
    }

    private void register() {
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter(ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.textView:

                startRecord();
               /* intent = new Intent(this, TestService.class);
                intent.putExtra("name", "fengluoye");
                startService(intent);*/
                break;
            case R.id.tv_stop:

                intent = new Intent(this, TestService.class);
                stopService(intent);
                break;

            case R.id.tv_bind:
                intent = new Intent(this, BindService.class);
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
                break;

            case R.id.tv_unbind:
                unbindService(connection);
                break;

            case R.id.tv_send:
                Intent intent = new Intent();
                intent.setAction(ACTION);
                sendBroadcast(intent);
                LocalBroadcastManager.getInstance(act).sendBroadcast(intent);

                String s = intent.resolveTypeIfNeeded(getContentResolver());
                Log.e(TAG, s);
                break;
        }
    }

    public void test() {
        ClassTest test = new ClassTest();
        InnerTest innerTest = test.innerTest;
        int age = innerTest.age;
        innerTest.getAge();
    }


    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION.equals(intent.getAction())) {
                Log.e(TAG, "接受到广播");
            }

            Bundle extras = intent.getExtras();
            ClassLoader classLoader = extras.getClassLoader();

        }
    }

    public void startRecord() {
        try {

            //makeFile();
            // 激活系统的照相机进行录像，通过Intent激活相机并实现录像功能
            intent = new Intent();

            intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
            //intent.addCategory("android.intent.category.DEFAULT");
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);

            // 以秒为单位指定允许的最大录制持续时间
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile());
            startActivityForResult(intent, 100);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    private void makeFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //SD卡可挂载
            //获取扩展存储设备的文件目录
            File rootFile = Environment.getExternalStorageDirectory();
            String tmpFilePath = rootFile.getPath() + "/yuTest";
            File tmpFile = new File(tmpFilePath);
            if (!tmpFile.exists()) {
                tmpFile.mkdir();
            } else {
                Log.i(TAG, "tmpFile exists");
            }
        }
    }

    private Uri getUriForFile() {

        File imagePath = new File(Environment.getExternalStorageDirectory().getPath(), "yhdxVideo");
        // File imagePath = new File(getExternalFilesDir(), "yhdxVideo");
        File file = new File(imagePath, "videoTest");

        if (file.exists()) {
            boolean delete = file.delete();
        }
        try {
            boolean newFile = file.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(getApplicationContext(), "com.mydesign.modes.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            //视频地址
            Uri uri = data.getData();
            if (uri == null) {
                return;
            }

        } catch (Exception e) {
            Log.e(TAG, "hhhh");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}