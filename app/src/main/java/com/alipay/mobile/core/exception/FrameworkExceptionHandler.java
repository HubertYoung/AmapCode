package com.alipay.mobile.core.exception;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Debug;
import android.os.Debug.MemoryInfo;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.core.impl.AppExitHelper;
import com.alipay.mobile.core.impl.MicroApplicationContextImpl;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.LauncherApplicationAgent.ExceptionHandlerAgent;
import com.alipay.mobile.framework.LauncherApplicationAgent.StandardExceptionHandlerAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.quinoxless.QuinoxUtilsDecorator.CrashCenter;
import com.alipay.mobile.framework.quinoxless.QuinoxUtilsDecorator.StartupSafeguard;
import com.alipay.mobile.quinox.utils.Constants;
import com.alipay.mobile.quinox.utils.LogUtil;
import com.alipay.mobile.quinox.utils.MonitorLogger;
import com.alipay.mobile.quinox.utils.SharedPreferenceUtil;
import com.alipay.mobile.quinox.utils.SystemUtil;
import com.alipay.mobile.quinox.utils.ThreadDumpUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FrameworkExceptionHandler implements UncaughtExceptionHandler {
    public static final String TAG = "ExceptionHandler";
    private static FrameworkExceptionHandler a;
    private Context b;
    private MicroApplicationContext c;
    private AppExitHelper d;
    private ExceptionHandlerAgent e;
    private List<StandardExceptionHandlerAgent> f = Collections.synchronizedList(new ArrayList());
    private boolean g = false;
    private UncaughtExceptionHandler mDefaultHandler;

    private FrameworkExceptionHandler() {
    }

    public static synchronized FrameworkExceptionHandler getInstance() {
        FrameworkExceptionHandler frameworkExceptionHandler;
        synchronized (FrameworkExceptionHandler.class) {
            if (a == null) {
                a = new FrameworkExceptionHandler();
            }
            frameworkExceptionHandler = a;
        }
        return frameworkExceptionHandler;
    }

    public FrameworkExceptionHandler init(Context ctx, AppExitHelper appExitHelper, ExceptionHandlerAgent agent) {
        if (Thread.getDefaultUncaughtExceptionHandler() instanceof FrameworkExceptionHandler) {
            Log.e(TAG, "Thread.getDefaultUncaughtExceptionHandler() is a FrameworkExceptionHandler");
        } else {
            this.b = ctx;
            this.d = appExitHelper;
            this.e = agent;
            this.c = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
            this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
            try {
                Class hiddenListenerClazz = Class.forName("com.alipay.mobile.common.logging.api.HiddenNativeCrashListener");
                Object hiddenListener = hiddenListenerClazz.newInstance();
                Method register = hiddenListenerClazz.getDeclaredMethod("setAgentListener", new Class[]{String.class, Object.class});
                register.setAccessible(true);
                FNativeCrashListener ncl = new FNativeCrashListener();
                ncl.setAppExitHelper(this.d);
                ncl.setMicroApplicationContext(this.c);
                register.invoke(hiddenListener, new Object[]{"framework", ncl});
            } catch (Throwable tr) {
                TraceLogger.w((String) TAG, tr);
            }
        }
        return this;
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        TraceLogger.i((String) TAG, (String) "FrameworkExceptionHandler got Exception.");
        if ((ex instanceof ClassNotFoundException) || (ex instanceof NoClassDefFoundError)) {
            try {
                StartupSafeguard.stopOptHostClassLoader();
            } catch (Throwable th) {
            }
        }
        try {
            SystemUtil.checkAndDisableArrayMapCache(Log.getStackTraceString(ex));
        } catch (Throwable th2) {
        }
        TraceLogger.i((String) TAG, "mExceptionHandlerAgent is " + (this.e == null ? "null" : this.e.toString()));
        if (this.e instanceof StandardExceptionHandlerAgent) {
            if (((StandardExceptionHandlerAgent) this.e).uncaughtException(this.mDefaultHandler, thread, ex)) {
                return;
            }
        } else if ((this.e instanceof ExceptionHandlerAgent) && this.e.uncaughtException(thread, ex)) {
            return;
        }
        if (LogUtil.isDebug()) {
            a(thread, ex);
            return;
        }
        String threadName = null;
        if (thread == null) {
            try {
                threadName = Thread.currentThread().getName();
            } catch (Throwable tr) {
                TraceLogger.w((String) TAG, tr);
            }
        } else {
            threadName = thread.getName();
        }
        Thread mainLooperThread = null;
        try {
            mainLooperThread = Looper.getMainLooper().getThread();
        } catch (Throwable tr2) {
            TraceLogger.w((String) TAG, tr2);
        }
        if (MicroApplicationContextImpl.MICROAPPLICATIONCONTEXTIMPL_WORKTHREAD.equals(threadName)) {
            TraceLogger.w(TAG, "Exception occurs in worker thread, but it will not crash.\r\n", ex);
        } else if (thread == mainLooperThread || "main".equals(threadName) || (TextUtils.isEmpty(threadName) && mainLooperThread == null)) {
            a(thread, ex);
            return;
        } else if (Constants.LAUNCHER_APPLICATION_INIT.equals(threadName) || Constants.MULTI_DEX_INIT.equals(threadName) || Constants.LOCAL_BROADCAST_MANAGER_SUB_THREAD.equals(threadName)) {
            a(thread, ex);
            return;
        } else if (LogUtil.isDebug()) {
            LogUtil.w(TAG, "Exception occurs in worker thread, but it will not crash.\r\n", ex);
        }
        b(ex);
    }

    private boolean a(Throwable ex) {
        boolean filter = false;
        if ((this.e instanceof StandardExceptionHandlerAgent) && ((StandardExceptionHandlerAgent) this.e).filter(ex)) {
            filter = true;
        } else if ((ex instanceof ClassNotFoundException) && ex.getMessage() != null && ex.getMessage().contains("com.taobao.infsword.service.AppInstallReceiver")) {
            filter = true;
        }
        if (!this.f.isEmpty()) {
            for (StandardExceptionHandlerAgent agent : this.f) {
                try {
                    if (agent.filter(ex) || filter) {
                        filter = true;
                    } else {
                        filter = false;
                    }
                } catch (Throwable e2) {
                    TraceLogger.d((String) TAG, e2);
                }
            }
        }
        return filter;
    }

    private void a(Thread thread, Throwable ex) {
        if (this.mDefaultHandler != null) {
            TraceLogger.e(TAG, "FrameworkExceptionHandler: This is the exception that cause Crash.\r\n", ex);
            CrashCenter.updateLastJavaCrashTime();
            if (ex != null) {
                boolean filtered = false;
                try {
                    filtered = a(ex);
                } catch (Throwable tr) {
                    TraceLogger.w((String) TAG, tr);
                }
                if (!filtered) {
                    MonitorLogger.sendCrash(ex);
                } else {
                    MonitorLogger.sendCrash(MonitorLogger.MONITORPOINT_INVALID_CRASH, ex, null);
                }
            }
            WeakReference weakReference = null;
            if (this.c != null) {
                try {
                    this.c.clearState();
                    this.c.clearTopApps();
                    weakReference = this.c.getTopActivity();
                } catch (Throwable tr2) {
                    TraceLogger.w((String) TAG, tr2);
                }
            }
            if (this.d != null) {
                if (weakReference != null) {
                    try {
                        this.d.finishAllActivities((Activity) weakReference.get(), null);
                    } catch (Throwable tr3) {
                        TraceLogger.w((String) TAG, tr3);
                    }
                } else {
                    this.d.finishAllActivities(null, null);
                }
            }
            Throwable ex2 = new Throwable("NegligibleThrowable", ex);
            this.mDefaultHandler.uncaughtException(thread, ex2);
            Throwable th = ex2;
        }
    }

    public void registerExceptionHandlerAgent(StandardExceptionHandlerAgent agent) {
        this.f.add(agent);
    }

    private void b(Throwable ex) {
        try {
            SharedPreferences sp = SharedPreferenceUtil.getInstance().getSharedPreferences(this.b, Constants.FRAMEWORK_PREFERENCES, 0);
            long curDay = System.currentTimeMillis() / TimeUnit.DAYS.toMillis(1);
            if (curDay != sp.getLong("KEY_CRASH_CUR_DAY", 0)) {
                sp.edit().putLong("KEY_CRASH_CUR_DAY", curDay).putInt("KEY_CRASH_REPORT_COUNT", 1).apply();
            } else {
                int writeLogTime = sp.getInt("KEY_CRASH_REPORT_COUNT", 0) + 1;
                if (writeLogTime <= 10) {
                    sp.edit().putInt("KEY_CRASH_REPORT_COUNT", writeLogTime).apply();
                } else {
                    return;
                }
            }
            MonitorLogger.exception(MonitorLogger.MONITORPOINT_SUB_THREAD_CRASH, ex, (String) null);
        } catch (Throwable t) {
            TraceLogger.w(TAG, "reportSubThreadExceptions error", t);
        }
        boolean printThreads = false;
        try {
            printThreads = c(ex) && !this.g;
            if (printThreads) {
                this.g = true;
            }
            if (printThreads) {
                MemoryInfo[] appMemInfos = ((ActivityManager) LauncherApplicationAgent.getInstance().getSystemService(WidgetType.ACTIVITY)).getProcessMemoryInfo(new int[]{Process.myPid()});
                if (appMemInfos.length > 0) {
                    MemoryInfo memInfo = appMemInfos[0];
                    TraceLogger.i((String) "OOMException", "totalProportional: " + Debug.getPss());
                    TraceLogger.i((String) "OOMException", "dalvikHeapAlloc: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
                    TraceLogger.i((String) "OOMException", "totalPss: " + memInfo.getTotalPss());
                    TraceLogger.i((String) "OOMException", "dalvikPss: " + memInfo.dalvikPss);
                    TraceLogger.i((String) "OOMException", "nativePss: " + memInfo.nativePss);
                    TraceLogger.i((String) "OOMException", "otherPss: " + memInfo.otherPss);
                }
                try {
                    MonitorLogger.flush(false);
                } catch (Throwable th) {
                }
            }
        } catch (Throwable th2) {
        }
        if (printThreads) {
            try {
                ThreadDumpUtil.logAllThreadsTraces();
                try {
                    MonitorLogger.flush(false);
                } catch (Throwable th3) {
                }
            } catch (Throwable th4) {
            }
        }
    }

    private static boolean c(Throwable tr) {
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
}
