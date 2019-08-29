package com.alipay.tianyan.mobilesdk;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.BatteryID;
import com.alipay.mobile.common.logging.api.monitor.BatteryModel;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.tianyan.mobilesdk.TianyanLoggingDelegator.CommonSimpleDelegate;
import com.alipay.tianyan.mobilesdk.TianyanLoggingDelegator.ConfigServiceDelegate;
import com.alipay.tianyan.mobilesdk.TianyanLoggingDelegator.MonitorContextDelegate;

public class TianyanLoggingDispatcher {
    private static CommonSimpleDelegate a;
    private static ConfigServiceDelegate b;
    private static MonitorContextDelegate c;
    static boolean sIsFrameworkBackground = true;
    static boolean sIsMonitorBackground = true;
    static boolean sIsRelaxedBackground = true;
    static boolean sIsStrictBackground = true;

    static boolean putCommonSimpleDelegate(CommonSimpleDelegate delegate) {
        if (delegate == null) {
            return false;
        }
        String origin = delegate.getClass().getName();
        String processAlias = LoggerFactory.getProcessInfo().getProcessAlias();
        if (a == null) {
            a = delegate;
            LoggerFactory.getTraceLogger().info("TianyanLoggingDispatcher", processAlias + " process, putCommonSimpleDelegate: " + origin);
            return true;
        }
        LoggerFactory.getTraceLogger().warn((String) "TianyanLoggingDispatcher", processAlias + " process, putCommonSimpleDelegate, exist: " + origin);
        return false;
    }

    static boolean removeCommonSimpleDelegate(CommonSimpleDelegate delegate) {
        if (delegate == null || a == null) {
            return false;
        }
        String origin = delegate.getClass().getName();
        String processAlias = LoggerFactory.getProcessInfo().getProcessAlias();
        if (a == delegate) {
            a = null;
            LoggerFactory.getTraceLogger().info("TianyanLoggingDispatcher", processAlias + " process, removeCommonSimpleDelegate: " + origin);
            return true;
        }
        LoggerFactory.getTraceLogger().warn((String) "TianyanLoggingDispatcher", processAlias + " process, removeCommonSimpleDelegate, not yours: " + origin);
        return false;
    }

    static boolean putConfigServiceDelegate(ConfigServiceDelegate delegate) {
        if (delegate == null) {
            return false;
        }
        String origin = delegate.getClass().getName();
        String processAlias = LoggerFactory.getProcessInfo().getProcessAlias();
        if (b == null) {
            b = delegate;
            LoggerFactory.getTraceLogger().info("TianyanLoggingDispatcher", processAlias + " process, putConfigServiceDelegate: " + origin);
            return true;
        }
        LoggerFactory.getTraceLogger().warn((String) "TianyanLoggingDispatcher", processAlias + " process, putConfigServiceDelegate, exist: " + origin);
        return false;
    }

    static boolean removeConfigServiceDelegate(ConfigServiceDelegate delegate) {
        if (delegate == null || b == null) {
            return false;
        }
        String origin = delegate.getClass().getName();
        String processAlias = LoggerFactory.getProcessInfo().getProcessAlias();
        if (b == delegate) {
            b = null;
            LoggerFactory.getTraceLogger().info("TianyanLoggingDispatcher", processAlias + " process, removeConfigServiceDelegate: " + origin);
            return true;
        }
        LoggerFactory.getTraceLogger().warn((String) "TianyanLoggingDispatcher", processAlias + " process, removeConfigServiceDelegate, not yours: " + origin);
        return false;
    }

    static boolean putMonitorContextDelegate(MonitorContextDelegate delegate) {
        if (delegate == null) {
            return false;
        }
        String origin = delegate.getClass().getName();
        String processAlias = LoggerFactory.getProcessInfo().getProcessAlias();
        if (c == null) {
            c = delegate;
            LoggerFactory.getTraceLogger().info("TianyanLoggingDispatcher", processAlias + " process, putMonitorContextDelegate: " + origin);
            return true;
        }
        LoggerFactory.getTraceLogger().warn((String) "TianyanLoggingDispatcher", processAlias + " process, putMonitorContextDelegate, exist: " + origin);
        return false;
    }

    static boolean removeMonitorContextDelegate(MonitorContextDelegate delegate) {
        if (delegate == null || c == null) {
            return false;
        }
        String origin = delegate.getClass().getName();
        String processAlias = LoggerFactory.getProcessInfo().getProcessAlias();
        if (c == delegate) {
            c = null;
            LoggerFactory.getTraceLogger().info("TianyanLoggingDispatcher", processAlias + " process, removeMonitorContextDelegate: " + origin);
            return true;
        }
        LoggerFactory.getTraceLogger().warn((String) "TianyanLoggingDispatcher", processAlias + " process, removeMonitorContextDelegate, not yours: " + origin);
        return false;
    }

    static void acceptTimeTicksMadly() {
        if (a != null) {
            try {
                a.acceptTimeTicksMadly();
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error("TianyanLoggingDispatcher", "acceptTimeTicksMadly", t);
            }
        }
    }

    static String getBundleByClass(String className) {
        String str = null;
        if (a == null) {
            return str;
        }
        try {
            return a.getBundleByClass(className);
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error("TianyanLoggingDispatcher", "getBundleNameByClassName", t);
            return str;
        }
    }

    static String getConfigValueByKey(String configKey, String defaultValue) {
        if (b == null) {
            return defaultValue;
        }
        try {
            return b.getConfigValueByKey(configKey, defaultValue);
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error("TianyanLoggingDispatcher", "getConfigValueByKey", t);
            return defaultValue;
        }
    }

    static void notePowerConsume(BatteryModel batteryModel) {
        if (c != null) {
            try {
                c.notePowerConsume(batteryModel);
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error("TianyanLoggingDispatcher", "notePowerConsume", t);
            }
        }
    }

    static boolean isPowerConsumeAccept(BatteryID type, String diagnose) {
        boolean z = true;
        if (c == null) {
            return z;
        }
        try {
            return c.isPowerConsumeAccept(type, diagnose);
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error("TianyanLoggingDispatcher", "isPowerConsumeAccept", t);
            return z;
        }
    }

    static void noteTraficConsume(DataflowModel dataflowModel) {
        if (c != null) {
            try {
                c.noteTraficConsume(dataflowModel);
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error("TianyanLoggingDispatcher", "noteTraficConsume", t);
            }
        }
    }

    static boolean isTraficConsumeAccept(DataflowID type, String url) {
        boolean z = true;
        if (c == null) {
            return z;
        }
        try {
            return c.isTraficConsumeAccept(type, url);
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error("TianyanLoggingDispatcher", "isTraficConsumeAccept", t);
            return z;
        }
    }
}
