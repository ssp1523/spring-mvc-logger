package com.logger;

import org.springframework.web.method.HandlerMethod;

/**
 * 日志处理
 * @author: sunshaoping
 * @date: Create by in 上午10:06 2018/6/7
 */
public interface LoggerHandler {

    /**
     * HandlerMethod spring 方法处理对象
     */
    HandlerMethod handlerMethod();

    /**
     * spring mvc 请求方法配置信息
     * @see SpringMVCLogger
     */
    SpringMVCLoggerInfo springMVCLoggerInfo();

    /**
     * 是否拦截
     * true ：拦截
     * false：不拦截
     */
    boolean isInterceptor();


}
