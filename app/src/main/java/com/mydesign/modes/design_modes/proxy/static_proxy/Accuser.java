package com.mydesign.modes.design_modes.proxy.static_proxy;

import com.orhanobut.logger.Logger;

/**
 * 原告---- 真实类
 */
public class Accuser implements Lawsuit {

    @Override
    public void lawsuit() {
        Logger.e("向法院提起诉讼，告XXX欠一个亿不还");
    }
}
