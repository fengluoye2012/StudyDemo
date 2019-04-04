package com.mydesign.modes.design_modes.observer;

import com.orhanobut.logger.Logger;

/**
 * 具体的被观察者
 */
public class ConcreateObservable {

    private DataSetObserver observer = ConcreteDataSetObserver.getInstance().attach(new Observable() {
        @Override
        public void update() {
            Logger.e("更新内容");
        }
    });

    public ConcreateObservable() {
        super();

        ConcreteDataSetObserver.getInstance().notifyData();

    }

}
