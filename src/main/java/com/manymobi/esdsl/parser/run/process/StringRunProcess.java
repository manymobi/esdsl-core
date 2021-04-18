package com.manymobi.esdsl.parser.run.process;

import com.manymobi.esdsl.parser.ParamMap;

import java.util.List;

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
        super(child.toArray(new RunProcess[0]));
        this.content = content;
    }

    public StringRunProcess(String content) {
        super(new RunProcess[0]);
        this.content = content;
    }

    @Override
    public String runProcess(ParamMap<String, Object> parameter) throws IllegalParameterException {
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
