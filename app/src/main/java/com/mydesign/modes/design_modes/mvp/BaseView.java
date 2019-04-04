package com.mydesign.modes.design_modes.mvp;

import android.content.Context;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showLoadingView();

    void hideLoadingView();

    void showMessage(String str);

    void alertMessage(String title,String msg,String confirm,String cancel);

    void detach();

    Context contextView();

    void showErrorView();
}
