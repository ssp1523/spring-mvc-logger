package com.logger;

import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * spring mvc 日志拦截器,打印debug日志级别，
 * @author: sunshaoping
 * @date: Create by in 上午10:52 2018/5/24
 */

public class LoggerHandlerInterceptor extends HandlerInterceptorAdapter {


    /**
     * 请求日志
     */
    private final SpringMVCRequestLogger springMVCRequestLogger;
    /**
     * 响应日志拦截
     */
    private final SpringMVCResponseLogger springMVCResponseLogger;

    public LoggerHandlerInterceptor(SpringMVCRequestLogger springMVCRequestLogger, SpringMVCResponseLogger springMVCResponseLogger) {
        this.springMVCRequestLogger = springMVCRequestLogger;
        this.springMVCResponseLogger = springMVCResponseLogger;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof LoggerHandler)) {
            return true;
        }
        LoggerHandler loggerHandler = (LoggerHandler) handler;

        //请求开始时间
        long requestStart = System.currentTimeMillis();
        request.setAttribute(SpringMVCRequestLogger.REQUEST_START_TIME, requestStart);

        if (springMVCResponseLogger != null
                && isInterceptor(request, loggerHandler)
                && loggerHandler.springMVCLoggerInfo().isRequest()) {
            //请求日志
            springMVCRequestLogger.requestLogger(request, loggerHandler);
        }
        return true;
    }

    private boolean isInterceptor(HttpServletRequest request, LoggerHandler loggerHandler) {
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        Set<HttpMethod> methods = loggerHandler.springMVCLoggerInfo().getMethods();
        if (CollectionUtils.isEmpty(methods)) {
            return loggerHandler.isInterceptor();
        }
        return loggerHandler.isInterceptor() && methods.contains(method);
    }

    /**
     * 请求完成
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        afterConcurrentHandlingStarted(request, response, handler);
    }

    /**
     * 并发请求
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof LoggerHandler)) {
            return;
        }
        LoggerHandler loggerHandler = (LoggerHandler) handler;
        if (springMVCResponseLogger != null
                && isInterceptor(request, loggerHandler)
                && loggerHandler.springMVCLoggerInfo().isResponse()) {
            //响应日志
            springMVCResponseLogger.responseLogger(request, response, loggerHandler);
        }

    }
}
