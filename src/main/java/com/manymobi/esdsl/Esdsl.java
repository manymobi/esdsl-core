package com.manymobi.esdsl;

import com.manymobi.esdsl.annotations.Mapper;
import com.manymobi.esdsl.handler.EsdslFileResourceHandler;
import com.manymobi.esdsl.handler.JsonEncoder;
import com.manymobi.esdsl.handler.MethodHandler;
import com.manymobi.esdsl.handler.ParamHandler;
import com.manymobi.esdsl.handler.RequestHandler;
import com.manymobi.esdsl.handler.ResponseBodyHandler;
import com.manymobi.esdsl.handler.RestHandler;
import com.manymobi.esdsl.handler.VariableHandler;
import com.manymobi.esdsl.handler.impl.DefaultMethodHandler;
import com.manymobi.esdsl.handler.impl.DefaultRestHandler;
import com.manymobi.esdsl.handler.impl.DefaultVariableHandler;
import com.manymobi.esdsl.handler.impl.EsdslInvocationHandler;
import com.manymobi.esdsl.handler.impl.FaultTolerantRequestHandler;
import com.manymobi.esdsl.handler.ResponseContextHandler;
import com.manymobi.esdsl.handler.impl.parms.NumberParamHandler;
import com.manymobi.esdsl.handler.impl.parms.ObjectParamHandler;
import com.manymobi.esdsl.handler.impl.parms.StringParamHandler;
import com.manymobi.esdsl.handler.impl.response.ObjectResponseBodyHandler;
import com.manymobi.esdsl.handler.impl.response.ObjectResponseContextHandler;
import com.manymobi.esdsl.parser.EsdslResource;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 14:30
 * @version 1.0
 * @since 1.0
 */
