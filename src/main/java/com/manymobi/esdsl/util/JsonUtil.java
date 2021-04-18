package com.manymobi.esdsl.util;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 梁建军
 * 创建日期： 2021/4/17
 * 创建时间： 下午 8:11
 * @version 1.0
 * @since 1.0
 */
public class JsonUtil {

    /**
     * json容错处理，处理中间多的","
     * 主要在使用if的时候，有些“,”没法不写
     * <p>
     * 当 遇到 “{”或”[“  下一个就是 ","将会被去掉
     * 当 遇到 "}"或”]“ 前一个是 ","将会被去掉
     */
    public static String faultTolerant(String jsonString) {
        if (jsonString == null) {
            return null;
        }

        //需要清除的序号
        List<Integer> clearIndex = new LinkedList<>();

        //当为  true 时候  下一个要是碰到”,“将会被去掉
        boolean clear = false;
        //当碰到 "}"或”]“ 这个位置的”,“将会被去掉
        int tempClear = -1;
        //跳过
        boolean skip = false;
        //跳过的时候遇到撇
        boolean skimming = false;

        for (int i = 0; i < jsonString.length(); i++) {
            char c = jsonString.charAt(i);
            if (skip) {
                if (skimming) {
                    skimming = false;
                    continue;
                }
                if (c == '\\') {
                    skimming = true;
                }
                if (c == '"') {
                    skip = false;
                }
                continue;
            }
            if (c == '{' || c == '[') {
                clear = true;
            } else if (c == '}' || c == ']') {
                if (tempClear > 0) {
                    clearIndex.add(tempClear);
                }
                clear = false;
            } else if (c == '\t' || c == '\n' || c == '\r') {
                continue;
            } else if (c == '\\') {
                i++;
                clear = false;
            } else if (c == ',') {
                if (tempClear > 0) {
                    clearIndex.add(tempClear);
                }
                if (clear) {
                    clearIndex.add(i);
                } else {
                    tempClear = i;
                    continue;
                }
                clear = false;
            } else if (c == '"') {
                skip = true;
                clear = false;
            } else {
                clear = false;
            }

            tempClear = -1;
        }


        if (clearIndex.size() > 0) {

            char[] chars = jsonString.toCharArray();

            for (Integer index : clearIndex) {
                chars[index] = ' ';
            }
            jsonString = new String(chars);

        }

        return jsonString;


    }
}
