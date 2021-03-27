//package com.manymobi.esdsl.parser;
//
//import com.alibaba.fastjson.JSON;
//import com.manymobi.esdsl.RequestMethod;
//import com.manymobi.esdsl.antlr4.EsdslParser;
//import com.manymobi.esdsl.parser.run.process.*;
//import org.antlr.v4.runtime.tree.ParseTree;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.*;
//
///**
// * @author 梁建军
// * 创建日期： 2018/11/5
// * 创建时间： 14:30
// * @version 1.0
// * @since 1.0
// */
//public class Esdsl {
//
//    private static final Logger log = LoggerFactory.getLogger(Esdsl.class);
//
//    /**
//     * 方法名称
//     */
//    private String methodName;
//
//    private RunProcess url;
//
//    private RunProcess json;
//
//    private RequestMethod requestMethod;
//
//    private boolean dynamicRequestBody;
//
//    private Esdsl(ParseTree parseTree) throws IOException {
//        EsdslParser.EsdslContext esdslContext = (EsdslParser.EsdslContext) parseTree;
//        for (int i = 0; i < esdslContext.getChildCount(); i++) {
//            ParseTree child = esdslContext.getChild(i);
//            if (child instanceof EsdslParser.MethodNameContext) {
//                processMethodName((EsdslParser.MethodNameContext) child);
//            } else if (child instanceof EsdslParser.RequestContext) {
//                processRequest((EsdslParser.RequestContext) child);
//            } else if (child instanceof EsdslParser.JsonContext) {
//                processJson((EsdslParser.JsonContext) child);
//            } else {
//                throw new IOException("发现未知节点" + child.getClass().getName());
//            }
//        }
//    }
//
//    private Esdsl(Esdsl esdsl0, Esdsl esdsl) {
//        methodName = esdsl0.methodName;
//        url = esdsl0.url;
//        json = esdsl0.json;
//        requestMethod = esdsl0.requestMethod;
//
//        if (esdsl.methodName != null) {
//            methodName = esdsl.methodName;
//        }
//        if (esdsl.url != null) {
//            url = esdsl.url;
//        }
//        if (esdsl.json != null) {
//            json = esdsl.json;
//        }
//        if (esdsl.requestMethod != null) {
//            requestMethod = esdsl.requestMethod;
//        }
//    }
//
//    private Esdsl(Esdsl esdsl, boolean dynamicRequestBody) {
//
//        methodName = esdsl.methodName;
//        url = esdsl.url;
//        json = esdsl.json;
//        requestMethod = esdsl.requestMethod;
//        this.dynamicRequestBody = dynamicRequestBody;
//    }
//
//    public Esdsl generate(Esdsl esdsl) {
//        return new Esdsl(this, esdsl);
//    }
//
//
//    public Esdsl dynamicRequestBody() {
//        return new Esdsl(this, true);
//    }
//
//    private void processMethodName(EsdslParser.MethodNameContext child) {
//        EsdslParser.StringContext stringContext = (EsdslParser.StringContext) child.getChild(1);
//        methodName = stringContext.getText();
//        if (log.isDebugEnabled()) {
//            log.debug("方法名称{}", methodName);
//        }
//    }
//
//    private void processRequest(EsdslParser.RequestContext child) throws IOException {
//
//        requestMethod = RequestMethod.valueOf(child.getChild(0).getText());
//
//        url = process(child.getChild(2));
//    }
//
//    private void processJson(EsdslParser.JsonContext jsonContext) throws IOException {
//        json = process(jsonContext);
//    }
//
//    /**
//     * @param parameter 参数
//     */
//    public String url(Map<String, Object> parameter) throws IllegalParameterException {
//        if (url == null) {
//            throw new IllegalParameterException("缺少 url ");
//        }
//        return url.runProcess(parameter);
//    }
//
//    public String json(Map<String, Object> parameter) throws IllegalParameterException {
//        if (dynamicRequestBody) {
//            Object requestBody = parameter.get("requestBody");
//            if (requestBody instanceof String) {
//                return (String) requestBody;
//            }
//            return JSON.toJSONString(requestBody);
//        }
//        if (json == null) {
//            return null;
//        }
//        String jsonString = json.runProcess(parameter);
//
//
//        return jsonFaultTolerant(jsonString);
//    }
//
//
//    /**
//     * 过程
//     */
//    private RunProcess process(ParseTree child) throws IOException {
//        EsdslRunProcess.Build build = new EsdslRunProcess.Build();
//        LinkedList<AbstractRunProcess.Build> list = new LinkedList<>();
//        list.add(build);
//        process(list, child);
//        AbstractRunProcess.Build last;
//
//        while ((last = list.getLast()) != build) {
//            list.removeLast();
//            list.getLast().addRunProcess(last.build());
//        }
//
//        return build.build();
//    }
//
//    /**
//     * 过程
//     */
//    private void process(LinkedList<AbstractRunProcess.Build> builds, ParseTree child) throws IOException {
//
//        if (child instanceof EsdslParser.LogicContext) {
//            processLogic(builds, (EsdslParser.LogicContext) child);
//            return;
//        }
//        if (child instanceof EsdslParser.VariableContext) {
//            processVariable(builds, (EsdslParser.VariableContext) child);
//            return;
//        }
//
//        if (child.getChildCount() == 0) {
//            AbstractRunProcess.Build last = builds.getLast();
//            if (last instanceof StringRunProcess.Build) {
//                ((StringRunProcess.Build) last).addString(child.getText());
//            } else {
//                StringRunProcess.Build build = new StringRunProcess.Build();
//                build.addString(child.getText());
//                builds.add(build);
//            }
//        } else {
//            for (int i = 0; i < child.getChildCount(); i++) {
//                process(builds, child.getChild(i));
//            }
//        }
//
//
//    }
//
//    /**
//     * 过程逻辑
//     */
//    private void processLogic(LinkedList<AbstractRunProcess.Build> builds, EsdslParser.LogicContext child) throws IOException {
//
//        AbstractRunProcess.Build last = builds.getLast();
//        if (last instanceof StringRunProcess.Build) {
//            builds.removeLast();
//            builds.getLast().addRunProcess(last.build());
//        }
//        last = builds.getLast();
//        AbstractRunProcess.Build build = null;
//        ParseTree child1 = child.getChild(0);
//        if (child1 instanceof EsdslParser.ZifContext) {
//            build = new IfRunProcess.Build((EsdslParser.ZifContext) child1);
//        } else if (child1 instanceof EsdslParser.ZelseifContext) {
//            if (last instanceof IfRunProcess.Build) {
//                ((IfRunProcess.Build) last).addElseIf((EsdslParser.ZelseifContext) child1);
//            } else {
//                throw new IOException("没有开始符" + child1.getSourceInterval());
//            }
//        } else if (child1 instanceof EsdslParser.ZelseContext) {
//            if (last instanceof IfRunProcess.Build) {
//                ((IfRunProcess.Build) last).addElse((EsdslParser.ZelseContext) child1);
//            } else {
//                throw new IOException("没有开始符" + child1.getSourceInterval());
//            }
//        } else if (child1 instanceof EsdslParser.ZendifContext) {
//            if (last instanceof IfRunProcess.Build) {
//                builds.removeLast();
//                builds.getLast().addRunProcess(last.build());
//            } else {
//                throw new IOException("没有开始符" + child1.getSourceInterval());
//            }
//        } else if (child1 instanceof EsdslParser.ZforContext) {
//            build = new ForRunProcess.Build((EsdslParser.ZforContext) child1);
//        } else if (child1 instanceof EsdslParser.ZendforContext) {
//            if (last instanceof ForRunProcess.Build) {
//                builds.removeLast();
//                builds.getLast().addRunProcess(last.build());
//            } else {
//                throw new IOException("没有开始符" + child1.getSourceInterval());
//            }
//        } else if (child1 instanceof EsdslParser.VariableContext) {
//            processVariable(builds, (EsdslParser.VariableContext) child1);
//        } else {
//            throw new IOException("发现未知节点" + child1.getSourceInterval());
//        }
//
//        if (build != null) {
//            builds.add(build);
//        }
//    }
//
//    /**
//     * 过程变量
//     */
//    private void processVariable(LinkedList<AbstractRunProcess.Build> builds, EsdslParser.VariableContext child1) {
//        AbstractRunProcess.Build last = builds.getLast();
//        if (last instanceof StringRunProcess.Build) {
//            builds.removeLast();
//            builds.getLast().addRunProcess(last.build());
//        }
//        last = builds.getLast();
//
//        VariableRunProcess.Build build = new VariableRunProcess.Build((EsdslParser.VariableContext) child1);
//        last.addRunProcess(build.build());
//    }
//
//
//    private static final Set SET = new HashSet();
//
//    /**
//     * json容错处理，处理中间多的","
//     * 主要在使用if的时候，有些“,”没法不写
//     * <p>
//     * 当 遇到 “{”或”[“  下一个就是 ","将会被去掉
//     * 当 遇到 "}"或”]“ 前一个是 ","将会被去掉
//     */
//    private String jsonFaultTolerant(String jsonString) {
//
//        //需要清除的序号
//        List<Integer> clearIndex = new LinkedList<>();
//
//        //当为  true 时候  下一个要是碰到”,“将会被去掉
//        boolean clear = false;
//        //当碰到 "}"或”]“ 这个位置的”,“将会被去掉
//        int tempClear = -1;
//
//        for (int i = 0; i < jsonString.length(); i++) {
//            char c = jsonString.charAt(i);
//
//            if (c == '{' || c == '[') {
//                clear = true;
//            } else if (c == '}' || c == ']') {
//                if (tempClear > 0) {
//                    clearIndex.add(tempClear);
//                }
//                clear = false;
//            } else if (c == '\t' || c == '\n' || c == '\r') {
//                continue;
//            } else if (c == '\\') {
//                i++;
//                clear = false;
//            } else if (c == ',') {
//                if (clear) {
//                    clearIndex.add(i);
//                } else {
//                    tempClear = i;
//                    continue;
//                }
//                clear = false;
//            } else {
//                clear = false;
//            }
//
//            tempClear = -1;
//        }
//
//
//        if (clearIndex.size() > 0) {
//
//            char[] chars = jsonString.toCharArray();
//
//            for (Integer index : clearIndex) {
//                chars[index] = ' ';
//            }
//            jsonString = new String(chars);
//
//        }
//
//        return jsonString;
//
//
//    }
//
//
//    /**
//     * 获取方法名称
//     */
//    public String getMethodName() {
//        return methodName;
//    }
//
//    /**
//     * 获取请求方法
//     */
//    public RequestMethod getRequestMethod() {
//        return requestMethod;
//    }
//
//    public static class Build {
//        public Esdsl build(ParseTree parseTree) throws IOException {
//            return new Esdsl(parseTree);
//        }
//    }
//}
