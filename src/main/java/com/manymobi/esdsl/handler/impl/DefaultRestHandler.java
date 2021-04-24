package com.manymobi.esdsl.handler.impl;

import com.manymobi.esdsl.handler.Cancellable;
import com.manymobi.esdsl.handler.Request;
import com.manymobi.esdsl.handler.Response;
import com.manymobi.esdsl.handler.RestHandler;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.ResponseException;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * @author 梁建军
 * 创建日期： 2021/4/19
 * 创建时间： 下午8:53
 * @version 1.0
 * @since 1.0
 */
public class DefaultRestHandler implements RestHandler {

    private final RestClient restClient;

    public DefaultRestHandler(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void close() throws Exception {
        restClient.close();
    }

    @Override
    public Response performRequest(Request request) {
        try {
            return to(restClient.performRequest(to(request)));
        } catch (IOException e) {
            throw to(e);
        }
    }


    @Override
    public Cancellable performRequestAsync(Request request, com.manymobi.esdsl.handler.ResponseListener responseListener) {
        return restClient.performRequestAsync(to(request), new ResponseListener() {
            @Override
            public void onSuccess(org.elasticsearch.client.Response response) {
                try {
                    responseListener.onSuccess(to(response));
                } catch (Exception e) {
                    onFailure(to(e));
                }
            }

            @Override
            public void onFailure(Exception exception) {
                responseListener.onFailure(exception);
            }
        })::cancel;

    }


    private org.elasticsearch.client.Request to(Request request1) {
        org.elasticsearch.client.Request request = new org.elasticsearch.client.Request(
                request1.getRequestMethod().name(),
                request1.getUrl()
        );
        request.setJsonEntity(request1.getJson());
        return request;
    }

    private Response to(org.elasticsearch.client.Response response) {
        int statusCode = response.getStatusLine().getStatusCode();
        String body = null;
        try {
            body = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Response(statusCode, body);
    }


    private RuntimeException to(Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        if (e instanceof ResponseException) {
            return new com.manymobi.esdsl.exception.ResponseException(to(((ResponseException) e).getResponse()));
        }
        return new RuntimeException(e);

    }
}
