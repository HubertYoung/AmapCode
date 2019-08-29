package com.alipay.mobile.nebulauc.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.h5container.service.UcService;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5HttpCacheProvider;
import com.alipay.mobile.nebula.provider.H5ServiceWorkerPushProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebulauc.impl.setup.UcSetupExceptionHelper;
import com.alipay.mobile.nebulauc.impl.setup.UcSetupTracing;
import com.alipay.mobile.nebulauc.provider.UCHttpCacheProvider;
import com.alipay.mobile.nebulauc.provider.UCServiceWorkerProvider;
import com.alipay.mobile.nebulauc.util.CommonUtil;
import com.alipay.mobile.nebulaucsdk.UcSdkConstants;
import com.uc.webview.export.Build;
import com.uc.webview.export.Build.Version;
import com.uc.webview.export.extension.UCCore;

public class UcServiceImpl extends UcService {
    private static final String TAG = "H5UcService";
    /* access modifiers changed from: private */
    public BroadcastReceiver mUcInitSuccessReceiver;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        H5Utils.handleTinyAppKeyEvent((String) "uc_init", (String) "UcServiceImpl.onCreate()");
        H5Log.d(TAG, "onCreate");
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service != null) {
            h5Service.getProviderManager().setProvider(H5HttpCacheProvider.class.getName(), new UCHttpCacheProvider());
            h5Service.getProviderManager().setProvider(H5ServiceWorkerPushProvider.class.getName(), new UCServiceWorkerProvider());
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        H5Log.d(TAG, "onDestroy");
    }

    public static boolean isInLiteIdleTask() {
        return "h5_tiny_initUc_idleTask".equals(Thread.currentThread().getName());
    }

    private synchronized void registerUcInitSuccessReceiver(final boolean enableHA) {
        if (this.mUcInitSuccessReceiver == null) {
            try {
                H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                if (h5EventHandlerService != null) {
                    h5EventHandlerService.onUcInitAbandonedInLiteProcess();
                }
            } catch (Throwable thr) {
                H5Log.w(TAG, "call PerformanceLogger.setUcInitAbandoned() error!", thr);
            }
            this.mUcInitSuccessReceiver = new BroadcastReceiver() {
                /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void onReceive(android.content.Context r6, android.content.Intent r7) {
                    /*
                        r5 = this;
                        java.lang.String r2 = "H5UcService"
                        java.lang.String r3 = "UcInitSuccessReceiver onReceive uc init success event"
                        com.alipay.mobile.nebula.util.H5Log.debug(r2, r3)     // Catch:{ Throwable -> 0x007d }
                        boolean r2 = com.alipay.mobile.nebula.util.H5Utils.isInTinyProcess()     // Catch:{ Throwable -> 0x007d }
                        if (r2 == 0) goto L_0x0017
                        boolean r2 = com.alipay.mobile.h5container.api.H5Flag.ucReady     // Catch:{ Throwable -> 0x007d }
                        if (r2 != 0) goto L_0x0017
                        boolean r2 = com.alipay.mobile.framework.app.ui.ActivityHelper.isBackgroundRunning()     // Catch:{ Throwable -> 0x007d }
                        if (r2 != 0) goto L_0x0032
                    L_0x0017:
                        java.lang.String r2 = "H5UcService"
                        java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x007d }
                        r3.<init>()     // Catch:{ Throwable -> 0x007d }
                        java.lang.String r4 = "UcInitSuccessReceiver cancel! ucReady = "
                        java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x007d }
                        boolean r4 = com.alipay.mobile.h5container.api.H5Flag.ucReady     // Catch:{ Throwable -> 0x007d }
                        java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x007d }
                        java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x007d }
                        com.alipay.mobile.nebula.util.H5Log.debug(r2, r3)     // Catch:{ Throwable -> 0x007d }
                    L_0x0031:
                        return
                    L_0x0032:
                        java.lang.String r2 = "NORMAL"
                        com.alipay.mobile.nebulauc.impl.UcServiceImpl$1$1 r3 = new com.alipay.mobile.nebulauc.impl.UcServiceImpl$1$1     // Catch:{ Throwable -> 0x007d }
                        r3.<init>()     // Catch:{ Throwable -> 0x007d }
                        com.alipay.mobile.nebula.util.H5Utils.runNotOnMain(r2, r3)     // Catch:{ Throwable -> 0x007d }
                        com.alipay.mobile.framework.LauncherApplicationAgent r2 = com.alipay.mobile.framework.LauncherApplicationAgent.getInstance()     // Catch:{ Throwable -> 0x007d }
                        android.app.Application r2 = r2.getApplicationContext()     // Catch:{ Throwable -> 0x007d }
                        android.support.v4.content.LocalBroadcastManager r2 = android.support.v4.content.LocalBroadcastManager.getInstance(r2)     // Catch:{ Throwable -> 0x007d }
                        com.alipay.mobile.nebulauc.impl.UcServiceImpl r3 = com.alipay.mobile.nebulauc.impl.UcServiceImpl.this     // Catch:{ Throwable -> 0x007d }
                        android.content.BroadcastReceiver r3 = r3.mUcInitSuccessReceiver     // Catch:{ Throwable -> 0x007d }
                        r2.unregisterReceiver(r3)     // Catch:{ Throwable -> 0x007d }
                        java.lang.Class<com.alipay.mobile.h5container.service.H5EventHandlerService> r2 = com.alipay.mobile.h5container.service.H5EventHandlerService.class
                        java.lang.String r2 = r2.getName()     // Catch:{ Throwable -> 0x0086 }
                        java.lang.Object r0 = com.alipay.mobile.nebula.util.H5Utils.findServiceByInterface(r2)     // Catch:{ Throwable -> 0x0086 }
                        com.alipay.mobile.h5container.service.H5EventHandlerService r0 = (com.alipay.mobile.h5container.service.H5EventHandlerService) r0     // Catch:{ Throwable -> 0x0086 }
                        if (r0 == 0) goto L_0x0062
                        r0.onUcReInitSuccessInLiteProcess()     // Catch:{ Throwable -> 0x0086 }
                    L_0x0062:
                        java.lang.String r2 = "H5UcService"
                        java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x007d }
                        r3.<init>()     // Catch:{ Throwable -> 0x007d }
                        java.lang.String r4 = "UcInitSuccessReceiver uc reInit over, ucReady = "
                        java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x007d }
                        boolean r4 = com.alipay.mobile.h5container.api.H5Flag.ucReady     // Catch:{ Throwable -> 0x007d }
                        java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x007d }
                        java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x007d }
                        com.alipay.mobile.nebula.util.H5Log.debug(r2, r3)     // Catch:{ Throwable -> 0x007d }
                        goto L_0x0031
                    L_0x007d:
                        r1 = move-exception
                        java.lang.String r2 = "H5UcService"
                        java.lang.String r3 = "UcInitSuccessReceiver reInit uc error!"
                        com.alipay.mobile.nebula.util.H5Log.e(r2, r3, r1)
                        goto L_0x0031
                    L_0x0086:
                        r1 = move-exception
                        java.lang.String r2 = "H5UcService"
                        java.lang.String r3 = "call PerformanceLogger.setUcReInitSuccess() error!"
                        com.alipay.mobile.nebula.util.H5Log.w(r2, r3, r1)     // Catch:{ Throwable -> 0x007d }
                        goto L_0x0062
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulauc.impl.UcServiceImpl.AnonymousClass1.onReceive(android.content.Context, android.content.Intent):void");
                }
            };
            LocalBroadcastManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext()).registerReceiver(this.mUcInitSuccessReceiver, new IntentFilter("uc_init_success_in_main"));
        }
        return;
    }

    public boolean init(boolean enableHA) {
        if (isInLiteIdleTask()) {
            H5Log.d(TAG, "detect lite idleTask!");
            if (!UcSdkConstants.UC_VERSION.equalsIgnoreCase(H5DevConfig.getStringConfig("KEY_LAST_SUCCESS_ODEX_VERSION", null))) {
                H5Log.d(TAG, "not trigger idle task because uc version changed!");
                registerUcInitSuccessReceiver(enableHA);
                UcSetupTracing.addCommonInfo("lite_blocked", "1");
                return false;
            } else if (!H5DevConfig.getBooleanConfig("KEY_MAIN_UCODEX_INIT_SUCCESS", false)) {
                H5Log.d(TAG, "not trigger idle task because main process uc not init!");
                registerUcInitSuccessReceiver(enableHA);
                UcSetupTracing.addCommonInfo("lite_blocked", "2");
                return false;
            }
        }
        markInitThread();
        H5Utils.handleTinyAppKeyEvent((String) "uc_init", (String) "UcServiceImpl.init.start");
        boolean result = UcServiceSetup.init(enableHA);
        H5Utils.handleTinyAppKeyEvent((String) "uc_init", (String) "UcServiceImpl.init.end");
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(H5Utils.getContext());
        Intent intent = new Intent(H5Param.H5_ACTION_UC_INIT_FINISH);
        intent.putExtra("result", result);
        H5Log.d(TAG, "result " + result);
        H5Flag.ucReady = result;
        H5Flag.initUcNormal = result;
        manager.sendBroadcast(intent);
        return result;
    }

    private void markInitThread() {
        String threadName = Thread.currentThread().getName();
        if (!TextUtils.isEmpty(threadName)) {
            UcSetupTracing.addCommonInfo("thread", threadName);
        }
    }

    public APWebView createWebView(Context context, boolean enableHA) {
        UCWebView webView;
        H5Log.d(TAG, "createWebView");
        try {
            UcServiceSetup.init(enableHA);
            webView = UCWebView.getInstance(context);
            if (UcSetupExceptionHelper.isRetryInitUc) {
                UcSetupExceptionHelper.uploadUcRetryResult(true);
            }
        } catch (Throwable throwable) {
            H5Log.e(TAG, "create uc webview exception.", throwable);
            H5Flag.ucReady = false;
            H5Flag.initUcNormal = false;
            webView = null;
            UcServiceSetup.s7zInited = false;
            UcServiceSetup.sUcInited = false;
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_UC_CREATE_FAILED").param1().add(Version.NAME, null).param3().add("isTinyApp", String.valueOf(H5Utils.isInTinyProcess())).add("ucVersion", UcSdkConstants.UC_VERSION).param4().add("ext0", CommonUtil.stringify(throwable)));
        }
        H5Log.d(TAG, "createWebView end");
        return webView;
    }

    public WebResourceResponse getResponse(String url) {
        com.uc.webview.export.WebResourceResponse wrs = UCCore.getResponseByUrl(url);
        if (wrs == null) {
            return null;
        }
        return new WebResourceResponse(wrs.getMimeType(), wrs.getEncoding(), wrs.getData());
    }

    public String initUC7zSo() {
        return "unsupport";
    }

    public String getUcVersion() {
        return Version.NAME + "_" + Build.CORE_TIME;
    }
}
