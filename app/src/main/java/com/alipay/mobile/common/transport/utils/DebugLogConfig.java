package com.alipay.mobile.common.transport.utils;

import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.io.ByteArrayInputStream;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DebugLogConfig {
    static DalvikLogHandler activeHandler;
    static boolean enabledH2Logger = false;
    static boolean enabledHttpClientLogger = false;

    public class DalvikLogHandler extends Handler {
        protected DalvikLogHandler() {
        }

        public void close() {
        }

        public void flush() {
        }

        public void publish(LogRecord record) {
            if (!Thread.currentThread().getName().contains("LogServiceInTools")) {
                String tag = "HttpClient";
                String sourceClassName = record.getSourceClassName();
                if (!TextUtils.isEmpty(sourceClassName)) {
                    int lastPointIndex = sourceClassName.lastIndexOf(".");
                    if (lastPointIndex == -1) {
                        tag = tag + "_" + sourceClassName;
                    } else {
                        tag = tag + "_" + sourceClassName.substring(lastPointIndex + 1);
                    }
                }
                Formatter formatter = getFormatter();
                if (formatter != null) {
                    LogCatUtil.printInfo(tag, "[" + Thread.currentThread().getName() + "] " + formatter.format(record));
                } else {
                    LogCatUtil.printInfo(tag, record.getSequenceNumber() + "," + record.getThreadID() + "," + record.getLoggerName() + ",[" + record.getSourceClassName() + MetaRecord.LOG_SEPARATOR + record.getSourceMethodName() + "]," + record.getMessage());
                }
            }
        }
    }

    public static void enableH2Logger() {
        if (!enabledH2Logger) {
            enabledH2Logger = true;
            try {
                a();
                LogManager.getLogManager().readConfiguration(new ByteArrayInputStream("com.android.okhttp.level = FINEST".getBytes()));
                Logger.getLogger("com.android.okhttp.OkHttpClient").setLevel(Level.FINEST);
                Logger.getLogger("com.android.okhttp").setLevel(Level.FINEST);
                LogCatUtil.info("DebugLogConfig", "[enableH2Logger] finish.");
            } catch (Throwable e) {
                Log.w("DebugLogConfig", "[enableH2Logger] error", e);
            }
        }
    }

    public static void enableHttpClient() {
        if (!enabledHttpClientLogger) {
            enabledHttpClientLogger = true;
            a();
            if (!MiscUtils.isDebugger(TransportEnvUtil.getContext())) {
                LogCatUtil.info("DebugLogConfig", "[enableHttpClient] Not debugger, return.");
            } else if (!TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.SYSTEM_LOGGER_ENABLE, "T")) {
                LogCatUtil.info("DebugLogConfig", "[enableHttpClient] SYSTEM_LOGGER_ENABLE it's off, return.");
            } else {
                try {
                    LogManager.getLogManager().readConfiguration(new ByteArrayInputStream("org.apache.http.impl.conn.level = FINEST\norg.apache.http.impl.client.level = FINEST\norg.apache.http.client.level = FINEST\norg.apache.http.level = FINEST \n".getBytes()));
                    Logger.getLogger("org.apache.http.wire").setLevel(Level.FINEST);
                    Logger.getLogger("httpclient.wire.content").setLevel(Level.FINEST);
                    Logger.getLogger("org.apache.http.headers").setLevel(Level.FINEST);
                    Logger.getLogger("httpclient.wire.header").setLevel(Level.FINEST);
                    Logger.getLogger("com.alipay.mobile.common.transport.http").setLevel(Level.FINEST);
                    Logger.getLogger("org.apache.http.impl.conn.tsccm").setLevel(Level.FINEST);
                    System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
                    System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
                    System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
                    System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "debug");
                    System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "debug");
                    LogCatUtil.info("DebugLogConfig", "[enableHttpClient] Enabled httpclient log.");
                } catch (Throwable th) {
                    Log.w(DebugLogConfig.class.getSimpleName(), "Can't read configuration file for logging");
                }
            }
        }
    }

    private static void a() {
        if (activeHandler == null) {
            try {
                Logger rootLogger = LogManager.getLogManager().getLogger("");
                DalvikLogHandler dalvikLogHandler = new DalvikLogHandler();
                activeHandler = dalvikLogHandler;
                dalvikLogHandler.setLevel(Level.ALL);
                activeHandler.setFormatter(new SimpleFormatter());
                rootLogger.addHandler(activeHandler);
                LogCatUtil.info("DebugLogConfig", "[registerLogHandler] finish.");
            } catch (Throwable e) {
                LogCatUtil.warn("DebugLogConfig", "[registerLogHandler] Error", e);
            }
        }
    }
}
