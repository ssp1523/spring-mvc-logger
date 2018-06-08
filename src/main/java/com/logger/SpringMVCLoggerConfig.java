package com.logger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcRegistrationsAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * spring mvc logger 配置
 * @author: sunshaoping
 * @date: Create by in 下午2:17 2018/5/25
 */
@Configuration
@ConditionalOnWebApplication
public class SpringMVCLoggerConfig extends WebMvcConfigurerAdapter {

    /**
     * 请求日志
     */
    private SpringMVCRequestLogger springMVCRequestLogger;
    /**
     * 响应日志拦截
     */
    private SpringMVCResponseLogger springMVCResponseLogger;


    /**
     * 添加logger日记拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerHandlerInterceptor(springMVCRequestLogger, springMVCResponseLogger));
    }


    /**
     * request 缓存
     */
    @Bean
    @ConditionalOnMissingBean
    AbstractRequestLoggingFilter commonsRequestLoggingFilter() {
        return new CommonsRequestLoggingFilter() {
            @Override
            protected boolean shouldLog(HttpServletRequest request) {
                return false;
            }

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                BufferedRequestWrapper bufferedRequestWrapper = new BufferedRequestWrapper(request);
                super.doFilterInternal(bufferedRequestWrapper, response, filterChain);
            }

        };
    }

    /**
     * Response 缓存
     */
    @Bean
    @ConditionalOnMissingBean
    ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

    @Autowired(required = false)
    public void setSpringMVCRequestLogger(SpringMVCRequestLogger springMVCRequestLogger) {
        this.springMVCRequestLogger = springMVCRequestLogger;
    }

    @Autowired(required = false)
    public void setSpringMVCResponseLogger(SpringMVCResponseLogger springMVCResponseLogger) {
        this.springMVCResponseLogger = springMVCResponseLogger;
    }


    /**
     * 日志相关初始化
     */
    @Configuration
    public static class LoggerConfig {

        @Bean
        @ConditionalOnMissingBean
        public InfoLoggerFactory infoLoggerFactory() {
            return new InfoLoggerFactory();
        }

        @Bean
        @ConditionalOnMissingBean
        public NoLoggerFactory noLoggerFactory() {
            return new NoLoggerFactory();
        }

        @Bean
        @ConditionalOnMissingBean
        public DebugLoggerFactory debugLoggerFactory() {
            return new DebugLoggerFactory();
        }

        @Bean
        @ConditionalOnMissingBean
        public SpringMVCResponseLogger springMVCResponseLogger() {
            return new DefaultSpringMVCResponseLogger();
        }

        @Bean
        @ConditionalOnMissingBean
        public SpringMVCRequestLogger springMVCRequestLogger() {
            return new DefaultSpringMVCRequestLogger();
        }
    }


    @Configuration
    @Import(WebMvcAutoConfiguration.EnableWebMvcConfiguration.class)
    public static class WebMvcRegistrationConfig extends WebMvcRegistrationsAdapter implements ApplicationContextAware {

        private ApplicationContext applicationContext;

        /**
         * 重写createHandlerMethod方法
         */
        @Override
        public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
            return new RequestMappingHandlerMapping() {

                @Override
                protected HandlerMethod createHandlerMethod(Object handler, Method method) {
                    HandlerMethod handlerMethod = super.createHandlerMethod(handler, method);
                    SpringMVCLoggerInfo springMVCLoggerInfo = SpringMVCLoggerInfo.createSpringMVCLoggerInfo(handlerMethod, applicationContext);
                    if (springMVCLoggerInfo == null) {
                        return new NotInterceptorLoggerHandler(handlerMethod);
                    }
                    return new HandlerMethodLoggerHandler(springMVCLoggerInfo, handlerMethod);
                }
            };
        }


        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }
    }

}
