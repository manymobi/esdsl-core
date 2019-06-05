package com.manymobi.esdsl.parser;

import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * @author 梁建军
 * 创建日期： 2019/6/5
 * 创建时间： 14:28
 * @version 1.0
 * @since 1.0
 */
public class SEsdslBaseVisitor extends EsdslBaseVisitor {

    @Override
    public Object visitEsdslarray(EsdslParser.EsdslarrayContext ctx) {
        System.out.println("visitEsdslarray");
        for (EsdslParser.EsdslContext esdslContext : ctx.esdsl()) {
            visit(esdslContext);
        }
        return null;
    }

    @Override
    public Object visitEsdsl(EsdslParser.EsdslContext ctx) {
        System.out.println("visitEsdsl");
        String methodName = (String) visit(ctx.methodName());
        visit(ctx.request());
        visit(ctx.json());


        System.out.println("");
        System.out.println("=================================================");
        System.out.println("methodName=" + methodName);
        System.out.println("=================================================");
        System.out.println("");
        return null;
    }

    @Override
    public Object visitMethodName(EsdslParser.MethodNameContext ctx) {
        System.out.println("visitMethodName");
        return ctx.STRING1().getText();
    }

    @Override
    public Object visitRequest(EsdslParser.RequestContext ctx) {
        System.out.println("visitRequest");
        return null;
    }

    @Override
    public Object visitUri(EsdslParser.UriContext ctx) {
        System.out.println("visitUri");
        return null;
    }

    @Override
    public Object visitPath(EsdslParser.PathContext ctx) {
        System.out.println("visitPath");
        return null;
    }

    @Override
    public Object visitQuery(EsdslParser.QueryContext ctx) {
        System.out.println("visitQuery");
        return null;
    }

    @Override
    public Object visitSearch(EsdslParser.SearchContext ctx) {
        System.out.println("visitSearch");
        return null;
    }

    @Override
    public Object visitSearchparameter(EsdslParser.SearchparameterContext ctx) {
        System.out.println("visitSearchparameter");
        return null;
    }

    @Override
    public Object visitString(EsdslParser.StringContext ctx) {
        System.out.println("visitString");
        TerminalNode terminalNode = ctx.STRING1();
        if (terminalNode != null) {
            return terminalNode.getText();
        }
        EsdslParser.ParameterContext parameter = ctx.parameter();
        if (parameter != null) {
            return visit(parameter);
        }
        return null;
    }

    @Override
    public Object visitJson(EsdslParser.JsonContext ctx) {
        System.out.println("visitJson");
        return null;
    }

    @Override
    public Object visitObj(EsdslParser.ObjContext ctx) {
        System.out.println("visitObj");
        return null;
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
        System.out.println("visitValue");
        return null;
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
    public Object visitParameter(EsdslParser.ParameterContext ctx) {
        System.out.println("visitParameter");
        return null;
    }
}
