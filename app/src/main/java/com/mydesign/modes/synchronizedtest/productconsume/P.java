package com.mydesign.modes.synchronizedtest.productconsume;

public class P {
    private MyStack myStack;

    public P(MyStack myStack) {
        this.myStack = myStack;
    }

    public void add() {
        myStack.add();
    }

}
