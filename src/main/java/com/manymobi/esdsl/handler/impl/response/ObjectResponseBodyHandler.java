package com.manymobi.esdsl.handler.impl.response;

import com.manymobi.esdsl.handler.Request;
import com.manymobi.esdsl.handler.Response;
import com.manymobi.esdsl.handler.ResponseBodyHandler;
import com.manymobi.esdsl.handler.ResponseContextHandler;
import com.manymobi.esdsl.handler.RestHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author 梁建军
 * 创建日期： 2021/4/23
 * 创建时间： 下午5:15
 * @version 1.0
 * @since 1.0
 */
public class ObjectResponseBodyHandler implements ResponseBodyHandler {
    @Override
    public Object handler(RestHandler restHandler, Request request, Type returnType, ResponseContextHandler responseContextHandler) {
        Response response = restHandler.performRequest(request);
        return responseContextHandler.handler(response.getBody(), returnType);
    }

    @Override
    public boolean can(Method method, Type returnType) {
        return true;
    }

    @Override
    public Type getContextType(Type returnType) {
        return returnType;
    }
}
