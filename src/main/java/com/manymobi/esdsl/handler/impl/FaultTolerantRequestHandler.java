package com.manymobi.esdsl.handler.impl;

import com.manymobi.esdsl.handler.Request;
import com.manymobi.esdsl.handler.RequestHandler;
import com.manymobi.esdsl.util.JsonUtil;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 下午 3:32
 * @version 1.0
 * @since 1.0
 * 处理多余的 ","
 */
public class FaultTolerantRequestHandler implements RequestHandler {

    @Override
    public Request handler(Request request) {
        return request.newBuild()
                .setJson(JsonUtil.faultTolerant(request.getJson()))
                .build();
    }
}
