package com.manymobi.esdsl.parser.run.process;

import com.manymobi.esdsl.parser.AgentMap;

import java.util.List;
import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 13:56
 * @version 1.0
 * @since 1.0
 * for
 */
public class ForRunProcess extends AbstractRunProcess {

    private String indexName;
    private String itemName;
    private String parameter;
    private String open;
    private Boolean openB;
    private String close;
    private Boolean closeB;
    private String separator;
    private Boolean separatorB;


    protected ForRunProcess(List<RunProcess> child, Build build) {
        super(child.toArray(new RunProcess[0]));
        indexName = build.indexName;
        itemName = build.itemName;
        parameter = build.parameter;
        open = build.open;
        openB = build.openB;
        close = build.close;
        closeB = build.closeB;
        separator = build.separator;
        separatorB = build.separatorB;
    }

    @Override
    public String runProcess(Map<String, Object> parameter) throws IllegalParameterException {
        AgentMap<String, Object> agentMap = new AgentMap<>(parameter);

        StringBuilder stringBuilder = new StringBuilder();

        if (open != null) {
            stringBuilder.append(openB ? getVariable(parameter, open) : open);
        }

        Object o = getVariable(agentMap, this.parameter);

        int i = 0;
        if (o instanceof Iterable) {

            Iterable<Object> collection = (Iterable) o;
            for (Object o1 : collection) {
                if (separator != null && i != 0) {
                    stringBuilder.append(separatorB ? getVariable(parameter, separator) : separator);
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
            stringBuilder.append(closeB ? getVariable(parameter, close) : close);
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
        private VariableRunProcess.Type type;
        private String open;
        private Boolean openB;
        private String close;
        private Boolean closeB;
        private String separator;
        private Boolean separatorB;

        public Build() {
        }

        public void setIndexName(String indexName) {
            this.indexName = indexName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public void setParameter(VariableRunProcess.Type type,String parameter) {
            this.type = type;
            this.parameter = parameter;
        }

        public void setOpen(Boolean openB, String open) {
            this.open = open;
            this.openB = openB;
        }

        public void setOpenB() {
        }

        public void setClose(Boolean closeB, String close) {
            this.close = close;
            this.closeB = closeB;
        }

        public void setSeparator(Boolean separatorB, String separator) {
            this.separator = separator;
            this.separatorB = separatorB;
        }


        @Override
        public ForRunProcess build() {
            return new ForRunProcess(child, this);
        }
    }
}
