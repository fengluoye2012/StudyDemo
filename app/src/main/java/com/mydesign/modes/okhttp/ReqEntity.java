package com.mydesign.modes.okhttp;

import com.mydesign.modes.base.BaseActivity;

public class ReqEntity extends BaseReqEntity{

    public String key;
    public int pageIndex;

    public ReqEntity(String key, int pageIndex) {
        this.key = key;
        this.pageIndex = pageIndex;
    }
}
