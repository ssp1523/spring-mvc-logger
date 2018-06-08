package com.github.springmvclogger;

/**
 * spring mvc logger 异常
 * @author: sunshaoping
 * @date: Create by in 下午4:38 2018/6/8
 */
public class SpringMVCLoggerException extends RuntimeException {
    public SpringMVCLoggerException(String message) {
        super(message);
    }

    public SpringMVCLoggerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpringMVCLoggerException(Throwable cause) {
        super(cause);
    }
}
