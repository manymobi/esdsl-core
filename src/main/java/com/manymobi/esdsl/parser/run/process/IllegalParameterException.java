package com.manymobi.esdsl.parser.run.process;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 13:52
 * @version 1.0
 * @since 1.0
 */
public class IllegalParameterException extends RuntimeException {

    public IllegalParameterException() {
    }

    public IllegalParameterException(String message) {
        super(message);
    }

    public IllegalParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalParameterException(Throwable cause) {
        super(cause);
    }

    public IllegalParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
