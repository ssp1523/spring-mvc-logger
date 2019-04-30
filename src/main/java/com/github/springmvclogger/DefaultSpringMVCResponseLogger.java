package com.github.springmvclogger;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

/**
 *  spring mvc 响应默认实现
 * @author: sunshaoping
 * @date: Create by in 下午6:39 2018/5/24
 */
public class DefaultSpringMVCResponseLogger extends BaseSpringMVCLogger implements SpringMVCResponseLogger {


    @Override
    public void responseLogger(HttpServletRequest request, HttpServletResponse response, LoggerHandler loggerHandler) {

        Logger logger = createLogger(request, loggerHandler);
        Response res =
                Response.builder()
                        .charset(Charset.forName(response.getCharacterEncoding()))
                        .status(HttpStatus.valueOf(response.getStatus()))
                        .body(getResponseBody(response))
                        .headers(getResponseHeaders(response))
                        .build();
        long requestStartTime = (long) request.getAttribute(SpringMVCRequestLogger.REQUEST_START_TIME);
        long end = System.currentTimeMillis();

        logger.logResponse(loggerHandler.springMVCLoggerInfo().getConfigKey(), res, end - requestStartTime);

        applicationContext.publishEvent(new SpringMVCResponseLoggerEvent(this, request, response, loggerHandler));
    }
}
