package com.manymobi.esdsl.handler.impl;

import com.manymobi.esdsl.handler.Cancellable;
import com.manymobi.esdsl.handler.Request;
import com.manymobi.esdsl.handler.Response;
import com.manymobi.esdsl.handler.ResponseListener;
import com.manymobi.esdsl.handler.RestHandler;
import org.slf4j.Logger;

/**
 * @author 梁建军
 * 创建日期： 2021/4/23
 * 创建时间： 下午4:42
 * @version 1.0
 * @since 1.0
 * 用于打印日志的
 */
public class LogRestHandler implements RestHandler {

    private final RestHandler restHandler;
    private final Logger logger;

    public LogRestHandler(RestHandler restHandler, Logger logger) {

        this.restHandler = restHandler;
        this.logger = logger;
    }

    @Override
    public Response performRequest(Request request) {

        log(request);
        Response response = restHandler.performRequest(request);
        log(response);
        return response;
    }

    @Override
    public Cancellable performRequestAsync(Request request, ResponseListener responseListener) {
        log(request);
        return restHandler.performRequestAsync(request, new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                log(response);
                responseListener.onSuccess(response);
            }

            @Override
            public void onFailure(Exception exception) {
                responseListener.onFailure(exception);
            }
        });
    }

    private void log(Request request) {
        if (logger.isDebugEnabled()) {
            logger.debug("==> {} {}", request.getRequestMethod(), request.getUrl());
            logger.debug("==> {}", request.getJson());
        }
    }

    private void log(Response response) {
        if (logger.isDebugEnabled()) {
            logger.debug("<== {}", response.getBody());
        }
    }

    @Override
    public void close() throws Exception {
        restHandler.close();
    }
}
