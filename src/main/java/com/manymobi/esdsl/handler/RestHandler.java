package com.manymobi.esdsl.handler;

import com.manymobi.esdsl.annotations.RequestMethod;

import java.lang.reflect.Type;

/**
 * @author 梁建军
 * 创建日期： 2018/11/8
 * 创建时间： 18:38
 * @version 1.0
 * @since 1.0
 */
public interface RestHandler<T> extends AutoCloseable {

    T handler(RequestMethod requestMethod, String url, String json, Type returnType, JsonHandler jsonHandler);
}
