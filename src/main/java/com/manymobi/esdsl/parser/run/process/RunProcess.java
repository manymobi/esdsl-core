package com.manymobi.esdsl.parser.run.process;


import com.manymobi.esdsl.parser.ParamMap;

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
     * @return 处理之后的 String
     */
    String runProcess(ParamMap<String, Object> parameter);

}
