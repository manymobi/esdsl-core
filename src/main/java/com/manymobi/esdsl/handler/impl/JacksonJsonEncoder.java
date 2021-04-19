package com.manymobi.esdsl.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manymobi.esdsl.handler.JsonEncoder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2021/4/17
 * 创建时间： 下午 10:31
 * @version 1.0
 * @since 1.0
 */
public class JacksonJsonEncoder implements JsonEncoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String toJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map toJsonMap(Object o) {
        return objectMapper.convertValue(o, HashMap.class);
    }

    @Override
    public Object parseObject(String json, Type type) {
        try {
            return objectMapper.readValue(json, new TypeReference<Object>() {
                @Override
                public Type getType() {
                    return type;
                }
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
