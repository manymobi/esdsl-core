package com.manymobi.esdsl.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manymobi.esdsl.Esdsl;
import com.manymobi.esdsl.annotations.RequestMethod;
import com.manymobi.esdsl.handler.JsonHandler;
import com.manymobi.esdsl.handler.RestHandler;
import com.manymobi.esdsl.handler.impl.FastjsonJsonHandler;
import com.manymobi.esdsl.handler.impl.JacksonJsonHandler;
import com.manymobi.esdsl.handler.impl.PathEsdslFileResourceHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 上午 11:30
 * @version 1.0
 * @since 1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestMapper1Test {

    protected Esdsl esdsl;


    @BeforeAll
    public void init() {
        Esdsl.Build build = new Esdsl.Build();
        build.setEsdslFileResourceHandler(new PathEsdslFileResourceHandler(new File("src/test/resources/")));
        build.setRestHandler(new RestHandler() {
            @Override
            public Object handler(RequestMethod requestMethod, String url, String json, Type returnType, JsonHandler jsonHandler) {
                return jsonHandler.parseObject(json, returnType);
            }

            @Override
            public void close() throws Exception {

            }
        });
        build.setJsonHandler(new JacksonJsonHandler());
        esdsl = build.build();
    }


    @Test
    void search() {
        TestMapper target = esdsl.target(TestMapper.class);
        Object search = target.search("0000000000000");


        Object parse = JSON
                .parse("{\"highlight\":{\"pre_tags\":[\"<tag1>\",\"<tag2>\"],\"post_tags\":[\"</tag1>\",\"</tag2>\"],\"fields\":{\"content\":\"0000000000000\"}},\"query\":{}}");
        assertEquals(search, parse);
    }

    @Test
    void search0() {
        TestMapper target = esdsl.target(TestMapper.class);
        String s = "{\"highlight\":{\"pre_tags\":[\"<tag1>\",,,,\"<tag2>\"],\"post_tags\":[\"</tag1>\",\"</tag2>\"],\"fields\":{\"content\":{}}},\"query\":{}}";
        JSONObject search = target.search(s);
        String s1 = search.getJSONObject("highlight").getJSONObject("fields").getString("content");
        assertEquals(s, s1);
    }

    @Test
    public void search1() {
        TestMapper target = esdsl.target(TestMapper.class);
        Object search = target.search1("0000000000000", Arrays.asList("1", "2", "3"));

        assertEquals(search
                .toString(), "{\"highlight\":{\"pre_tags\":[\"1\",\"2\",\"3\"],\"post_tags\":[\"</tag1>\",\"</tag2>\"],\"fields\":{\"content\":{}}},\"query\":{\"match\":{\"id\":\"0000000000000\"}}}");
    }

    @Test
    public void search10() {
        TestMapper target = esdsl.target(TestMapper.class);
        Object search = target.search1("0000000000000", Arrays.asList(1, 2, 3));
        String s = search.toString();
        assertEquals("{\"highlight\":{\"pre_tags\":[1,2,3],\"post_tags\":[\"</tag1>\",\"</tag2>\"],\"fields\":{\"content\":{}}},\"query\":{\"match\":{\"id\":\"0000000000000\"}}}", s);
    }

    @Test
    public void search2() {
        TestMapper target = esdsl.target(TestMapper.class);
        Object search = target.search2("0000000000000", 2);
        assertEquals("{\"ssss\":\"0000000000000\",\"integer\":2}", search.toString());
    }

}
