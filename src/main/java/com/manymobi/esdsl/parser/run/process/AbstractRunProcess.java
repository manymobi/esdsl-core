package com.manymobi.esdsl.parser.run.process;


import com.manymobi.esdsl.parser.ParamMap;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 14:45
 * @version 1.0
 * @since 1.0
 * 抽象处理
 */
public abstract class AbstractRunProcess implements RunProcess {
    /**
     * 处理器列表
     */
    protected final RunProcess[] child;

    protected AbstractRunProcess(RunProcess[] child) {
        this.child = child;
    }


    public abstract static class Build<T extends AbstractRunProcess> {

        protected LinkedList<RunProcess> child = new LinkedList<>();

        public void addRunProcess(RunProcess runProcess) {
            child.add(runProcess);
        }

        public abstract T build();
    }

}
