package com.manymobi.esdsl.handler.impl.parms;

import com.manymobi.esdsl.handler.ParamHandler;
import com.manymobi.esdsl.handler.ParamHandlerFactory;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 下午 2:05
 * @version 1.0
 * @since 1.0
 * 处理数字类型的参数
 */
public class NumberParamHandlerFactory implements ParamHandlerFactory<Number> {

    public boolean can(Class<?> parameterType) {
        return Number.class.isAssignableFrom(parameterType);
    }

    @Override
    public ParamHandler<Number> apply(String paramName) {
        return new NumberParamHandler(paramName);
    }
}
