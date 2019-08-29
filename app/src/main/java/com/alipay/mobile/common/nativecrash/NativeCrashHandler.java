package com.alipay.mobile.common.nativecrash;

import android.content.Context;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi.NativeCrashApiGetter;
import com.autonavi.widget.ui.BalloonLayout;
import com.uc.crashsdk.export.CrashApi;
import com.uc.crashsdk.export.CustomInfo;
import com.uc.crashsdk.export.VersionInfo;
import java.io.File;

public class NativeCrashHandler {
    public static boolean ENABLE_NATIVE_LOG = true;
    public static boolean ENABLE_UNEXP_LOG = false;
    public static boolean ENANBLE_JAVA_LOG = false;
    public static final String FILE_PATH = "app_crash";
    private static String a;
    /* access modifiers changed from: private */
    public static CrashApi b = null;

    public static final synchronized String getPath() {
        String str;
        synchronized (NativeCrashHandler.class) {
            str = a;
        }
        return str;
    }

    public static void initialize(Context context) {
        a = context.getApplicationInfo().dataDir + File.separator + FILE_PATH;
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.mVersion = LoggerFactory.getLogContext().getProductVersion();
        CrashApi createInstance = CrashApi.createInstance(context, a(), versionInfo, new CrashClientImpl(), null, ENANBLE_JAVA_LOG, ENABLE_NATIVE_LOG, ENABLE_UNEXP_LOG);
        b = createInstance;
        createInstance.setCrashLogUploadUrl(null);
        a(context);
        NativeCrashHandlerApi.setCrashGetter(new NativeCrashApiGetter() {
            public final CrashApi getCrashApi() {
                return NativeCrashHandler.b;
            }
        });
        b.addHeaderInfo(CrashFilterUtils.MPAAS_PRODUCT_VERSION, LoggerFactory.getLogContext().getProductVersion());
        if (ENABLE_UNEXP_LOG && LoggerFactory.getProcessInfo().isMainProcess()) {
            b.registerInfoCallback(NativeCrashHandlerApi.CAT_LAST_PRODUCT_INFO, 256);
        }
        LoggerFactory.getTraceLogger().info("NativeCrashHandler", "initialize ok");
    }

    private static CustomInfo a() {
        CustomInfo info = new CustomInfo();
        info.mCrashLogPrefix = CrashCombineUtils.TOMB;
        info.mMaxNativeLogcatLineCount = 1000;
        info.mMaxUnexpLogcatLineCount = 500;
        info.mMaxJavaLogcatLineCount = 1;
        info.mMaxCrashLogFilesCount = 3;
        info.mCrashLogsFolderName = FILE_PATH;
        info.mZipLog = false;
        info.mOmitJavaCrash = false;
        info.mCallJavaDefaultHandler = true;
        info.mUnexpDelayMillSeconds = 5000;
        info.mSyncUploadSetupCrashLogs = false;
        return info;
    }

    private static void a(Context context) {
        try {
            if (LoggingUtil.loadLibrary(context, "crashsdk")) {
                b.crashSoLoaded();
                if (ENABLE_UNEXP_LOG && LoggerFactory.getProcessInfo().isMainProcess()) {
                    new Thread(new Runnable() {
                        public final void run() {
                            try {
                                Thread.sleep(BalloonLayout.DEFAULT_DISPLAY_DURATION);
                            } catch (Throwable th) {
                            }
                            try {
                                NativeCrashHandler.b.setMainProcess();
                            } catch (Throwable tr) {
                                LoggerFactory.getTraceLogger().error("NativeCrashHandler", "setMainProcess exception", tr);
                            }
                        }
                    }, "prepareCrashInfo").start();
                    return;
                }
                return;
            }
            LoggerFactory.getTraceLogger().error((String) "NativeCrashHandler", (String) "loadLibrary failed");
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error("NativeCrashHandler", "loadLibrary exception", e);
        }
    }
}
