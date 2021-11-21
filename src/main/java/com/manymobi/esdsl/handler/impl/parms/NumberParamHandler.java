package com.manymobi.esdsl.handler.impl.parms;

import com.manymobi.esdsl.handler.JsonEncoder;
import com.manymobi.esdsl.handler.ParamHandler;

import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 下午 2:05
 * @version 1.0
 * @since 1.0
 * 处理数字类型的参数
 */
public class NumberParamHandler extends ParamHandler<Number> {

    @Deprecated
    public NumberParamHandler(String paramName, JsonEncoder jsonEncoder) {
        super(paramName, jsonEncoder);
    }

    public NumberParamHandler(String paramName) {
        super(paramName);
    }

    @Override
    public void handler(Map<String, Object> map, Number o) {
        map.put(paramName, o);
    }

    @Deprecated
    public static class Build extends ParamHandler.Build<NumberParamHandler> {

        @Override
        public boolean can(Class<?> parameterType) {
            return Number.class.isAssignableFrom(parameterType);
        }

        @Override
        public NumberParamHandler build(String paramName) {
            return new NumberParamHandler(paramName, jsonEncoder);
        }
    }
}
