package com.manymobi.esdsl;

import com.manymobi.esdsl.annotations.Mapper;
import com.manymobi.esdsl.handler.*;
import com.manymobi.esdsl.handler.impl.DefaultMethodHandler;
import com.manymobi.esdsl.handler.impl.DefaultVariableHandler;
import com.manymobi.esdsl.handler.impl.EsdslInvocationHandler;
import com.manymobi.esdsl.handler.impl.RequestJsonHandlerImpl;
import com.manymobi.esdsl.handler.impl.parms.NumberParamHandler;
import com.manymobi.esdsl.handler.impl.parms.ObjectParamHandler;
import com.manymobi.esdsl.handler.impl.parms.StringParamHandler;
import com.manymobi.esdsl.parser.EsdslResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 14:30
 * @version 1.0
 * @since 1.0
 */
public class Esdsl implements AutoCloseable{

    private static final Logger log = LoggerFactory.getLogger(Esdsl.class);

    private final RestHandler restHandler;

    private final JsonHandler jsonHandler;

    private final EsdslResource esdslResource;

    private final VariableHandler variableHandler;

    private final ParamHandler.Build[] paramHandlers;

    private final RequestJsonHandler[] requestJsonHandlers;

    private final ConcurrentMap<Class, Object> objectMap = new ConcurrentHashMap<>();

    public Esdsl(Build build) {
        this.restHandler = build.restHandler;
        this.jsonHandler = build.jsonHandler;
        this.variableHandler = build.variableHandler;
        this.requestJsonHandlers = build.requestJsonHandlers.toArray(new RequestJsonHandler[0]);
        this.paramHandlers = build.paramHandlers.toArray(new ParamHandler.Build[0]);
        for (ParamHandler.Build paramHandler : this.paramHandlers) {
            paramHandler.setJsonHandler(jsonHandler);
        }
        esdslResource = new EsdslResource(build.esdslFileResourceHandler);
    }

    public <T> T target(Class<T> tClass) {
        return (T) objectMap.computeIfAbsent(tClass, aClass -> target0(tClass));
    }

    private <T> T target0(Class<T> tClass) {

        Map<Method, MethodHandler> methodToHandler = new HashMap<>();
        EsdslInvocationHandler esdslInvocationHandler = new EsdslInvocationHandler(methodToHandler);
        Mapper mapper = tClass.getAnnotation(Mapper.class);
        for (Method method : tClass.getMethods()) {
            if (method.getDeclaringClass() == Object.class) {
                continue;
            } else if (EsdslInvocationHandler.isDefaultMethod(method)) {
                continue;
            } else {
                DefaultMethodHandler defaultMethodHandler = new DefaultMethodHandler.Build()
                        .setMethods(method)
                        .setMapper(mapper)
                        .setEsdslResource(esdslResource)
                        .setRestHandler(restHandler)
                        .setJsonHandler(jsonHandler)
                        .setVariableHandler(variableHandler)
                        .setParamHandlers(paramHandlers)
                        .setRequestJsonHandler(requestJsonHandlers)
                        .build();
                methodToHandler.put(method, defaultMethodHandler);
            }
        }

        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{tClass}, esdslInvocationHandler);
    }

    @Override
    public void close() throws Exception {
        restHandler.close();
    }


    public static class Build {


        private RestHandler restHandler;

        private EsdslFileResourceHandler esdslFileResourceHandler;

        private JsonHandler jsonHandler;

        private VariableHandler variableHandler = new DefaultVariableHandler();

        private List<RequestJsonHandler> requestJsonHandlers = new ArrayList<>(
                Collections.singletonList(new RequestJsonHandlerImpl())
        );

        private List<ParamHandler.Build> paramHandlers = new ArrayList<>(Arrays.asList(
                new NumberParamHandler.Build(),
                new StringParamHandler.Build(),
                new ObjectParamHandler.Build()
        ));


        public void setRestHandler(RestHandler restHandler) {
            this.restHandler = restHandler;
        }

        public void setEsdslFileResourceHandler(EsdslFileResourceHandler esdslFileResourceHandler) {
            this.esdslFileResourceHandler = esdslFileResourceHandler;
        }

        public void setJsonHandler(JsonHandler jsonHandler) {
            this.jsonHandler = jsonHandler;
        }

        public void setVariableHandler(VariableHandler variableHandler) {
            this.variableHandler = variableHandler;
        }

        public void setParamHandlers(List<ParamHandler.Build> paramHandlers) {
            this.paramHandlers = paramHandlers;
        }

        public Esdsl build() {
            return new Esdsl(this);
        }
    }
}
