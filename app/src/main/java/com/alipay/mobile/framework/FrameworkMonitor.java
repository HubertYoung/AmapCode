package com.alipay.mobile.framework;

import android.content.Context;
import android.util.Log;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.common.logging.api.monitor.MTBizReportName;
import com.alipay.mobile.common.logging.util.avail.ExceptionData;
import com.alipay.mobile.quinox.bundle.Bundle;
import com.alipay.mobile.quinox.utils.LiteProcessInfo;
import com.alipay.mobile.quinox.utils.MonitorLogger;
import com.alipay.mobile.quinox.utils.TraceLogger;
import com.alipay.sdk.authjs.a;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FrameworkMonitor {
    public static final String BUNDLE_CLASSLOADER_NOT_FOUND = "1002";
    public static final String BUNDLE_INIT_EXCEPTION = "1000";
    public static final String BUNDLE_LOAD_EXCEPTION = "1001";
    public static final String MICROAPP_STARTUP_FAIL_CREATE_FAIL = "2004";
    public static final String MICROAPP_STARTUP_FAIL_DOSTARTAPP_CALL_REJECT = "2002";
    public static final String MICROAPP_STARTUP_FAIL_DOSTARTAPP_EXE_REJECT = "2003";
    public static final String MICROAPP_STARTUP_FAIL_MAC_STUCK = "2009";
    public static final String MICROAPP_STARTUP_FAIL_NEED_LOGIN = "2007";
    public static final String MICROAPP_STARTUP_FAIL_NOT_FOUND = "1000";
    public static final String MICROAPP_STARTUP_FAIL_RESTART_FAIL = "2005";
    public static final String MICROAPP_STARTUP_FAIL_STARTAPP_EXE_REJECT = "2001";
    public static final String MICROAPP_STARTUP_FAIL_TINYAPP_FAIL = "2006";
    public static final String MICROAPP_STARTUP_FAIL_WAIT_AUTH = "2008";
    private static final String a = FrameworkMonitor.class.getSimpleName();
    private static FrameworkMonitor h;
    private Context b;
    private boolean c = false;
    private Set<String> d;
    private Set<String> e;
    private Set<String> f;
    private int g = 0;

    private FrameworkMonitor(Context context) {
        this.b = context;
        if (context != null) {
            try {
                this.c = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getBoolean("enable.framework.monitor", false);
            } catch (Exception e2) {
                TraceLogger.e(a, (Throwable) e2);
            }
        }
    }

    public static FrameworkMonitor getInstance(Context context) {
        if (h == null) {
            synchronized (FrameworkMonitor.class) {
                try {
                    if (h == null) {
                        Context appContext = context;
                        if (context == null) {
                            appContext = LauncherApplicationAgent.getInstance().getApplicationContext();
                        }
                        h = new FrameworkMonitor(appContext);
                    }
                }
            }
        }
        return h;
    }

    public void handleDescriptionCfgLoadFail(String reason, String msg) {
        Map extParams = new HashMap();
        if (msg != null) {
            extParams.put("msg", msg);
        }
        mtBizReport("DESCRIPTION_CFG_LOAD_FAIL", reason, extParams);
    }

    public void handleDescriptionCfgStale(String resAppVersion, String classDefVersion) {
        Map extParams = new HashMap();
        if (classDefVersion != null) {
            extParams.put("classDefVersion", classDefVersion);
        }
        mtBizReport("DESCRIPTION_CFG_IS_STALE", resAppVersion, extParams);
    }

    public void handleBundleStale(Bundle bundle, long size, String md5) {
        Map extParams = new HashMap();
        extParams.put("md5", md5);
        extParams.put("size", Long.toString(size));
        extParams.put("location", bundle.getLocation());
        extParams.put("expectMd5", bundle.getMD5());
        extParams.put("expectSize", Long.toString(bundle.getSize()));
        mtBizReport("BUNDLE_IS_STALE", bundle.getName(), extParams);
    }

    public synchronized void handleAppNotFound(String appId, String reason) {
        if (this.d == null) {
            this.d = new HashSet();
        }
        if (!this.d.contains(appId)) {
            this.d.add(appId);
            Map extParams = new HashMap();
            extParams.put("reason", reason);
            mtBizReport("DESCRIPTION_NOT_FOUND_APP", appId, extParams);
        }
    }

    public synchronized void handleServiceNotFound(String serviceName) {
        if (!"com.alipay.tiny.api.TinyExternalService".equals(serviceName)) {
            if (this.d == null) {
                this.d = new HashSet();
            }
            if (LiteProcessInfo.g(this.b).isCurrentProcessALiteProcess()) {
                TraceLogger.w(a, (String) "skip service not found, when in lite process");
            } else if (!this.d.contains(serviceName)) {
                this.d.add(serviceName);
                mtBizReport("DESCRIPTION_NOT_FOUND_SERVICE", serviceName, null);
            }
        }
    }

    public synchronized void handleDescriptionInitFail(MicroDescription description, Throwable throwable) {
        if (this.e == null) {
            this.e = new HashSet();
        }
        if (description != null && !this.e.contains(description.getClassName())) {
            this.e.add(description.getClassName());
            Map extParams = new HashMap();
            if (description != null) {
                extParams.put("desc", description.toString());
            }
            if (throwable != null) {
                extParams.put("msg", Log.getStackTraceString(throwable));
            }
            mtBizReport("DESCRIPTION_INIT_FAIL", description.getClassName(), extParams);
        }
    }

    public synchronized void handleBundleClassLoaderNotFound(String bundleName, Throwable throwable) {
        if (this.f == null) {
            this.f = new HashSet();
        }
        if (bundleName != null && !this.f.contains(bundleName)) {
            this.f.add(bundleName);
            Map extParams = new HashMap();
            if (throwable != null) {
                extParams.put("msg", Log.getStackTraceString(throwable));
            }
            mtBizReport("DESCRIPTION_NOT_FOUND_CLASSLOADER", bundleName, extParams);
        }
    }

    public void handleBundleLocationNotFound(String bundleName, File bundleFile) {
        Map extParams = new HashMap();
        if (bundleFile != null) {
            extParams.put("path", bundleFile.getAbsolutePath());
        }
        mtBizReport("BUNDLE_LOCATION_NOT_FOUND", bundleName, extParams);
    }

    public void handleAppStartupReject(String appId, String rejectType, Set<Advice> rejectAdvices) {
        if (rejectAdvices != null && !rejectAdvices.isEmpty()) {
            Map extParams = new HashMap();
            String rejectAdviceName = null;
            try {
                rejectAdviceName = rejectAdvices.iterator().next().getClass().getName();
                if (!"com.alipay.mobile.nebulabiz.nebulahandler.H5StartAppAdvice".equals(rejectAdviceName) && !"com.alipay.mobile.liteprocess.advice.StartAppAdvice".equals(rejectAdviceName) && !"com.alipay.mobile.security.gesture.service.h".equals(rejectAdviceName) && !"com.alipay.android.phone.businesscommon.message.MessageSwitcherAdvice".equals(rejectAdviceName) && !"com.alipay.android.phone.wallet.buscode.BusCodeH5Advice".equals(rejectAdviceName)) {
                    extParams.put("type", rejectType);
                    extParams.put("appId", appId);
                    extParams.put("stackFrame", appId + "###" + rejectType);
                    mtBizReport("MICROAPP_STARTUP_REJECT", rejectAdviceName, extParams);
                }
            } catch (Throwable t) {
                TraceLogger.e(a, t);
            }
        }
    }

    public void handleMicroAppStartupFail(String appId, String errorCode) {
        Map extParams = new HashMap();
        if (appId != null) {
            extParams.put("appId", appId);
            extParams.put("stackFrame", appId + "###" + errorCode);
        }
        mtBizReport(MTBizReportName.FRAME_MICROAPP_STARTUP_FAIL, errorCode, extParams);
        MonitorLogger.cuRecordException(LauncherApplicationAgent.getInstance().getApplicationContext(), ExceptionData.TYPE_START_APP_FAIL);
    }

    public void handleMicroAppStartupSuccess(String appId) {
        MonitorLogger.cuClearException(LauncherApplicationAgent.getInstance().getApplicationContext(), ExceptionData.TYPE_START_APP_FAIL);
    }

    public void handleLoadBundleFail(String bundleName, String errorCode) {
        Map extParams = new HashMap();
        if (bundleName != null) {
            extParams.put(a.d, bundleName);
        }
        mtBizReport("BUNDLE_LOAD_FAIL", errorCode, extParams);
    }

    public void handleLoadingPagePending(String appId, String timeout) {
        Map extParams = new HashMap();
        extParams.put("timeout", timeout);
        mtBizReport("LOADING_PAGE_PENDING", appId, extParams);
    }

    public void mtBizReport(String subName, String failCode, Map<String, String> extParams) {
        if (this.c) {
            this.g++;
            if (this.g <= 100) {
                MonitorLogger.mtBizReport(MTBizReportName.MTBIZ_FRAME, subName, failCode, extParams);
            } else {
                TraceLogger.i(a, "exceed report limit:" + this.g);
            }
        }
    }
}
