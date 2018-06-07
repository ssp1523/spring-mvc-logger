package com.logger;

import javax.servlet.http.HttpServletRequest;

/**
 * spring mvc 日志
 * @author: sunshaoping
 * @date: Create by in 下午6:36 2018/5/24
 */
public interface SpringMVCRequestLogger {


    /**
     * 请求开始时间key {@link HttpServletRequest}
     */
    String REQUEST_START_TIME = "request_start_time";

    void requestLogger(HttpServletRequest request, LoggerHandler loggerHandler);

}
