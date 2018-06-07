package com.logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring mvc 日志,多个实现可以使用排序
 * @author: sunshaoping
 * @date: Create by in 下午6:36 2018/5/24
 * @see org.springframework.core.annotation.Order
 * @see org.springframework.core.Ordered
 */
public interface SpringMVCResponseLogger {

    void responseLogger(HttpServletRequest request, HttpServletResponse response, LoggerHandler loggerHandler);
}
