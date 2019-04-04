package com.mydesign.modes.utils;

import android.content.res.Resources;
import android.util.TypedValue;

import com.mydesign.modes.base.MyApplication;


public class CommonUtils {

    public static int dipToPixels(int dip) {
        Resources r = MyApplication.getContext().getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
        return (int) px;
    }
}
