package com.framework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 * Utility class for creating and managing loggers.
 */

public final class LoggerManager {

    private static final ThreadLocal<Logger> THREAD_LOCAL_LOGGER =
            new ThreadLocal<>();

    private static final Logger FALLBACK_LOGGER =
            LogManager.getLogger("FallbackLogger");

    private LoggerManager() {}

    public static void startTest(String testName, Class<?> clazz) {
        ThreadContext.put("testName", testName);
        THREAD_LOCAL_LOGGER.set(LogManager.getLogger(clazz));
        get().info("===== TEST STARTED: {} =====", testName);
    }

    public static Logger get() {
        Logger logger = THREAD_LOCAL_LOGGER.get();
        return (logger != null) ? logger : FALLBACK_LOGGER;
    }

    public static void endTest() {
        get().info("===== TEST FINISHED =====");
        THREAD_LOCAL_LOGGER.remove();
        ThreadContext.clearAll();
    }
}

