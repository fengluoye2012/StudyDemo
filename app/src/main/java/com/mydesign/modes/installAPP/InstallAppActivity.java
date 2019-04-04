package com.mydesign.modes.installAPP;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mydesign.modes.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

public class InstallAppActivity extends Activity {

    @BindView(R.id.tv_install)
    TextView tvInstall;
    @BindView(R.id.tv_uninstall)
    TextView tvUninstall;
    private String TAG;
    private Activity mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = InstallAppActivity.class.getSimpleName();
        mContext = this;
        setContentView(R.layout.activity_install_app);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.tv_install, R.id.tv_uninstall})
    public void click(View view) {
        if (view.getId() == R.id.tv_install) {
            installApp();
        } else if (view.getId() == R.id.tv_uninstall) {
            unInstallApp();
        }
    }

    private void installApp() {
        //判断SD卡是否加载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String fileStr = Environment.getExternalStorageDirectory() + "/" + "apk/yingyongbao_7332130.apk";
            install(fileStr);
        } else {
            Log.e(TAG, "SD卡不存在");
        }
    }

    //PackageInstallerActivity
    private void install(String filePath) {
        Log.e(TAG, "开始执行安装: " + filePath);
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.e(TAG, "版本大于 N ，开始使用 fileProvider 进行安装");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, "com.mydesign.modes.fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            Log.e(TAG, "正常进行安装");
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

    private void unInstallApp() {

    }


    public void gotoActivity(Activity activity) {
        Intent intent = new Intent(activity, InstallAppActivity.class);
        activity.startActivity(intent);
    }
}
