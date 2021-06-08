package com.manymobi.esdsl.handler.impl.response;

import com.manymobi.esdsl.handler.Cancellable;
import com.manymobi.esdsl.handler.Request;
import com.manymobi.esdsl.handler.Response;
import com.manymobi.esdsl.handler.ResponseBodyHandler;
import com.manymobi.esdsl.handler.ResponseContextHandler;
import com.manymobi.esdsl.handler.ResponseListener;
import com.manymobi.esdsl.handler.RestHandler;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

/**
 * @author 梁建军
 * 创建日期： 2021/4/23
 * 创建时间： 下午8:15
 * @version 1.0
 * @since 1.0
 */
public class FutureResponseBodyHandler implements ResponseBodyHandler {


    @Override
    public Object handler(RestHandler restHandler, Request request, Type returnType, Type contextType, ResponseContextHandler responseContextHandler) {
        CompletableFuture<Object> completableFuture = new CompletableFuture<>();
        Cancellable cancellable = restHandler.performRequestAsync(request, new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                Object handler = responseContextHandler.handler(response.getBody(), contextType);
                completableFuture.complete(handler);
            }

            @Override
            public void onFailure(Exception exception) {
                completableFuture.completeExceptionally(exception);
            }
        });
        return completableFuture.whenComplete((o, throwable) -> {
            if (throwable instanceof CancellationException) {
                cancellable.cancel();
            }
        });
    }

    @Override
    public boolean can(Method method, Type returnType) {
        if (!(returnType instanceof ParameterizedType)) {
            return false;
        }
        Type rawType = ((ParameterizedType) returnType).getRawType();
        return Future.class == rawType
                || CompletableFuture.class == rawType
                || CompletionStage.class == rawType;
    }

    @Override
    public Type getContextType(Type returnType) {
        Type[] actualTypeArguments = ((ParameterizedType) returnType).getActualTypeArguments();
        return actualTypeArguments[0];
    }
}
