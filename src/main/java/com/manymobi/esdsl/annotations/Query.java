package com.manymobi.esdsl.annotations;

import java.lang.annotation.*;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 13:40
 * @version 1.0
 * @since 1.0
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Query {
    /**
     * 查询语句
     *
     * @return 查询语句
     */
    String value();
}
