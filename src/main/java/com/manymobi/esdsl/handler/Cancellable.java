package com.manymobi.esdsl.handler;

/**
 * @author 梁建军
 * 创建日期： 2021/4/23
 * 创建时间： 下午12:05
 * @version 1.0
 * @since 1.0
 * 可取消
 */
public interface Cancellable {
    /**
     * 取消
     */
    void cancel();
}
