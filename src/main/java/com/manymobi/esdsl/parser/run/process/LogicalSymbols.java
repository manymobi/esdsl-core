package com.manymobi.esdsl.parser.run.process;

import java.util.Objects;

/**
 * @author 梁建军
 * 创建日期： 2021/4/15
 * 创建时间： 下午 9:50
 * @version 1.0
 * @since 1.0
 * 逻辑符号
 */
public enum LogicalSymbols {
    /**
     * 等于
     */
    EQUAL_TO("=="),
    /**
     * 大于
     */
    GREATER_THAN(">"),
    /**
     * 大于等于
     */
    GREATER_THAN_EQUAL_TO(">="),
    /**
     * 小于
     */
    LESS_THAN("<"),
    /**
     * 小于等于
     */
    LESS_THAN_EQUAL_TO("<="),
    /**
     * 不等于
     */
    NOT_EQUAL_TO("!=", "<>");
    /**
     * 符号
     */
    private final String[] symbols;

    LogicalSymbols(String... symbols) {

        this.symbols = symbols;
    }

    public String[] getSymbols() {
        return symbols;
    }

    public static LogicalSymbols get(String symbols) {
        for (LogicalSymbols logicalSymbols : LogicalSymbols.values()) {
            for (String s : logicalSymbols.getSymbols()) {
                if (Objects.equals(symbols, s)) {
                    return logicalSymbols;
                }
            }
        }
        throw new IllegalParameterException("不支持" + symbols);
    }
}
