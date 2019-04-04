package com.mydesign.modes.newwidgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.mydesign.modes.R;
import com.orhanobut.logger.Logger;


public class ProgressWebView extends WebView {

    private ProgressBar progressBar;

    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        progressBar = new ProgressBar(getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(3));
        progressBar.setLayoutParams(layoutParams);
        progressBar.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
        progressBar.setProgress(0);
        addView(progressBar);

        initWebView();
    }

    private void initWebView() {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(true);
        //settings.setAppCachePath("/data/data/" + AppContext.getApp().getPackageName() + "/databases/");
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        // settings.setDatabasePath("/data/data/" + AppContext.getApp().getPackageName() + "/databases/");
        //设置可以访问文件
        settings.setAllowFileAccess(true);

        //5.0 之后支持默认不支持https连接；支持http,https混合加载;这个很重要
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Logger.e("progress:::" + newProgress);
                progressBar.setVisibility(VISIBLE);
                progressBar.setMax(100);
                progressBar.setProgress(newProgress);
                if (newProgress >= 100) {
                    progressBar.setVisibility(GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Logger.e("title::" + title + "");
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public View getVideoLoadingProgressView() {
                return super.getVideoLoadingProgressView();
            }
        });
    }

    public int dip2px(float dpValue) {
        try {
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } catch (Exception e) {
            return 0;
        }
    }

    public void initProgress(){
        progressBar.setProgress(0);
    }


}
