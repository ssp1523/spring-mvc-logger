package com.github.springmvclogger;

import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpMethod;

import java.lang.annotation.*;

/**
 * spring mvc 日志注解
 * 配置在方法上比类上优先级高
 * 必须开启日志级别: INFO
 * @author: sunshaoping
 * @date: Create by in 下午6:52 2018/6/1
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SpringMVCLogger {
    /**
     * {@link #configKey()}
     */
    @AliasFor("configKey")
    String value() default "";

    /**
     * 日志配置key,默认类名#方法
     */
    @AliasFor("value")
    String configKey() default "";

    /**
     * 指定请求方式 打印日志，只针对配置在类{@link ElementType#TYPE}上,默认全部
     * @see HttpMethod
     */
    HttpMethod[] methods() default {};

    /**
     * body 体打印最大长度
     * @return 默认值 512
     */
    int bodyMaxLength() default 512;

    /**
     * 是否打印请求日志，默认打印
     * @return 默认true
     */
    boolean request() default true;

    /**
     * 是否打印响应日志，默认不打印
     * @return 默认值 false
     */
    boolean response() default false;

    /**
     * 打印请求头信息
     * 优先级大于 {@link #notRequestHeaders}
     * @return 默认打印全部
     */
    String[] requestHeaders() default {};

    /**
     * 不打印的请求头信息
     */
    String[] notRequestHeaders() default {};

    /**
     * 日志工厂类，日志级别
     * @see InfoLoggerFactory
     */
    Class<? extends LoggerFactory> loggerFactory() default InfoLoggerFactory.class;


}


