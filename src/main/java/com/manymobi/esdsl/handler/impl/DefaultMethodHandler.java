package com.manymobi.esdsl.handler.impl;

import com.manymobi.esdsl.annotations.Mapper;
import com.manymobi.esdsl.annotations.Param;
import com.manymobi.esdsl.annotations.Query;
import com.manymobi.esdsl.annotations.RequestBody;
import com.manymobi.esdsl.annotations.RequestMapping;
import com.manymobi.esdsl.annotations.RequestMethod;
import com.manymobi.esdsl.handler.JsonEncoder;
import com.manymobi.esdsl.handler.MethodHandler;
import com.manymobi.esdsl.handler.ParamHandler;
import com.manymobi.esdsl.handler.ParamsHandler;
import com.manymobi.esdsl.handler.Request;
import com.manymobi.esdsl.handler.RequestHandler;
import com.manymobi.esdsl.handler.ResponseBodyHandler;
import com.manymobi.esdsl.handler.ResponseContextHandler;
import com.manymobi.esdsl.handler.RestHandler;
import com.manymobi.esdsl.handler.VariableHandler;
import com.manymobi.esdsl.parser.EsdslBean;
import com.manymobi.esdsl.parser.EsdslResource;
import com.manymobi.esdsl.parser.ParamMap;
import com.manymobi.esdsl.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 梁建军
 * 创建日期： 2018/11/8
 * 创建时间： 16:22
 * @version 1.0
 * @since 1.0
 */
public class DefaultMethodHandler implements MethodHandler {

    private final Type returnType;

    private final EsdslBean esdslBean;
    /**
     * 参数
     */
    private final ParamsHandler paramsHandler;

    private final RestHandler restHandler;

    private final JsonEncoder jsonEncoder;

    private final RequestHandler[] requestHandler;

    private final Logger logger;

    private final ResponseBodyHandler responseBodyHandler;

    private final ResponseContextHandler responseContextHandler;

    private DefaultMethodHandler(Build build) {
        logger = LoggerFactory.getLogger(build.method.getDeclaringClass().getName() + "." + build.method.getName());
        jsonEncoder = build.jsonEncoder;
        restHandler = new LogRestHandler(build.restHandler, logger);
        requestHandler = build.requestHandler;
        ParamHandler.Build[] paramHandlers = build.paramHandlers;
        Method method = build.method;

        boolean dynamicRequestBody = false;
        //参数
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Class<?>[] parameterTypes = method.getParameterTypes();


        List<ParamHandler> paramHandlerList = new LinkedList<>();
        for (int i = 0; i < parameterTypes.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];

            Class<?> parameterType = parameterTypes[i];

            ParamHandler.Build paramHandlerBuild = Arrays.stream(paramHandlers)
                    .filter(paramHandler1 -> paramHandler1.can(parameterType))
                    .findFirst()
                    .get();
            ParamHandler paramHandler = null;
            for (Annotation annotation1 : parameterAnnotation) {
                if (annotation1 instanceof RequestBody) {
                    dynamicRequestBody = true;
                    paramHandler = paramHandlerBuild.build("requestBody");
                } else if (annotation1 instanceof Param) {
                    Param param = (Param) annotation1;
                    paramHandler = paramHandlerBuild.build(param.value());
                }
            }
            paramHandlerList.add(paramHandler);
        }

        paramsHandler = new DefaultParamsHandler(paramHandlerList, build.variableHandler);

