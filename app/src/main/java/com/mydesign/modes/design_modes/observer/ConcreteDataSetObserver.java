package com.mydesign.modes.design_modes.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体的观察者
 */

public class ConcreteDataSetObserver implements DataSetObserver {

    private List<Observable> observableList = new ArrayList<>();

    private static ConcreteDataSetObserver instance;

    public static ConcreteDataSetObserver getInstance() {
        if (instance == null) {
            synchronized (ConcreteDataSetObserver.class) {
                if (instance == null) {
                    instance = new ConcreteDataSetObserver();
                }
            }
        }
        return instance;
    }

    @Override
    public DataSetObserver attach(Observable observable) {

        checkNull(observable);
        if (observableList.contains(observable)) {
            throw new IllegalArgumentException("当前observable 只能注册一次");
        }
        observableList.add(observable);

        return this;
    }

    @Override
    public void detach(Observable observable) {
        checkNull(observable);
        if (observableList.contains(observable)) {
            observableList.remove(observable);
        }
    }

    @Override
    public void notifyData() {
        for (int i = observableList.size() - 1; i >= 0; i--) {
            observableList.get(i).update();
        }
    }

    private void checkNull(Observable observable) {
        if (observable == null) {
            throw new NullPointerException("observable 不能为null");
        }
    }
}
