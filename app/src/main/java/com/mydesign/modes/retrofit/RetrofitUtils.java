package com.mydesign.modes.retrofit;


import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.mydesign.modes.okhttp.OkHttpClientUtils;
import com.orhanobut.logger.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtils {

    private final String TAG = RetrofitUtils.class.getSimpleName();

    private static RetrofitUtils instance;

    private String baseUrl = "http://www.wanandroid.com/";
    private final Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
            //.addConverterFactory(GsonConverterFactory.create())//json 转换器 返回json
            .addConverterFactory(ScalarsConverterFactory.create())//返回文本
            //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//同步
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())//异步
            //.callFactory()
            //.client()
            //.callbackExecutor()
            .build();


    private RetrofitUtils() {
    }

    public static RetrofitUtils getInstance() {
        if (instance == null) {
            synchronized (OkHttpClientUtils.class) {
                if (instance == null) {
                    instance = new RetrofitUtils();
                }
            }
        }
        return instance;
    }


    public void getJson() {
        //创建一个代理对象；
        GitHubService service = retrofit.create(GitHubService.class);
        //调用
        Call<Repo> call = service.listRepos();
        call.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                Repo body = response.body();
                Logger.e(body.toString());
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
            }
        });
    }

    public void getString() {
        GitHubService service = retrofit.create(GitHubService.class);
        Call<String> call = service.getString();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();
                Logger.e(body);

                Gson gson = new Gson();
                Repo repo = gson.fromJson(body, Repo.class);
                Logger.e(repo.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Logger.e("失败了:::" + Log.getStackTraceString(t));
            }
        });
    }

    public void getRxJava2() {
        GitHubService service = retrofit.create(GitHubService.class);
        Observable<String> observable = service.getRxjava2String();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                //销毁之后，后续的方法就不在执行；
                //d.dispose();
            }

            @Override
            public void onNext(String s) {
                Logger.e(s);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("发生异常：：" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Logger.e("完成");
            }
        });
    }

    public void getJavaString() {
        GitHubService service = retrofit.create(GitHubService.class);
        CompletableFuture<String> completableFuture = service.getJavaString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            completableFuture.applyToEither(new CompletableFuture<String>() {
                @Override
                public String get() throws InterruptedException, ExecutionException {
                    return super.get();
                }
            }, new Function<String, Object>() {
                @Override
                public Object apply(String s) {
                    return null;
                }
            });
        }
    }
}
