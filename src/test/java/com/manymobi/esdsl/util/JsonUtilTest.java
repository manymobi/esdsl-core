package com.manymobi.esdsl.util;


import com.manymobi.esdsl.util.JsonUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author 梁建军
 * 创建日期： 2021/4/17
 * 创建时间： 下午 9:45
 * @version 1.0
 * @since 1.0
 */
public class JsonUtilTest {

    @Test
    public void testFaultTolerant() {
        String s = "{\"query\":{},,\"highlight\":{\"pre_tags\":[\"<tag1>\",\"<tag2>\",],,\"post_tags\":[\"</tag1>\",\"</tag2>\",],,\"fields\":{\"content\":\"{\\\"highlight\\\":{\\\"pre_tags\\\":[\\\"<tag1>\\\",,,,\\\"<tag2>\\\"],\\\"post_tags\\\":[\\\"</tag1>\\\",\\\"</tag2>\\\"],\\\"fields\\\":{\\\"content\\\":{}}},\\\"query\\\":{}}\",,},,},,}";

        String s1 = JsonUtil.faultTolerant(s);
        assertEquals(s1, "{\"query\":{} ,\"highlight\":{\"pre_tags\":[\"<tag1>\",\"<tag2>\" ] ,\"post_tags\":[\"</tag1>\",\"</tag2>\" ] ,\"fields\":{\"content\":\"{\\\"highlight\\\":{\\\"pre_tags\\\":[\\\"<tag1>\\\",,,,\\\"<tag2>\\\"],\\\"post_tags\\\":[\\\"</tag1>\\\",\\\"</tag2>\\\"],\\\"fields\\\":{\\\"content\\\":{}}},\\\"query\\\":{}}\"  }  }  }");
    }
}