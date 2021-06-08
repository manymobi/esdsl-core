package com.manymobi.esdsl.handler.impl.response;

import com.manymobi.esdsl.handler.Request;
import com.manymobi.esdsl.handler.Response;
import com.manymobi.esdsl.handler.ResponseBodyHandler;
import com.manymobi.esdsl.handler.ResponseContextHandler;
import com.manymobi.esdsl.handler.RestHandler;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * @author 梁建军
 * 创建日期： 2021/4/23
 * 创建时间： 下午5:15
 * @version 1.0
 * @since 1.0
 */
public class OptionalResponseBodyHandler implements ResponseBodyHandler {

    @Override
    public Object handler(RestHandler restHandler, Request request, Type returnType, Type contextType, ResponseContextHandler responseContextHandler) {
        Response response = restHandler.performRequest(request);
        return Optional.ofNullable(responseContextHandler.handler(response.getBody(), contextType));
    }



    @Override
    public boolean can(Method method, Type returnType) {
        if (!(returnType instanceof ParameterizedType)) {
            return false;
        }
        Type rawType = ((ParameterizedType) returnType).getRawType();
        return Optional.class == rawType;
    }

    @Override
    public Type getContextType(Type returnType) {
        Type[] actualTypeArguments = ((ParameterizedType) returnType).getActualTypeArguments();
        return actualTypeArguments[0];
    }
}
