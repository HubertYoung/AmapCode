package com.alipay.mobile.quinox.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.ExceptionID;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.common.logging.util.avail.ExceptionCollector;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class MonitorLogger {
    static final String APM = "apm";
    static final String CRASH = "crash";
    static final String EXCEPTION = "exception";
    static final String FOOTPRINT = "footprint";
    public static Object MONITORPOINT_CLIENTSERR = null;
    public static Object MONITORPOINT_FOOTPRINT = null;
    public static Object MONITORPOINT_IGNORE_CRASH = null;
    public static Object MONITORPOINT_INVALID_CRASH = null;
    public static Object MONITORPOINT_PERFORMANCE = null;
    public static Object MONITORPOINT_SUB_THREAD_CRASH = null;
    static final String MTBIZREPORT = "mtBizReport";
    private static final String MTBIZ_FRAME = "BIZ_FRAME";
    static final String PERFORMANCE = "performance";
    public static final String SUBNAME_QUINOX = "QUINOX_ERROR";
    static List<String> onceReportExceptions = new CopyOnWriteArrayList();
    static LogContext sLogContext;
    static com.alipay.mobile.common.logging.api.monitor.MonitorLogger sMonitorLogger;

    public static void init() {
        MONITORPOINT_CLIENTSERR = ExceptionID.MONITORPOINT_CLIENTSERR;
        MONITORPOINT_SUB_THREAD_CRASH = ExceptionID.MONITORPOINT_SUB_THREAD_CRASH;
        MONITORPOINT_IGNORE_CRASH = ExceptionID.MONITORPOINT_IGNORE_CRASH;
        MONITORPOINT_INVALID_CRASH = ExceptionID.MONITORPOINT_INVALID_CRASH;
        sLogContext = LoggerFactory.getLogContext();
        sMonitorLogger = LoggerFactory.getMonitorLogger();
        MONITORPOINT_FOOTPRINT = PerformanceID.MONITORPOINT_FOOTPRINT;
        MONITORPOINT_PERFORMANCE = PerformanceID.MONITORPOINT_PERFORMANCE;
    }

    public static void upload(String str) {
        String str2;
        StringBuilder sb;
        try {
            if (sLogContext != null) {
                sLogContext.upload(str);
            }
            str2 = "LauncherApplication";
            sb = new StringBuilder("upload(upload=");
        } catch (Throwable th) {
            StringBuilder sb2 = new StringBuilder("upload(upload=");
            sb2.append(str);
            sb2.append(")");
            TraceLogger.w((String) "LauncherApplication", sb2.toString());
            throw th;
        }
        sb.append(str);
        sb.append(")");
        TraceLogger.w(str2, sb.toString());
    }

    public static void flush(boolean z) {
        try {
            if (sLogContext != null) {
                sLogContext.flush(z);
            }
            if (sLogContext != null) {
                sLogContext.flush("applog", z);
            }
        } catch (Throwable th) {
            TraceLogger.w((String) "LauncherApplication", th);
        }
    }

    public static void sendCrash(Throwable th) {
        sendCrash(th, null);
    }

    public static void sendCrash(Throwable th, String str) {
        try {
            if (sMonitorLogger != null) {
                sMonitorLogger.crash(th, str);
            }
        } catch (Throwable th2) {
            TraceLogger.e((String) "LauncherApplication", th);
            throw th2;
        }
        TraceLogger.e((String) "LauncherApplication", th);
    }

    public static void sendCrash(Object obj, Throwable th, String str) {
        try {
            if (sMonitorLogger != null) {
                sMonitorLogger.crash((ExceptionID) obj, th, str);
            }
        } catch (Throwable th2) {
            TraceLogger.e((String) "LauncherApplication", th);
            throw th2;
        }
        TraceLogger.e((String) "LauncherApplication", th);
    }

    @Deprecated
    public static void exception(Throwable th) {
        exception((String) "unknown", th, (String) null);
    }

    @Deprecated
    public static void exception(Throwable th, String str) {
        exception((String) "unknown", th, str);
    }

    public static void exception(String str, Throwable th, String str2, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(":");
        sb.append(str2);
        TraceLogger.e(SUBNAME_QUINOX, sb.toString(), th);
        HashMap hashMap = new HashMap();
        if (str2 != null) {
            hashMap.put("msg", str2);
        }
        if (th != null) {
            hashMap.put("stack", Log.getStackTraceString(th));
        }
        if (z) {
            if (!onceReportExceptions.contains(str)) {
                onceReportExceptions.add(str);
            } else {
                return;
            }
        }
        mtBizReport("BIZ_FRAME", SUBNAME_QUINOX, str, hashMap);
    }

    public static void exception(String str, Throwable th, String str2) {
        exception(str, th, str2, false);
    }

    public static void exception(Object obj, Throwable th, String str) {
        try {
            if (sMonitorLogger != null) {
                if (!TextUtils.isEmpty(str)) {
                    th = new RuntimeException(str, th);
                }
                if (obj != null) {
                    sMonitorLogger.exception((ExceptionID) obj, th);
                } else {
                    TraceLogger.w((String) "MonitorLogger", (String) "call exception with null exceptionID");
                }
            }
        } catch (Throwable th2) {
            TraceLogger.e((String) "LauncherApplication", th);
            throw th2;
        }
        TraceLogger.e((String) "LauncherApplication", th);
    }

    public static void apm(String str, String str2, Throwable th, Map<String, String> map) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                if (sMonitorLogger != null) {
                    sMonitorLogger.apm(str, str2, th, map);
                }
            } catch (Throwable th2) {
                TraceLogger.e((String) "LauncherApplication", th);
                throw th2;
            }
            TraceLogger.e((String) "LauncherApplication", th);
        }
    }

    public static void footprint(String str, String str2) {
        footprint(str, str2, null, null, null, null);
    }

    public static void footprint(String str, String str2, String str3, String str4, String str5, Map<String, String> map) {
        try {
            if (sMonitorLogger != null) {
                sMonitorLogger.footprint(str, str2, str3, str4, str5, map);
            }
        } catch (Throwable th) {
            TraceLogger.i((String) "LauncherApplication", str2);
            throw th;
        }
        TraceLogger.i((String) "LauncherApplication", str2);
    }

    public static void performance(Object obj) {
        performance(MONITORPOINT_FOOTPRINT, obj);
    }

    public static void performance(Object obj, Object obj2) {
        if (obj != null && obj2 != null) {
            try {
                if (sMonitorLogger != null) {
                    sMonitorLogger.performance((PerformanceID) obj, (Performance) obj2);
                }
            } catch (Throwable th) {
                TraceLogger.i((String) "LauncherApplication", (String) "performanceLog");
                throw th;
            }
            TraceLogger.i((String) "LauncherApplication", (String) "performanceLog");
        }
    }

    public static Object createPerformanceBuilderObject() {
        try {
            return MonitorLogger.class.getClassLoader().loadClass("com.alipay.mobile.common.logging.api.monitor.Performance$Builder").newInstance();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static void mtBizReport(String str, String str2, String str3) {
        mtBizReport(str, str2, str3, null);
    }

    public static void mtBizReport(String str, String str2, String str3, Map<String, String> map) {
        try {
            if (sMonitorLogger != null) {
                sMonitorLogger.mtBizReport(str, str2, str3, map);
            }
        } catch (Throwable th) {
            TraceLogger.i((String) "LauncherApplication", str3);
            throw th;
        }
        TraceLogger.i((String) "LauncherApplication", str3);
    }

    public static void dangerousUpload(String str, String str2, String str3, Map<String, String> map) {
        try {
            if (sMonitorLogger != null) {
                ReflectUtil.invokeMethod((Object) sMonitorLogger, (String) "dangerousUpload", new Class[]{String.class, String.class, String.class, Map.class}, new Object[]{str, str2, str3, map});
                sMonitorLogger.mtBizReport(str, str2, str3, map);
            }
        } catch (Throwable th) {
            TraceLogger.i((String) "LauncherApplication", str3);
            throw th;
        }
        TraceLogger.i((String) "LauncherApplication", str3);
    }

    public static Object invokePerformanceBuilder(Object obj, String str, String str2, String str3, String str4, Map<String, String> map) {
        if (obj == null) {
            return null;
        }
        try {
            Class<?> cls = obj.getClass();
            if (str != null) {
                cls.getMethod("setSubType", new Class[]{String.class}).invoke(obj, new Object[]{str});
            }
            if (str2 != null) {
                cls.getMethod("setParam1", new Class[]{String.class}).invoke(obj, new Object[]{str2});
            }
            if (str3 != null) {
                cls.getMethod("setParam2", new Class[]{String.class}).invoke(obj, new Object[]{str3});
            }
            if (str4 != null) {
                cls.getMethod("setParam3", new Class[]{String.class}).invoke(obj, new Object[]{str4});
            }
            if (map != null && !map.isEmpty()) {
                Method method = cls.getMethod("addExtParam", new Class[]{String.class, String.class});
                for (Entry next : map.entrySet()) {
                    method.invoke(obj, new Object[]{next.getKey(), next.getValue()});
                }
            }
            return cls.getMethod("build", new Class[0]).invoke(obj, new Object[0]);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static void traceNativeCrash() {
        traceNativeCrash(null, null, true);
    }

    public static void traceNativeCrash(String str, String str2, boolean z) {
        try {
            Method method = sLogContext.getClass().getMethod("traceNativeCrash", new Class[]{String.class, String.class, Boolean.TYPE});
            method.setAccessible(true);
            method.invoke(sLogContext, new Object[]{str, str2, Boolean.valueOf(z)});
        } catch (Throwable th) {
            TraceLogger.w((String) "LauncherApplication", th);
        }
    }

    public static Map<String, String> getStartupReason() {
        try {
            return LoggerFactory.getProcessInfo().getStartupReason();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static Bundle getStartupBundle() {
        try {
            Object invoke = MonitorLogger.class.getClassLoader().loadClass("com.alipay.mobile.common.logging.api.LoggerFactory").getDeclaredMethod("getProcessInfo", new Class[0]).invoke(null, new Object[0]);
            return (Bundle) invoke.getClass().getDeclaredMethod("getStartupBundle", new Class[0]).invoke(invoke, new Object[0]);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static Uri getStartupData() {
        try {
            Object invoke = MonitorLogger.class.getClassLoader().loadClass("com.alipay.mobile.common.logging.api.LoggerFactory").getDeclaredMethod("getProcessInfo", new Class[0]).invoke(null, new Object[0]);
            return (Uri) invoke.getClass().getDeclaredMethod("getStartupData", new Class[0]).invoke(invoke, new Object[0]);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static void prepareStartupReason() {
        try {
            Object invoke = MonitorLogger.class.getClassLoader().loadClass("com.alipay.mobile.common.logging.api.LoggerFactory").getDeclaredMethod("getProcessInfo", new Class[0]).invoke(null, new Object[0]);
            invoke.getClass().getDeclaredMethod("prepareStartupReason", new Class[0]).invoke(invoke, new Object[0]);
        } catch (Throwable th) {
            TraceLogger.i((String) "MonitorLogger", th);
        }
    }

    public static void notifyClientEvent(String str, Object obj) {
        try {
            sLogContext.getClass().getDeclaredMethod("notifyClientEvent", new Class[]{String.class, Object.class}).invoke(sLogContext, new Object[]{str, obj});
        } catch (Throwable th) {
            TraceLogger.i((String) "MonitorLogger", th);
        }
    }

    public static String getClientStatus() {
        try {
            Method declaredMethod = sLogContext.getClass().getDeclaredMethod("getClientStatus", new Class[]{Boolean.TYPE});
            declaredMethod.setAccessible(true);
            String str = (String) declaredMethod.invoke(sLogContext, new Object[]{Boolean.FALSE});
            if (str == null) {
                str = "unknown";
            }
            return str;
        } catch (Throwable th) {
            TraceLogger.i((String) "MonitorLogger", th);
            return "unknown";
        }
    }

    public static String getClientStatus(String str) {
        try {
            Method declaredMethod = sLogContext.getClass().getDeclaredMethod("getClientStatus", new Class[]{Boolean.TYPE, Boolean.TYPE, String.class});
            declaredMethod.setAccessible(true);
            String str2 = (String) declaredMethod.invoke(sLogContext, new Object[]{Boolean.FALSE, Boolean.TRUE, str});
            if (str2 == null) {
                str2 = "unknown";
            }
            return str2;
        } catch (Throwable th) {
            TraceLogger.i((String) "MonitorLogger", th);
            return "unknown";
        }
    }

    public static String getBizExternParams(String str) {
        try {
            Method declaredMethod = sLogContext.getClass().getDeclaredMethod("getBizExternParams", new Class[0]);
            declaredMethod.setAccessible(true);
            Map map = (Map) declaredMethod.invoke(sLogContext, new Object[0]);
            if (map != null) {
                return (String) map.get(str);
            }
            return null;
        } catch (Throwable th) {
            TraceLogger.i((String) "MonitorLogger", th);
            return null;
        }
    }

    public static void putBizExternParams(String str, String str2) {
        try {
            Method declaredMethod = sLogContext.getClass().getDeclaredMethod("putBizExternParams", new Class[]{String.class, String.class});
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(sLogContext, new Object[]{str, str2});
        } catch (Throwable th) {
            TraceLogger.i((String) "MonitorLogger", th);
        }
    }

    public static void cuRecordNewLaunchTime(Context context, long j) {
        try {
            ExceptionCollector.getInstance(context).recordNewLaunchTime(j);
        } catch (Throwable th) {
            TraceLogger.i((String) "MonitorLogger", th);
        }
    }

    public static List<Long> cuGetLaunchTimes(Context context) {
        try {
            Class<?> cls = Class.forName("com.alipay.mobile.common.logging.util.avail.ExceptionCollector");
            return (List) cls.getDeclaredMethod("getLaunchTimes", new Class[0]).invoke(cls.getDeclaredMethod("getInstance", new Class[]{Context.class}).invoke(null, new Object[]{context}), new Object[0]);
        } catch (Throwable th) {
            TraceLogger.i((String) "MonitorLogger", th);
            return null;
        }
    }

    public static void cuRecordException(Context context, String str) {
        try {
            Class<?> cls = Class.forName("com.alipay.mobile.common.logging.util.avail.ExceptionCollector");
            Object invoke = cls.getDeclaredMethod("getInstance", new Class[]{Context.class}).invoke(null, new Object[]{context});
            cls.getDeclaredMethod("recordException", new Class[]{String.class}).invoke(invoke, new Object[]{str});
        } catch (Throwable th) {
            TraceLogger.i((String) "MonitorLogger", th);
        }
    }

    public static void cuRecordException(Context context, String str, long j) {
        try {
            Class<?> cls = Class.forName("com.alipay.mobile.common.logging.util.avail.ExceptionCollector");
            Object invoke = cls.getDeclaredMethod("getInstance", new Class[]{Context.class}).invoke(null, new Object[]{context});
            cls.getDeclaredMethod("recordException", new Class[]{String.class, Long.TYPE}).invoke(invoke, new Object[]{str, Long.valueOf(j)});
        } catch (Throwable th) {
            TraceLogger.i((String) "MonitorLogger", th);
        }
    }

    public static void cuClearException(Context context, String str) {
        try {
            Class<?> cls = Class.forName("com.alipay.mobile.common.logging.util.avail.ExceptionCollector");
            Object invoke = cls.getDeclaredMethod("getInstance", new Class[]{Context.class}).invoke(null, new Object[]{context});
            cls.getDeclaredMethod("clearException", new Class[]{String.class}).invoke(invoke, new Object[]{str});
        } catch (Throwable th) {
            TraceLogger.i((String) "MonitorLogger", th);
        }
    }

    public static void updateUpdateBundleKeysToLog(Set<String> set) {
        if (set != null) {
            try {
                if (!set.isEmpty()) {
                    String str = null;
                    HashSet<String> hashSet = new HashSet<>();
                    hashSet.addAll(set);
                    if (!hashSet.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (String append : hashSet) {
                            sb.append(append);
                            sb.append(MergeUtil.SEPARATOR_KV);
                        }
                        str = sb.deleteCharAt(sb.length() - 1).toString();
                    }
                    if (!TextUtils.isEmpty(str)) {
                        sLogContext.getClass().getDeclaredMethod("putBizExternParams", new Class[]{String.class, String.class}).invoke(sLogContext, new Object[]{"bundleUpdates", str});
                    }
                }
            } catch (Throwable th) {
                TraceLogger.e((String) "MonitorLogger", th);
            }
        }
    }
}
