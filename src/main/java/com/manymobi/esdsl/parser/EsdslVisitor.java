package com.manymobi.esdsl.parser;

import com.manymobi.esdsl.annotations.RequestMethod;
import com.manymobi.esdsl.antlr4.EsdslParser;
import com.manymobi.esdsl.parser.run.process.AndOrXor;
import com.manymobi.esdsl.parser.run.process.ExpressionRunProcess;
import com.manymobi.esdsl.parser.run.process.ForRunProcess;
import com.manymobi.esdsl.parser.run.process.IfRunProcess;
import com.manymobi.esdsl.parser.run.process.ListRunProcess;
import com.manymobi.esdsl.parser.run.process.RunProcess;
import com.manymobi.esdsl.parser.run.process.StringRunProcess;
import com.manymobi.esdsl.parser.run.process.VariableRunProcess;
import com.manymobi.esdsl.antlr4.EsdslParserBaseVisitor;
import com.manymobi.esdsl.util.Optional;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 梁建军
 * 创建日期： 2019/6/5
 * 创建时间： 14:28
 * @version 1.0
 * @since 1.0
 */
public class EsdslVisitor extends EsdslParserBaseVisitor {

    @Override
    public Object visitEsdslarray(EsdslParser.EsdslarrayContext ctx) {
        return ctx.esdsl()
                .stream()
                .map(esdslContext -> (EsdslBean) visit(esdslContext))
                .collect(Collectors.toList());
    }

    @Override
    public Object visitEsdsl(EsdslParser.EsdslContext ctx) {

        String methodName = ctx.ID().getText();
        RequestBean requestBean = (RequestBean) visit(ctx.request());
        RunProcess json = (RunProcess) Optional.ofNullable(ctx.json()).map(this::visit).orElse(null);
        return new EsdslBean(methodName, requestBean.requestMethod, requestBean.url, json);
    }


