package com.alipay.mobile.antui.utils;

import com.alipay.mobile.antui.excutor.AntUIExecutorManager;
import java.util.Map;

public class AuiLogger {
    private static final String APP = "AntUILogger";

    public static void debug(String tag, String content) {
        AntUIExecutorManager.getInstance().getLoggerExecutor().debug(buildTag(APP, tag), content);
    }

    public static void info(String tag, String content) {
        AntUIExecutorManager.getInstance().getLoggerExecutor().info(buildTag(APP, tag), content);
    }

    public static void error(String tag, String content) {
        AntUIExecutorManager.getInstance().getLoggerExecutor().error(buildTag(APP, tag), content);
    }

    public static void mtBizReport(String tag, String content) {
        error(tag, content);
        AntUIExecutorManager.getInstance().getLoggerExecutor().mtBizReport(tag, content);
    }

    public static void eventBehavor(String seedId, String param1, String param2, String param3, Map<String, String> param4) {
        AntUIExecutorManager.getInstance().getLoggerExecutor().eventBehavor("UC-ANTUI", seedId, param1, param2, param3, param4);
    }

    private static String buildTag(String app, String tag) {
        return "[" + app + "," + tag + "]";
    }
}
