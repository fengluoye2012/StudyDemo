package com.mydesign.modes.design_modes.build;

//链式调用
public class Chained {

    private String name;
    private int age;
    private int sex;

    public String getName() {
        return name;
    }

    public Chained setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Chained setAge(int age) {
        this.age = age;
        return this;
    }

    public int getSex() {
        return sex;
    }

    public Chained setSex(int sex) {
        this.sex = sex;
        return this;
    }
}