    @Override
    public Object visitRequest(EsdslParser.RequestContext ctx) {
        RequestMethod requestMethod = RequestMethod.valueOf(ctx
                .REQUEST_METHOD()
                .getText());

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
        TerminalNode terminalNode = ctx.ID();
        if (terminalNode != null) {
            return new StringRunProcess.Build()
                    .addString(terminalNode.getText())
                    .build();
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
        ListRunProcess.Build build = new ListRunProcess.Build();
        build.addRunProcess(
                new StringRunProcess.Build()
                        .addString(ctx.STRING().getText())
                        .addString(":")
                        .build()
        );
        build.addRunProcess((RunProcess) visit(ctx.statement()));
        return build.build();
    }

    @Override
    public Object visitArray(EsdslParser.ArrayContext ctx) {
        ListRunProcess.Build build = new ListRunProcess.Build();
        for (ParseTree child : ctx.children) {
            build.addRunProcess((RunProcess) visit(child));
        }
        return build.build();
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
        IfRunProcess.Build build = new IfRunProcess.Build();
        List<EsdslParser.ExpressionContext> expression = ctx.expression();
        List<EsdslParser.StatementContext> statement = ctx.statement();
        int i = 0;
        for (; i < expression.size(); i++) {
            IfRunProcess.If.Build build1 = new IfRunProcess.If.Build();
            build1.setExpression((ExpressionRunProcess) visit(expression.get(i)));
            build1.setStatement((RunProcess) visit(statement.get(i)));

            build.addIfOrElseIf(build1.build());
        }

        if (statement.size() > i) {
            build.setElse((RunProcess) visit(statement.get(i)));
        }
        return build.build();
    }

    @Override
    public Object visitForStatement(EsdslParser.ForStatementContext ctx) {
        ForRunProcess.Build build = new ForRunProcess.Build();

        build.setIndexName(Optional.ofNullable(ctx.index())
                .map(this::visit)
                .map(o -> (String) o)
                .orElse(null));
        build.setItemName((String) visit(ctx.item()));
        String parameter = ctx.parameter().getText();
        build.setParameter(VariableRunProcess.Type.get(parameter.substring(0, 1)),
                parameter.substring(2, parameter.length() - 1));

        build.addRunProcess((RunProcess) visit(ctx.statement()));

        for (EsdslParser.ForParameterContext forParameterContext : ctx.forParameter()) {

            switch (forParameterContext.children.get(0).getText()) {
                case "open":
                    build.setOpen(null, (String) visit(forParameterContext.children.get(2)));
                    break;
                case "close":
                    build.setClose(null, (String) visit(forParameterContext.children.get(2)));
                    break;
                case "separator":
                    build.setSeparator(null, (String) visit(forParameterContext.children.get(2)));
                    break;
                default:
                    throw new RuntimeException("不该走到这个里");
            }
        }
        return build.build();
    }

    @Override
    public Object visitForParameter(EsdslParser.ForParameterContext ctx) {
        //空的，没错
        return null;
    }

    @Override
    public Object visitSymbolTring(EsdslParser.SymbolTringContext ctx) {
        String text = ctx.getText();
        return text.substring(1, text.length() - 1);
    }

    @Override
    public Object visitStatement(EsdslParser.StatementContext ctx) {
        return Optional
                .<ParseTree>ofNullable(ctx.value())
                .or(() -> Optional.ofNullable(ctx.forStatement()))
                .or(() -> Optional.ofNullable(ctx.ifThenStatement()))
                .or(() -> Optional.ofNullable(ctx.forStatement()))
                .map(this::visit)
                .map(o -> (RunProcess) o)
                .map(runProcess -> {
                    ListRunProcess.Build listRunProcess = new ListRunProcess.Build();
                    listRunProcess.addRunProcess(runProcess);
                    if (!(runProcess instanceof ForRunProcess || runProcess instanceof IfRunProcess)) {
                        listRunProcess.addRunProcess(new StringRunProcess(","));
                    }
                    return listRunProcess.build();
                })
                .orElseGet(() -> {
                    ListRunProcess.Build listRunProcess = new ListRunProcess.Build();
                    ctx.pair().stream()
                            .map(this::visit)
                            .map(o -> (RunProcess) o)
                            .forEach(runProcess -> {
                                listRunProcess.addRunProcess(runProcess);
                                listRunProcess.addRunProcess(new StringRunProcess(","));
                            });
                    return listRunProcess.build();
                });
    }

    @Override
    public Object visitExpression(EsdslParser.ExpressionContext ctx) {
        ExpressionRunProcess.Build build = new ExpressionRunProcess.Build();
        List<EsdslParser.SingleIfconditionContext> singleIfconditionContexts = ctx.singleIfcondition();

        for (EsdslParser.SingleIfconditionContext context : singleIfconditionContexts) {
            ExpressionRunProcess.SingleIfCondition o = (ExpressionRunProcess.SingleIfCondition) visit(context);
            build.addIfCondition(o);
        }

        List<TerminalNode> terminalNodes = ctx.AND_OR_XOR();
        for (TerminalNode terminalNode : terminalNodes) {
            build.addAndOrXor(AndOrXor.get(terminalNode.getText()));
        }
        return build.build();
    }

    @Override
    public Object visitSingleIfcondition(EsdslParser.SingleIfconditionContext ctx) {

        return new ExpressionRunProcess.SingleIfCondition(
                ctx.wrong() != null,
                (RunProcess) visit(ctx.value(0)),
                (String) visit(ctx.logicCharacter()),
                (RunProcess) visit(ctx.value(1))
        );
    }

    @Override
    public Object visitLogicCharacter(EsdslParser.LogicCharacterContext ctx) {
        return ctx.getText();
    }

    @Override
    public Object visitWrong(EsdslParser.WrongContext ctx) {
        //空的，没错
        return null;
    }

    @Override
    public Object visitIndex(EsdslParser.IndexContext ctx) {
        return ctx.getText();
    }

    @Override
    public Object visitItem(EsdslParser.ItemContext ctx) {
        return ctx.getText();
    }

    @Override
    public Object visitTerminal(TerminalNode node) {
        return new StringRunProcess(node.getText());
    }

    @Override
    public Object visitParameter(EsdslParser.ParameterContext ctx) {
        String text = ctx
                .PARAMETER()
                .getText();
        return new VariableRunProcess.Build(VariableRunProcess.Type.get(text.substring(0, 1)),
                text.substring(2, text.length() - 1))
                .build();
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
