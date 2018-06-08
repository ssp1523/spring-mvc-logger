package com.github.springmvclogger;

import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpMethod;

import java.lang.annotation.*;

/**
 * 不打印日志注解,测试扩展实现
 * @author: sunshaoping
 * @date: Create by in 下午4:52 2018/6/8
 * @see SpringMVCLogger
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringMVCLogger(loggerFactory = NoLoggerFactory.class,response = true)
public @interface NotLogger {

    /**
     * Alias for {@link SpringMVCLogger#value}.
     */
    @AliasFor(annotation = SpringMVCLogger.class)
    String value() default "";

    /**
     * Alias for {@link SpringMVCLogger#configKey}.
     */
    @AliasFor(annotation = SpringMVCLogger.class)
    String configKey() default "";

    /**
     * Alias for {@link SpringMVCLogger#methods}.
     */
    @AliasFor(annotation = SpringMVCLogger.class)
    HttpMethod[] methods() default {};

    /**
     * Alias for {@link SpringMVCLogger#bodyMaxLength}.
     */
    @AliasFor(annotation = SpringMVCLogger.class)
    int bodyMaxLength() default 512;

    /**
     * Alias for {@link SpringMVCLogger#request}.
     */
    @AliasFor(annotation = SpringMVCLogger.class)
    boolean request() default true;

    /**
     * Alias for {@link SpringMVCLogger#requestHeaders}.
     */
    @AliasFor(annotation = SpringMVCLogger.class)
    String[] requestHeaders() default {};

    /**
     * Alias for {@link SpringMVCLogger#notRequestHeaders}.
     */
    @AliasFor(annotation = SpringMVCLogger.class)
    String[] notRequestHeaders() default {};

}
