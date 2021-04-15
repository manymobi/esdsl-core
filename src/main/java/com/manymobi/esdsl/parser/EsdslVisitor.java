package com.manymobi.esdsl.parser;

import com.manymobi.esdsl.RequestMethod;
import com.manymobi.esdsl.antlr4.EsdslBaseVisitor;
import com.manymobi.esdsl.antlr4.EsdslParser;
import com.manymobi.esdsl.parser.run.process.ListRunProcess;
import com.manymobi.esdsl.parser.run.process.RunProcess;
import com.manymobi.esdsl.parser.run.process.StringRunProcess;
import com.manymobi.esdsl.parser.run.process.VariableRunProcess;
import com.manymobi.esdsl.util.Optional;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.stream.Collectors;

/**
 * @author 梁建军
 * 创建日期： 2019/6/5
 * 创建时间： 14:28
 * @version 1.0
 * @since 1.0
 */
public class EsdslVisitor extends EsdslBaseVisitor {

    @Override
    public Object visitEsdslarray(EsdslParser.EsdslarrayContext ctx) {
        return ctx.esdsl()
                .stream()
                .map(esdslContext -> (EsdslBean) visit(esdslContext))
                .collect(Collectors.toList());
    }

    @Override
    public Object visitEsdsl(EsdslParser.EsdslContext ctx) {
        String methodName = (String) visit(ctx.methodName());
        RequestBean requestBean = (RequestBean) visit(ctx.request());
        RunProcess json = (RunProcess) visit(ctx.json());

        System.out.println("");
        System.out.println("=================================================");
        System.out.println("methodName=" + methodName);
        System.out.println("=================================================");
        System.out.println("");
        return new EsdslBean(methodName, requestBean.requestMethod, requestBean.url, json);
    }

    @Override
    public Object visitMethodName(EsdslParser.MethodNameContext ctx) {
        return ctx.STRING1().getText();
    }

    @Override
    public Object visitRequest(EsdslParser.RequestContext ctx) {
        RequestMethod requestMethod = RequestMethod.valueOf(ctx.REQUEST_METHOD().getText());

        RunProcess url = (RunProcess) visit(ctx.uri());

        return new RequestBean(requestMethod, url);
    }

    @Override
    public Object visitUri(EsdslParser.UriContext ctx) {
        ListRunProcess.Build build = new ListRunProcess.Build();

        Optional.ofNullable(ctx.path())
                .map(this::visit)
                .map(o -> (RunProcess) o)
                .ifPresent(runProcess -> {
                    build.addRunProcess(new StringRunProcess("/"));
                    build.addRunProcess(runProcess);
                });

        Optional.ofNullable(ctx.query())
                .map(this::visit)
                .map(o -> (RunProcess) o)
                .ifPresent(build::addRunProcess);

        return build.build();
    }

    @Override
    public Object visitPath(EsdslParser.PathContext ctx) {
        ListRunProcess.Build build = new ListRunProcess.Build();
        final boolean[] start = {true};
        ctx.string()
                .stream()
                .map(this::visit)
                .map(o -> (RunProcess) o)
                .forEach(runProcess -> {
                    if (start[0]) {
                        start[0] = false;
                    } else {
                        build.addRunProcess(new StringRunProcess("/"));
                    }
                    build.addRunProcess(runProcess);
                });
        return build.build();
    }

    @Override
    public Object visitQuery(EsdslParser.QueryContext ctx) {
        ListRunProcess.Build build = new ListRunProcess.Build();
        build.addRunProcess(new StringRunProcess("?"));
        build.addRunProcess((RunProcess) visit(ctx.search()));
        return build.build();
    }

    @Override
    public Object visitSearch(EsdslParser.SearchContext ctx) {
        ListRunProcess.Build build = new ListRunProcess.Build();
        final boolean[] start = {true};
        ctx.searchparameter()
                .stream()
                .map(this::visit)
                .map(o -> (RunProcess) o)
                .forEach(runProcess -> {
                    if (start[0]) {
                        start[0] = false;
                    } else {
                        build.addRunProcess(new StringRunProcess("&"));
                    }
                    build.addRunProcess(runProcess);
                });
        return build.build();
    }

    @Override
    public Object visitSearchparameter(EsdslParser.SearchparameterContext ctx) {
        ListRunProcess.Build build = new ListRunProcess.Build();

        EsdslParser.StringContext key = ctx.string(0);
        build.addRunProcess((RunProcess) visit(key));
        EsdslParser.StringContext value = ctx.string(1);
        Optional.<ParseTree>ofNullable(value)
                .or(() -> Optional.ofNullable(ctx.NUMBER()))
                .map(this::visit)
                .map(o -> (RunProcess) o)
                .ifPresent(runProcess -> {
                    build.addRunProcess(new StringRunProcess("="));
                    build.addRunProcess(runProcess);
                });
        return build.build();
    }

