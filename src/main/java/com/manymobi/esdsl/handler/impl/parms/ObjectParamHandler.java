package com.manymobi.esdsl.handler.impl.parms;

import com.manymobi.esdsl.handler.JsonEncoder;
import com.manymobi.esdsl.handler.ParamHandler;

import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 下午 2:04
 * @version 1.0
 * @since 1.0
 */
public class ObjectParamHandler extends ParamHandler<Object> {

    public ObjectParamHandler(String paramName, JsonEncoder jsonEncoder) {
        super(paramName, jsonEncoder);
    }

    @Override
    public void handler(Map map, Object o) {

        if (paramName == null || "".equals(paramName)) {
            Map json = jsonEncoder.toJsonMap(o);
            map.putAll(json);
        } else {
            map.put(paramName, o);
        }
    }



    public static class Build extends ParamHandler.Build<ObjectParamHandler> {

        @Override
        public boolean can(Class<?> parameterType) {
            return true;
        }

        @Override
        public ObjectParamHandler build(String paramName) {
            return new ObjectParamHandler(paramName, jsonEncoder);
        }
    }
}
