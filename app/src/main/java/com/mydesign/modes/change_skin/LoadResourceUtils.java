package com.mydesign.modes.change_skin;

import android.os.Environment;
import android.util.Log;

import java.io.File;


public class LoadResourceUtils {

    private static String TAG = LoadResourceUtils.class.getSimpleName();

    public static void loadResource(String filePath) {

        //SD 卡未加载
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(TAG,"SD卡不存在");
            return;
        }



    }
}
