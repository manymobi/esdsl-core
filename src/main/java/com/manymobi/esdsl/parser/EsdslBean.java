package com.manymobi.esdsl.parser;

import com.manymobi.esdsl.annotations.RequestMethod;
import com.manymobi.esdsl.handler.JsonEncoder;
import com.manymobi.esdsl.parser.run.process.RunProcess;
import com.manymobi.esdsl.util.Optional;

/**
 * @author 梁建军
 * 创建日期： 2021/4/17
 * 创建时间： 下午 11:21
 * @version 1.0
 * @since 1.0
 */

public class EsdslBean {
    /**
     * 方法名称
     */
    private final String methodName;
    /**
     * 请求方式
     */
    private final RequestMethod requestMethod;
    /**
     * url 处理器
     */
    private final RunProcess url;
    /**
     * json处理器
     */
    private final RunProcess json;
    /**
     * 动态请求体
     */
    private final boolean dynamicRequestBody;

    public EsdslBean(String methodName, RequestMethod requestMethod, RunProcess url, RunProcess json) {
        this.methodName = methodName;
        this.requestMethod = requestMethod;
        this.url = url;
        this.json = json;
        dynamicRequestBody = false;
    }

    public EsdslBean(EsdslBean esdslBean0, EsdslBean esdslBean) {
        methodName = Optional.ofNullable(esdslBean.methodName).orElse(esdslBean0.methodName);
        url = Optional.ofNullable(esdslBean.url).orElse(esdslBean0.url);
        json = Optional.ofNullable(esdslBean.json).orElse(esdslBean0.json);
        requestMethod = Optional.ofNullable(esdslBean.requestMethod).orElse(esdslBean0.requestMethod);
        dynamicRequestBody = Optional.ofNullable(esdslBean.dynamicRequestBody).orElse(esdslBean0.dynamicRequestBody);
    }

    public EsdslBean(EsdslBean esdslBean0, boolean dynamicRequestBody) {
        methodName = esdslBean0.methodName;
        url = esdslBean0.url;
        json = esdslBean0.json;
        requestMethod = esdslBean0.requestMethod;
        this.dynamicRequestBody = dynamicRequestBody;
    }

    public String getMethodName() {
        return methodName;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public RunProcess getUrl() {
        return url;
    }

    public RunProcess getJson() {
        return json;
    }

    public String url(ParamMap<String, Object> parameter) {
        return getUrl().runProcess(parameter);
    }

    public boolean isDynamicRequestBody() {
        return dynamicRequestBody;
    }

    public String json(ParamMap<String, Object> parameter, JsonEncoder jsonEncoder) {
        if (dynamicRequestBody) {
            Object requestBody = parameter.get("requestBody");
            if (requestBody instanceof String) {
                return (String) requestBody;
            }
            return jsonEncoder.toJson(requestBody);
        }
        if (json == null) {
            return null;
        }
        return getJson().runProcess(parameter);
    }


    public EsdslBean generate(EsdslBean tempEsdsl) {
        return new EsdslBean(this, tempEsdsl);
    }

    public EsdslBean dynamicRequestBody() {
        return new EsdslBean(this, true);
    }
}
