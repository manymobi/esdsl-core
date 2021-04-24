package com.manymobi.esdsl.exception;

import com.manymobi.esdsl.handler.Response;

/**
 * @author 梁建军
 * 创建日期： 2021/4/24
 * 创建时间： 下午9:26
 * @version 1.0
 * @since 1.0
 */
public class NotFoundException extends ResponseException {

    public NotFoundException(Response response) {
        super(response);
    }
}
