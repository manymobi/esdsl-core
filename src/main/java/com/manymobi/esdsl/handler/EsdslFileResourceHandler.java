package com.manymobi.esdsl.handler;

import java.io.InputStream;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 上午 1:02
 * @version 1.0
 * @since 1.0
 * 加载 esdsl文件
 */
public interface EsdslFileResourceHandler {

    InputStream load(String fileName);

}
