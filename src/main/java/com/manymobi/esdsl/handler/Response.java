package com.manymobi.esdsl.handler;

/**
 * @author 梁建军
 * 创建日期： 2021/4/23
 * 创建时间： 下午12:03
 * @version 1.0
 * @since 1.0
 */
public class Response {

    private final int statusCode;
    private final String body;

    public Response(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }
}
