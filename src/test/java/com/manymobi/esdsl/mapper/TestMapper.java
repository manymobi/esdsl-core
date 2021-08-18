package com.manymobi.esdsl.mapper;

import com.alibaba.fastjson.JSONObject;
import com.manymobi.esdsl.annotations.*;

import java.util.List;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 上午 11:28
 * @version 1.0
 * @since 1.0
 */
@Mapper("esdsl/test.esdsl")
public interface TestMapper {

    JSONObject search(@Param("content") String content);

    JSONObject search1(@Param("sssss") String sssss, @Param("list") List list);

    @Query("{\"ssss\":#{sssss},\"integer\":#{integer}}")
    @RequestMapping(method = RequestMethod.GET, value = "/v")
    JSONObject search2(@Param("sssss") String sssss, @Param("integer") Integer integer);

    @RequestMapping(method = RequestMethod.GET, value = "/v")
    JSONObject search3(@RequestBody String sssss);

    @RequestMapping(method = RequestMethod.GET, value = "/v-${url}")
    JSONObject search4(@RequestBody String sssss,@Param("url") String url);

}
