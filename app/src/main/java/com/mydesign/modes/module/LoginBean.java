package com.mydesign.modes.module;


public class LoginBean {
    public String name;
    public int age;

    //此时this代表当前调用对象
    public void say(){
        int age = this.age;
        String name = this.name;
    }
}
