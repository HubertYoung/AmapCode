package com.alipay.mobile.common.logging.impl;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Debug;
import android.os.Debug.MemoryInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LogEvent;
import com.alipay.mobile.common.logging.api.LogEvent.Level;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.UncaughtExceptionCallback;
import com.alipay.mobile.common.logging.api.monitor.ExceptionID;
import com.alipay.mobile.common.logging.api.monitor.MTBizReportName;
import com.alipay.mobile.common.logging.helper.BugReportAnalyzer;
import com.alipay.mobile.common.logging.render.ExceptionRender;
import com.alipay.mobile.common.logging.util.LogcatUtil;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.common.logging.util.MemoryUtil;
import com.alipay.mobile.common.logging.util.avail.ExceptionCollector;
import com.alipay.mobile.common.logging.util.avail.ExceptionData;
import com.alipay.mobile.common.logging.util.crash.CrashConstants;
import com.alipay.mobile.common.nativecrash.CrashCombineUtils;
import com.alipay.mobile.common.nativecrash.NativeCrashHandler;
import com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi;
import com.alipay.mobile.common.os.LoggingLifecycleCallback;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class StatisticalExceptionHandler implements UncaughtExceptionHandler {
    private static final String a = StatisticalExceptionHandler.class.getSimpleName();
    private static StatisticalExceptionHandler b;
    /* access modifiers changed from: private */
    public UncaughtExceptionHandler c;
    private UncaughtExceptionCallback d;
    private Context e;
    private Runnable f;
    private boolean g;

    private StatisticalExceptionHandler(Context context) {
        this.e = context;
        this.f = new a(this, context);
    }

    public static synchronized StatisticalExceptionHandler createInstance(Context context) {
        StatisticalExceptionHandler statisticalExceptionHandler;
        synchronized (StatisticalExceptionHandler.class) {
            if (b == null) {
                b = new StatisticalExceptionHandler(context);
            }
            statisticalExceptionHandler = b;
        }
        return statisticalExceptionHandler;
    }

    public static StatisticalExceptionHandler getInstance() {
        if (b != null) {
            return b;
        }
        throw new IllegalStateException("need createInstance befor use");
    }

    public synchronized void setup() {
        if (!this.g) {
            this.g = true;
            if (LoggerFactory.getProcessInfo().isMainProcess()) {
                NativeCrashHandler.ENANBLE_JAVA_LOG = true;
                NativeCrashHandler.ENABLE_UNEXP_LOG = true;
                if (this.e instanceof Application) {
                    ((Application) this.e).registerActivityLifecycleCallbacks(new LoggingLifecycleCallback(this.e));
                } else {
                    NativeCrashHandler.ENABLE_UNEXP_LOG = false;
                    NativeCrashHandler.ENANBLE_JAVA_LOG = false;
                }
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                NativeCrashHandler.initialize(this.e);
                this.c = Thread.getDefaultUncaughtExceptionHandler();
                Thread.setDefaultUncaughtExceptionHandler(this);
            } else if (this.f != null) {
                new Handler(Looper.getMainLooper()).post(this.f);
            }
        }
    }

    public synchronized void takedown() {
        this.g = false;
        Thread.setDefaultUncaughtExceptionHandler(this.c);
    }

    public void setUncaughtExceptionCallback(UncaughtExceptionCallback mUncaughtExceptionCallback) {
        this.d = mUncaughtExceptionCallback;
    }

    public UncaughtExceptionCallback getUncaughtExceptionCallback() {
        return this.d;
    }

    public String getExternalExceptionInfo(Throwable ex) {
        try {
            UncaughtExceptionCallback exceptionCallback = getInstance().getUncaughtExceptionCallback();
            if (exceptionCallback != null) {
                return exceptionCallback.getExternalExceptionInfo(Thread.currentThread(), ex);
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public void uncaughtException(Thread thread, Throwable throwable) {
        Throwable crashCause = throwable;
        boolean negligibleThrowable = false;
        if ("NegligibleThrowable".equals(throwable.getMessage())) {
            negligibleThrowable = true;
            crashCause = throwable.getCause();
        }
        if (LoggerFactory.getProcessInfo().isMainProcess() || LoggerFactory.getProcessInfo().isLiteProcess()) {
            LoggerFactory.getTraceLogger().debug(a, "enter uncaughtException.");
            if (crashCause == null || negligibleThrowable) {
                LoggerFactory.getLogContext().flush(true);
                LoggerFactory.getLogContext().flush("applog", true);
                LoggerFactory.getLogContext().backupCurrentFile("applog", false);
            } else {
                LoggerFactory.getMonitorLogger().crash(crashCause, null);
            }
            try {
                if (a(throwable)) {
                    MemoryInfo[] appMemInfos = ((ActivityManager) this.e.getSystemService(WidgetType.ACTIVITY)).getProcessMemoryInfo(new int[]{Process.myPid()});
                    if (appMemInfos.length > 0) {
                        MemoryInfo memInfo = appMemInfos[0];
                        LoggerFactory.getTraceLogger().info("OOMException", "totalProportional: " + Debug.getPss());
                        LoggerFactory.getTraceLogger().info("OOMException", "dalvikHeapAlloc: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
                        LoggerFactory.getTraceLogger().info("OOMException", "totalPss: " + memInfo.getTotalPss());
                        LoggerFactory.getTraceLogger().info("OOMException", "dalvikPss: " + memInfo.dalvikPss);
                        LoggerFactory.getTraceLogger().info("OOMException", "nativePss: " + memInfo.nativePss);
                        LoggerFactory.getTraceLogger().info("OOMException", "otherPss: " + memInfo.otherPss);
                        HashMap params = new HashMap();
                        params.put("totalProportional", Debug.getPss());
                        params.put("dalvikHeapAlloc", (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
                        params.put("totalPss", memInfo.getTotalPss());
                        params.put("dalvikPss", memInfo.dalvikPss);
                        params.put("nativePss", memInfo.nativePss);
                        params.put("otherPss", memInfo.otherPss);
                        LoggerFactory.getMonitorLogger().mtBizReport(MTBizReportName.MTBIZ_FRAME, "FRAME_OOM_REPORT", "0", params);
                    }
                    try {
                        LoggerFactory.getLogContext().flush(true);
                        LoggerFactory.getLogContext().flush("applog", true);
                    } catch (Throwable th) {
                    }
                }
            } catch (Throwable th2) {
            }
            try {
                if (a(throwable)) {
                    Class memoryMonitorClass = Class.forName("com.alipay.android.phone.mobilesdk.apm.memory.MemoryMonitor");
                    memoryMonitorClass.getDeclaredMethod("handleDestroyActivities", new Class[0]).invoke(memoryMonitorClass.getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]), new Object[0]);
                }
            } catch (Throwable th3) {
            }
            try {
                if (a(throwable)) {
                    Method method = Class.forName("com.alipay.mobile.quinox.utils.ThreadDumpUtil").getDeclaredMethod("logAllThreadsTraces", new Class[0]);
                    method.setAccessible(true);
                    method.invoke(null, new Object[0]);
                    try {
                        LoggerFactory.getLogContext().flush(true);
                        LoggerFactory.getLogContext().flush("applog", true);
                    } catch (Throwable th4) {
                    }
                }
            } catch (Throwable th5) {
            }
            LogcatUtil.dumpLogcatForException(this.e);
            if ("foreground".equals(LoggerFactory.getLogContext().getClientStatus(false))) {
                ExceptionCollector.getInstance(this.e).recordException(ExceptionData.TYPE_CRASH);
            }
            try {
                LoggerFactory.getLogContext().flush(true);
                LoggerFactory.getLogContext().flush("applog", true);
                LoggerFactory.getLogContext().backupCurrentFile("applog", false);
            } catch (Throwable th6) {
            }
            if (b(throwable)) {
                MemoryUtil.dumpMemHeap(this.e);
            }
            BugReportAnalyzer.a().a(200, true);
        } else if (LoggerFactory.getProcessInfo().isPushProcess() || LoggerFactory.getProcessInfo().isToolsProcess() || LoggerFactory.getProcessInfo().isExtProcess()) {
            LoggerFactory.getMonitorLogger().crash(ExceptionID.MONITORPOINT_IGNORE_CRASH, crashCause, null);
            try {
                LoggerFactory.getLogContext().flush(true);
                LoggerFactory.getLogContext().flush("applog", true);
                LoggerFactory.getLogContext().backupCurrentFile("applog", false);
            } catch (Throwable th7) {
            }
        } else {
            LoggerFactory.getTraceLogger().error(a, "uncaughtException: unknown process " + LoggerFactory.getProcessInfo().getProcessAlias(), crashCause);
            LoggerFactory.getMonitorLogger().crash(ExceptionID.MONITORPOINT_IGNORE_CRASH, crashCause, null);
            try {
                LoggerFactory.getLogContext().flush(true);
                LoggerFactory.getLogContext().flush("applog", true);
                LoggerFactory.getLogContext().backupCurrentFile("applog", false);
            } catch (Throwable th8) {
            }
        }
        if (this.c != null) {
            if (CrashConstants.sCrashDefWaitTime > 0 && (LoggerFactory.getProcessInfo().isMainProcess() || LoggerFactory.getProcessInfo().isLiteProcess())) {
                long timeSlept = 0;
                while (timeSlept < CrashConstants.sCrashDefWaitTime && timeSlept < 4000) {
                    try {
                        Thread.sleep(500);
                    } catch (Throwable th9) {
                    }
                    timeSlept += 500;
                }
            }
            try {
                this.c.uncaughtException(thread, crashCause);
            } catch (Throwable th10) {
            }
        }
    }

    private static boolean a(Throwable tr) {
        if (tr == null) {
            return false;
        }
        for (Throwable temp = tr; temp != null; temp = temp.getCause()) {
            if (temp instanceof RuntimeException) {
                String message = temp.getMessage();
                if (!TextUtils.isEmpty(message) && message.contains("InputChannel is not initialized.")) {
                    return true;
                }
            }
            if (temp instanceof OutOfMemoryError) {
                return true;
            }
        }
        return false;
    }

    private static boolean b(Throwable tr) {
        if (tr == null) {
            return false;
        }
        for (Throwable temp = tr; temp != null; temp = temp.getCause()) {
            if (temp instanceof OutOfMemoryError) {
                return true;
            }
        }
        return false;
    }

    public void handleNativeException(String filePath, String callStack) {
        String processAlias = LoggerFactory.getProcessInfo().getProcessAlias();
        String logcatMessage = String.format("a native crash occured in [%s] process [%s] thread. callStack: %s, filePath: %s", new Object[]{processAlias, Thread.currentThread().getName(), callStack, filePath});
        LoggerFactory.getTraceLogger().error(a, logcatMessage);
        LoggingUtil.reflectErrorLogAutomationCrash(logcatMessage);
        if (LoggerFactory.getProcessInfo().isMainProcess() || LoggerFactory.getProcessInfo().isLiteProcess()) {
            if (LoggerFactory.getLogContext().traceNativeCrash(filePath, callStack, false)) {
                NativeCrashHandlerApi.onReportCrash(CrashCombineUtils.UserTrackReport(filePath, callStack), filePath, callStack, true);
            }
        } else if (LoggerFactory.getProcessInfo().isPushProcess() || LoggerFactory.getProcessInfo().isToolsProcess() || LoggerFactory.getProcessInfo().isExtProcess()) {
            NativeCrashHandlerApi.onReportCrash(a(filePath, callStack), filePath, callStack, true);
        } else {
            LoggerFactory.getTraceLogger().error(a, "handleNativeException, error: unknown process " + processAlias);
        }
        StringBuilder message = new StringBuilder("handleNativeException");
        message.append(", filePath: ").append(filePath);
        message.append(", callStack: ").append(callStack);
        message.append(", process: ").append(processAlias);
        message.append(", thread: ").append(Thread.currentThread().getName());
        LoggerFactory.getTraceLogger().error(a, message.toString());
        LoggerFactory.getTraceLogger().error(a, (String) "a native crash occured. END");
        LoggingUtil.reflectErrorLogAutomationCrash("a native crash occured. END");
        LoggerFactory.getLogContext().flush(true);
        LoggerFactory.getLogContext().flush("applog", true);
        LoggerFactory.getLogContext().backupCurrentFile("applog", false);
    }

    private static String a(String filePath, String callStack) {
        String crashInfo = CrashCombineUtils.UserTrackReport(filePath, callStack);
        String logcatMessage = "handleNativeExceptionDirectly, length:" + (crashInfo == null ? -1 : crashInfo.length());
        LoggerFactory.getTraceLogger().error(a, logcatMessage);
        LoggingUtil.reflectErrorLogAutomationCrash(logcatMessage);
        LogContext logContext = LoggerFactory.getLogContext();
        logContext.syncAppendLogEvent(new LogEvent("crash", null, Level.ERROR, new ExceptionRender(logContext).a(ExceptionID.MONITORPOINT_IGNORE_CRASH, crashInfo, null, false, LoggerFactory.getProcessInfo().getProcessAlias(), Thread.currentThread().getName(), true)));
        CrashCombineUtils.deleteFileByPath(filePath);
        LoggerFactory.getTraceLogger().error(a, (String) "handleNativeExceptionDirectly: deleteFileByPath");
        LoggingUtil.reflectErrorLogAutomationCrash("handleNativeExceptionDirectly: deleteFileByPath");
        return crashInfo;
    }
}
