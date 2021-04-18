package com.manymobi.esdsl.parser.run.process;

import com.manymobi.esdsl.parser.AgentMap;
import com.manymobi.esdsl.parser.ParamMap;

import java.util.List;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 13:56
 * @version 1.0
 * @since 1.0
 * for
 */
public class ForRunProcess extends AbstractRunProcess {
    /**
     * 序号名称
     */
    private final String indexName;
    /**
     * item名称
     */
    private final String itemName;
    /**
     * 参数名称
     */
    private final String parameter;
    /**
     * 参数名称类型
     */
    private final VariableRunProcess.Type parameterType;
    /**
     * 开始字符
     */
    private final String open;
    /**
     * 开始字符类型
     */
    private final VariableRunProcess.Type openType;
    /**
     * 结束字符
     */
    private final String close;
    /**
     * 结束字符类型
     */
    private final VariableRunProcess.Type closeType;
    /**
     * 分割字符
     */
    private final String separator;
    /**
     * 分割字符类型
     */
    private final VariableRunProcess.Type separatorType;


    protected ForRunProcess(List<RunProcess> child, Build build) {
        super(child.toArray(new RunProcess[0]));
        indexName = build.indexName;
        itemName = build.itemName;
        parameter = build.parameter;
        parameterType = build.parameterType;
        open = build.open;
        openType = build.openType;
        close = build.close;
        closeType = build.closeType;
        separator = build.separator;
        separatorType = build.separatorType;
    }

    @Override
    public String runProcess(ParamMap<String, Object> parameter) throws IllegalParameterException {
        AgentMap<String, Object> agentMap = new AgentMap<>(parameter);

        StringBuilder stringBuilder = new StringBuilder();

        if (open != null) {
            stringBuilder.append(openType != null ? agentMap.getString(open, openType) : open);
        }

        Object o = agentMap.get(this.parameter);

        int i = 0;
        if (o instanceof Iterable) {
            Iterable<Object> collection = (Iterable) o;
            for (Object o1 : collection) {
                if (separator != null && i != 0) {
                    stringBuilder
                            .append(separatorType != null ? agentMap.getString(separator, separatorType) : separator);
                }
                if (indexName != null) {
                    agentMap.put(indexName, i);
                }
                agentMap.put(itemName, o1);
                process(agentMap, stringBuilder);
                i++;
            }
        } else {
            if (indexName != null) {
                agentMap.put(indexName, i);
            }
            agentMap.put(itemName, o);
            process(agentMap, stringBuilder);
        }

        if (close != null) {
            stringBuilder.append(closeType != null ? agentMap.getString(close, closeType) : close);
        }
        return stringBuilder.toString();
    }

    private void process(AgentMap<String, Object> agentMap, StringBuilder stringBuilder) {
        for (RunProcess runProcess : child) {
            String s = runProcess.runProcess(agentMap);
            if (s != null) {
                stringBuilder.append(s);
            }
        }
    }


    public static class Build extends AbstractRunProcess.Build<ForRunProcess> {

        private String indexName;
        private String itemName;
        private String parameter;
        private VariableRunProcess.Type parameterType;
        private String open;
        private VariableRunProcess.Type openType;
        private String close;
        private VariableRunProcess.Type closeType;
        private String separator;
        private VariableRunProcess.Type separatorType;

        public Build() {
        }

        public void setIndexName(String indexName) {
            this.indexName = indexName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public void setParameter(VariableRunProcess.Type parameterType, String parameter) {
            this.parameterType = parameterType;
            this.parameter = parameter;
        }

        public void setOpen(VariableRunProcess.Type openType, String open) {
            this.open = open;
            this.openType = openType;
        }

        public void setClose(VariableRunProcess.Type closeType, String close) {
            this.close = close;
            this.closeType = closeType;
        }

        public void setSeparator(VariableRunProcess.Type separatorType, String separator) {
            this.separator = separator;
            this.separatorType = separatorType;
        }


        @Override
        public ForRunProcess build() {
            return new ForRunProcess(child, this);
        }
    }
}
