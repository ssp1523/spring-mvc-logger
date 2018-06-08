package com.logger.listener;

import com.logger.SpringMVCRequestLoggerEvent;
import com.logger.SpringMVCResponseLoggerEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 日记监听
 * @author: sunshaoping
 * @date: Create by in 下午4:22 2018/6/8
 */
@Component
public class LoggerListener {


    @EventListener(SpringMVCResponseLoggerEvent.class)
    public void responseListener(SpringMVCResponseLoggerEvent loggerEvent) {
        System.out.println("响应日志监听:" + loggerEvent.getLoggerHandler().handlerMethod());
    }

    @EventListener(SpringMVCRequestLoggerEvent.class)
    public void requestListener(SpringMVCRequestLoggerEvent loggerEvent) {
        System.out.println("请求日志监听:" + loggerEvent.getLoggerHandler().handlerMethod());
    }

}
