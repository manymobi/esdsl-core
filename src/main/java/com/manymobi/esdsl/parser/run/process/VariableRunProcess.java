package com.manymobi.esdsl.parser.run.process;


import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 13:57
 * @version 1.0
 * @since 1.0
 * 变量
 */
public class VariableRunProcess extends AbstractRunProcess {

    /**
     * 变量名称
     */
    private final String variableName;
    /**
     * 变量类型
     */
    private final Type type;

    protected VariableRunProcess(List<RunProcess> child, Build build) {
        super(child.toArray(new RunProcess[0]));
        variableName = build.variableName;
        type = build.type;
    }

    @Override
    public String runProcess(Map<String, Object> parameter) throws IllegalParameterException {
        switch (type) {

            case TRANSFORMATION:
                // TODO: 梁建军 2021/4/12  这里要换成可替代的
                return JSONObject.toJSONString(getVariable(parameter, variableName));
            case DIRECT:
                return String.valueOf(getVariable(parameter, variableName));
            case GRADLE:
                throw new RuntimeException("未实现");
            default:
                throw new RuntimeException("不该运行到这里");
        }
    }

    public static class Build extends AbstractRunProcess.Build<VariableRunProcess> {

        /**
         * 变量名称
         */
        private final String variableName;

        /**
         * 转字符串
         */
        private final Type type;

        public Build(Type type, String variableName) {
            this.type = type;
            this.variableName = variableName;
        }

        @Override
        public VariableRunProcess build() {
            return new VariableRunProcess(child, this);
        }
    }

    /**
     * 变量类型
     */
    public enum Type {
        /**
         * 转换
         */
        TRANSFORMATION("#"),

        /**
         * 直接
         */
        DIRECT("$"),
        /**
         * gradle脚本
         */
        GRADLE("@");


        private final String keyword;

        /**
         * 关键字
         */
        Type(String keyword) {
            this.keyword = keyword;
        }

        public static Type get(String keyword) {
            for (Type value : Type.values()) {
                if (Objects.equals(keyword, value.keyword)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Type 中没有 " + keyword);
        }

        public String getKeyword() {
            return keyword;
        }
    }
}
