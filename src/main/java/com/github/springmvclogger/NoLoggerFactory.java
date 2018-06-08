package com.github.springmvclogger;

/**
 * 不打印日志工厂
 * @author: sunshaoping
 * @date: Create by in 下午7:01 2018/5/24
 */
public class NoLoggerFactory implements LoggerFactory {

    @Override
    public Logger create(Class<?> type) {
        return new Logger() {
            @Override
            protected boolean shouldLog() {
                return false;
            }

            @Override
            protected void log(String configKey, String format, Object... args) {

            }
        };
    }


}
