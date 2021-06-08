package com.manymobi.esdsl.mapper;

import com.alibaba.fastjson.JSONObject;
import com.manymobi.esdsl.annotations.Mapper;
import com.manymobi.esdsl.annotations.Param;

import java.util.Optional;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 上午 11:28
 * @version 1.0
 * @since 1.0
 */
@Mapper("esdsl/optional_test.esdsl")
public interface OptionalTestMapper {

    Optional<JSONObject> search1(@Param("content") String content, @Param("fulltext") String fulltext);
}
