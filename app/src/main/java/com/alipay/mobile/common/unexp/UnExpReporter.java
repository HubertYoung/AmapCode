package com.alipay.mobile.common.unexp;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.MTBizReportName;
import com.alipay.mobile.common.logging.util.FileUtil;
import com.alipay.mobile.common.nativecrash.CrashCombineUtils;
import com.alipay.mobile.common.nativecrash.CrashFilterUtils;
import com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi;
import com.uc.crashsdk.export.LogType;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UnExpReporter {
    public static void handleUnExp(String path) {
        if (TextUtils.isEmpty(path) || !path.endsWith("fg_unexp.log")) {
            LoggerFactory.getTraceLogger().info("UnExpReporter", "handleUnExp but path invalid:" + path);
            return;
        }
        File unExpFile = new File(path);
        if (!unExpFile.exists()) {
            LoggerFactory.getTraceLogger().info("UnExpReporter", "handleUnExp but path not exits:" + path);
        } else if (LoggerFactory.getProcessInfo().isMainProcess()) {
            LoggerFactory.getTraceLogger().error((String) "UnExpReporter", "a unexp crash occured in main process. filePath: " + unExpFile.getAbsolutePath());
            String content = CrashCombineUtils.UserTrackReport(unExpFile.getAbsolutePath(), LogType.UNEXP_TYPE);
            FileUtil.deleteFileNotDir(unExpFile);
            if (!TextUtils.equals(LogType.UNEXP_TYPE, content)) {
                String unExpType = a(content);
                LoggerFactory.getTraceLogger().info("UnExpReporter", "handleUnExp got unExpType:" + unExpType);
                if (!TextUtils.equals("-1", unExpType)) {
                    Map params = new HashMap();
                    params.put("stackFrame", content);
                    LoggerFactory.getMonitorLogger().mtBizReport(MTBizReportName.MTBIZ_APM, "CRASH_FEELING", unExpType, params);
                }
            }
        } else {
            FileUtil.deleteFileNotDir(unExpFile);
        }
    }

    private static String a(String content) {
        if (TextUtils.isEmpty(content)) {
            return "0";
        }
        try {
            if (content.contains("last killed:") || content.contains("last exited:")) {
                return "3";
            }
            if (content.contains("last anr:")) {
                return "4";
            }
            if (content.contains("restart: true")) {
                return "5";
            }
            String recordProductVersion = CrashFilterUtils.getNativeCrashInfo(content, NativeCrashHandlerApi.KEY_LAST_RUNTIME_VERSION);
            String currentVersion = LoggerFactory.getLogContext().getProductVersion();
            if (TextUtils.isEmpty(recordProductVersion) || TextUtils.isEmpty(currentVersion) || TextUtils.equals(recordProductVersion, currentVersion)) {
                String recordCodePath = CrashFilterUtils.getNativeCrashInfo(content, NativeCrashHandlerApi.KEY_LAST_RUNTIME_CODE_PATH);
                String currentCodePath = LoggerFactory.getLogContext().getApplicationContext().getPackageCodePath();
                if (!TextUtils.isEmpty(recordCodePath) && !TextUtils.isEmpty(currentCodePath) && !TextUtils.equals(recordCodePath, currentCodePath)) {
                    LoggerFactory.getTraceLogger().info("UnExpReporter", "getUnExpType got package reinstall");
                    return "-1";
                }
                return "0";
            }
            LoggerFactory.getTraceLogger().info("UnExpReporter", "getUnExpType got version upgrade");
            return "-1";
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "UnExpReporter", tr);
        }
    }
}
