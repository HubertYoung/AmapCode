package com.alipay.mobile.common.nativecrash;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.uc.crashsdk.export.CrashApi;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class NativeCrashHandlerApi {
    public static final String CAT_LAST_PRODUCT_INFO = "last product info:";
    public static final String KEY_LAST_RUNTIME_CODE_PATH = "LastRuntimeCodePath";
    public static final String KEY_LAST_RUNTIME_VERSION = "LastRuntimeVersion";
    private static String a = "NativeCrashHandlerApi";
    private static NativeCrashApiGetter b;
    private static OnNativeCrashUploadListener c;
    private static Map<String, ReportCrashListener> d;
    private static volatile int e = -1;
    public static String sLastCodePath = "";
    public static String sLastRunningProductVersion = "";

    public interface NativeCrashApiGetter {
        CrashApi getCrashApi();
    }

    @Deprecated
    public interface OnNativeCrashUploadListener {
        @Deprecated
        void onUpload(String str);
    }

    public interface ReportCrashListener {
        Object onReportCrash(String str, String str2, String str3, String str4, boolean z);
    }

    public static void setNewInstall() {
        CrashApi api = getCrashApi();
        if (api != null) {
            api.setNewInstall();
            LoggerFactory.getTraceLogger().info(a, "setNewInstall");
            return;
        }
        LoggerFactory.getTraceLogger().info(a, "setNewInstall failed, crashApi is null");
    }

    public static void setForeground(boolean isForeground) {
        CrashApi api = getCrashApi();
        if (api == null) {
            LoggerFactory.getTraceLogger().info(a, "setForeground failed, crashApi is null");
        } else if (isForeground && e == 1) {
            return;
        } else {
            if (isForeground || e != 0) {
                api.setForeground(isForeground);
                if (isForeground) {
                    e = 1;
                } else {
                    e = 0;
                }
                LoggerFactory.getTraceLogger().info(a, "setForeground: " + isForeground);
            } else {
                return;
            }
        }
        LoggerFactory.getTraceLogger().info(a, "flush applog");
        LoggerFactory.getLogContext().flush("applog", false);
        LoggerFactory.getTraceLogger().info(a, "flush applog done");
    }

    public static void onExit() {
        CrashApi api = getCrashApi();
        if (api == null) {
            LoggerFactory.getTraceLogger().info(a, "onExit failed, crashApi is null");
        } else if (e != 2) {
            api.onExit();
            e = 2;
            LoggerFactory.getTraceLogger().info(a, "onExit");
        } else {
            return;
        }
        LoggerFactory.getTraceLogger().info(a, "flush applog");
        LoggerFactory.getLogContext().flush("applog", false);
        LoggerFactory.getTraceLogger().info(a, "flush applog done");
    }

    public static void setLastRunningProductVersion(String lastVersion) {
        sLastRunningProductVersion = lastVersion;
        LoggerFactory.getTraceLogger().info(a, "setLastRunningProductVersion: " + sLastRunningProductVersion);
    }

    public static void setLastCodePath(String lastCodePath) {
        sLastCodePath = lastCodePath;
        LoggerFactory.getTraceLogger().info(a, "setLastCodePath: " + sLastCodePath);
    }

    public static boolean addCrashHeadInfo(String key, String value) {
        if (b != null) {
            CrashApi api = b.getCrashApi();
            if (api != null) {
                api.addHeaderInfo(key, value);
                LoggerFactory.getTraceLogger().info(a, "addCrashHeadInfo success, key: " + key);
                return true;
            }
            LoggerFactory.getTraceLogger().warn(a, "addCrashHeadInfo failed, CrashApi is null, key: " + key);
            return false;
        }
        LoggerFactory.getTraceLogger().warn(a, "addCrashHeadInfo failed, crashGetter is null, key: " + key);
        return false;
    }

    public static CrashApi getCrashApi() {
        if (b != null) {
            CrashApi api = b.getCrashApi();
            if (api != null) {
                return api;
            }
            LoggerFactory.getTraceLogger().info(a, "getCrashApi failed, api is null");
        } else {
            LoggerFactory.getTraceLogger().warn(a, (String) "getCrashApi failed, crashGetter is null");
        }
        return null;
    }

    public static void setOnNativeCrashUploadListener(OnNativeCrashUploadListener listener) {
        c = listener;
    }

    public static NativeCrashApiGetter getCrashGetter() {
        return b;
    }

    public static void setCrashGetter(NativeCrashApiGetter crashGetter) {
        b = crashGetter;
    }

    private static void a() {
        if (d == null) {
            synchronized (NativeCrashHandlerApi.class) {
                if (d == null) {
                    d = new LinkedHashMap();
                }
            }
        }
    }

    public static boolean putReportCrashListener(String bizKey, ReportCrashListener listener) {
        if (listener == null || TextUtils.isEmpty(bizKey)) {
            return false;
        }
        a();
        if (d.containsKey(bizKey)) {
            LoggerFactory.getTraceLogger().warn(a, "putReportCrashListener, exist: " + bizKey);
            return false;
        }
        d.put(bizKey, listener);
        LoggerFactory.getTraceLogger().info(a, "putReportCrashListener: " + bizKey);
        return true;
    }

    public static boolean removeReportCrashListener(String bizKey) {
        if (d == null || TextUtils.isEmpty(bizKey)) {
            return false;
        }
        if (d.remove(bizKey) == null) {
            LoggerFactory.getTraceLogger().warn(a, "removeReportCrashListener, not exist: " + bizKey);
            return false;
        }
        LoggerFactory.getTraceLogger().info(a, "removeReportCrashListener: " + bizKey);
        return true;
    }

    public static void onReportCrash(String crashInfo, String filePath, String callStack, boolean isReturnJVM) {
        LoggerFactory.getTraceLogger().info(a, "onReportCrash");
        if (c != null) {
            long deltaTime = System.currentTimeMillis();
            try {
                c.onUpload(crashInfo);
                deltaTime = System.currentTimeMillis() - deltaTime;
                LoggerFactory.getTraceLogger().info(a, deltaTime + " spend, OnNativeCrashUploadListener.onUpload");
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error(a, (System.currentTimeMillis() - deltaTime) + " spend, OnNativeCrashUploadListener.onUpload", t);
            }
        }
        if (d != null && d.size() > 0) {
            long previousTime = System.currentTimeMillis();
            for (Entry entry : d.entrySet()) {
                String bizKey = (String) entry.getKey();
                try {
                    ((ReportCrashListener) entry.getValue()).onReportCrash(bizKey, crashInfo, filePath, callStack, isReturnJVM);
                    long currentTime = System.currentTimeMillis();
                    long deltaTime2 = currentTime - previousTime;
                    previousTime = currentTime;
                    LoggerFactory.getTraceLogger().info(a, deltaTime2 + " spend, ReportCrashListener.onReportCrash: " + bizKey);
                } catch (Throwable t2) {
                    long currentTime2 = System.currentTimeMillis();
                    long deltaTime3 = currentTime2 - previousTime;
                    previousTime = currentTime2;
                    LoggerFactory.getTraceLogger().error(a, deltaTime3 + " spend, ReportCrashListener.onReportCrash: " + bizKey, t2);
                }
            }
        }
    }
}
