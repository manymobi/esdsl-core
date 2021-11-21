package com.manymobi.esdsl.handler.impl.parms;

import com.manymobi.esdsl.handler.JsonEncoder;
import com.manymobi.esdsl.handler.ParamHandler;
import com.manymobi.esdsl.handler.ParamHandlerFactory;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 下午 2:04
 * @version 1.0
 * @since 1.0
 */
public class ObjectParamHandlerFactory implements ParamHandlerFactory<Object>, JsonEncoder.Set {

    private JsonEncoder jsonEncoder;

    @Override
    public boolean can(Class<?> parameterType) {
        return true;
    }

    @Override
    public ParamHandler<Object> apply(String paramName) {
        return new ObjectParamHandler(paramName, jsonEncoder);
    }

    @Override
    public void setJsonEncoder(JsonEncoder jsonEncoder) {
        this.jsonEncoder = jsonEncoder;
    }
}
