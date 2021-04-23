package com.manymobi.esdsl.handler;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author 梁建军
 * 创建日期： 2021/4/20
 * 创建时间： 下午12:21
 * @version 1.0
 * @since 1.0
 * 响应
 */
public interface ResponseContextHandler extends JsonEncoder.Set {

    Object handler(String body, Type returnType);

    /**
     * 判断是否是当前处理器处理
     *
     * @param method     方法
     * @param returnType 返回类型
     * @return 当前处理器处理
     */
    boolean can(Method method, Type returnType);

    @Override
    default void setJsonEncoder(JsonEncoder jsonEncoder) {

    }
}
