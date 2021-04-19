package com.manymobi.esdsl.parser;

import com.manymobi.esdsl.handler.JsonEncoder;
import com.manymobi.esdsl.handler.VariableHandler;
import com.manymobi.esdsl.parser.run.process.VariableRunProcess;

import java.util.HashMap;

/**
 * @author 梁建军
 * 创建日期： 2021/4/17
 * 创建时间： 下午 4:27
 * @version 1.0
 * @since 1.0
 */
public class ParamMapImpl<Key, Value> extends HashMap<Key, Value> implements ParamMap<Key, Value> {

    private final JsonEncoder jsonEncoder;

    private final VariableHandler variableHandler;

    public ParamMapImpl(JsonEncoder jsonEncoder, VariableHandler variableHandler) {
        this.jsonEncoder = jsonEncoder;
        this.variableHandler = variableHandler;
    }

    @Override
    public String getString(String variableName, VariableRunProcess.Type type) {
        switch (type) {

            case TRANSFORMATION:
                return jsonEncoder.toJson(variableHandler.getVariable(this, variableName));
            case DIRECT:
                return String.valueOf(variableHandler.getVariable(this, variableName));
            case GRADLE:
                throw new RuntimeException("未实现");
            default:
                throw new RuntimeException("不该运行到这里");
        }

    }

    @Override
    public Object get(String variableName, VariableRunProcess.Type type) {
        switch (type) {
            case TRANSFORMATION:
            case DIRECT:
                return variableHandler.getVariable(this, variableName);
            case GRADLE:
                throw new RuntimeException("未实现");
            default:
                throw new RuntimeException("不该运行到这里");
        }
    }


    @Override
    public JsonEncoder getJsonHandler() {
        return jsonEncoder;
    }

    @Override
    public VariableHandler getVariableHandler() {
        return variableHandler;
    }
}
