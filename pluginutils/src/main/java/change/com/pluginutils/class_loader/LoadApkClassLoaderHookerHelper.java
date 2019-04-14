package change.com.pluginutils.class_loader;

import android.content.pm.ApplicationInfo;

import change.com.pluginutils.utils.RefInvoke;
import change.com.pluginutils.utils.PluginUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 为每个插件创建一个ClassLoaded（DexClassLoaded）;
 * <p>
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

    private static Map<String, Object> sLoadedApk = new HashMap<>();

    /**
     * 调用ActivityThread的getPackageInfoNoCheck()方法；getPackageInfoNoCheck(ApplicationInfo ai, CompatibilityInfo compatInfo)
     *
     * @param apkFile
     */
    public static void hookLoadedApkInActivityThread(File apkFile) {
        try {
            Class<?> activityThreadClass = RefInvoke.getClass("android.app.ActivityThread");
            //获取ActivityThread的sCurrentActivityThread对象
            Object sCurrentActivityThread = RefInvoke.getStaticFieldObject(activityThreadClass, "sCurrentActivityThread");
            //获取mPackages对象，是ArrayMap对象，以packageName为key,以LoadedApk为Value;
            Map mPackages = (Map) RefInvoke.getStaticFieldObject(activityThreadClass, "mPackages");

            //获取CompatibilityInfo对象
            Class compatibilityInfoClass = RefInvoke.getClass("android.content.res.CompatibilityInfo");
            Object campatibilityInfo = RefInvoke.getStaticFieldObject(compatibilityInfoClass, "DEFAULT_COMPATIBILITY_INFO");

            //从 apk中取得Applicationlnfo信息;
            ApplicationInfo applicationInfo = generateApplicationInfo(apkFile);

            if (applicationInfo == null) {
                return;
            }


            //调用getPackageInfoNoCheck()方法获取loadedApk对象；
            //RefInvoke.getClass("");
            Class[] pClass = {applicationInfo.getClass(), compatibilityInfoClass};
            Object[] objects = {applicationInfo, campatibilityInfo};
            Object loadedApk = RefInvoke.invokeStaticMethod(activityThreadClass, "getPackageInfoNoCheck", pClass, objects);

            //为每个插件创建一个ClassLoaded；
            String odexPath = PluginUtils.getPluginOptDexDir(applicationInfo.packageName).getPath();
            String libDir = PluginUtils.getPluginLibDir(applicationInfo.packageName).getPath();

            CustomClassLoader classLoader = new CustomClassLoader(apkFile.getPath(), odexPath, libDir, ClassLoader.getSystemClassLoader());

            //将LoadedApk的mClassLoader对象，换成自己创建的；
            RefInvoke.setFieldObject(loadedApk, "mClassLoader", classLoader);

            //把插件的LoadedApk对象放入缓存；
            WeakReference weakReference = new WeakReference<>(loadedApk);
            mPackages.put(applicationInfo.packageName, weakReference);
            sLoadedApk.put(applicationInfo.packageName, loadedApk);

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
        Class<?> packageParser = RefInvoke.getClass("android.content.pm.PackageParser");
        Class<?> packageClass = RefInvoke.getClass("android.content.pm.PackageParser$Package");
        Object packageParserObj = packageParser.newInstance();

        //第一个参数：调用PackageParser的parsePackage()获取Package对象;
        Class[] classes = {File.class, int.class};
        Object[] parameters = {apkFile, 0};
        Object mPackage = RefInvoke.invokeInstanceMethod(packageParserObj, "parsePackage", classes, parameters);

        //第三个参数
        Class stateClass = RefInvoke.getClass("android.content.pm.PackageUserState");
        Object state = stateClass.newInstance();

        //调用generateApplicationInfo()方法，获取applicationInfo对象；
        Class[] infoClasses = {packageClass, int.class, stateClass};
        Object[] pValue = {mPackage, 0, state};

        ApplicationInfo applicationInfo = (ApplicationInfo) RefInvoke.invokeStaticMethod(packageParser, "generateApplicationInfo", infoClasses, pValue);
        String path = apkFile.getPath();
        if (applicationInfo != null) {
            applicationInfo.sourceDir = path;
            applicationInfo.publicSourceDir = path;
        }

        return applicationInfo;
    }

}
