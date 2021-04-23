package com.manymobi.esdsl.handler;

import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 下午 2:02
 * @version 1.0
 * @since 1.0
 * 单个方法参数处理器
 */
public abstract class ParamHandler<T> {
    /**
     * 参数名称
     */
    protected String paramName;
    /**
     * json编码器
     */
    protected JsonEncoder jsonEncoder;

    public ParamHandler(String paramName, JsonEncoder jsonEncoder) {
        this.paramName = paramName;
        this.jsonEncoder = jsonEncoder;
    }

    /**
     * @param target 目标
     * @param arg    方法的参数
     */
    public abstract void handler(Map<String, Object> target, T arg);

    public static abstract class Build<T extends ParamHandler> {

        protected JsonEncoder jsonEncoder;

        public void setJsonHandler(JsonEncoder jsonEncoder) {
            this.jsonEncoder = jsonEncoder;
        }

        public abstract boolean can(Class<?> parameterType);

        public abstract T build(String paramName);

    }

}
