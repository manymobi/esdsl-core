package com.manymobi.esdsl.parser.run.process;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 梁建军
 * 创建日期： 2021/4/11
 * 创建时间： 下午3:37
 * @version 1.0
 * @since 1.0
 * 多个顺序处理
 */
public class ListRunProcess extends AbstractRunProcess {

    protected ListRunProcess(List<RunProcess> child) {
        super(child);
    }

    @Override
    public String runProcess(Map<String, Object> parameter) throws IllegalParameterException {
        return child.stream()
                .map(runProcess -> runProcess.runProcess(parameter))
                .collect(Collectors.joining());
    }


    public static class Build extends AbstractRunProcess.Build<ListRunProcess> {


        @Override
        public void addRunProcess(RunProcess runProcess) {
            if (runProcess == null) {
                return;
            }
            // 当两个 StringRunProcess相遇合并成一个
            if (!child.isEmpty() && runProcess instanceof StringRunProcess) {
                RunProcess last = child.getLast();
                if (last instanceof StringRunProcess) {
                    StringRunProcess.Build build = new StringRunProcess.Build();
                    build.addString( ((StringRunProcess) last).getContent());
                    build.addString(((StringRunProcess) runProcess).getContent());
                    runProcess = build.build();
                }
            }
            super.addRunProcess(runProcess);
        }

        @Override
        public ListRunProcess build() {
            return new ListRunProcess(child);
        }
    }
}

