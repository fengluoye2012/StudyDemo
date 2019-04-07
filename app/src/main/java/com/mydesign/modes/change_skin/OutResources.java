package com.mydesign.modes.change_skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;

import com.mydesign.modes.R;
import com.mydesign.modes.base.MyApplication;


public class OutResources {

    private String TAG = OutResources.class.getSimpleName();
    private Resources resources;
    private static OutResources outResources;
    private String mOutPkgName;
    private String mFilePath;

    private OutResources() {
    }

    public static OutResources getInstance() {
        if (outResources == null) {
            synchronized (OutResources.class) {
                if (outResources == null) {
                    outResources = new OutResources();
                }
            }
        }
        return outResources;
    }

    /**
     * 创建Resources对象；
     * <p>
     * Resources对外公开的构造方法
     */
    void initResources(String filePath) {
        this.mFilePath = filePath;
        try {
            if (resources == null) {
                //通过反射的方式获取
                AssetManager assetManager = createAssetManager();
                if (assetManager == null) {
                    return;
                }

                Resources superRes = MyApplication.getContext().getResources();
                resources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
            }
            Log.e(TAG, "加载外部资源文件");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //第二种，按照系统的方式创建Resources对象
    void initRes(String filePath) {
        this.mFilePath = filePath;
        if (resources == null) {
            //创建Resources对象；
            resources = (Resources) RefInvoke.createObject(Resources.class);

            AssetManager assetManager = createAssetManager();
            if (assetManager == null) {
                return;
            }
            Resources superRes = MyApplication.getContext().getResources();
            DisplayMetrics displayMetrics = superRes.getDisplayMetrics();
            Configuration configuration = superRes.getConfiguration();

            //获取DisplayAdjustments的class对象和实例；
            Class disCls = null;
            try {
                disCls = Class.forName("android.view.DisplayAdjustments");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Object dis = RefInvoke.createObject(disCls);

            String className = "android.content.res.ResourcesImpl";
            Class[] pareTyples = {AssetManager.class, DisplayMetrics.class, Configuration.class, disCls};
            Object[] values = {assetManager, displayMetrics, configuration, dis};
            //创建ResourcesImpl实例；
            Object resourcesImpl = RefInvoke.createObject(className, pareTyples, values);

            Class resourcesImplClass = null;
            try {
                resourcesImplClass = Class.forName(className);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //调用Resources的setImpl()方法；
            RefInvoke.invokeInstanceMethod(resources, "setImpl", resourcesImplClass, resourcesImpl);
        }
        Log.e(TAG, "加载外部资源文件");
    }

    /**
     * 1）相同的资源名称，在不同的APK中，编译后对应的ID会不同；
     *
     * @param resId
     * @return
     */
    public Drawable getDrawable(Context context, int resId) {//获取图片
        //Application mContext = context.getApplication();

        if (resources == null) {
            return ContextCompat.getDrawable(context, resId);
        }
        //包名+资源名
        String resName = context.getResources().getResourceName(resId);
        //资源名
        String name = context.getResources().getResourceEntryName(resId);
        //包名
        String packageName = context.getResources().getResourcePackageName(resId);
        //对应资源的类型；
        String typeName = context.getResources().getResourceTypeName(resId);

        Log.e(TAG, "resName::" + resName);

        //需要获取的资源的包名；如果不存在对应的ID，获取不到包名；
        //String mOutPkgName = resources.getResourcePackageName(resId);

        //通过资源名称，类型获取对应的资源ID；
        int outResId = resources.getIdentifier(name, typeName, mOutPkgName);
        //outResId 没有这样的资源被发现；
        if (outResId == 0) {
            return ContextCompat.getDrawable(context, resId);
        }
        Log.e(TAG, "resId;;" + R.drawable.image_liu + ",,,outResId::" + outResId);
        return resources.getDrawable(outResId);
    }

    public int getColor(Context context, @ColorRes int id) {
        if (resources == null) {
            return ContextCompat.getColor(context, id);
        }

        Resources superRes = context.getResources();
        String packageName = superRes.getResourcePackageName(id);
        String name = superRes.getResourceEntryName(id);
        String typeName = superRes.getResourceTypeName(id);

        int outResId = resources.getIdentifier(name, typeName, mOutPkgName);
        if (outResId == 0) {
            return ContextCompat.getColor(context, id);
        }
        return resources.getColor(outResId);
    }

    private AssetManager createAssetManager() {
        try {
            Class<AssetManager> assetManagerClass = AssetManager.class;
            //AssetManager assetManager = (AssetManager) RefInvoke.createObject(clazz);
            //调用AssetManager的getSystem()方法；
            AssetManager assetManager = (AssetManager) RefInvoke.invokeStaticMethod(assetManagerClass, "getSystem");

            Log.e(TAG, "filePath::" + mFilePath);
            getPackageName(mFilePath);

            //调用addAssetPath()加载第三方资源；
            int addAssetPath = (int) RefInvoke.invokeInstanceMethod(assetManager, "addAssetPath", String.class, mFilePath);
            Log.e(TAG, "addAssetPath::" + addAssetPath);

            if (addAssetPath == 0) {
                Log.e(TAG, "加载资源失败");
            }
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getPackageName(String filePath) {
        //取得PackageManager引用
        PackageManager mPm = MyApplication.getContext().getPackageManager();
        //“检索在包归档文件中定义的应用程序包的总体信息”，说人话，外界传入了一个apk的文件路径，这个方法，拿到这个apk的包信息,这个包信息包含什么？
        PackageInfo mInfo = mPm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
        //先把包名存起来
        mOutPkgName = mInfo.packageName;
    }
}
