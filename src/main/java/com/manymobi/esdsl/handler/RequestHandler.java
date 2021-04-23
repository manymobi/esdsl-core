package com.manymobi.esdsl.handler;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 下午 3:31
 * @version 1.0
 * @since 1.0
 * 请求json处理器
 */
public interface RequestHandler {
    /**
     * 处理请求json
     *
     * @param json 原始数据
     * @return 处理后的
     */
    Request handler(Request json);
}
