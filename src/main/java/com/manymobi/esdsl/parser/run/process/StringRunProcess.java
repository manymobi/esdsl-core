package com.manymobi.esdsl.parser.run.process;

import java.util.List;
import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 14:45
 * @version 1.0
 * @since 1.0
 */
public class StringRunProcess extends AbstractRunProcess {

    private String content;

    protected StringRunProcess(List<RunProcess> child, String content) {
        super(child);
        this.content = content;
    }

    @Override
    public String runProcess(Map<String, Object> parameter) throws IllegalParameterException {
        return content;
    }

    public String getContent() {
        return content;
    }

    public static class Build extends AbstractRunProcess.Build<StringRunProcess> {

        StringBuilder stringBuilder = new StringBuilder();


        public void addString(String s) {
            stringBuilder.append(s);
        }

        @Override
        public void addRunProcess(RunProcess runProcess) {
            if (runProcess instanceof StringRunProcess) {
                addString(((StringRunProcess) runProcess).getContent());
                for (RunProcess process : ((StringRunProcess) runProcess).child) {
                    addRunProcess(process);
                }

            } else {
                super.addRunProcess(runProcess);
            }
        }

        @Override
        public StringRunProcess build() {
            return new StringRunProcess(child,stringBuilder.toString());
        }
    }
}
