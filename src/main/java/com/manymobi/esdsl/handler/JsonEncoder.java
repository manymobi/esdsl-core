package com.manymobi.esdsl.handler;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2021/4/17
 * 创建时间： 下午 10:28
 * @version 1.0
 * @since 1.0
 * json 编码处理
 */
public interface JsonEncoder {
    /**
     * 转换成 String
     *
     * @param o 对象
     * @return String
     */
    String toJson(Object o);

    /**
     * obj 转map
     *
     * @param o obj
     * @return map
     */
    Map<Object, Object> toJsonMap(Object o);

    /**
     * 解析json
     *
     * @param json json
     * @param type 参数类型
     * @return 实例对象
     */
    Object parseObject(String json, Type type);


    interface Set {

        void setJsonEncoder(JsonEncoder jsonEncoder);
    }

}
