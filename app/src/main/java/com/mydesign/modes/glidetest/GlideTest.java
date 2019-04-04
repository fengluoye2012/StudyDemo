package com.mydesign.modes.glidetest;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.mydesign.modes.R;
import com.mydesign.modes.base.MyApplication;


public class GlideTest {

    private static RequestOptions options;

    public static void LoadImageView(Context act, String url, ImageView imageView) {

        if (options == null) {
            options = new RequestOptions().error(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background);
        }

        Glide.with(act).load(url).apply(options).into(imageView);
    }

    public static void loadImageView(String url, SimpleDraweeView view, int defaultResource) {
        Uri uri = Uri.parse(url);
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(MyApplication.getContext().getResources())
                .setFadeDuration(100)
                .setPlaceholderImage(defaultResource)//默认图
                .setFailureImage(defaultResource)//加载失败
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setRetainImageOnFailure(true)
                .setTapToRetryEnabled(true)
                .setOldController(view.getController())
                .build();

        view.setHierarchy(hierarchy);
        view.setController(controller);
    }
}
