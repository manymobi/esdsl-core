package com.manymobi.esdsl.handler;

import com.manymobi.esdsl.parser.ParamMap;

import java.lang.annotation.Annotation;

/**
 * @author 梁建军
 * 创建日期： 2018/11/8
 * 创建时间： 17:52
 * @version 1.0
 * @since 1.0
 * 参数处理
 */
public interface ParamsHandler {

    ParamMap<String, Object> handle(Object[] argv, JsonHandler jsonHandler);

}