public class Esdsl implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(Esdsl.class);
    /**
     * es 客户端处理
     */
    private final RestHandler restHandler;
    /**
     * json转码处理
     */
    private final JsonEncoder jsonEncoder;
    /**
     * esdsl资源，里面报错 esdsl 解析之后的内容
     */
    private final EsdslResource esdslResource;
    /**
     * 变量获取使用该接口
     */
    private final VariableHandler variableHandler;
    /**
     * 不同类型参数不同处理
     */
    private final ParamHandler.Build[] paramHandlers;
    /**
     * 发送前处理对请求处理
     * 生成的json 默认会有多余的 "," 使用@{@link FaultTolerantRequestHandler} 去掉了
     * 需要添加新的处理，数组开始 一定要是 {@link FaultTolerantRequestHandler}
     */
    private final RequestHandler[] requestHandlers;

    private final ResponseBodyHandler[] responseBodyHandlers;

    private final ResponseContextHandler[] responseContextHandlers;

    private final ConcurrentMap<Class, Object> objectMap = new ConcurrentHashMap<>();

    public Esdsl(Build build) {
        this.restHandler = build.restHandler;
        this.jsonEncoder = build.jsonEncoder;
        this.variableHandler = build.variableHandler;
        this.requestHandlers = build.requestHandlers.toArray(new RequestHandler[0]);
        this.responseBodyHandlers = build.responseBodyHandlers.toArray(new ResponseBodyHandler[0]);
        for (ResponseBodyHandler responseBodyHandler : this.responseBodyHandlers) {
            responseBodyHandler.setJsonEncoder(jsonEncoder);
        }
        this.responseContextHandlers = build.responseContextHandlers.toArray(new ResponseContextHandler[0]);
        for (ResponseContextHandler responseContextHandler : this.responseContextHandlers) {
            responseContextHandler.setJsonEncoder(jsonEncoder);
        }
        this.paramHandlers = build.paramHandlers.toArray(new ParamHandler.Build[0]);
        for (ParamHandler.Build paramHandler : this.paramHandlers) {
            paramHandler.setJsonHandler(jsonEncoder);
        }
        esdslResource = new EsdslResource(build.esdslFileResourceHandler);
    }

    public RestHandler getRestHandler() {
        return restHandler;
    }

    public JsonEncoder getJsonEncoder() {
        return jsonEncoder;
    }

    public EsdslResource getEsdslResource() {
        return esdslResource;
    }

    public VariableHandler getVariableHandler() {
        return variableHandler;
    }

    public ParamHandler.Build[] getParamHandlers() {
        return paramHandlers;
    }

    public RequestHandler[] getRequestJsonHandlers() {
        return requestHandlers;
    }

    public <T> T target(Class<T> tClass) {
        return (T) objectMap.computeIfAbsent(tClass, aClass -> target0(tClass));
    }

    private <T> T target0(Class<T> tClass) {

        Mapper mapper = tClass.getAnnotation(Mapper.class);

        Map<Method, MethodHandler> methodToHandler = Arrays.stream(tClass.getMethods())
                .filter(method -> method.getDeclaringClass() != Object.class)
                .filter(method -> !EsdslInvocationHandler.isDefaultMethod(method))
                .collect(Collectors.toMap(method -> method,
                        method -> new DefaultMethodHandler.Build()
                                .setMethods(method)
                                .setMapper(mapper)
                                .setEsdslResource(esdslResource)
                                .setRestHandler(restHandler)
                                .setJsonHandler(jsonEncoder)
                                .setVariableHandler(variableHandler)
                                .setParamHandlers(paramHandlers)
                                .setRequestJsonHandler(requestHandlers)
                                .setResponseBodyHandler(responseBodyHandlers)
                                .setResponseContextHandler(responseContextHandlers)
                                .build())
                );

        EsdslInvocationHandler esdslInvocationHandler = new EsdslInvocationHandler(methodToHandler);

        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{tClass}, esdslInvocationHandler);
    }

    @Override
    public void close() throws Exception {
        restHandler.close();
    }


    public static class Build {


        private RestHandler restHandler;

        private EsdslFileResourceHandler esdslFileResourceHandler;

        private JsonEncoder jsonEncoder;

        private VariableHandler variableHandler = new DefaultVariableHandler();


        private List<ResponseBodyHandler> responseBodyHandlers = Arrays.asList(
                new ObjectResponseBodyHandler()
        );

        private List<ResponseContextHandler> responseContextHandlers = Arrays.asList(
                new ObjectResponseContextHandler()
        );

        private List<RequestHandler> requestHandlers = new ArrayList<>(
                Collections.singletonList(new FaultTolerantRequestHandler())
        );

        private List<ParamHandler.Build> paramHandlers = Arrays.asList(
                new NumberParamHandler.Build(),
                new StringParamHandler.Build(),
                new ObjectParamHandler.Build()
        );


        public Build setRestHandler(RestHandler restHandler) {
            this.restHandler = restHandler;
            return this;
        }

        public Build setEsdslFileResourceHandler(EsdslFileResourceHandler esdslFileResourceHandler) {
            this.esdslFileResourceHandler = esdslFileResourceHandler;
            return this;
        }

        public Build setJsonEncoder(JsonEncoder jsonEncoder) {
            this.jsonEncoder = jsonEncoder;
            return this;
        }

        public Build setVariableHandler(VariableHandler variableHandler) {
            this.variableHandler = variableHandler;
            return this;
        }

        public Build setParamHandlers(List<ParamHandler.Build> paramHandlers) {
            this.paramHandlers = paramHandlers;
            return this;
        }

        public Build setRestClient(RestClient restClient) {
            this.restHandler = new DefaultRestHandler(restClient);
            return this;
        }

        public Build setResponseBodyHandlers(List<ResponseBodyHandler> responseBodyHandlers) {
            this.responseBodyHandlers = responseBodyHandlers;
            return this;
        }

        public Build setResponseContextHandlers(List<ResponseContextHandler> responseContextHandlers) {
            this.responseContextHandlers = responseContextHandlers;
            return this;
        }

        public void setRequestHandlers(List<RequestHandler> requestHandlers) {
            this.requestHandlers = requestHandlers;
        }

        public Esdsl build() {
            return new Esdsl(this);
        }
    }
}
