package com.mydesign.modes.change_skin;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import com.mydesign.modes.R;

import org.xmlpull.v1.XmlPullParser;

import static android.support.v7.widget.VectorEnabledTintResources.MAX_SDK_WHERE_REQUIRED;

//如何加载资源文件
public class ChangeSkinActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LayoutInflater 是系统服务之一；可以将setFactory()放在父类中
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        layoutInflater.setFactory(new SkinFactory());
        setContentView(R.layout.activity_change_skin);
    }

    //参考AppCompatDelegateImplV9类
    class SkinFactory implements LayoutInflater.Factory2 {
        private AppCompatViewInflaterSkin mAppCompatViewInflater;
        private final boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return onCreateView(null, name, context, attrs);
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            return createView(parent, name, context, attrs);
        }

        public View createView(View parent, final String name, @NonNull Context context, @NonNull AttributeSet attrs) {
            if (mAppCompatViewInflater == null) {
                mAppCompatViewInflater = new AppCompatViewInflaterSkin();
            }

            boolean inheritContext = false;
            if (IS_PRE_LOLLIPOP) {
                inheritContext = (attrs instanceof XmlPullParser)
                        // If we have a XmlPullParser, we can detect where we are in the layout
                        ? ((XmlPullParser) attrs).getDepth() > 1
                        // Otherwise we have to use the old heuristic
                        : shouldInheritContext((ViewParent) parent);
            }

            return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext, IS_PRE_LOLLIPOP, /* Only read android:theme pre-L (L+ handles this anyway) */
                    true, /* Read read app:theme as a fallback at all times for legacy reasons */
                    shouldBeUsed() /* Only tint wrap the context if enabled */);
        }

        public boolean shouldBeUsed() {
            return AppCompatDelegate.isCompatVectorFromResourcesEnabled() && Build.VERSION.SDK_INT <= MAX_SDK_WHERE_REQUIRED;
        }

        private boolean shouldInheritContext(ViewParent parent) {
            if (parent == null) {
                // The initial parent is null so just return false
                return false;
            }
            final View windowDecor = getWindow().getDecorView();
            while (true) {
                if (parent == null) {
                    // Bingo. We've hit a view which has a null parent before being terminated from
                    // the loop. This is (most probably) because it's the root view in an inflation
                    // call, therefore we should inherit. This works as the inflated layout is only
                    // added to the hierarchy at the end of the inflate() call.
                    return true;
                } else if (parent == windowDecor || !(parent instanceof View) || ViewCompat.isAttachedToWindow((View) parent)) {
                    // We have either hit the window's decor view, a parent which isn't a View
                    // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                    // is currently added to the view hierarchy. This means that it has not be
                    // inflated in the current inflate() call and we should not inherit the context.
                    return false;
                }
                parent = parent.getParent();
            }
        }
    }
}
