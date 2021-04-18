package com.manymobi.esdsl.handler.impl;

import com.manymobi.esdsl.handler.EsdslFileResourceHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 上午 1:02
 * @version 1.0
 * @since 1.0
 */
public class PathEsdslFileResourceHandler implements EsdslFileResourceHandler {

    private final File path;

    public PathEsdslFileResourceHandler(File path) {
        this.path = path;
    }

    @Override
    public InputStream load(String fileName) {
        File file = new File(path, fileName);
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
