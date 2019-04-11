package com.mydesign.modes.plug.hook;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;

import com.mydesign.modes.plug.RefInvoke;

import java.io.File;
import java.util.Map;

/**
 * 在ActivityThread的H类中，当LAUNCH_ACTIVITY时，r.packageInfo是LoadedApk类型，LoadedApk是apk在内存中的表示。
 * 可以从中获取apk的各种信息，比如apk中四大组件的信息；
 * getPackageInfo()方法根据packageName获取缓存的LoadedApk，如果获取不到，则常见一个；
 * <p>
 * <p>
 * 由于我们采用欺上瞒下的方式，在真正创建Activity时需要将class替换过来；
 * <p>
 * 在ActivityThread 类的performLaunchActivity()方法中通过下面两行代码，将activity创建出来；所以在创建之前必须要将要启动的Activity替换回来；
 * java.lang.ClassLoader cl = appContext.getClassLoader();
 * activity = mInstrumentation.newActivity(cl, component.getClassName(), r.intent);
 */
public class LoadApkClassLoaderHookerHelper {


    public static void hookLoadedApkInActivityThread(File apkFile) {
        try {
            Class<?> aClass = Class.forName("android.app.ActivityThread");
            //获取ActivityThread的sCurrentActivityThread对象
            Object sCurrentActivityThread = RefInvoke.getStaticFieldObject(aClass, "sCurrentActivityThread");
            //获取mPackages对象，是ArrayMap对象，以packageName为key,以LoadedApk为Value;
            Map mPackages = (Map) RefInvoke.getStaticFieldObject(aClass, "mPackages");


            //准备参数
            Object campatibilityInfo = RefInvoke.getStaticFieldObject("android.content.res.CompatibilityInfo", "DEFAULT_COMPATIBILITY_INFO");

            //从 apk中取得Applicationlnfo信息;
            ApplicationInfo applicationInfo = generateApplicationInfo(apkFile);


            //调用ActivityThread的getPackageInfoNoCheck()方法；
            //getPackageInfoNoCheck(ApplicationInfo ai, CompatibilityInfo compatInfo)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据PackageParser类的generateApplicationInfo(Package p, int flags,PackageUserState state)生成ApplicationInfo；
     *
     * @param apkFile
     * @return
     */
    private static ApplicationInfo generateApplicationInfo(File apkFile) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        //找出需要反射的核心类PackageParser(解析apk包)
        Class<?> packageParser = Class.forName("android.content.pm.PackageParser");
        Class<?> packageParser$Package = Class.forName("android.content.pm.PackageParser$Package");

        Object packageParserObj = packageParser.newInstance();
        //调用PackageParser的parsePackage();
        Class [] classes ={File.class,int.class};
        Object [] objects ={apkFile,0};
        Object parsePackage = RefInvoke.invokeInstanceMethod(packageParserObj, "parsePackage",classes,objects);

        Object packageParser$PackageObj = packageParser$Package.newInstance();
        


        return null;
    }
}
