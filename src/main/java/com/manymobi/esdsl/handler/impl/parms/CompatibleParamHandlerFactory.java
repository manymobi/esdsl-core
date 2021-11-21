package com.manymobi.esdsl.handler.impl.parms;

import com.manymobi.esdsl.handler.JsonEncoder;
import com.manymobi.esdsl.handler.ParamHandler;
import com.manymobi.esdsl.handler.ParamHandlerFactory;

/**
 * @author 梁建军
 * 创建日期： 2021/10/15
 * 创建时间： 下午3:06
 * @version 1.0.0
 * @since 1.0.0
 * 兼容 {@link ParamHandler.Build}
 */
@Deprecated
public class CompatibleParamHandlerFactory implements ParamHandlerFactory<Object>, JsonEncoder.Set {

    private final ParamHandler.Build build;

    public CompatibleParamHandlerFactory(ParamHandler.Build build) {

        this.build = build;
    }

    @Override
    public boolean can(Class<?> parameterType) {
        return build.can(parameterType);
    }

    @Override
    public ParamHandler<Object> apply(String paramName) {
        return build.build(paramName);
    }

    @Override
    public void setJsonEncoder(JsonEncoder jsonEncoder) {
        build.setJsonHandler(jsonEncoder);
    }
}
