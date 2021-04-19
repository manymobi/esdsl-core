package com.manymobi.esdsl.handler;

import com.manymobi.esdsl.parser.ParamMap;

/**
 * @author 梁建军
 * 创建日期： 2018/11/8
 * 创建时间： 17:52
 * @version 1.0
 * @since 1.0
 * 方法参数处理
 */
public interface ParamsHandler {
    /**
     *
     * @param argv 方法参数
     * @param jsonEncoder
     * @return
     */
    ParamMap<String, Object> handle(Object[] argv, JsonEncoder jsonEncoder);

}
