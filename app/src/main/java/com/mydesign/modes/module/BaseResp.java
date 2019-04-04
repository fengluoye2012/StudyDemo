package com.mydesign.modes.module;

/**
 * 抽取 data 类型，在初始化时，根绝具体情况传入；
 *
 * @param <E>
 */
public class BaseResp<E> {
    public int code;
    public String message;
    public E data;
}
