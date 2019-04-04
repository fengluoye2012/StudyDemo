package com.mydesign.modes.newwidgets;

import com.mydesign.modes.base.MyApplication;

/**
 * Android 新控件的实践
 */

public class NewWidgetsTest {

    private static NewWidgetsTest instance;

    private NewWidgetsTest() {
    }

    public static NewWidgetsTest getInstance() {
        if (instance == null) {
            synchronized (NewWidgetsTest.class) {
                if (instance == null) {
                    instance = new NewWidgetsTest();
                }
            }
        }
        return instance;
    }

    public void test() {
//        NewWidgetsActivity.startActivity(MyApplication.getContext());
//        PaletteTestActivity.startActivity(MyApplication.getContext());
        BottomNavigationActivity.startActivity(MyApplication.getContext());
//        CoordinatorLayoutActivity.startActivity(MyApplication.getContext());
        TouTiaoDetailActivity.startActivity(MyApplication.getContext());
//        TouchEventActivity.startActivity(MyApplication.getContext());
//        OverScrollerActivity.startActivity(MyApplication.getContext());

//        CoordinatorLayoutTestActivity.startActivity(MyApplication.getContext());
    }

}
