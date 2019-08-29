package com.alipay.mobile.common.logging;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.common.logging.impl.BaseExceptionHandler;
import com.alipay.mobile.common.logging.impl.BehavorloggerImpl;
import com.alipay.mobile.common.logging.impl.MonitorLoggerImpl;
import com.alipay.mobile.common.logging.impl.StatisticalExceptionHandler;
import com.alipay.mobile.common.logging.impl.TraceLoggerImpl;
import com.alipay.mobile.common.logging.util.HybridEncryption;
import com.alipay.mobile.common.logging.util.LoggingSPCache;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.common.logging.util.ReflectUtil;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.lang.reflect.Method;

public class LoggerFactoryBinder {
    private static boolean a;

    public static native int nativeFree();

    public static native int nativeInit();

    public static void bind(Context context) {
        if (a) {
            Log.e(LoggerFactory.TAG, "bind", new IllegalStateException("LoggerFactory.bind repeated"));
            return;
        }
        a = true;
        ProcessInfoImpl processInfo = new ProcessInfoImpl(context);
        LoggerFactory.attachProcessInfo(processInfo);
        LoggerFactory.bindImpls(new DevicePropertyImpl(context));
        LoggingSPCache.createInstance(context);
        BaseExceptionHandler.createInstance();
        StatisticalExceptionHandler.createInstance(context);
        HybridEncryption.createInstance(context);
        LogContextImpl logContext = new LogContextImpl(context);
        LoggerFactory.attachLogContext(logContext);
        logContext.a();
        a(context);
        b(context);
        TraceLogger traceLogger = new TraceLoggerImpl(logContext);
        LoggerFactory.bind(traceLogger, new BehavorloggerImpl(logContext), new MonitorLoggerImpl(logContext));
        traceLogger.info(LoggerFactory.TAG, LoggingUtil.concatArray(",", Build.BRAND, Build.MANUFACTURER, Build.DISPLAY, Build.MODEL, VERSION.RELEASE, Oauth2AccessToken.KEY_UID, Integer.valueOf(processInfo.getUserId()), "pid", Integer.valueOf(processInfo.getProcessId()), processInfo.getProcessAlias(), logContext.getReleaseType(), logContext.getProductVersion(), logContext.getUserId(), "patchVer", logContext.getHotpatchVersion(), logContext.getApkUniqueId(), "bundleVer", logContext.getBundleVersion(), "birdNest", logContext.getBirdNestVersion()));
        processInfo.prepareStartupReason();
        a();
        if (processInfo.isMainProcess() && LoggingUtil.loadLibrary(context, "logging")) {
            try {
                nativeInit();
            } catch (Throwable t) {
                traceLogger.error(LoggerFactory.TAG, "nativeInit", t);
            }
        }
        if (processInfo.isMainProcess() || processInfo.isLiteProcess()) {
            try {
                if (context.getPackageManager().getPackageInfo(context.getPackageName(), 128).applicationInfo.metaData.getBoolean("perf_test")) {
                    ReflectUtil.invokeMethod("com.alipay.loginterceptor.LogInterceptManager", "init", new Class[]{Context.class}, null, new Object[]{context});
                }
            } catch (Throwable th) {
            }
        }
        if (processInfo.isMainProcess()) {
            new Thread(new d(logContext), "CreateLogSession").start();
        }
    }

    private static void a(Context context) {
        try {
            Method method = Class.forName("com.alipay.mobile.monitor.track.spm.SpmMonitorBinder").getDeclaredMethod("bind", new Class[]{Context.class});
            method.setAccessible(true);
            method.invoke(null, new Object[]{context});
        } catch (Throwable e) {
            Log.e(LoggerFactory.TAG, "initSpmMonitor", e);
        }
    }

    private static void b(Context context) {
        try {
            Method method = Class.forName("com.alipay.android.phone.wallet.tinytracker.TinyPageMonitorBinder").getDeclaredMethod("bind", new Class[]{Context.class});
            method.setAccessible(true);
            method.invoke(null, new Object[]{context});
        } catch (Throwable e) {
            Log.e(LoggerFactory.TAG, "initTinyPageMonitor", e);
        }
    }

    private static void a() {
        String brand = LoggerFactory.getDeviceProperty().getBrandName();
        if (!TextUtils.isEmpty(brand)) {
            LoggerFactory.getLogContext().putBizExternParams("brand", brand);
        }
        String romVersion = LoggerFactory.getDeviceProperty().getRomVersion();
        if (!TextUtils.isEmpty(romVersion)) {
            LoggerFactory.getLogContext().putBizExternParams("romVersion", romVersion);
        }
    }

    public static void write(int prio, String tag, String msg, Throwable tr) {
        if (tag != null && msg != null) {
            switch (prio) {
                case 0:
                    return;
                case 1:
                    LoggerFactory.getTraceLogger().verbose(tag, msg);
                    return;
                case 2:
                    LoggerFactory.getTraceLogger().debug(tag, msg);
                    return;
                case 3:
                    LoggerFactory.getTraceLogger().info(tag, msg);
                    return;
                case 4:
                    if (tr == null) {
                        LoggerFactory.getTraceLogger().warn(tag, msg);
                        return;
                    } else {
                        LoggerFactory.getTraceLogger().warn(tag, msg, tr);
                        return;
                    }
                case 5:
                    if (tr == null) {
                        LoggerFactory.getTraceLogger().error(tag, msg);
                        return;
                    } else {
                        LoggerFactory.getTraceLogger().error(tag, msg, tr);
                        return;
                    }
                default:
                    Log.e(LoggerFactory.TAG, "native log with error prio");
                    return;
            }
        }
    }
}
