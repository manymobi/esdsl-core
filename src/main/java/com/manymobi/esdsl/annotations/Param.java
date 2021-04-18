package com.manymobi.esdsl.annotations;

import java.lang.annotation.*;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 13:20
 * @version 1.0
 * @since 1.0
 * 表示参数
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {
    /**
     * 表示参数
     */
    String value() default "";
}
