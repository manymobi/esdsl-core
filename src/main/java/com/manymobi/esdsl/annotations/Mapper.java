package com.manymobi.esdsl.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 13:19
 * @version 1.0
 * @since 1.0
 * 注解到需要mapper 类或 方法上，进行初始化
 */
@Documented
@Inherited
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface Mapper {

    /**
     * 文件名称，xxx.esdsl
     *
     * @return 文件名
     */
    String value();

}
