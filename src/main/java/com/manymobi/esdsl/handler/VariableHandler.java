package com.manymobi.esdsl.handler;

/**
 * @author 梁建军
 * 创建日期： 2021/4/17
 * 创建时间： 下午 11:02
 * @version 1.0
 * @since 1.0
 * 变量处理程序
 * esdsl中的变量获取使用该接口
 */
public interface VariableHandler {
    /**
     * 获取变量
     *
     * @param parameter 原始数据
     * @param key       变量名称
     * @return 获取的实际变量
     */
    Object getVariable(Object parameter, String key);

}
