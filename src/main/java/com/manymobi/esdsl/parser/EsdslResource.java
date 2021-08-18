package com.manymobi.esdsl.parser;

import com.manymobi.esdsl.antlr4.EsdslLexer;
import com.manymobi.esdsl.antlr4.EsdslParser;
import com.manymobi.esdsl.handler.EsdslFileResourceHandler;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 14:06
 * @version 1.0
 * @since 1.0
 */
public class EsdslResource {
    private final EsdslVisitor esdslVisitor = new EsdslVisitor();

    private final ConcurrentMap<String, EsdslBean> esdslMap = new ConcurrentHashMap<>();

    private EsdslFileResourceHandler esdslFileResourceHandler;

    public EsdslResource(EsdslFileResourceHandler esdslFileResourceHandler) {
        this.esdslFileResourceHandler = esdslFileResourceHandler;
    }

    public void load(String fileName, InputStream inputStream) {
        EsdslParser esdslParser = null;
        try {
            EsdslLexer esdslLexer = new EsdslLexer(CharStreams
                    .fromStream(inputStream));
            CommonTokenStream commonTokenStream = new CommonTokenStream(esdslLexer, Token.DEFAULT_CHANNEL);
            esdslParser = new EsdslParser(commonTokenStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
        esdslParser.setErrorHandler(new BailErrorStrategy());

        List<EsdslBean> visit = (List<EsdslBean>) esdslVisitor.visit(esdslParser.esdslarray());


        for (EsdslBean child : visit) {
            esdslMap.put(fileName + "#" + child.getMethodName(), child);
        }
    }

    /**
     * @param fileName   文件名
     * @param methodName 方法名称
     * @return bean
     */
    public EsdslBean get(String fileName, String methodName) {
        String key = fileName + "#" + methodName;
        EsdslBean esdslBean = esdslMap.get(key);
        if (esdslBean != null) {
            return esdslBean;
        }
        load(fileName, esdslFileResourceHandler.load(fileName));
        return esdslMap.get(key);
    }

    /**
     * 生成
     *
     * @param esdsl dsl
     * @return bean
     */
    public EsdslBean generate(String esdsl) {

        EsdslParser esdslParser = null;
        try {
            esdslParser = new EsdslParser(new CommonTokenStream(new EsdslLexer(CharStreams
                    .fromReader(new StringReader("==> temp\n" + esdsl)))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        esdslParser.setErrorHandler(new BailErrorStrategy());

        List<EsdslBean> visit = (List<EsdslBean>) esdslVisitor.visit(esdslParser.esdslarray());


        if (visit.size() != 1) {
            throw new RuntimeException("只允许有一个方法");
        }

        return visit.get(0);
    }

}
