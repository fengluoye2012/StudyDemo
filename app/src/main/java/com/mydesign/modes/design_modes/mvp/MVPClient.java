package com.mydesign.modes.design_modes.mvp;

import android.content.Context;


public class MVPClient implements HomeSearchContract.View, RecommendContract.View{

    Context context;

    public MVPClient(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        if (presenter instanceof HomeSearchContract.Presenter) {

        } else if (presenter instanceof RecommendContract.Presenter) {

        }
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void showMessage(String str) {

    }

    @Override
    public void alertMessage(String title, String msg, String confirm, String cancel) {

    }

    @Override
    public void detach() {

    }

    @Override
    public Context contextView() {
        return context;
    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void completeResearchList() {

    }

    @Override
    public void completeRecommend() {

    }
}
