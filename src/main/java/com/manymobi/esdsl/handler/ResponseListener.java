package com.manymobi.esdsl.handler;


/**
 * @author 梁建军
 * 创建日期： 2021/4/23
 * 创建时间： 下午12:04
 * @version 1.0
 * @since 1.0
 */
public interface ResponseListener {
    /**
     * 成功
     *
     * @param response 相应
     */
    void onSuccess(Response response);

    /**
     * 失败
     *
     * @param exception 异常
     */
    void onFailure(Exception exception);
}
