package com.manymobi.esdsl.handler;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author 梁建军
 * 创建日期： 2021/4/20
 * 创建时间： 下午12:21
 * @version 1.0
 * @since 1.0
 * 响应最外层处理，用于直接返回特殊的类型
 * 例如： {@link reactor.core.publisher.Mono}
 * {@link reactor.core.publisher.Flux}
 */
public interface ResponseBodyHandler extends JsonEncoder.Set {

    Object handler(RestHandler restHandler, Request request, Type returnType, ResponseContextHandler responseContextHandler);

    /**
     * 判断是否是当前处理器处理
     *
     * @param method     方法
     * @param returnType 返回类型
     * @return 当前处理器处理
     */
    boolean can(Method method, Type returnType);

    /**
     * 获取内容 类型， 这个会传给 @{@link ResponseContextHandler}
     *
     * @param returnType Mapper 方法返回变量类型
     * @return 内容类型
     */
    Type getContextType(Type returnType);

    @Override
    default void setJsonEncoder(JsonEncoder jsonEncoder) {

    }
}
