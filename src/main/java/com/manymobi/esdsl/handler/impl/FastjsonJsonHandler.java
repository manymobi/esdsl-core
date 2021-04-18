package com.manymobi.esdsl.handler.impl;

import com.alibaba.fastjson.JSON;
import com.manymobi.esdsl.handler.JsonHandler;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2021/4/17
 * 创建时间： 下午 10:31
 * @version 1.0
 * @since 1.0
 */
public class FastjsonJsonHandler implements JsonHandler {

    @Override
    public String toJson(Object o) {
        return JSON.toJSONString(o);
    }

    @Override
    public Map toJsonMap(Object o) {
        Object o1 = JSON.toJSON(o);
        if (o1 instanceof Map) {
            return (Map) o1;
        }
        throw new RuntimeException("不支持这个类型");
    }

    @Override
    public Object parseObject(String json, Type type) {
        return JSON.parseObject(json, type);
    }
}
