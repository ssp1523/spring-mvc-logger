package com.logger;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 基本实现
 * @author: sunshaoping
 * @date: Create by in 下午6:40 2018/5/24
 */
public abstract class BaseSpringMVCLogger implements ApplicationContextAware {

    protected static final String SPRING_MVC_LOGGER = "spring_mvc_logger_key";

    protected ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected HttpHeaders getRequestHeaders(HttpServletRequest request, SpringMVCLoggerInfo springMVCLoggerInfo) {
        HttpHeaders headers = getHttpHeaders(request);
        List<String> headerList = springMVCLoggerInfo.getRequestHeaders();
        List<String> notHeaders = springMVCLoggerInfo.getNotRequestHeaders();
        //指定头信息
        if (!CollectionUtils.isEmpty(headerList)) {
            headers = getHttpHeaders(headerList, headers);
        } else if (!CollectionUtils.isEmpty(notHeaders)) {
            //排除不用的头信息
            HttpHeaders headers1 = new HttpHeaders();
            headers1.putAll(headers);
            for (String headerName : notHeaders) {
                headers1.remove(headerName);
            }
            headers = headers1;
        }

        return headers;
    }

    private HttpHeaders getHttpHeaders(HttpServletRequest request) {
        ServletServerHttpRequest nativeRequest = WebUtils.getNativeRequest(request, ServletServerHttpRequest.class);
        if (nativeRequest == null) {
            nativeRequest = new ServletServerHttpRequest(request);
        }
        return nativeRequest.getHeaders();
    }

    private HttpHeaders getHttpHeaders(List<String> headerList, HttpHeaders headers) {
        HttpHeaders result = new HttpHeaders();
        for (String headerName : headerList) {
            result.put(headerName, headers.get(headerName));
        }
        return result;
    }

    protected byte[] getRequestBody(HttpServletRequest request, int maxLength) {
        BufferedRequestWrapper wrapper =
                WebUtils.getNativeRequest(request, BufferedRequestWrapper.class);
        if (wrapper != null) {
            byte[] byteArray = wrapper.toByteArray();
            if (byteArray.length > maxLength) {
                return Arrays.copyOf(byteArray, maxLength);
            }
            return byteArray;
        }
        return new byte[0];
    }


    protected String getUri(HttpServletRequest request) {
        StringBuilder msg = new StringBuilder();
        String host = request.getHeader(HttpHeaders.HOST);
        if (!StringUtils.isEmpty(host)) {
            msg.append("http://");
            msg.append(host).append("/");
        }
        msg.append(request.getRequestURI());
        String queryString = request.getQueryString();
        if (queryString != null) {
            msg.append('?').append(queryString);
        }
        return msg.toString();
    }

    @SuppressWarnings("unchecked")
    protected HttpHeaders getResponseHeaders(HttpServletResponse response) {
        ServletServerHttpResponse nativeResponse = WebUtils.getNativeResponse(response, ServletServerHttpResponse.class);
        if (nativeResponse == null) {
            nativeResponse = new ServletServerHttpResponse(response);
        }
        return nativeResponse.getHeaders();
    }

    protected byte[] getResponseBody(HttpServletResponse response) {
        ContentCachingResponseWrapper cachingResponseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (cachingResponseWrapper != null) {
            return cachingResponseWrapper.getContentAsByteArray();
        }
        return new byte[0];
    }

    protected Logger createLogger(HttpServletRequest request, LoggerHandler loggerHandler) {
        Class<?> clazz = loggerHandler.handlerMethod().getBeanType();
        String attrKey = SPRING_MVC_LOGGER + clazz.getName();
        Logger logger = (Logger) request.getAttribute(attrKey);
        if (logger == null) {
            LoggerFactory loggerFactory = loggerHandler.springMVCLoggerInfo().getLoggerFactory();
            logger = loggerFactory.create(clazz);
            request.setAttribute(attrKey, logger);
        }
        return logger;

    }
}
