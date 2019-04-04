package com.mydesign.modes.okhttp;


public class OKHttpTest {


    public static void get() {
        OkHttpClientUtils.getInstance().req("http://www.wanandroid.com/article/list/0/json?size =1", false, new ReqEntity("Android", 10));
       // OkHttpClientUtils.getInstance().req("", true, new NullEntity());
    }
}
