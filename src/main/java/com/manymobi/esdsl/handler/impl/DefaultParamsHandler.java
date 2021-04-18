package com.manymobi.esdsl.handler.impl;

import com.manymobi.esdsl.annotations.Param;
import com.manymobi.esdsl.annotations.RequestBody;
import com.manymobi.esdsl.handler.JsonHandler;
import com.manymobi.esdsl.handler.ParamHandler;
import com.manymobi.esdsl.handler.ParamsHandler;
import com.manymobi.esdsl.handler.VariableHandler;
import com.manymobi.esdsl.parser.ParamMap;
import com.manymobi.esdsl.parser.ParamMapImpl;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author 梁建军
 * 创建日期： 2018/11/8
 * 创建时间： 17:56
 * @version 1.0
 * @since 1.0
 */
public class DefaultParamsHandler implements ParamsHandler {


    private final VariableHandler variableHandler;

    private final ParamHandler[] paramHandlers;

    public <T extends DefaultParamsHandler> DefaultParamsHandler(List<ParamHandler> paramHandlers, VariableHandler variableHandler) {
        this.paramHandlers = paramHandlers.toArray(new ParamHandler[0]);
        this.variableHandler = variableHandler;
    }

    @Override
    public ParamMap<String, Object> handle(Object[] argv, JsonHandler jsonHandler) {
        ParamMap<String, Object> map = new ParamMapImpl<>(jsonHandler, variableHandler);
        if (argv != null) {
            for (int i = 0; i < argv.length; i++) {
                paramHandlers[i].handler(map, argv[i]);
            }
        }
        return map;
    }


}
