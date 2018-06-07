package com.logger;

import lombok.Data;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * controller 方法日志配置信息
 * @author: sunshaoping
 * @date: Create by in 上午10:10 2018/6/7
 * @see SpringMVCLogger
 */
@Data
public class SpringMVCLoggerInfo {

    private String configKey;

    private Set<HttpMethod> methods;

    private int bodyMaxLength;

    private boolean request;

    private boolean response;

    private List<String> requestHeaders;

    private List<String> notRequestHeaders;

    private Class<? extends LoggerFactory> loggerFactory;

    /**
     * 创建实例
     * @return null 方法和注解上无{@link SpringMVCLogger} 注解
     */
    public static SpringMVCLoggerInfo createSpringMVCLoggerInfo(HandlerMethod handlerMethod) {
        SpringMVCLogger springMVCLogger = handlerMethod.getMethodAnnotation(SpringMVCLogger.class);
        boolean isClass = false;
        if (springMVCLogger == null) {
            Class<?> beanType = handlerMethod.getBeanType();
            springMVCLogger = AnnotationUtils.findAnnotation(beanType, SpringMVCLogger.class);
            isClass = true;
        }
        if (springMVCLogger == null) {
            return null;
        }
        SpringMVCLoggerInfo springMVCLoggerInfo = new SpringMVCLoggerInfo();
        String configKey = springMVCLogger.configKey();
        if (configKey.trim().length() > 0) {
            springMVCLoggerInfo.configKey = configKey;
        } else {
            springMVCLoggerInfo.configKey = handlerMethod.getBeanType().getSimpleName() + "#" + handlerMethod.getMethod().getName();
        }
        if (isClass) {
            springMVCLoggerInfo.methods = new HashSet<>(Arrays.asList(springMVCLogger.methods()));
        }
        springMVCLoggerInfo.bodyMaxLength = springMVCLogger.bodyMaxLength();
        springMVCLoggerInfo.request = springMVCLogger.request();
        springMVCLoggerInfo.response = springMVCLogger.response();
        springMVCLoggerInfo.requestHeaders = Arrays.asList(springMVCLogger.requestHeaders());
        springMVCLoggerInfo.notRequestHeaders = Arrays.asList(springMVCLogger.notRequestHeaders());
        springMVCLoggerInfo.loggerFactory = springMVCLogger.loggerFactory();
        return springMVCLoggerInfo;
    }
}