    @Override
    public Object visitString(EsdslParser.StringContext ctx) {
        TerminalNode terminalNode = ctx.STRING1();
        if (terminalNode != null) {
            return new StringRunProcess.Build().addString(terminalNode.getText()).build();
        }
        EsdslParser.ParameterContext parameter = ctx.parameter();
        return visit(parameter);
    }

    @Override
    public Object visitJson(EsdslParser.JsonContext ctx) {
        return visit(ctx.value());
    }

    @Override
    public Object visitObj(EsdslParser.ObjContext ctx) {
        ListRunProcess.Build listRunProcess = new ListRunProcess.Build();
        listRunProcess.addRunProcess(new StringRunProcess("{"));
        Optional.ofNullable(ctx.statement())
                .ifPresent(statementContexts -> statementContexts
                        .stream()
                        .map(this::visit)
                        .map(o -> (RunProcess) o)
                        .forEach(listRunProcess::addRunProcess)
                );

        listRunProcess.addRunProcess(new StringRunProcess("}"));
        return listRunProcess.build();
    }

    @Override
    public Object visitPair(EsdslParser.PairContext ctx) {
        System.out.println("visitPair");
        return null;
    }

    @Override
    public Object visitArray(EsdslParser.ArrayContext ctx) {
        System.out.println("visitArray");
        return null;
    }

    @Override
    public Object visitValue(EsdslParser.ValueContext ctx) {
//        value
//        : STRING
//                | parameter
//                | NUMBER
//                | obj
//                | array
//                | 'true'
//                | 'false'
//                | 'null'
//        ;
        return Optional.<ParseTree>ofNullable(ctx.STRING())
                .or(() -> Optional.ofNullable(ctx.parameter()))
                .or(() -> Optional.ofNullable(ctx.NUMBER()))
                .or(() -> Optional.ofNullable(ctx.obj()))
                .or(() -> Optional.ofNullable(ctx.array()))
                .map(this::visit)
                .orElseGet(() -> new StringRunProcess.Build()
                        .addString(ctx.getText())
                        .build());
    }

    @Override
    public Object visitIfThenStatement(EsdslParser.IfThenStatementContext ctx) {
        System.out.println("visitIfThenStatement");
        return null;
    }

    @Override
    public Object visitForStatement(EsdslParser.ForStatementContext ctx) {
        System.out.println("visitForStatement");
        return null;
    }

    @Override
    public Object visitForParameter(EsdslParser.ForParameterContext ctx) {
        System.out.println("visitForParameter");
        return null;
    }

    @Override
    public Object visitSymbolTring(EsdslParser.SymbolTringContext ctx) {
        System.out.println("visitSymbolTring");
        return null;
    }

    @Override
    public Object visitStatement(EsdslParser.StatementContext ctx) {
        System.out.println("visitStatement");
        return null;
    }

    @Override
    public Object visitExpression(EsdslParser.ExpressionContext ctx) {
        System.out.println("visitExpression");
        return null;
    }

    @Override
    public Object visitSingleIfcondition(EsdslParser.SingleIfconditionContext ctx) {
        System.out.println("visitSingleIfcondition");
        return null;
    }

    @Override
    public Object visitLogicCharacter(EsdslParser.LogicCharacterContext ctx) {
        System.out.println("visitLogicCharacter");
        return null;
    }

    @Override
    public Object visitWrong(EsdslParser.WrongContext ctx) {
        System.out.println("visitWrong");
        return null;
    }

    @Override
    public Object visitIndex(EsdslParser.IndexContext ctx) {
        System.out.println("visitIndex");
        return null;
    }

    @Override
    public Object visitItem(EsdslParser.ItemContext ctx) {
        System.out.println("visitItem");
        return null;
    }

    @Override
    public Object visitTerminal(TerminalNode node) {
        return new StringRunProcess(node.getText());
    }

    @Override
    public Object visitParameter(EsdslParser.ParameterContext ctx) {
        String text = ctx.PARAMETER().getText();
        return new VariableRunProcess.Build(VariableRunProcess.Type.get(text.substring(0, 1)),
                text.substring(2, text.length() - 1))
                .build();
    }

    private static class EsdslBean {
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

        public EsdslBean(String methodName, RequestMethod requestMethod, RunProcess url, RunProcess json) {
            this.methodName = methodName;
            this.requestMethod = requestMethod;
            this.url = url;
            this.json = json;
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
    }

    private static class RequestBean {

        private final RequestMethod requestMethod;

        private final RunProcess url;

        public RequestBean(RequestMethod requestMethod, RunProcess url) {
            this.requestMethod = requestMethod;
            this.url = url;
        }
    }
}
