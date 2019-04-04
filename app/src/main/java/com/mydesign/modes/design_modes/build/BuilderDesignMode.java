package com.mydesign.modes.design_modes.build;

/**
 * 建造者模式：
 * 定义：讲一个复杂对象的构建与他的表示分类，使得同样的构建过程可以创建不同的表示。
 * 使用场景：
 * 1）相同的方法，不同的执行顺序，产生不同的事件结果时；
 * 2）多个部件或零件，都可以装配到一个对象中，但是产生的运行结果又不相同时；
 * 3）产品类非常复杂，或者产品类中的调用顺序不同产生了不同的作用，这个时候使用建造这模式非常适合；
 * 4）当初始化一个对象特别复杂，如参数多，且很多参数都具有默认值时；
 */

//当前适用于场景四：参数多的情况；
public class BuilderDesignMode {

    private String name;
    private int age;
    private int sex;

    //私有化构造函数
    private BuilderDesignMode(Builder builder) {
        name = builder.name;
        age = builder.age;
        sex = builder.sex;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "BuilderDesignMode{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }

    /**
     * 静态内部类；
     * this：代表当前类
     */
    public static class Builder {

        private String name;
        private int age;
        private int sex;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setSex(int sex) {
            this.sex = sex;
            return this;
        }

        public BuilderDesignMode create() {
            return new BuilderDesignMode(this);
        }
    }
}
