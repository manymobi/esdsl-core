package com.manymobi.esdsl.mapper;

import com.alibaba.fastjson.JSON;
import com.manymobi.esdsl.Esdsl;
import com.manymobi.esdsl.annotations.RequestMethod;
import com.manymobi.esdsl.handler.JsonEncoder;
import com.manymobi.esdsl.handler.RestHandler;
import com.manymobi.esdsl.handler.impl.JacksonJsonEncoder;
import com.manymobi.esdsl.handler.impl.PathEsdslFileResourceHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 上午 11:30
 * @version 1.0
 * @since 1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Test2MapperTest {

    protected Esdsl esdsl;


    @BeforeAll
    public void init() {
        Esdsl.Build build = new Esdsl.Build();
        build.setEsdslFileResourceHandler(new PathEsdslFileResourceHandler(new File("src/test/resources/")));
        build.setRestHandler(new RestHandler() {
            @Override
            public Object handler(RequestMethod requestMethod, String url, String json, Type returnType, JsonEncoder jsonEncoder) {
                return jsonEncoder.parseObject(json, returnType);
            }

            @Override
            public void close() throws Exception {

            }
        });
//        build.setJsonHandler(new FastjsonJsonHandler());
        build.setJsonHandler(new JacksonJsonEncoder());
        esdsl = build.build();
    }


    @Test
    void search() {
        Test2Mapper target = esdsl.target(Test2Mapper.class);
        Test2Mapper.Bean bean = new Test2Mapper.Bean();
        bean.setContent("content");
        bean.setKey("rwerwe4");
        Object search = target.search(bean);


        Object parse = JSON
                .parse("{\"highlight\":{\"pre_tags\":[\"<tag1>\",\"<tag2>\"],\"post_tags\":[\"</tag1>\",\"</tag2>\"],\"fields\":{\"content\":\"content\"}},\"query\":{}}");
        assertEquals(search, parse);
    }

    @Test
    void search_0() {
        Test2Mapper target = esdsl.target(Test2Mapper.class);
        Test2Mapper.Bean bean = new Test2Mapper.Bean();
        bean.setContent("content");
        bean.setKey("rwerwe");
        Object search = target.search(bean);


        Object parse = JSON
                .parse("{\"highlight\":{\"pre_tags\":[\"<tag1>\",\"<tag2>\"],\"post_tags\":[\"</tag1>\",\"</tag2>\"],\"fields\":{\"content\":\"content\"}},\"query\":{\"match\":{\"id\":123133131223123212313131}}}");
        assertEquals(search, parse);
    }

    @Test
    public void search1() {
        Test2Mapper target = esdsl.target(Test2Mapper.class);
        Object search = target.search1("{\"ssss\":\"0000000000000\",\"integer\":2}", "2");
        assertEquals(search.toString(), "{\"query\":{\"ssss\":\"0000000000000\",\"integer\":2}}");
    }

    @Test
    public void search2() {
        Test2Mapper target = esdsl.target(Test2Mapper.class);
        Object search = target.search2("{\"ssss\":\"0000000000000\",\"integer\":2}", "2");
        assertEquals(search.toString(), "{\"query\":\"{\\\"ssss\\\":\\\"0000000000000\\\",\\\"integer\\\":2}\"}");
    }

    @Test
    void search3() {
        Test2Mapper target = esdsl.target(Test2Mapper.class);
        Test2Mapper.Bean bean = new Test2Mapper.Bean();
        bean.setContent("content");
        bean.setKey("rwerwe4");
        Object search = target.search3(bean,"1");


        Object parse = JSON
                .parse("{\"highlight\":{\"pre_tags\":[\"<tag1>\",\"<tag2>\"],\"post_tags\":[\"</tag1>\",\"</tag2>\"],\"fields\":{\"content\":\"content\"}},\"query\":{\"match\":{\"id\":\"rwerwe4\"}}}");
        assertEquals(search, parse);
    }

    @Test
    void search3_() {
        Test2Mapper target = esdsl.target(Test2Mapper.class);
        Test2Mapper.Bean bean = new Test2Mapper.Bean();
        bean.setContent("content");
        bean.setKey(null);
        Object search = target.search3(bean,"1");


        Object parse = JSON
                .parse("{\"highlight\":{\"pre_tags\":[\"<tag1>\",\"<tag2>\"],\"post_tags\":[\"</tag1>\",\"</tag2>\"],\"fields\":{\"content\":\"content\"}},\"query\":{}}");
        assertEquals(search, parse);
    }


}
