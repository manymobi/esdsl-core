package com.manymobi.esdsl.handler;

import com.manymobi.esdsl.annotations.RequestMethod;

import java.lang.reflect.Type;

/**
 * @author 梁建军
 * 创建日期： 2018/11/8
 * 创建时间： 18:38
 * @version 1.0
 * @since 1.0
 * 客户端处理器，不同客户端不同实现
 */
public interface RestHandler<T> extends AutoCloseable {
    /**
     * 处理
     *
     * @param requestMethod 请求方式
     * @param url           url
     * @param json          json
     * @param returnType    方法返回类型
     * @param jsonEncoder   json编码器
     * @return 方法返回值
     */
    T handler(RequestMethod requestMethod, String url, String json, Type returnType, JsonEncoder jsonEncoder);
}
