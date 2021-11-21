package com.manymobi.esdsl.handler.impl.parms;

import com.manymobi.esdsl.handler.ParamHandler;
import com.manymobi.esdsl.handler.ParamHandlerFactory;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 下午 2:06
 * @version 1.0
 * @since 1.0
 */
public class StringParamHandlerFactory implements ParamHandlerFactory<String> {

    @Override
    public boolean can(Class<?> parameterType) {
        return String.class.isAssignableFrom(parameterType);
    }

    @Override
    public ParamHandler<String> apply(String paramName) {
        return new StringParamHandler(paramName);
    }

}
