package com.mydesign.modes.plug.hook;

import dalvik.system.DexClassLoader;

public class CustomClassLoader extends DexClassLoader {

    public CustomClassLoader(String path, String odexPath, String libDir, ClassLoader classLoader) {
        super(path,odexPath,libDir,classLoader);
    }
}
