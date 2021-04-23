package com.manymobi.esdsl.handler.impl.response;

import com.manymobi.esdsl.handler.JsonEncoder;
import com.manymobi.esdsl.handler.ResponseContextHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author 梁建军
 * 创建日期： 2021/4/23
 * 创建时间： 下午5:43
 * @version 1.0
 * @since 1.0
 */
public class ObjectResponseContextHandler implements ResponseContextHandler {

    private JsonEncoder jsonEncoder;

    @Override
    public Object handler(String body, Type returnType) {
        return jsonEncoder.parseObject(body, returnType);
    }

    @Override
    public boolean can(Method method, Type returnType) {
        return true;
    }

    @Override
    public void setJsonEncoder(JsonEncoder jsonEncoder) {
        this.jsonEncoder = jsonEncoder;
    }
}
