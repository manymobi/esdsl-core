package com.manymobi.esdsl.parser.run.process;

import java.util.Objects;

/**
 * @author 梁建军
 * 创建日期： 2021/4/15
 * 创建时间： 下午 9:51
 * @version 1.0
 * @since 1.0
 */
public enum AndOrXor {
    /**
     * 与
     */
    AND("&", "&&"),

    /**
     * 或
     */
    OR("|", "||"),

    /**
     * 异或
     */
    XOR("^");

    private final String[] symbols;

    AndOrXor(String... symbols) {

        this.symbols = symbols;
    }

    public static AndOrXor get(String symbols) {
        for (AndOrXor logicalSymbols : AndOrXor.values()) {
            for (String s : logicalSymbols.getSymbols()) {
                if (Objects.equals(symbols, s)) {
                    return logicalSymbols;
                }
            }
        }
        return null;
    }

    public String[] getSymbols() {
        return symbols;
    }
}
