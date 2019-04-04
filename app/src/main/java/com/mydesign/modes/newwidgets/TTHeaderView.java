package com.mydesign.modes.newwidgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.mydesign.modes.base.MyApplication;
import com.orhanobut.logger.Logger;


public class TTHeaderView extends LinearLayout{

    private Context context;
    private ProgressWebView progressWebView;

    public TTHeaderView(Context context) {
        this(context, null);
    }

    public TTHeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TTHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        setOrientation(VERTICAL);

        progressWebView = MyApplication.getProgressWebView();

        progressWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Logger.e("onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Logger.e("onPageFinished");

                if (webViewPageFinish != null) {
                    webViewPageFinish.webViewPageFinish();
                }
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }


        });
        addView(progressWebView);
    }


    public void loadUrl(String url) {
        progressWebView.initProgress();
        progressWebView.loadUrl(url);
    }

    interface WebViewPageFinish {
        public void webViewPageFinish();
    }

    public WebViewPageFinish webViewPageFinish;

    public void setMyWebViewPageFinish(WebViewPageFinish webViewPageFinish) {
        this.webViewPageFinish = webViewPageFinish;
    }

    public void destroyView() {
        if (progressWebView != null) {
            progressWebView.removeAllViews();
        }
        removeAllViews();
    }



}
