package com.mydesign.modes.design_modes.observer;

/**
 * 观察者
 */

public interface DataSetObserver {
    DataSetObserver attach(Observable observable);

    void detach(Observable observable);

    void notifyData();
}
