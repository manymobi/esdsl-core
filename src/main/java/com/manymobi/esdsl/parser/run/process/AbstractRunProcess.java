package com.manymobi.esdsl.parser.run.process;


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
    protected final List<RunProcess> child;

    protected AbstractRunProcess(List<RunProcess> child) {
        this.child = child;
    }

    /**
     * 变量
     */
    protected static Object getVariable(Map<String, Object> parameter, String key) {

        // TODO: 梁建军 2021/4/12  这个方法要重写
        if (key == null) {
            return null;
        }

        String[] split = key.split("\\.");
        Object temp = parameter;
        for (String s : split) {
            if (temp instanceof Map) {
                temp = ((Map) temp).get(s);
            } else {
                //首字母大写
                String titleCase = s.substring(0, 1).toUpperCase() + s.substring(1);
                try {
                    Method method = temp.getClass().getMethod("get" + titleCase);
                    temp = method.invoke(temp);
                } catch (Exception e) {
                    throw new IllegalParameterException("获取参数失败,参数 " + parameter.toString() + " key " + key, e);
                }
            }
        }

        return temp;
    }

    public abstract static class Build<T extends AbstractRunProcess> {

        protected LinkedList<RunProcess> child = new LinkedList<>();

        public void addRunProcess(RunProcess runProcess) {
            child.add(runProcess);
        }

        public abstract T build();
    }

}
