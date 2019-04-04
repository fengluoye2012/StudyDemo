package com.mydesign.modes.design_modes.proxy.d_proxy;


import com.orhanobut.logger.Logger;

public class RealPurchase implements OverSeasPurchase {

    @Override
    public void purchase() {
        Logger.e("购买韩国面膜。。。");
    }

    @Override
    public void pay() {
        Logger.e("收到产品，非常棒 给代购公司多支付20元辛苦费，表示感谢！！！");
    }
}
