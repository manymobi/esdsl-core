package com.manymobi.esdsl.handler.impl;

import com.manymobi.esdsl.handler.RequestJsonHandler;
import com.manymobi.esdsl.util.JsonUtil;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 下午 3:32
 * @version 1.0
 * @since 1.0
 */
public class RequestJsonHandlerImpl implements RequestJsonHandler {

    @Override
    public String handler(String json) {
        return JsonUtil.faultTolerant(json);
    }
}
