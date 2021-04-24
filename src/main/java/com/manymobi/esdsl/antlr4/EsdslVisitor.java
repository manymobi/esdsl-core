// Generated from /home/ljj/IdeaProjects/manymobi/esdsl-core/src/main/java/com/manymobi/esdsl/antlr4/Esdsl.g4 by ANTLR 4.9.2
package com.manymobi.esdsl.antlr4;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link EsdslParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface EsdslVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link EsdslParser#esdslarray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEsdslarray(EsdslParser.EsdslarrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#esdsl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEsdsl(EsdslParser.EsdslContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#methodName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodName(EsdslParser.MethodNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#request}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRequest(EsdslParser.RequestContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#uri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUri(EsdslParser.UriContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#path}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPath(EsdslParser.PathContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(EsdslParser.QueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#search}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSearch(EsdslParser.SearchContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#searchparameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSearchparameter(EsdslParser.SearchparameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(EsdslParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#json}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJson(EsdslParser.JsonContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#obj}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObj(EsdslParser.ObjContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#pair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair(EsdslParser.PairContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(EsdslParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(EsdslParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#ifThenStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenStatement(EsdslParser.IfThenStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(EsdslParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#forParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForParameter(EsdslParser.ForParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#symbolTring}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbolTring(EsdslParser.SymbolTringContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(EsdslParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(EsdslParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#singleIfcondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleIfcondition(EsdslParser.SingleIfconditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#logicCharacter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicCharacter(EsdslParser.LogicCharacterContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#wrong}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWrong(EsdslParser.WrongContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#index}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndex(EsdslParser.IndexContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#item}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitItem(EsdslParser.ItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link EsdslParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(EsdslParser.ParameterContext ctx);
}