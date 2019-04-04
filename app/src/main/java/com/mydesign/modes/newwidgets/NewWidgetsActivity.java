package com.mydesign.modes.newwidgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mydesign.modes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ConstraintLayout 约束布局的使用
 * ToolBar的直接使用或者自定义
 * TextInputLayout
 * SearchView的使用
 */
public class NewWidgetsActivity extends AppCompatActivity {

    @BindView(R.id.commonHeadTitle)
    CommonHeadTitle commonHeadTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ConstraintLayout实践
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        View decorView = getWindow().getDecorView();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, NewWidgetsActivity.class);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}
