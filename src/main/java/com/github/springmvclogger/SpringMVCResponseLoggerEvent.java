package com.github.springmvclogger;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring mvc 响应日志事件对象
 * @author: sunshaoping
 * @date: Create by in 下午4:11 2018/6/8
 * @see DefaultSpringMVCResponseLogger
 */

@Getter
public class SpringMVCResponseLoggerEvent extends ApplicationEvent {

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    /**
     * 日志处理
     */
    private final LoggerHandler loggerHandler;


    /**
     * Create a new ApplicationEvent.
     * @param source the object on which the event initially occurred (never {@code null})
     * @param response
     */
    public SpringMVCResponseLoggerEvent(SpringMVCResponseLogger source, HttpServletRequest request, HttpServletResponse response, LoggerHandler loggerHandler) {
        super(source);
        this.request = request;
        this.response = response;
        this.loggerHandler = loggerHandler;
    }

    @Override
    public SpringMVCResponseLogger getSource() {
        return (SpringMVCResponseLogger) super.getSource();
    }
}
