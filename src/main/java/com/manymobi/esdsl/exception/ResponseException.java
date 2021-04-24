package com.manymobi.esdsl.exception;


import com.manymobi.esdsl.handler.Response;

/**
 * @author 梁建军
 * 创建日期： 2021/4/24
 * 创建时间： 下午2:50
 * @version 1.0
 * @since 1.0
 */
public class ResponseException extends RuntimeException {
    private final Response response;

    public ResponseException(Response response) {
        super(response.getBody());
        this.response = response;
    }

    /**
     * Returns the {@link Response} that caused this exception to be thrown.
     *
     * @return Response
     */
    public Response getResponse() {
        return response;
    }
}
