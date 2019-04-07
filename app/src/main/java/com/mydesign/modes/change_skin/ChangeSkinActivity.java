package com.mydesign.modes.change_skin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mydesign.modes.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 1:搞明白APP 是如何加载res资源的；
 * 2:缓存所有打开的页面的View；
 * 解析View中的属性；
 * 3：如何让缓存的View加载新的资源文件；
 */
public class ChangeSkinActivity extends  Activity{


    @BindView(R.id.tv_change)
    TextView tvChange;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tv_next)
    TextView tvNext;
    private Resources resources;
    private String TAG = ChangeSkinActivity.class.getSimpleName();
    private Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        act = this;

       /* //LayoutInflater 是系统服务之一；可以将setFactory()放在父类中
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        layoutInflater.setFactory(new SkinFactory(act));*/

        setContentView(R.layout.activity_change_skin);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_change, R.id.tv_next})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.tv_next:
                startActivity(new Intent(act, ChangeSkinActivity.class));
                break;
            case R.id.tv_change:
               /* //所有的资源都可以通过Resources获取到；
                XmlResourceParser layout = getResources().getLayout(R.layout.activity_change_skin);
                int color = getResources().getColor(R.color.color_e83b36);
                //Drawable drawable = getResources().getDrawable(R.drawable.image_liu);*/

                SkinManager.getInstance().changeSkin();

                initRes();
                loadImage();
                break;
        }
    }


    //可以放在Application中或者MainActivity；
    private void initRes() {
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "a_test_change/app.zip";
        OutResources.getInstance().initRes(filePath);
    }

    private void loadImage() {
        imageView.setImageDrawable(OutResources.getInstance().getDrawable(act, R.drawable.image_liu));
    }
}
