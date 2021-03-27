package com.manymobi.esdsl;

import com.manymobi.esdsl.antlr4.EsdslLexer;
import com.manymobi.esdsl.antlr4.EsdslParser;
import com.manymobi.esdsl.parser.EsdslVisitor;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.UnbufferedTokenStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author 梁建军
 * 创建日期： 2019/6/5
 * 创建时间： 14:32
 * @version 1.0
 * @since 1.0
 */
public class Demo {

    public static void main(String[] args) throws IOException, InterruptedException {

        FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/esdsl/test.esdsl"));
        EsdslParser esdslParser = new EsdslParser(new UnbufferedTokenStream(new EsdslLexer(CharStreams.fromStream(fileInputStream))));
        esdslParser.setErrorHandler(new BailErrorStrategy());

        EsdslVisitor esdslBaseVisitor = new EsdslVisitor();
        Object visit = esdslBaseVisitor.visit(esdslParser.esdslarray());


        System.out.println(visit);


    }

}
