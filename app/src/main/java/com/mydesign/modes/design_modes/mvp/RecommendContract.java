package com.mydesign.modes.design_modes.mvp;

import android.content.Context;

public class RecommendContract {

    public interface View extends BaseView<BasePresenter> {
        //搜索
        void completeRecommend();
    }


    public interface Presenter extends BasePresenter {

        /**
         * 加载推荐列表
         */
        void loadRecommeds(boolean refresh, int recommendType, String merchandiseIdList, String orderId);

        void loadSearchRecommend(int recommendType, String merchandiseIdList, int pagerIndex);
    }

}