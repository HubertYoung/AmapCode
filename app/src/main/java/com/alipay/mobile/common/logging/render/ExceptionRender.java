package com.alipay.mobile.common.logging.render;

import android.os.Build;
import android.os.Build.VERSION;
import com.alipay.android.phone.maplatformlib.MaPlatformResult;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.common.logging.api.monitor.ExceptionID;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.common.logging.util.NetUtil;
import com.alipay.mobile.common.nativecrash.CrashFilterUtils;
import com.alipay.tianyan.mobilesdk.TianyanLoggingStatus;
import com.uc.crashsdk.export.LogType;
import java.util.Map;

public class ExceptionRender extends BaseRender {
    public ExceptionRender(LogContext logContext) {
        super(logContext);
    }

    public final String a(ExceptionID exceptionID, Throwable ex, String extParam) {
        return a(exceptionID, LoggingUtil.throwableToString(ex), extParam, false, LoggerFactory.getProcessInfo().getProcessAlias(), Thread.currentThread().getName(), false);
    }

    public final String a(ExceptionID exceptionID, String ex, String extParam, boolean traceNativeCrashWhenBoot, String processName, String threadName, boolean isNativeCrash) {
        return a(exceptionID, ex, extParam, traceNativeCrashWhenBoot, processName, threadName, isNativeCrash, null, null);
    }

    public final String a(ExceptionID exceptionID, Throwable ex, String bizType, Map<String, String> extParams) {
        return a(exceptionID, LoggingUtil.throwableToString(ex), null, false, LoggerFactory.getProcessInfo().getProcessAlias(), Thread.currentThread().getName(), false, bizType, extParams);
    }

    private String a(ExceptionID exceptionID, String ex, String extParam, boolean traceNativeCrashWhenBoot, String processName, String threadName, boolean isNativeCrash, String bizType, Map<String, String> extParams) {
        String startupReason;
        StringBuilder msg = new StringBuilder();
        msg.append("e");
        LoggingUtil.appendParam(msg, LoggingUtil.getNowTime());
        LoggingUtil.appendParam(msg, this.b.getProductId());
        LoggingUtil.appendParam(msg, this.b.getProductVersion());
        LoggingUtil.appendParam(msg, "4");
        LoggingUtil.appendParam(msg, this.b.getClientId());
        LoggingUtil.appendExtParam(msg, extParams);
        LoggingUtil.appendParam(msg, this.b.getUserId());
        LoggingUtil.appendParam(msg, LogCategory.CATEGORY_EXCEPTION);
        if (isNativeCrash) {
            ex = a(ex);
        }
        LoggingUtil.appendParam(msg, this.b.getClientStatus(traceNativeCrashWhenBoot, isNativeCrash, ex));
        LoggingUtil.appendParam(msg, bizType);
        String appId = this.b.getStorageParam(LogContext.STORAGE_APPID);
        if (isNativeCrash) {
            appId = CrashFilterUtils.getNativeCrashInfo(ex, LogContext.STORAGE_APPID);
        }
        LoggingUtil.appendParam(msg, appId);
        LoggingUtil.appendParam(msg, null);
        LoggingUtil.appendParam(msg, exceptionID.getDes());
        LoggingUtil.appendParam(msg, ex);
        LoggingUtil.appendParam(msg, LoggingUtil.getTopActivity());
        LoggingUtil.appendParam(msg, this.b.getChannelId());
        LoggingUtil.appendParam(msg, null);
        String refViewId = this.b.getContextParam(LogContext.STORAGE_REFVIEWID);
        if (isNativeCrash) {
            refViewId = CrashFilterUtils.getNativeCrashInfo(ex, LogContext.STORAGE_REFVIEWID);
        }
        LoggingUtil.appendParam(msg, refViewId);
        String viewId = this.b.getContextParam(LogContext.STORAGE_VIEWID);
        if (isNativeCrash) {
            viewId = CrashFilterUtils.getNativeCrashInfo(ex, LogContext.STORAGE_VIEWID);
        }
        LoggingUtil.appendParam(msg, viewId);
        LoggingUtil.appendParam(msg, TianyanLoggingStatus.isMonitorBackground() ? "bg" : "fg");
        LoggingUtil.appendParam(msg, this.b.getStorageParam(LogContext.LOCAL_STORAGE_ACTIONID));
        LoggingUtil.appendParam(msg, this.b.getStorageParam(LogContext.LOCAL_STORAGE_ACTIONTOKEN));
        LoggingUtil.appendParam(msg, Build.MODEL);
        LoggingUtil.appendParam(msg, VERSION.RELEASE);
        LoggingUtil.appendParam(msg, NetUtil.getNetworkTypeOptimized(this.b.getApplicationContext()));
        LoggingUtil.appendParam(msg, extParam);
        LoggingUtil.appendParam(msg, this.b.getReleaseCode());
        LoggingUtil.appendParam(msg, this.b.getDeviceId());
        LoggingUtil.appendParam(msg, this.b.getLanguage());
        LoggingUtil.appendParam(msg, this.b.getHotpatchVersion());
        LoggingUtil.appendParam(msg, processName);
        LoggingUtil.appendParam(msg, threadName);
        LoggingUtil.appendParam(msg, isNativeCrash ? MaPlatformResult.METHOD_NATIVE : LogType.JAVA_TYPE);
        LoggingUtil.appendParam(msg, this.b.getApkUniqueId());
        try {
            startupReason = LoggerFactory.getProcessInfo().getStartupReason().get(ProcessInfo.SR_TO_STRING);
        } catch (Throwable th) {
            startupReason = null;
        }
        LoggingUtil.appendParam(msg, startupReason);
        LoggingUtil.appendExtParam(msg, this.b.getBizExternParams());
        LoggingUtil.appendParam(msg, a());
        return msg.append("$$").toString();
    }
}
