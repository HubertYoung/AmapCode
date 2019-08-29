package com.alipay.mobile.common.nativecrash;

import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.impl.StatisticalExceptionHandler;
import com.alipay.mobile.common.unexp.UnExpReporter;
import com.uc.crashsdk.export.ICrashClient;
import com.uc.crashsdk.export.LogType;
import java.io.File;

public class CrashClientImpl implements ICrashClient {
    public boolean onAddCrashStats(String arg0, int arg1, int arg2) {
        return false;
    }

    public void onCrashRestarting(boolean arg0) {
    }

    public String onGetCallbackInfo(String arg0) {
        try {
            if (NativeCrashHandlerApi.CAT_LAST_PRODUCT_INFO.equalsIgnoreCase(arg0)) {
                String version = LoggerFactory.getLogContext().getProductVersion();
                if (!TextUtils.isEmpty(NativeCrashHandlerApi.sLastRunningProductVersion)) {
                    version = NativeCrashHandlerApi.sLastRunningProductVersion;
                }
                String codePath = LoggerFactory.getLogContext().getApplicationContext().getPackageCodePath();
                if (!TextUtils.isEmpty(NativeCrashHandlerApi.sLastCodePath)) {
                    codePath = NativeCrashHandlerApi.sLastCodePath;
                }
                return (("LastRuntimeVersion: " + version) + "\n") + "LastRuntimeCodePath: " + codePath;
            }
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "CrashClientImpl", tr);
        }
        return null;
    }

    public void onLogGenerated(File file, String logType) {
        if (file != null) {
            Log.w("CrashClientImpl", "logType: " + logType + " onLogGenerated = " + file.getAbsolutePath());
            if (LogType.NATIVE_TYPE.equalsIgnoreCase(logType)) {
                StatisticalExceptionHandler.getInstance().handleNativeException(file.getAbsolutePath(), logType);
            } else if (LogType.UNEXP_TYPE.equalsIgnoreCase(logType)) {
                try {
                    UnExpReporter.handleUnExp(file.getAbsolutePath());
                    if (file.exists()) {
                        LoggerFactory.getTraceLogger().warn((String) "CrashClientImpl", "delete on file:" + file.getAbsolutePath() + " result: " + file.delete());
                    } else {
                        Log.w("CrashClientImpl", logType + " onLogGenerated but log file not exist.");
                    }
                } catch (Throwable tr) {
                    LoggerFactory.getTraceLogger().warn((String) "CrashClientImpl", tr);
                }
            } else if (LogType.JAVA_TYPE.equalsIgnoreCase(logType)) {
                try {
                    if (file.exists()) {
                        LoggerFactory.getTraceLogger().warn((String) "CrashClientImpl", "delete on file:" + file.getAbsolutePath() + " result: " + file.delete());
                    } else {
                        Log.w("CrashClientImpl", logType + " onLogGenerated but log file not exist.");
                    }
                } catch (Throwable tr2) {
                    LoggerFactory.getTraceLogger().warn((String) "CrashClientImpl", tr2);
                }
            } else {
                try {
                    if (file.exists()) {
                        LoggerFactory.getTraceLogger().warn((String) "CrashClientImpl", "delete on file:" + file.getAbsolutePath() + " result: " + file.delete());
                    } else {
                        Log.w("CrashClientImpl", logType + " onLogGenerated but log file not exist.");
                    }
                    LoggerFactory.getLogContext().flush(true);
                    LoggerFactory.getLogContext().flush("applog", true);
                } catch (Throwable tr3) {
                    LoggerFactory.getTraceLogger().warn((String) "CrashClientImpl", tr3);
                }
            }
        }
    }

    public File onBeforeUploadLog(File file) {
        return null;
    }
}
