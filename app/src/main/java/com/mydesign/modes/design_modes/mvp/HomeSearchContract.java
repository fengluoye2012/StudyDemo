package com.mydesign.modes.design_modes.mvp;

public class HomeSearchContract {

    public interface View extends BaseView<BasePresenter> {
        void completeResearchList();
    }


    public interface Presenter extends BasePresenter {
        void getResearchList(String key, int page, int size);
    }
}
