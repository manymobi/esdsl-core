package com.manymobi.esdsl.parser.run.process;

import java.util.List;
import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 16:42
 * @version 1.0
 * @since 1.0
 */
public class EsdslRunProcess extends AbstractRunProcess {

    protected EsdslRunProcess(List<RunProcess> child) {
        super(child);
    }

    @Override
    public String runProcess(Map<String, Object> parameter) throws IllegalParameterException {

        StringBuilder stringBuilder = new StringBuilder();
        for (RunProcess runProcess : child) {
            String s = runProcess.runProcess(parameter);
            if (s!=null) {
                stringBuilder.append(s);
            }
        }

        return stringBuilder.toString();
    }

    public static class Build extends AbstractRunProcess.Build<EsdslRunProcess> {

        @Override
        public EsdslRunProcess build() {
            return new EsdslRunProcess(child);
        }
    }
}
