package com.autonavi.miniapp.monitor.util;

import android.content.Context;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.common.logging.util.LogcatUtil;
import com.alipay.mobile.common.logging.util.LoggingSPCache;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.common.logging.util.avail.ExceptionCollector;
import com.alipay.mobile.common.logging.util.avail.ExceptionData;
import com.alipay.mobile.common.logging.util.config.LogConfigUtils;
import java.io.File;

public class TransUtils {
    private static final String TAG = "MonitorTransUtils";

    public static boolean isOfflineForExternalFile() {
        return LoggingUtil.isOfflineForExternalFile();
    }

    public static File getCommonExternalStorageDir() {
        return LoggingUtil.getCommonExternalStorageDir();
    }

    public static boolean uploadLeisureLogcategory(boolean z) {
        return LogConfigUtils.uploadLeisureLogcategory(z);
    }

    public static void exceptionCollectorRecordException(Context context) {
        ExceptionCollector.getInstance(context).recordException(ExceptionData.TYPE_ANR);
    }

    public static void updateLogContext(String str, String str2) {
        if (LoggingSPCache.STORAGE_CHANNELID.equals(str)) {
            LoggerFactory.getLogContext().setChannelIdNoCommit(str2);
        } else if (LoggingSPCache.STORAGE_RELEASETYPE.equals(str)) {
            LoggerFactory.getLogContext().setReleaseTypeNoCommit(str2);
        } else if (LoggingSPCache.STORAGE_RELEASECODE.equals(str)) {
            LoggerFactory.getLogContext().setReleaseCodeNoCommit(str2);
        } else if (LoggingSPCache.STORAGE_PRODUCTID.equals(str)) {
            LoggerFactory.getLogContext().setProductIdNoCommit(str2);
        } else if (LoggingSPCache.STORAGE_PRODUCTVERSION.equals(str)) {
            LoggerFactory.getLogContext().setProductVersionNoCommit(str2);
        } else if (LoggingSPCache.STORAGE_USERID.equals(str)) {
            LoggerFactory.getLogContext().setUserIdNoCommit(str2);
        } else if (LoggingSPCache.STORAGE_CLIENTID.equals(str)) {
            LoggerFactory.getLogContext().setClientIdNoCommit(str2);
        } else if ("utdid".equals(str)) {
            LoggerFactory.getLogContext().setDeviceIdNoCommit(str2);
        } else if ("language".equals(str)) {
            LoggerFactory.getLogContext().setLanguageNoCommit(str2);
        } else if (LoggingSPCache.STORAGE_HOTPATCHVERSION.equals(str)) {
            LoggerFactory.getLogContext().setHotpatchVersionNoCommit(str2);
        } else if (LoggingSPCache.STORAGE_BUNDLEVERSION.equals(str)) {
            LoggerFactory.getLogContext().setBundleVersionNoCommit(str2);
        } else if (LoggingSPCache.STORAGE_BIRDNESTVERSION.equals(str)) {
            LoggerFactory.getLogContext().setBirdNestVersionNoCommit(str2);
        } else if (LoggingSPCache.STORAGE_PACKAGEID.equals(str)) {
            LoggerFactory.getLogContext().setPackageIdNoCommit(str2);
        } else if (LoggingSPCache.STORAGE_LOGHOST.equals(str)) {
            LoggerFactory.getLogContext().setLogHostNoCommit(str2);
        } else {
            TraceLogger traceLogger = LoggerFactory.getTraceLogger();
            StringBuilder sb = new StringBuilder("not mapping, type: ");
            sb.append(str);
            sb.append(", value: ");
            sb.append(str2);
            traceLogger.error((String) TAG, sb.toString());
        }
    }

    public static void dumpLogcat(File file, int i) {
        LogcatUtil.dumpLogcat(file, i);
    }
}
