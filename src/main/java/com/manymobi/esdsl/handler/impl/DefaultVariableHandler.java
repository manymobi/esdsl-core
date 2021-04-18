package com.manymobi.esdsl.handler.impl;

import com.manymobi.esdsl.handler.VariableHandler;
import com.manymobi.esdsl.parser.run.process.IllegalParameterException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 梁建军
 * 创建日期： 2021/4/17
 * 创建时间： 下午 11:03
 * @version 1.0
 * @since 1.0
 */
public class DefaultVariableHandler implements VariableHandler {

    private final ConcurrentMap<String, Method> methodMap = new ConcurrentHashMap<>();

    @Override
    public Object getVariable(Object parameter, String key) {

        if (key == null) {
            return null;
        }

        String[] split = key.split("\\.");
        Object temp = parameter;
        for (String s : split) {
            if (temp == null) {
                return null;
            }
            if (temp instanceof Map) {
                temp = ((Map) temp).get(s);
            } else {
                //首字母大写
                String titleCase = s.substring(0, 1).toUpperCase() + s.substring(1);

                String className = temp.getClass().getName();
                String methodName = "get" + titleCase;

                Object finalTemp = temp;
                try {
                    Method method = methodMap.computeIfAbsent(className + "#" + methodName, s1 -> {
                        try {
                            return finalTemp.getClass()
                                    .getMethod(methodName);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    temp = method.invoke(temp);
                } catch (Exception e) {
                    throw new IllegalParameterException("获取参数失败,参数 " + parameter + " key " + key, e);
                }
            }
        }

        return temp;
    }
}
