package me.ml3426.xomapper.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * Xo-Mapper的日志工具类
 * 
 * @author ml3426 
 * @since 0.0.1
 */
public final class LogUtil {

    /**
     * XO-MAPPER的日志输出器
     */
    private final static Logger XO_LOGGER = LoggerFactory.getLogger("XOMAPPER");

    /**
     * debug级别的日志输出器
     * 
     * @param debugInfo debug级别的日志或者日志模板
     * @param logArgs 当<code>debugInfo</code>为日志模板时，此为日志模板的参数
     */
    public static void debug(final Supplier<String> debugInfo, final Object... logArgs) {
        if (XO_LOGGER.isDebugEnabled()) {
            XO_LOGGER.debug(debugInfo.get(), logArgs);
        }
    }

    /**
     * info级别的日志输出器
     *
     * @param infoInfo info级别的日志或者日志模板
     * @param logArgs 当<code>infoInfo</code>为日志模板时，此为日志模板的参数
     */
    public static void info(final Supplier<String> infoInfo, final Object... logArgs) {
        if (XO_LOGGER.isInfoEnabled()) {
            XO_LOGGER.info(infoInfo.get(), logArgs);
        }

    }

    /**
     * warn级别的日志输出器
     *
     * @param warnInfo warn级别的日志或者日志模板
     * @param logArgs 当<code>warnInfo</code>为日志模板时，此为日志模板的参数
     */
    public static void warn(final Supplier<String> warnInfo, final Object... logArgs) {
        if (XO_LOGGER.isWarnEnabled()) {
            XO_LOGGER.warn(warnInfo.get(), logArgs);
        }
    }

    /**
     * warn级别的日志输出器
     *
     * @param warnInfo warn级别的日志
     * @param throwable 需要记录的异常
     */
    public static void warn(final Supplier<String> warnInfo, final Throwable throwable) {
        if (XO_LOGGER.isWarnEnabled()) {
            XO_LOGGER.warn(warnInfo.get(), throwable);
        }
    }

    /**
     * error级别的日志输出器
     *
     * @param errorInfo error级别的日志
     * @param throwable 需要记录的异常
     */
    public static void error(final Supplier<String> errorInfo, final Throwable throwable) {
        if (XO_LOGGER.isErrorEnabled()) {
            XO_LOGGER.error(errorInfo.get(), throwable);
        }
    }
}
