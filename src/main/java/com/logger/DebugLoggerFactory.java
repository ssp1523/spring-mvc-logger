package com.logger;

/**
 * debug 级别日志工厂
 * @author: sunshaoping
 * @date: Create by in 下午7:01 2018/5/24
 */
public class DebugLoggerFactory implements LoggerFactory {

    @Override
    public Logger create(Class<?> type) {
        return new DebugLogger(type);
    }

    public static class DebugLogger extends Logger {

        private final org.slf4j.Logger logger;


        public DebugLogger(Class<?> clazz) {
            this(org.slf4j.LoggerFactory.getLogger(clazz));
        }

        public DebugLogger(org.slf4j.Logger logger) {
            this.logger = logger;
        }


        @Override
        protected boolean shouldLog() {
            return logger.isDebugEnabled();
        }

        @Override
        protected void log(String configKey, String format, Object... args) {
            logger.debug("[{}]{}", configKey, String.format(format, args));
        }

    }

}
