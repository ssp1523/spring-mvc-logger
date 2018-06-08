package com.logger;

import org.springframework.web.method.HandlerMethod;

/**
 * 不拦截
 * @author: sunshaoping
 * @date: Create by in 上午10:28 2018/6/7
 */
public class NotInterceptorLoggerHandler extends HandlerMethod implements LoggerHandler {

    public NotInterceptorLoggerHandler(HandlerMethod handlerMethod) {
        super(handlerMethod);
    }

    @Override
    public HandlerMethod handlerMethod() {
        return null;
    }

    @Override
    public SpringMVCLoggerInfo springMVCLoggerInfo() {
        return null;
    }

    @Override
    public boolean isInterceptor() {
        return false;
    }

    @Override
    public HandlerMethod createWithResolvedBean() {
        HandlerMethod handlerMethod = super.createWithResolvedBean();
        return new NotInterceptorLoggerHandler(handlerMethod);
    }
}
