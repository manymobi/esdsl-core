package com.manymobi.esdsl.handler.impl.response;

import com.manymobi.esdsl.handler.Cancellable;
import com.manymobi.esdsl.handler.Request;
import com.manymobi.esdsl.handler.Response;
import com.manymobi.esdsl.handler.ResponseBodyHandler;
import com.manymobi.esdsl.handler.ResponseContextHandler;
import com.manymobi.esdsl.handler.ResponseListener;
import com.manymobi.esdsl.handler.RestHandler;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author 梁建军
 * 创建日期： 2021/4/23
 * 创建时间： 下午8:15
 * @version 1.0
 * @since 1.0
 */
public class MonoResponseBodyHandler implements ResponseBodyHandler {


    @Override
    public Object handler(RestHandler restHandler, Request request, Type returnType, Type contextType, ResponseContextHandler responseContextHandler) {
        return Mono.from(s -> s.onSubscribe(new Subscription() {
            Cancellable cancellable;
            @Override
            public void request(long n) {
                cancellable = restHandler.performRequestAsync(request, new ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        Object handler = responseContextHandler.handler(response.getBody(), contextType);
                        s.onNext(handler);
                        s.onComplete();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        s.onError(exception);
                        s.onComplete();
                    }
                });
            }

            @Override
            public void cancel() {
                if (cancellable != null) {
                    cancellable.cancel();
                }
            }
        }));
    }

    @Override
    public boolean can(Method method, Type returnType) {
        if (!(returnType instanceof ParameterizedType)) {
            return false;
        }
        Type rawType = ((ParameterizedType) returnType).getRawType();
        return Mono.class == rawType;
    }

    @Override
    public Type getContextType(Type returnType) {
        Type[] actualTypeArguments = ((ParameterizedType) returnType).getActualTypeArguments();
        return actualTypeArguments[0];
    }
}
