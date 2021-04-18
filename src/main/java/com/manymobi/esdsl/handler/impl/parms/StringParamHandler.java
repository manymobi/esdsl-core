package com.manymobi.esdsl.handler.impl.parms;

import com.manymobi.esdsl.handler.JsonHandler;
import com.manymobi.esdsl.handler.ParamHandler;

import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 下午 2:06
 * @version 1.0
 * @since 1.0
 */
public class StringParamHandler extends ParamHandler<String> {

    public StringParamHandler(String paramName, JsonHandler jsonHandler) {
        super(paramName, jsonHandler);
    }

    @Override
    public void handler(Map<String, Object> map, String o) {
        map.put(paramName, o);
    }


    public static class Build extends ParamHandler.Build<StringParamHandler> {

        @Override
        public boolean can(Class<?> parameterType) {
            return String.class.isAssignableFrom(parameterType);
        }

        @Override
        public StringParamHandler build(String paramName) {
            return new StringParamHandler(paramName, jsonHandler);
        }
    }
}
