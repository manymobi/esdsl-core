package com.manymobi.esdsl.handler;

/**
 * @author 梁建军
 * 创建日期： 2018/11/8
 * 创建时间： 15:04
 * @version 1.0
 * @since 1.0
 * 方法实现
 */
public interface MethodHandler {

    Object invoke(Object[] argv) throws Throwable;
}
