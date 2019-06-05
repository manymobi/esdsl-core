package com.manymobi.esdsl.parser.run.process;

import com.manymobi.esdsl.parser.AgentMap;
import com.manymobi.esdsl.parser.EsdslParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.math.BigDecimal;
import java.util.Collection;
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
    private String variableName;
    private String open;
    private Boolean openB;
    private String close;
    private Boolean closeB;
    private String separator;
    private Boolean separatorB;

    protected ForRunProcess(List<RunProcess> child, Build build) {
        super(child);
        indexName = build.indexName;
        itemName = build.itemName;
        variableName = build.variableName;
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

        Object o = getVariable(agentMap, variableName);

        int i = 0;
        if (o instanceof Collection) {

            Collection collection = (Collection) o;
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
        private String variableName;
        private String open;
        private Boolean openB;
        private String close;
        private Boolean closeB;
        private String separator;
        private Boolean separatorB;

        public Build(EsdslParser.ZforContext child1) {

            for (int i = 0; i < child1.getChildCount(); i++) {
                ParseTree child = child1.getChild(i);
                if (child instanceof EsdslParser.IndexContext) {
                    indexName = child.getText();
                } else if (child instanceof EsdslParser.ItemContext) {
                    itemName = child.getText();
                } else if (child instanceof EsdslParser.VariableContext) {
                    variableName = child.getChild(1).getText();
                } else if (child instanceof EsdslParser.ForParameterContext) {
                    for (int i1 = 0; i1 < child.getChildCount(); i1++) {
                        ParseTree child2 = child.getChild(i1);
                        switch (child2.getText()) {
                            case "open":
                                i1 += 2;
                                child2 = child.getChild(i1);
                                if (child2 instanceof EsdslParser.VariableContext) {
                                    open = child2.getChild(1).getText();
                                    openB = true;
                                } else {
                                    openB = false;
                                    open = child2.getText();
                                    open = open.substring(1, open.length() - 1);
                                }
                                break;
                            case "close":
                                i1 += 2;
                                child2 = child.getChild(i1);
                                if (child2 instanceof EsdslParser.VariableContext) {
                                    close = child2.getChild(1).getText();
                                    closeB = true;
                                } else {
                                    closeB = false;
                                    close = child2.getText();
                                    close = close.substring(1, close.length() - 1);
                                }
                                break;
                            case "separator":
                                i1 += 2;
                                child2 = child.getChild(i1);
                                if (child2 instanceof EsdslParser.VariableContext) {
                                    separator = child2.getChild(1).getText();
                                    separatorB = true;
                                } else {
                                    separatorB = false;
                                    separator = child2.getText();
                                    separator = separator.substring(1, separator.length() - 1);
                                }
                                break;
                        }
                    }
                }
            }
        }

        @Override
        public ForRunProcess build() {
            return new ForRunProcess(child, this);
        }
    }
}
