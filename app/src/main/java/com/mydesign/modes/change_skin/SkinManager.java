package com.mydesign.modes.change_skin;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SkinManager {

    private static SkinManager instance;
    private ArrayMap<Context, List<View>> arrayMap = new ArrayMap<>();

    //最好放在SP中；
    private boolean hasChangeSkin = false;

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager();
                }
            }
        }
        return instance;
    }


    public ArrayMap<Context, List<View>> getArrayMap() {
        return arrayMap;
    }

    /**
     *
     *
     * @param context
     * @return
     */
    public List<View> get(Context context) {
        List<View> views = arrayMap.get(context);
        if (views == null) {
            views = new ArrayList<>();
            arrayMap.put(context, views);
        }
        return views;
    }

    public void changeSkin() {
        if (hasChangeSkin) {
            return;
        }
        //hasChangeSkin = true;
        Collection<List<View>> values = arrayMap.values();
        for (List<View> viewList : values) {
            for (View view : viewList) {
                if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;

                }
                view.requestLayout();
            }
        }
    }
}
