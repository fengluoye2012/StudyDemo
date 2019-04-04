package com.mydesign.modes.newwidgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mydesign.modes.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TabLayout
 * Palette 优化界面色彩搭配
 */
public class PaletteTestActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette_test);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        String[] titles = {"我的", "你的", "他的"};
        final String[] urls = {""
                , "http://g.hiphotos.baidu.com/image/pic/item/730e0cf3d7ca7bcb944f655cb3096b63f624a889.jpg",
                "http://e.hiphotos.baidu.com/image/pic/item/241f95cad1c8a786f5d00e7a6a09c93d71cf50cf.jpg"};
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(Arrays.asList(urls), Arrays.asList(titles));
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(viewPageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tabLayout.getSelectedTabPosition();
                loadImageSimpleTarget(urls[pos]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void loadImageSimpleTarget(String url) {
        Glide.with(PaletteTestActivity.this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                paletteTest(resource);
            }
        });
    }

    private void paletteTest(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

                /*// 获取到柔和的深色的颜色（可传默认值）
                palette.getDarkMutedColor(Color.BLUE);
                // 获取到活跃的深色的颜色（可传默认值）
                palette.getDarkVibrantColor(Color.BLUE);
                // 获取到柔和的明亮的颜色（可传默认值）
                palette.getLightMutedColor(Color.BLUE);
                // 获取到活跃的明亮的颜色（可传默认值）
                palette.getLightVibrantColor(Color.BLUE);
                // 获取图片中最活跃的颜色（也可以说整个图片出现最多的颜色）（可传默认值）
                palette.getVibrantColor(Color.BLUE);
                // 获取图片中一个最柔和的颜色（可传默认值）
                palette.getMutedColor(Color.BLUE);*/

                Palette.Swatch vibrant = palette.getLightMutedSwatch();
                if (vibrant == null) {
                    for (Palette.Swatch swatch : palette.getSwatches()) {
                        vibrant = swatch;
                        break;
                    }
                }

                if (vibrant == null) {
                    return;
                }

                // 这样获取的颜色可以进行改变。
                int rbg = vibrant.getRgb();

                // ... 省略一些无关紧要的代码

                tabLayout.setBackgroundColor(rbg);
                //toolbar.setBackgroundColor(rbg);
                if (Build.VERSION.SDK_INT > 21) {
                    Window window = getWindow();
                    //状态栏改变颜色。
                    int color = changeColor(rbg);
                    window.setStatusBarColor(color);
                }
            }
        });
    }

    // 对获取到的RGB颜色进行修改。（涉及到位运算，我也不是很懂这块）
    private int changeColor(int rgb) {
        int red = rgb >> 16 & 0xFF;
        int green = rgb >> 8 & 0xFF;
        int blue = rgb & 0xFF;
        red = (int) Math.floor(red * (1 - 0.2));
        green = (int) Math.floor(green * (1 - 0.2));
        blue = (int) Math.floor(blue * (1 - 0.2));
        return Color.rgb(red, green, blue);
    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PaletteTestActivity.class);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}
