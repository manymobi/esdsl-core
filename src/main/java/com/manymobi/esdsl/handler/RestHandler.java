package com.manymobi.esdsl.handler;

import java.io.IOException;

/**
 * @author 梁建军
 * 创建日期： 2018/11/8
 * 创建时间： 18:38
 * @version 1.0
 * @since 1.0
 * 客户端处理器，不同客户端不同实现
 */
public interface RestHandler extends AutoCloseable {
    /**
     * 执行请求
     *
     * @param request 请求
     * @return 响应
     * @throws IOException io异常
     */
    Response performRequest(Request request) throws IOException;


    /**
     * 执行异步请求
     *
     * @param request          请求
     * @param responseListener 相应回调
     * @return 可取消
     */
    Cancellable performRequestAsync(Request request, ResponseListener responseListener);
}
