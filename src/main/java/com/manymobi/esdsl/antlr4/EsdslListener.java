// Generated from D:/java/esdsl-core/src/main/java/com/manymobi/esdsl/antlr4\Esdsl.g4 by ANTLR 4.7.2
package com.manymobi.esdsl.antlr4;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link EsdslParser}.
 */
public interface EsdslListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link EsdslParser#esdslarray}.
	 * @param ctx the parse tree
	 */
	void enterEsdslarray(EsdslParser.EsdslarrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#esdslarray}.
	 * @param ctx the parse tree
	 */
	void exitEsdslarray(EsdslParser.EsdslarrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#esdsl}.
	 * @param ctx the parse tree
	 */
	void enterEsdsl(EsdslParser.EsdslContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#esdsl}.
	 * @param ctx the parse tree
	 */
	void exitEsdsl(EsdslParser.EsdslContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#methodName}.
	 * @param ctx the parse tree
	 */
	void enterMethodName(EsdslParser.MethodNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#methodName}.
	 * @param ctx the parse tree
	 */
	void exitMethodName(EsdslParser.MethodNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#request}.
	 * @param ctx the parse tree
	 */
	void enterRequest(EsdslParser.RequestContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#request}.
	 * @param ctx the parse tree
	 */
	void exitRequest(EsdslParser.RequestContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#uri}.
	 * @param ctx the parse tree
	 */
	void enterUri(EsdslParser.UriContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#uri}.
	 * @param ctx the parse tree
	 */
	void exitUri(EsdslParser.UriContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#path}.
	 * @param ctx the parse tree
	 */
	void enterPath(EsdslParser.PathContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#path}.
	 * @param ctx the parse tree
	 */
	void exitPath(EsdslParser.PathContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(EsdslParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(EsdslParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#search}.
	 * @param ctx the parse tree
	 */
	void enterSearch(EsdslParser.SearchContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#search}.
	 * @param ctx the parse tree
	 */
	void exitSearch(EsdslParser.SearchContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#searchparameter}.
	 * @param ctx the parse tree
	 */
	void enterSearchparameter(EsdslParser.SearchparameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#searchparameter}.
	 * @param ctx the parse tree
	 */
	void exitSearchparameter(EsdslParser.SearchparameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(EsdslParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(EsdslParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#json}.
	 * @param ctx the parse tree
	 */
	void enterJson(EsdslParser.JsonContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#json}.
	 * @param ctx the parse tree
	 */
	void exitJson(EsdslParser.JsonContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#obj}.
	 * @param ctx the parse tree
	 */
	void enterObj(EsdslParser.ObjContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#obj}.
	 * @param ctx the parse tree
	 */
	void exitObj(EsdslParser.ObjContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#pair}.
	 * @param ctx the parse tree
	 */
	void enterPair(EsdslParser.PairContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#pair}.
	 * @param ctx the parse tree
	 */
	void exitPair(EsdslParser.PairContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(EsdslParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(EsdslParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(EsdslParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(EsdslParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#ifThenStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfThenStatement(EsdslParser.IfThenStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#ifThenStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfThenStatement(EsdslParser.IfThenStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(EsdslParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(EsdslParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#forParameter}.
	 * @param ctx the parse tree
	 */
	void enterForParameter(EsdslParser.ForParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#forParameter}.
	 * @param ctx the parse tree
	 */
	void exitForParameter(EsdslParser.ForParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#symbolTring}.
	 * @param ctx the parse tree
	 */
	void enterSymbolTring(EsdslParser.SymbolTringContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#symbolTring}.
	 * @param ctx the parse tree
	 */
	void exitSymbolTring(EsdslParser.SymbolTringContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(EsdslParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(EsdslParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(EsdslParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(EsdslParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#singleIfcondition}.
	 * @param ctx the parse tree
	 */
	void enterSingleIfcondition(EsdslParser.SingleIfconditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#singleIfcondition}.
	 * @param ctx the parse tree
	 */
	void exitSingleIfcondition(EsdslParser.SingleIfconditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#logicCharacter}.
	 * @param ctx the parse tree
	 */
	void enterLogicCharacter(EsdslParser.LogicCharacterContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#logicCharacter}.
	 * @param ctx the parse tree
	 */
	void exitLogicCharacter(EsdslParser.LogicCharacterContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#wrong}.
	 * @param ctx the parse tree
	 */
	void enterWrong(EsdslParser.WrongContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#wrong}.
	 * @param ctx the parse tree
	 */
	void exitWrong(EsdslParser.WrongContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#index}.
	 * @param ctx the parse tree
	 */
	void enterIndex(EsdslParser.IndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#index}.
	 * @param ctx the parse tree
	 */
	void exitIndex(EsdslParser.IndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#item}.
	 * @param ctx the parse tree
	 */
	void enterItem(EsdslParser.ItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#item}.
	 * @param ctx the parse tree
	 */
	void exitItem(EsdslParser.ItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link EsdslParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(EsdslParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link EsdslParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(EsdslParser.ParameterContext ctx);
}