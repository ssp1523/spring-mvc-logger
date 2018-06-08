package com.github.springmvclogger;

/**
 * info 级别日志工厂
 * @author: sunshaoping
 * @date: Create by in 下午7:01 2018/5/24
 */
public class InfoLoggerFactory implements LoggerFactory {

    @Override
    public InfoLogger create(Class<?> type) {
        return new InfoLogger(type);
    }

    public static class InfoLogger extends Logger {


        private final org.slf4j.Logger logger;


        public InfoLogger(Class<?> clazz) {
            this(org.slf4j.LoggerFactory.getLogger(clazz));
        }

        public InfoLogger(org.slf4j.Logger logger) {
            this.logger = logger;
        }


        @Override
        protected boolean shouldLog() {
            return logger.isInfoEnabled();
        }

        @Override
        protected void log(String configKey, String format, Object... args) {
            logger.info("[{}]{}", configKey, String.format(format, args));
        }

    }

}
