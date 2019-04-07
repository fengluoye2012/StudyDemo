package com.mydesign.modes.okhttp;


import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.Handshake;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttpClient
 * 1：正常的执行流程；一个网络请求的执行流程；OkHttpClient、Request、Call（RealCall）、Dispatcher（决定异步请求何时会执行）
 * 2：header、cache、拦截、取消请求；
 */
public class OkHttpClientUtils {

    private static OkHttpClientUtils instance = null;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient.Builder()//.addInterceptor(null)  //拦截
            //.cache(new Cache(new File(), 50L)) //缓存
            .connectTimeout(5, TimeUnit.SECONDS) //5秒钟
            .callTimeout(5, TimeUnit.SECONDS).build();

    private OkHttpClientUtils() {
    }

    public static OkHttpClientUtils getInstance() {
        if (instance == null) {
            synchronized (OkHttpClientUtils.class) {
                if (instance == null) {
                    instance = new OkHttpClientUtils();
                }
            }
        }
        return instance;
    }

    public void req(String url, boolean post, BaseReqEntity entity) {
        if (post) {
            post(url, entity);
        } else {
            get(url, entity);
        }
    }

    public void get(String url, BaseReqEntity entity) {
        CacheControl cacheControl = new CacheControl.Builder().maxAge(1000, TimeUnit.SECONDS).maxStale(1000, TimeUnit.SECONDS).immutable().build();


        final Request request = new Request.Builder().get().url(url).tag("").cacheControl(cacheControl).build();

        String s = request.toString();

        //异步
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Request request1 = response.request();
                HttpUrl url1 = request1.url();

                String string = response.body().string();
                com.orhanobut.logger.Logger.e(string);
            }
        });

    }

    public void post(String url, BaseReqEntity entity) {

        CacheControl cacheControl = new CacheControl.Builder().maxAge(1000, TimeUnit.SECONDS).maxStale(1000, TimeUnit.SECONDS).minFresh(1000, TimeUnit.SECONDS).immutable().build();

        RequestBody requestBody = RequestBody.create(JSON, "p=1&k=android");
        Request post = new Request.Builder().post(requestBody).cacheControl(cacheControl).url(url).headers(getHeader()).build();

        client.newCall(post).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Handshake handshake = response.handshake();
                Request request = response.request();
                Object tag = request.tag();
                HttpUrl httpUrl = request.url();

                String string = response.body().string();
                com.orhanobut.logger.Logger.e(string);
            }
        });
    }


    public Headers.Builder getHeaders() {
        Headers.Builder builder = new Headers.Builder();
        builder.add("time", "");
        builder.add("", "");
        return builder;
    }

    public Headers getHeader() {
        return getHeader(new HashMap<String, String>());
    }

    public Headers getHeader(Map<String, String> map) {
        return Headers.of(map);
    }

    public Headers.Builder getHeaderss() {
        Headers.Builder builder = new Headers.Builder();
        builder.add("time", "");
        builder.add("", "");
        return builder;
    }


    //取消某个请求
    public void CancelCall(Object tag) {
        Dispatcher dispatcher = client.dispatcher();
        //准备执行的
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        //即将执行的
        for (Call call : dispatcher.runningCalls()) {
            if (tag.equals(call.request().tag())) {// && call.isCanceled() && call.isExecuted()
                call.cancel();
            }
        }
    }
}
