package com.logger;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

/**
 * feign spring mvc 实现
 * @author: sunshaoping
 * @date: Create by in 下午6:39 2018/5/24
 */
public class DefaultSpringMVCRequestLogger extends BaseSpringMVCLogger implements SpringMVCRequestLogger {


    @Override
    public void requestLogger(HttpServletRequest request, LoggerHandler loggerHandler) {

        SpringMVCLoggerInfo springMVCLoggerInfo = loggerHandler.springMVCLoggerInfo();

        byte[] body = getRequestBody(request, springMVCLoggerInfo.getBodyMaxLength());

        Request requestFeign =
                Request.builder()
                        .method(HttpMethod.resolve(request.getMethod()))
                        .url(getUri(request))
                        .headers(getRequestHeaders(request, springMVCLoggerInfo))
                        .body(body)
                        .charset(Charset.forName(request.getCharacterEncoding()))
                        .build();
        //打印日志 请求日志
        Logger logger = createLogger(request, loggerHandler);
        logger.logRequest(springMVCLoggerInfo.getConfigKey(), requestFeign);
        applicationContext.publishEvent(new SpringMVCRequestLoggerEvent(this, request, loggerHandler));
    }


}
