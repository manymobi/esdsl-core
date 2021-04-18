package com.manymobi.esdsl.annotations;

import java.lang.annotation.*;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 13:22
 * @version 1.0
 * @since 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    /**
     * 请求方法
     *
     * @return 请求方法
     */
    RequestMethod method();

    /**
     * 请求路径
     *
     * @return 请求路径
     */
    String value();
}
