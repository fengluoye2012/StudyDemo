package com.mydesign.modes.design_modes.proxy.static_proxy;


import com.orhanobut.logger.Logger;

/**
 * 律师---代理类
 * 作用：在不对原有的代码做改动的情况下，进行拓展；类似于AOP（面向切面编程，用于埋点）;
 */
public class LawyerProxy implements Lawsuit {

    private Accuser accuser;

    public LawyerProxy(Accuser accuser) {
        super();
        this.accuser = accuser;
    }

    @Override
    public void lawsuit() {
        Logger.e("律师准备资料，帮忙向法院提起诉讼");
        accuser.lawsuit();
        Logger.e("诉讼赢了，钱要回来了，给律师支付500万辛苦费");
    }
}