        //dsl
        StringBuilder esdslStringBuilder = new StringBuilder();
        Mapper mapper = Optional.ofNullable(method.getAnnotation(Mapper.class)).orElse(build.mapper);
        String esdslFileName = mapper.value();
        boolean requestMappingBoolean = true;
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            esdslStringBuilder.append(requestMapping.method().name())
                    .append(" ")
                    .append(requestMapping.value())
                    .append("\n");
            requestMappingBoolean = false;
        }
        boolean queryBoolean = true;
        Query query = method.getAnnotation(Query.class);
        if (query != null) {
            String dsl = query.value();
            esdslStringBuilder.append(dsl);
            queryBoolean = false;
        }

        EsdslBean tempEsdsl = null;
        if (esdslStringBuilder.length() > 0) {
            tempEsdsl = build.esdslResource.generate(esdslStringBuilder.toString());
        }

        if (requestMappingBoolean || (queryBoolean && (!dynamicRequestBody))) {
            EsdslBean temp = build.esdslResource.get(esdslFileName, build.method.getName());
            if (temp != null) {
                if (tempEsdsl != null) {
                    tempEsdsl = temp.generate(tempEsdsl);
                } else {
                    tempEsdsl = temp;
                }
            }
        }
        if (tempEsdsl == null) {
            throw new RuntimeException(build.method.getDeclaringClass().getName() + "#" + build.method
                    .getName() + "未找到请求处理器");
        }
        if (dynamicRequestBody) {
            tempEsdsl = tempEsdsl.dynamicRequestBody();
        }
        esdslBean = tempEsdsl;


        //返回值
        returnType = method.getGenericReturnType();

        //获取相应表层处理
        responseBodyHandler = Arrays.stream(build.responseBodyHandler)
                .filter(handler -> handler.can(method, returnType))
                .findFirst()
                .get();
        Type contextType = responseBodyHandler.getContextType(returnType);

        //获取内容处理
        responseContextHandler = Arrays.stream(build.responseContextHandler)
                .filter(handler -> handler.can(method, contextType))
                .findFirst()
                .get();

    }


    @Override
    public Object invoke(Object[] argv) throws Throwable {
        ParamMap<String, Object> handle = paramsHandler.handle(argv, jsonEncoder);
        RequestMethod requestMethod = esdslBean.getRequestMethod();
        String url = esdslBean.url(handle);
        String json = esdslBean.json(handle, jsonEncoder);
        Request request = new Request(requestMethod, url, json);

        for (RequestHandler handler : requestHandler) {
            request = handler.handler(request);
        }
        return responseBodyHandler.handler(restHandler, request, returnType, responseContextHandler);
    }

    public static class Build {
        private Method method;
        private EsdslResource esdslResource;
        private RestHandler restHandler;
        private Mapper mapper;
        private JsonEncoder jsonEncoder;
        private VariableHandler variableHandler;
        private ParamHandler.Build[] paramHandlers;
        private RequestHandler[] requestHandler;
        private ResponseBodyHandler[] responseBodyHandler;
        private ResponseContextHandler[] responseContextHandler;

        public Build setMethods(Method method) {
            this.method = method;
            return this;
        }

        public Build setMapper(Mapper mapper) {
            this.mapper = mapper;
            return this;
        }

        public Build setEsdslResource(EsdslResource esdslResource) {
            this.esdslResource = esdslResource;
            return this;
        }

        public Build setRestHandler(RestHandler restHandler) {
            this.restHandler = restHandler;
            return this;
        }

        public Build setJsonHandler(JsonEncoder jsonEncoder) {
            this.jsonEncoder = jsonEncoder;
            return this;
        }

        public Build setVariableHandler(VariableHandler variableHandler) {
            this.variableHandler = variableHandler;
            return this;
        }

        public Build setParamHandlers(ParamHandler.Build[] paramHandlers) {
            this.paramHandlers = paramHandlers;
            return this;
        }

        public Build setRequestJsonHandler(RequestHandler[] requestHandler) {
            this.requestHandler = requestHandler;
            return this;
        }

        public Build setResponseBodyHandler(ResponseBodyHandler[] responseBodyHandler) {
            this.responseBodyHandler = responseBodyHandler;
            return this;
        }

        public Build setResponseContextHandler(ResponseContextHandler[] responseContextHandler) {
            this.responseContextHandler = responseContextHandler;
            return this;
        }

        public DefaultMethodHandler build() {
            return new DefaultMethodHandler(this);
        }


    }
}
