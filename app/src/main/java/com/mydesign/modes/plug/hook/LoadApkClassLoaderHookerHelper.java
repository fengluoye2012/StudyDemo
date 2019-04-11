package com.mydesign.modes.plug.hook;

/**
 * 在ActivityThread的H类中，当LAUNCH_ACTIVITY时，r.packageInfo是LoadedApk类型，LoadedApk是apk在内存中的表示。
 * 可以从中获取apk的各种信息，比如apk中四大组件的信息；
 *
 * 由于我们采用欺上瞒下的方式，在真正创建Activity时需要将class替换过来；
 *
 */
public class LoadApkClassLoaderHookerHelper {

    public static void hookLoadedApkInActivityThread() {


    }
}
