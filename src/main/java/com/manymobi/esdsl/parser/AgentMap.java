package com.manymobi.esdsl.parser;

import com.manymobi.esdsl.handler.JsonHandler;
import com.manymobi.esdsl.handler.VariableHandler;
import com.manymobi.esdsl.parser.run.process.VariableRunProcess;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 15:06
 * @version 1.0
 * @since 1.0
 * 代理子类，对这个类数据进行修改，不影响原始的
 */
public class AgentMap<K, V> extends ParamMapImpl<K, V> implements ParamMap<K, V> {

    private final ParamMap<K, V> kvMap;


    public AgentMap(ParamMap<K, V> kvMap) {
        super(kvMap.getJsonHandler(), kvMap.getVariableHandler());
        this.kvMap = kvMap;
    }

    @Override
    public V get(Object key) {
        V v = super.get(key);
        if (v != null) {
            return v;
        }

        return kvMap.get(key);
    }
}
