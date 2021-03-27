package com.manymobi.esdsl.parser.run.process;


import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 13:51
 * @version 1.0
 * @since 1.0
 */
public interface RunProcess {
    /**
     * @param parameter 参数
     */
    String runProcess(Map<String, Object> parameter) throws IllegalParameterException;

}
