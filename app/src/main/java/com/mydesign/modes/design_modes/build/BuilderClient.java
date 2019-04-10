package com.mydesign.modes.design_modes.build;

import android.util.Log;

import com.mydesign.modes.module.BaseResp;
import com.mydesign.modes.module.LoginBean;

/**
 * 建造者模式：
 * 定义：将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
 * 使用场景：
 * 1）相同的方法，不同的执行顺序，产生不同的事件结果时；
 * 2）多个部件或零件，都可以装配到一个对象中，但是产生的运行结果又不相同时；
 * 3）产品类非常复杂，或者产品类中的调用顺序不同产生了不同的作用，这个时候使用建造这模式非常适合；
 * 4）当初始化一个对象特别复杂，如参数多，且很多参数都具有默认值时；
 */
public class BuilderClient {
    String TAG = BuilderClient.class.getSimpleName();

    private static BuilderClient instance;

    public static BuilderClient getInstance() {
        if (instance == null) {
            instance = new BuilderClient();
        }
        return instance;
    }

    public void builderTest() {

        Chained builderDesignMode = new Chained();
        //链式调用
        builderDesignMode.setName("fengluoye").setAge(18).setSex(1);

        //建造者模式
        BuilderDesignMode.Builder builder = new BuilderDesignMode.Builder();
        BuilderDesignMode mode = builder.setName("风落叶").setSex(1).setAge(18).create();
        BuilderDesignMode mode1 = builder.setName("风落").setSex(0).setAge(19).create();
        Log.e(TAG, mode.toString());
        Log.e(TAG, mode1.toString());


        BaseResp<LoginBean> bean = new BaseResp<>();
        int code = bean.code;
        String message = bean.message;
        LoginBean data = bean.data;
    }
}
