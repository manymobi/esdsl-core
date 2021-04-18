package com.manymobi.esdsl.parser;

import com.manymobi.esdsl.handler.JsonHandler;
import com.manymobi.esdsl.handler.VariableHandler;
import com.manymobi.esdsl.parser.run.process.VariableRunProcess;

import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2021/4/17
 * 创建时间： 下午 4:21
 * @version 1.0
 * @since 1.0
 */
public interface ParamMap<Key, Value> extends Map<Key, Value> {
    /**
     * 获取参数
     *
     * @param variableName 变量名称
     * @param type         变量类型
     * @return 变量数据
     */
    String getString(String variableName, VariableRunProcess.Type type);

    /**
     * 获取参数
     *
     * @param variableName 变量名称
     * @param type         变量类型
     * @return 变量数据
     */
    Object get(String variableName, VariableRunProcess.Type type);

    JsonHandler getJsonHandler();

    VariableHandler getVariableHandler();
}
