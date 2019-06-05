package com.manymobi.esdsl.parser.run.process;

import com.alibaba.fastjson.JSONObject;
import com.manymobi.esdsl.parser.EsdslParser;

import java.util.List;
import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 13:57
 * @version 1.0
 * @since 1.0
 * 变量
 */
public class VariableRunProcess extends AbstractRunProcess {

    private String variableName;
    /**
     * 转字符串
     * “#{” true
     * “${” false
     */
    private boolean turnString;

    protected VariableRunProcess(List<RunProcess> child, Build build) {
        super(child);
        variableName = build.variableName;
        turnString = build.turnString;
    }

    @Override
    public String runProcess(Map<String, Object> parameter) throws IllegalParameterException {
        if (turnString) {
            return JSONObject.toJSONString(getVariable(parameter,variableName));
        } else {
            return String.valueOf(getVariable(parameter,variableName));
        }
    }

    public static class Build extends AbstractRunProcess.Build<VariableRunProcess> {

        private String variableName;

        /**
         * 转字符串
         */
        private boolean turnString;

        public Build(EsdslParser.VariableContext child1) {

            variableName = child1.getChild(1).getText();

            turnString = "#{".equals(child1.getChild(0).getText());
        }

        @Override
        public VariableRunProcess build() {
            return new VariableRunProcess(child, this);
        }
    }
}
