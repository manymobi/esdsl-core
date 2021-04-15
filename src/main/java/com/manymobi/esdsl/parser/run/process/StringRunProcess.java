package com.manymobi.esdsl.parser.run.process;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 14:45
 * @version 1.0
 * @since 1.0
 * 字符串运行处理
 */
public class StringRunProcess extends AbstractRunProcess {
    /**
     * 内容
     */
    private final String content;

    public StringRunProcess(List<RunProcess> child, String content) {
        super(child);
        this.content = content;
    }

    public StringRunProcess(String content) {
        super(Collections.emptyList());
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

        /**
         * 添加字符
         *
         * @param s 字符内容
         * @return 当前
         */
        public Build addString(String s) {
            stringBuilder.append(s);
            return this;
        }

        @Override
        public void addRunProcess(RunProcess runProcess) {
            throw new RuntimeException("不支持");
        }

        @Override
        public StringRunProcess build() {
            return new StringRunProcess(child, stringBuilder.toString());
        }
    }
}
