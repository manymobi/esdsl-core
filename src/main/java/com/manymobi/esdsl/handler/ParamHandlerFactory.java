package com.manymobi.esdsl.handler;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 下午 2:02
 * @version 1.0
 * @since 1.0
 * 单个方法参数处理器
 */
public interface ParamHandlerFactory<T> {
    /**
     * 判断参数是否归当前处理
     *
     * @param parameterType 参数类型
     * @return 返回true 时候,就表示当前支持处理,将调用 @{@link ParamHandlerFactory#apply(String)} 方法
     */
    boolean can(Class<?> parameterType);

    /**
     * 申请一个参数处理器
     *
     * @param paramName 参数名称
     * @return 返回参数处理器
     */
    ParamHandler<T> apply(String paramName);
}
