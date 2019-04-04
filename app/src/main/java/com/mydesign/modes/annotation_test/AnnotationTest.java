package com.mydesign.modes.annotation_test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * <p>
 * 元标签有 @Retention:存活时间;
 * 、@Documented：将注解中的元素包含到 Javadoc 中去
 * 、@Target: 指定了注解运用的地方
 * 、@Inherited:超类被 @Inherited 注解过的注解进行注解的话，那么如果它的子类没有被任何注解应用的话，那么这个子类就继承了超类的注解。
 * 、@Repeatable:Java 1.8 才加进来的,注解的值可以同时取多个
 * 5 种。
 * 一般情况下，@Retention和@Target最常用；
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationTest {
    int id() default 0;
    String name() default "";
}
