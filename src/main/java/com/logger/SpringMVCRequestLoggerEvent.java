package com.logger;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;

/**
 * spring mvc 请求日志事件对象
 * @author: sunshaoping
 * @date: Create by in 下午4:11 2018/6/8
 * @see DefaultSpringMVCRequestLogger
 */

@Getter
public class SpringMVCRequestLoggerEvent extends ApplicationEvent {

    private final HttpServletRequest request;

    /**
     * 日志处理
     */
    private final LoggerHandler loggerHandler;


    /**
     * Create a new ApplicationEvent.
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public SpringMVCRequestLoggerEvent(SpringMVCRequestLogger source, HttpServletRequest request, LoggerHandler loggerHandler) {
        super(source);
        this.request = request;
        this.loggerHandler = loggerHandler;
    }

    @Override
    public SpringMVCRequestLogger getSource() {
        return (SpringMVCRequestLogger) super.getSource();
    }
}
