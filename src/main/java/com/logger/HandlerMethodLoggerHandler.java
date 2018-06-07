package com.logger;

import org.springframework.web.method.HandlerMethod;

/**
 * 日志处理类
 * @author: sunshaoping
 * @date: Create by in 上午10:28 2018/6/7
 */

public class HandlerMethodLoggerHandler extends HandlerMethod implements LoggerHandler {


    private final SpringMVCLoggerInfo springMVCLoggerInfo;

    public HandlerMethodLoggerHandler(SpringMVCLoggerInfo springMVCLoggerInfo, HandlerMethod handlerMethod) {
        super(handlerMethod);
        this.springMVCLoggerInfo = springMVCLoggerInfo;
    }

    @Override
    public HandlerMethod handlerMethod() {
        return this;
    }

    @Override
    public SpringMVCLoggerInfo springMVCLoggerInfo() {
        return springMVCLoggerInfo;
    }

    @Override
    public boolean isInterceptor() {
        return springMVCLoggerInfo != null && (springMVCLoggerInfo.isRequest() || springMVCLoggerInfo.isResponse());
    }

    @Override
    public HandlerMethod createWithResolvedBean() {
        HandlerMethod handlerMethod = super.createWithResolvedBean();
        return new HandlerMethodLoggerHandler(this.springMVCLoggerInfo, handlerMethod);
    }
}
