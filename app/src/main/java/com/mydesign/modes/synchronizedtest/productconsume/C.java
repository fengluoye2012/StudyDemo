package com.mydesign.modes.synchronizedtest.productconsume;


public class C {
    private MyStack myStack;

    public C(MyStack myStack) {
        this.myStack = myStack;
    }

    public void remove() {
        myStack.remove();
    }
}
