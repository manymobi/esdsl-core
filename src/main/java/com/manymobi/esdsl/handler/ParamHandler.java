package com.manymobi.esdsl.handler;

import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 下午 2:02
 * @version 1.0
 * @since 1.0
 */
public abstract class ParamHandler<T> {

    protected String paramName;

    protected JsonHandler jsonHandler;

    public ParamHandler(String paramName, JsonHandler jsonHandler) {
        this.paramName = paramName;
        this.jsonHandler = jsonHandler;
    }

    public abstract void handler(Map<String, Object> map, T o);

    public static abstract class Build<T extends ParamHandler> {

        protected JsonHandler jsonHandler;

        public void setJsonHandler(JsonHandler jsonHandler) {
            this.jsonHandler = jsonHandler;
        }

        public abstract boolean can(Class<?> parameterType);


        public abstract T build(String paramName);

    }

}