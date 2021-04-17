package com.manymobi.esdsl.util;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author 梁建军
 * 创建日期： 2021/4/17
 * 创建时间： 下午 9:45
 * @version 1.0
 * @since 1.0
 */
public class JsonUtilTest {

    @Test
    public void testFaultTolerant() throws JsonProcessingException {
        String s = "{\"query\":{},,\"highlight\":{\"pre_tags\":[\"<tag1>\",\"<tag2>\",],,\"post_tags\":[\"</tag1>\",\"</tag2>\",],,\"fields\":{\"content\":\"{\\\"highlight\\\":{\\\"pre_tags\\\":[\\\"<tag1>\\\",,,,\\\"<tag2>\\\"],\\\"post_tags\\\":[\\\"</tag1>\\\",\\\"</tag2>\\\"],\\\"fields\\\":{\\\"content\\\":{}}},\\\"query\\\":{}}\",,},,},,}";
        ObjectMapper objectMapper = new ObjectMapper();

        String s1 = JsonUtil.faultTolerant(s);
        Assert.assertEquals(s1,"{\"query\":{} ,\"highlight\":{\"pre_tags\":[\"<tag1>\",\"<tag2>\" ] ,\"post_tags\":[\"</tag1>\",\"</tag2>\" ] ,\"fields\":{\"content\":\"{\\\"highlight\\\":{\\\"pre_tags\\\":[\\\"<tag1>\\\",,,,\\\"<tag2>\\\"],\\\"post_tags\\\":[\\\"</tag1>\\\",\\\"</tag2>\\\"],\\\"fields\\\":{\\\"content\\\":{}}},\\\"query\\\":{}}\"  }  }  }");
    }
}