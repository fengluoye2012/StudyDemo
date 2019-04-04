package com.mydesign.modes.aidl_test;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mydesign.modes.IMyAidlInterface;
import com.mydesign.modes.R;

public class AIDLTestActivity extends Activity implements View.OnClickListener {

    private Intent intent;
    private String TAG = AIDLTestActivity.class.getSimpleName();
    private IMyAidlInterface iMyAidlInterface;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            Log.e(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidltest);

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView click = (TextView) findViewById(R.id.click);
        TextView tv_unbind = (TextView) findViewById(R.id.tv_unbind);
        textView.setOnClickListener(this);
        click.setOnClickListener(this);
        tv_unbind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                toBindService();
                Log.e(TAG, "myPid:::" + Process.myPid());

                break;
            case R.id.click:

                addTest();

                break;

            case R.id.tv_unbind:
                unbindService(connection);
                break;
            default:
                break;
        }
    }

    private void addTest() {
        try {
            int add = iMyAidlInterface.add(20, 30);
            Log.e(TAG, "add:::" + add);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void toBindService() {
        intent = new Intent(this, RemoteService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
}
