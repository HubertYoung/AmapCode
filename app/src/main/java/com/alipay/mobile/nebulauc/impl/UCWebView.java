package com.alipay.mobile.nebulauc.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.MutableContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.Rect;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.androidannotations.utils.PermissionUtils;
import com.alipay.android.phone.mobilesdk.storage.sp.APSharedPreferences;
import com.alipay.android.phone.mobilesdk.storage.sp.SharedPreferencesManager;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.framework.permission.RequestPermissionsResultCallback;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5PermissionCallBack;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.appcenter.res.H5ResourceManager;
import com.alipay.mobile.nebula.data.H5Trace;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5DeviceHelper;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.IH5EmbedViewJSCallback;
import com.alipay.mobile.nebula.webview.APDownloadListener;
import com.alipay.mobile.nebula.webview.APHitTestResult;
import com.alipay.mobile.nebula.webview.APWebBackForwardList;
import com.alipay.mobile.nebula.webview.APWebChromeClient;
import com.alipay.mobile.nebula.webview.APWebSettings;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebula.webview.APWebViewClient;
import com.alipay.mobile.nebula.webview.APWebViewListener;
import com.alipay.mobile.nebula.webview.H5ScrollChangedCallback;
import com.alipay.mobile.nebula.webview.WebViewType;
import com.alipay.mobile.nebulauc.R;
import com.alipay.mobile.nebulauc.impl.setup.UcArSetup;
import com.alipay.mobile.nebulauc.impl.setup.UcNetworkSetup;
import com.alipay.mobile.nebulauc.impl.setup.UcOtherBizSetup;
import com.alipay.mobile.nebulauc.impl.setup.UcServiceWorkerSetup;
import com.alipay.mobile.nebulauc.impl.setup.UcSetupTracing;
import com.alipay.mobile.nebulauc.impl.setup.UcVideoSetup;
import com.alipay.mobile.nebulauc.impl.view.H5InputBoardProviderImpl;
import com.alipay.mobile.nebulauc.input.H5NumInputKeyboard;
import com.alipay.mobile.nebulauc.input.H5NumInputKeyboardM40;
import com.alipay.mobile.nebulauc.input.H5NumInputKeyboardM57;
import com.alipay.mobile.nebulauc.nativeinput.H5NativeInputPlugin;
import com.alipay.mobile.nebulauc.plugin.H5WirelessDebugPlugin;
import com.alipay.mobile.nebulauc.util.CommonUtil;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.alipay.mobile.nebulauc.view.UCEmbededViewWrapper;
import com.alipay.mobile.nebulaucsdk.UcSdkConstants;
import com.alipay.mobile.tinyappcommon.subpackage.TinyAppSubPackagePlugin;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import com.alipay.mobile.webar.GeneralPermissionsManager;
import com.alipay.mobile.webar.GeneralPermissionsManager.IGeneralPermissions;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import com.uc.webview.export.Build;
import com.uc.webview.export.Build.Version;
import com.uc.webview.export.WebBackForwardList;
import com.uc.webview.export.WebView;
import com.uc.webview.export.WebView.HitTestResult;
import com.uc.webview.export.extension.EmbedViewConfig;
import com.uc.webview.export.extension.IEmbedView;
import com.uc.webview.export.extension.IEmbedViewContainer;
import com.uc.webview.export.extension.IEmbedViewContainer.OnParamChangedListener;
import com.uc.webview.export.extension.IEmbedViewContainer.OnStateChangedListener;
import com.uc.webview.export.extension.IEmbedViewContainer.OnVisibilityChangedListener;
import com.uc.webview.export.extension.UCClient;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.extension.UCExtension.InjectJSProvider;
import com.uc.webview.export.extension.UCSettings;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

public class UCWebView implements APWebView {
    public static final String TAG = "H5UCWebView";
    private static final String WEBAR_URL_WHITE_LIST_KEY = "webar_url_white_list";
    public static final String WEBVIEW_VERSION = (Version.NAME + "_" + Build.CORE_TIME);
    private static boolean ifRedirectDNS4UC = true;
    /* access modifiers changed from: private */
    public static int sActualProcessMode = 0;
    /* access modifiers changed from: private */
    public static BroadcastReceiver sAppxResourceLoadedReceiver;
    private static boolean sHasCheckRenderProcessReady = false;
    /* access modifiers changed from: private */
    public static boolean sHasDestroyNonIsolateStaticWebView = false;
    /* access modifiers changed from: private */
    public static boolean sHasReportRenderProcessLaunchFailed = false;
    private static boolean sIsAppxJsPreloaded = false;
    private static boolean sNeedReportTrace = false;
    private static final Queue<UCWebView> sPool = new ConcurrentLinkedQueue();
    /* access modifiers changed from: private */
    public static boolean sRenderProcessReady = false;
    private static boolean sUcInitStuffDone = false;
    /* access modifiers changed from: private */
    public static boolean sWaitPreCreating = false;
    /* access modifiers changed from: private */
    public static UCWebView sWebviewForMultiProcess = null;
    /* access modifiers changed from: private */
    public APWebViewListener apWebViewListener;
    /* access modifiers changed from: private */
    public Context context;
    private H5NativeInputPlugin h5NativeInputPlugin;
    private String ifRedirectDNS4UCConfigStr;
    private boolean mFirstLoadUrl;
    /* access modifiers changed from: private */
    public H5NumInputKeyboard mH5NumInputKeyboard;
    /* access modifiers changed from: private */
    public H5ScrollChangedCallback mH5ScrollChangedCallback;
    private boolean mIsMultiProcessPreCreate;
    private boolean mPageStartUnCalled;
    private float mScale;
    private APWebSettings webSettings;
    /* access modifiers changed from: private */
    public WebView webView;

    private class ProxyHitTestResult implements APHitTestResult {
        HitTestResult mUCHitTestResult;

        ProxyHitTestResult(HitTestResult hitTestResult) {
            this.mUCHitTestResult = hitTestResult;
        }

        public String getExtra() {
            return this.mUCHitTestResult.getExtra();
        }

        public int getType() {
            return this.mUCHitTestResult.getType();
        }
    }

    private final class WebViewEx extends WebView {
        private WebViewEx(Context context) {
            super(context);
        }

        public boolean coreOverScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            if (deltaY >= 0 || scrollY != 0 || UCWebView.this.apWebViewListener == null) {
                return super.coreOverScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
            }
            return UCWebView.this.apWebViewListener.overScrollBy(deltaX, deltaY, scrollX, scrollY);
        }

        /* access modifiers changed from: protected */
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (UCWebView.this.apWebViewListener != null) {
                UCWebView.this.apWebViewListener.onDetachedFromWindow();
            }
        }

        public void coreOnScrollChanged(int l, int t, int oldl, int oldt) {
            if (UCWebView.this.mH5ScrollChangedCallback != null) {
                UCWebView.this.mH5ScrollChangedCallback.onScroll(l - oldl, t - oldt);
            }
            super.coreOnScrollChanged(l, t, oldl, oldt);
        }
    }

    static void preCreateOnMainWithDelay(int delay) {
        if (UcServiceSetup.sWebViewPoolSize > 0 && sPool.size() < UcServiceSetup.sWebViewPoolSize && !sWaitPreCreating) {
            sWaitPreCreating = true;
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    try {
                        H5Utils.handleTinyAppKeyEvent((String) "uc_init", (String) "create.ucwebview.start");
                        UCWebView.preCreate();
                    } catch (Throwable t) {
                        H5Log.e(UCWebView.TAG, "preCreate exception ", t);
                    } finally {
                        r2 = "uc_init";
                        r3 = "create.ucwebview.end";
                        H5Utils.handleTinyAppKeyEvent(r2, r3);
                        UCWebView.sWaitPreCreating = false;
                        r3 = "WebView is pre-created";
                        H5Log.d(UCWebView.TAG, r3);
                    }
                }
            }, (long) delay);
        }
    }

    static void preCreateForMultiProcess() {
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                try {
                    MutableContextWrapper contextWrapper = new MutableContextWrapper(LauncherApplicationAgent.getInstance().getApplicationContext());
                    if (UCWebView.sWebviewForMultiProcess == null) {
                        H5Log.d(UCWebView.TAG, "create an empty webview for holding render process");
                        UCWebView.sWebviewForMultiProcess = new UCWebView(contextWrapper);
                        UCWebView.sWebviewForMultiProcess.setMultiProcessPreCreate();
                        UCWebView.sWebviewForMultiProcess.setWebViewClient(new UCWebViewClient(UCWebView.sWebviewForMultiProcess, null));
                        UCWebView.sWebviewForMultiProcess.loadUrl("about:blank");
                        if (UCWebView.sWebviewForMultiProcess.getView() != null) {
                            WebView webview = (WebView) UCWebView.sWebviewForMultiProcess.getView();
                            if (webview.getUCExtension() != null) {
                                webview.getUCExtension().setClient(new UCClient() {
                                    public void onWebViewEvent(WebView webView, int type, Object object) {
                                        if (H5Utils.isMainProcess()) {
                                            if (107 == type) {
                                                H5Log.d(UCWebView.TAG, "renderProcessReady from static webview");
                                                UCWebView.renderProcessReady(webView, true);
                                            }
                                            if (108 == type) {
                                                UCWebView.reportReleaseNonIsolateStaticView();
                                                UCWebView.releasePreCreateWebViewForMultiProcess();
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                } catch (Throwable throwable) {
                    H5Log.d(UCWebView.TAG, throwable.toString());
                }
            }
        });
    }

    static void releasePreCreateWebViewForMultiProcess() {
        sHasDestroyNonIsolateStaticWebView = true;
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                H5Log.d(UCWebView.TAG, "destroy non isolate static webview");
                try {
                    if (UCWebView.sWebviewForMultiProcess != null) {
                        UCWebView.sWebviewForMultiProcess.destroy();
                        UCWebView.sWebviewForMultiProcess = null;
                    }
                } catch (Throwable thr) {
                    H5Log.w(UCWebView.TAG, "destroy preload multi process ucwebview error!", thr);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static void preCreate() {
        UCWebView webView2;
        try {
            webView2 = new UCWebView(new MutableContextWrapper(LauncherApplicationAgent.getInstance().getApplicationContext()));
            webView2.setWebViewClient(new UCWebViewClient(webView2, null));
        } catch (Throwable throwable) {
            H5Log.e(TAG, "create uc webView exception.", throwable);
            webView2 = null;
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_UC_CREATE_FAILED").param1().add(Version.NAME, null).param3().add("isTinyApp", String.valueOf(H5Utils.isInTinyProcess())).add("ucVersion", UcSdkConstants.UC_VERSION).param4().add("ext0", CommonUtil.stringify(throwable)));
        }
        if (webView2 != null) {
            sPool.add(webView2);
            if (!sIsAppxJsPreloaded && H5Utils.isInTinyProcess()) {
                sIsAppxJsPreloaded = true;
                H5Utils.runOnMain(new Runnable() {
                    public void run() {
                        UCWebView.preloadAppXJs();
                    }
                });
            } else if (!UcServiceSetup.sWebViewPoolReallyUse) {
                UCWebView view = sPool.poll();
                if (view != null) {
                    H5Utils.runOnMain(new Runnable(view) {
                        final /* synthetic */ UCWebView val$view;

                        {
                            this.val$view = r1;
                        }

                        public void run() {
                            try {
                                if (this.val$view != null) {
                                    this.val$view.destroy();
                                }
                            } catch (Throwable thr) {
                                H5Log.w(UCWebView.TAG, "destroy preload ucwebview error!", thr);
                            }
                        }
                    }, 5000);
                }
            }
        }
    }

    static synchronized UCWebView getInstance(Context context2) {
        UCWebView webView2;
        synchronized (UCWebView.class) {
            if (H5Utils.isDebug() && H5DevConfig.getBooleanConfig("h5_disable_uc_precreate", false)) {
                webView2 = new UCWebView(context2);
            } else if (UcServiceSetup.sWebViewPoolSize <= 0) {
                webView2 = new UCWebView(context2);
            } else if (!isH5Activity(context2)) {
                webView2 = new UCWebView(context2);
            } else if (sPool.isEmpty()) {
                webView2 = new UCWebView(context2);
            } else {
                if (UcServiceSetup.sWebViewPoolReallyUse) {
                    try {
                        webView2 = sPool.poll();
                        if (!(webView2 == null || webView2.webView == null)) {
                            ((MutableContextWrapper) webView2.webView.getContext()).setBaseContext(context2);
                            ((MutableContextWrapper) webView2.webView.getCoreView().getContext()).setBaseContext(context2);
                            webView2.context = context2;
                            H5Log.d(TAG, "WebView get from pool");
                            H5PageData.sUseWebViewPool = true;
                            if (UcServiceSetup.sWebViewPoolKeep) {
                                preCreateOnMainWithDelay(UcServiceSetup.sWebViewCreateDelay);
                            }
                        }
                    } catch (Throwable throwable) {
                        H5Log.e(TAG, "WebView get from pool", throwable);
                    }
                }
                webView2 = new UCWebView(context2);
            }
        }
        return webView2;
    }

    private UCWebView(Context context2) {
        String str = null;
        this.mScale = 0.0f;
        this.mPageStartUnCalled = false;
        this.mFirstLoadUrl = true;
        this.mIsMultiProcessPreCreate = false;
        if (!sUcInitStuffDone) {
            sNeedReportTrace = true;
            UcSetupTracing.beginTrace("firstWebView");
        }
        H5PageData.sUcFirstWebView = sNeedReportTrace;
        this.webView = new WebViewEx(context2);
        this.context = context2;
        initUCWebView();
        this.ifRedirectDNS4UCConfigStr = H5ConfigUtil.getConfig("h5_ifRedirectDNS4UC");
        if (!sUcInitStuffDone) {
            sUcInitStuffDone = true;
            H5Log.d(TAG, "Do uc init stuff after first UCWebView created!");
            if (UcNetworkSetup.useNewInitTiming()) {
                H5Log.d(TAG, "[UcNetworkSetup] new init Timing!");
                UcNetworkSetup.initNetworkConfig();
                if (!UcArSetup.disableAR()) {
                    UcArSetup.init();
                }
                UcServiceWorkerSetup.initServiceWorkerCallback();
                UcServiceWorkerSetup.initServiceWorkerController();
                JSONObject jsonObjApollo = H5ConfigUtil.getConfigJSONObject("h5_ucApolloConfig");
                boolean useApollo = false;
                boolean downloadApolloIn4G = true;
                boolean downloadApolloInLiteProcess = false;
                if (jsonObjApollo != null) {
                    useApollo = "YES".equals(jsonObjApollo.getString("useApollo"));
                    downloadApolloIn4G = "YES".equals(jsonObjApollo.getString("downloadApolloIn4G"));
                    downloadApolloInLiteProcess = "YES".equalsIgnoreCase(jsonObjApollo.getString("downloadApolloInLiteProcess"));
                }
                UcVideoSetup.initVideoControl(context2, useApollo, jsonObjApollo != null ? jsonObjApollo.getString(TinyAppSubPackagePlugin.DOWNLOAD_URL) : str, downloadApolloIn4G, downloadApolloInLiteProcess);
                UcOtherBizSetup.init();
                UcNetworkSetup.clearUcHttpCache();
            }
            UcServiceSetup.initCommonConfig("h5_ucCommonConfigAfterCreateWebView");
            try {
                LocalBroadcastManager manager = LocalBroadcastManager.getInstance(H5Utils.getContext());
                Intent intent = new Intent(H5Param.H5_ACTION_UC_INIT_FINISH_COMPLETELY);
                intent.putExtra("result", true);
                manager.sendBroadcast(intent);
            } catch (Throwable throwable) {
                H5Log.e(TAG, "sendBroadcast", throwable);
            }
        }
        if (sNeedReportTrace && H5Utils.isInTinyProcess()) {
            sNeedReportTrace = false;
            UcSetupTracing.endTrace("firstWebView");
            H5Utils.executeOrdered(UcSetupTracing.TAG, new Runnable() {
                public void run() {
                    UcSetupTracing.report();
                }
            });
        }
    }

    public H5Plugin getH5NumInputKeyboard() {
        return this.mH5NumInputKeyboard;
    }

    public H5Plugin getH5NativeInput() {
        return this.h5NativeInputPlugin;
    }

    public void setScale(float v) {
        this.mScale = v;
    }

    public float getScale() {
        return this.mScale;
    }

    public void setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled) {
        this.webView.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
    }

    public void setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled) {
        this.webView.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled);
    }

    public Picture capturePicture() {
        return this.webView.capturePicture();
    }

    public void setAPWebViewListener(APWebViewListener apWebViewListener2) {
        this.apWebViewListener = apWebViewListener2;
    }

    private void initUCWebView() {
        this.webSettings = new UCWebSettings(this.webView);
        if (WebView.getCoreType() == 2) {
            H5Log.e((String) TAG, (String) "abort uc android webview.");
            throw new IllegalStateException("abort uc android webview.");
        } else if (this.webView.getUCExtension() == null) {
            throw new IllegalStateException("abort uc no extension.");
        } else {
            UCSettings ucSettings = this.webView.getUCExtension().getUCSettings();
            ucSettings.setUCCookieType(1);
            ucSettings.setEnableUCProxy(false);
            ucSettings.setForceUCProxy(false);
            if (H5Utils.isDebug() && H5WirelessDebugPlugin.sWirelessDebugOpening && !H5DevConfig.getBooleanConfig("h5_enable_ri_wired_debug", false)) {
                WebView webView2 = this.webView;
                WebView.setWebContentsDebuggingEnabled(false);
                WebView webView3 = this.webView;
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
    }

    public String getVersion() {
        return WEBVIEW_VERSION;
    }

    public WebViewType getType() {
        return WebViewType.THIRD_PARTY;
    }

    public void addJavascriptInterface(Object obj, String interfaceName) {
        this.webView.addJavascriptInterface(obj, interfaceName);
    }

    public void removeJavascriptInterface(String name) {
        this.webView.removeJavascriptInterface(name);
    }

    public void setWebContentsDebuggingEnabled(boolean b) {
        WebView.setWebContentsDebuggingEnabled(b);
    }

    public void flingScroll(int vx, int vy) {
        this.webView.flingScroll(vx, vy);
    }

    public boolean zoomIn() {
        return this.webView.zoomIn();
    }

    public boolean zoomOut() {
        return this.webView.zoomOut();
    }

    public void setHorizontalScrollbarOverlay(boolean b) {
    }

    public void setVerticalScrollbarOverlay(boolean overlay) {
        this.webView.setVerticalScrollbarOverlay(overlay);
    }

    public boolean overlayHorizontalScrollbar() {
        return this.webView.overlayHorizontalScrollbar();
    }

    public boolean overlayVerticalScrollbar() {
        return this.webView.overlayVerticalScrollbar();
    }

    public SslCertificate getCertificate() {
        return this.webView.getCertificate();
    }

    public void savePassword(String s, String s2, String s3) {
    }

    public void setHttpAuthUsernamePassword(String host, String realm, String username, String password) {
        this.webView.setHttpAuthUsernamePassword(host, realm, username, password);
    }

    public String[] getHttpAuthUsernamePassword(String host, String realm) {
        return this.webView.getHttpAuthUsernamePassword(host, realm);
    }

    public void destroy() {
        if (this.mPageStartUnCalled) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), "multi_process");
                    if (preferences != null) {
                        int count = preferences.getInt(NewHtcHomeBadger.COUNT, 0) + 1;
                        preferences.putInt(NewHtcHomeBadger.COUNT, count);
                        H5Log.d(UCWebView.TAG, "pageStart uncalled times: " + count + " status: " + preferences.commit());
                        if (UcServiceSetup.sFallbackLimit + 1 != count) {
                            return;
                        }
                        if (UCWebView.sRenderProcessReady || !UCWebView.sHasReportRenderProcessLaunchFailed) {
                            UCWebView.this.reportPageStartUnCalled();
                        }
                    }
                }
            });
        }
        this.webView.destroy();
    }

    /* access modifiers changed from: private */
    public void reportPageStartUnCalled() {
        H5LogData networkData = H5LogData.seedId("H5_UC_MULTI_PROCESS_PAGE_START_UNCALL");
        networkData.param3().add("configMultiProcessMode", Integer.valueOf(UcServiceSetup.sProcessMode));
        networkData.param3().add("actualMultiProcessMode", Integer.valueOf(sActualProcessMode));
        networkData.param3().add("multiProcessReady", Boolean.valueOf(sRenderProcessReady));
        networkData.param3().add("isolateSpeedUp", Boolean.valueOf(UcServiceSetup.sIsolateSpeedUp));
        networkData.param3().add("ucVersion", UcSdkConstants.UC_VERSION);
        networkData.param3().add("webviewVersion", WEBVIEW_VERSION);
        networkData.param3().add("cpuHardware", H5DeviceHelper.getCpuHardware());
        H5LogUtil.logNebulaTech(networkData);
    }

    public void setNetworkAvailable(boolean networkUp) {
        this.webView.setNetworkAvailable(networkUp);
    }

    public APWebBackForwardList saveState(Bundle bundle) {
        WebBackForwardList list = this.webView.saveState(bundle);
        if (list != null) {
            return new UCWebBackForwardList(list);
        }
        return null;
    }

    public APWebBackForwardList restoreState(Bundle bundle) {
        WebBackForwardList list = this.webView.restoreState(bundle);
        if (list != null) {
            return new UCWebBackForwardList(list);
        }
        return null;
    }

    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        checkRenderProcessStatus();
        this.webView.loadUrl(url, additionalHttpHeaders);
        redirectDNS4UC();
    }

    public void loadUrl(String url) {
        checkRenderProcessStatus();
        this.webView.loadUrl(url);
        redirectDNS4UC();
    }

    private void checkRenderProcessStatus() {
        if (!sRenderProcessReady && !this.mIsMultiProcessPreCreate && this.mFirstLoadUrl && H5Utils.isMainProcess()) {
            UcSetupTracing.setTrace("firstLoadUrl");
            this.mPageStartUnCalled = true;
            this.mFirstLoadUrl = false;
            if (!sHasCheckRenderProcessReady) {
                sHasCheckRenderProcessReady = true;
                H5Utils.runOnMain(new Runnable() {
                    public void run() {
                        if (!UCWebView.sRenderProcessReady) {
                            UCWebView.reportMultiProcessLaunchStatus(0, false, false);
                        }
                    }
                }, (long) UcServiceSetup.sRenderProcessLaunchTimeout);
            }
        }
    }

    public void execJavaScript4EmbedView(String url, IH5EmbedViewJSCallback ih5EmbedViewJSCallback) {
    }

    public void postUrl(String url, byte[] postData) {
        this.webView.postUrl(url, postData);
    }

    public void loadData(String data, String mimeType, String encoding) {
        this.webView.loadData(data, mimeType, encoding);
    }

    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        this.webView.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    public void evaluateJavascript(String s, ValueCallback<String> stringValueCallback) {
        this.webView.evaluateJavascript(s, stringValueCallback);
    }

    public void stopLoading() {
        this.webView.stopLoading();
    }

    public void reload() {
        this.webView.reload();
    }

    public boolean canGoBack() {
        return this.webView.canGoBack();
    }

    public void goBack() {
        this.webView.goBack();
    }

    public boolean canGoForward() {
        return this.webView.canGoForward();
    }

    public void goForward() {
        this.webView.goForward();
    }

    public boolean canGoBackOrForward(int steps) {
        return this.webView.canGoBackOrForward(steps);
    }

    public void goBackOrForward(int steps) {
        this.webView.goBackOrForward(steps);
    }

    public boolean pageUp(boolean top) {
        return this.webView.pageUp(top);
    }

    public boolean pageDown(boolean bottom) {
        return this.webView.pageDown(bottom);
    }

    public void setInitialScale(int scaleInPercent) {
        this.webView.setInitialScale(scaleInPercent);
    }

    public void invokeZoomPicker() {
        this.webView.invokeZoomPicker();
    }

    public String getUrl() {
        return this.webView.getUrl();
    }

    public String getOriginalUrl() {
        return this.webView.getOriginalUrl();
    }

    public String getTitle() {
        return this.webView.getTitle();
    }

    public Bitmap getFavicon() {
        return this.webView.getFavicon();
    }

    public int getProgress() {
        return this.webView.getProgress();
    }

    public int getContentHeight() {
        return this.webView.getContentHeight();
    }

    public int getContentWidth() {
        return 0;
    }

    public void onPause() {
        this.webView.onPause();
    }

    public void onResume() {
        this.webView.onResume();
    }

    public boolean isPaused() {
        return false;
    }

    public void freeMemory() {
    }

    public void clearCache(boolean includeDiskFiles) {
        this.webView.clearCache(includeDiskFiles);
    }

    public void clearFormData() {
        this.webView.clearFormData();
    }

    public void clearHistory() {
        this.webView.clearHistory();
    }

    public void clearSslPreferences() {
        this.webView.clearSslPreferences();
    }

    public APWebBackForwardList copyBackForwardList() {
        WebBackForwardList list = this.webView.copyBackForwardList();
        if (list != null) {
            return new UCWebBackForwardList(list);
        }
        return null;
    }

    public void setWebViewClient(final APWebViewClient apWebViewClient) {
        Context kbContext;
        if (apWebViewClient != null) {
            this.webView.setWebViewClient(new UCWebViewClient(this, apWebViewClient));
            if (this.webView.getUCExtension() != null) {
                this.webView.getUCExtension().setClient(new UCClient() {
                    public void onReceivedDispatchResponse(HashMap<String, String> map) {
                        apWebViewClient.onResourceResponse(UCWebView.this, map);
                    }

                    public void onWebViewEvent(WebView webview, int type, Object object) {
                        apWebViewClient.onWebViewEvent(UCWebView.this, type, object);
                        if (H5Utils.isMainProcess()) {
                            if (107 == type) {
                                H5Log.d(UCWebView.TAG, "renderProcessReady from dynamic webview");
                                UCWebView.renderProcessReady(webview, false);
                            }
                            if (109 == type && UCWebView.sHasDestroyNonIsolateStaticWebView) {
                                H5Log.d(UCWebView.TAG, "create isolate static webview");
                                UCWebView.preCreateForMultiProcess();
                                UCWebView.reportCreateIsolateStaticView();
                            }
                            if (108 == type) {
                                UCWebView.reportReleaseNonIsolateStaticView();
                                UCWebView.releasePreCreateWebViewForMultiProcess();
                            }
                        }
                    }

                    public void onFirstVisuallyNonEmptyDraw() {
                        apWebViewClient.onFirstVisuallyRender(UCWebView.this);
                    }

                    public void onResourceDidFinishLoading(String url, long size) {
                        apWebViewClient.onResourceFinishLoad(UCWebView.this, url, size);
                    }

                    public boolean onWillInterceptResponse(HashMap<String, String> hashMap) {
                        return apWebViewClient.shouldInterceptResponse(UCWebView.this, hashMap);
                    }

                    public boolean shouldOverrideUrlLoading(WebView view, String url, int nonStandardType) {
                        return apWebViewClient.shouldOverrideUrlLoadingForUC(UCWebView.this, url, nonStandardType);
                    }

                    public void onSaveFormDataPrompt(int promptType, ValueCallback<Boolean> saveFormDataCallBack) {
                        saveFormDataCallBack.onReceiveValue(Boolean.valueOf(false));
                    }

                    public IEmbedView getEmbedView(final EmbedViewConfig embedViewConfig, IEmbedViewContainer iEmbedViewContainer) {
                        if (!embedViewConfig.mIsCurrentPage) {
                            H5Log.d(UCWebView.TAG, "UCWebView onEmbedView !embedViewConfig.mIsCurrentPage return super");
                            return super.getEmbedView(embedViewConfig, iEmbedViewContainer);
                        }
                        try {
                            iEmbedViewContainer.setOnStateChangedListener(new OnStateChangedListener() {
                                EmbedViewConfig tmpConfig = embedViewConfig;

                                public void onAttachedToWebView() {
                                    H5Log.d(UCWebView.TAG, "UCWebView onEmbedViewAttachedToWebView " + this.tmpConfig.toString());
                                    if (UCWebView.this.apWebViewListener != null) {
                                        UCWebView.this.apWebViewListener.onEmbedViewAttachedToWebView(this.tmpConfig.mWidth, this.tmpConfig.mHeight, String.valueOf(this.tmpConfig.mEmbedViewID), this.tmpConfig.mType, this.tmpConfig.mObjectParam);
                                    }
                                }

                                public void onDetachedFromWebView() {
                                    H5Log.d(UCWebView.TAG, "UCWebView onEmbedViewDetachedFromWebView " + this.tmpConfig.toString());
                                    if (UCWebView.this.apWebViewListener != null) {
                                        UCWebView.this.apWebViewListener.onEmbedViewDetachedFromWebView(this.tmpConfig.mWidth, this.tmpConfig.mHeight, String.valueOf(this.tmpConfig.mEmbedViewID), this.tmpConfig.mType, this.tmpConfig.mObjectParam);
                                    }
                                }

                                public void onDestroy() {
                                    H5Log.d(UCWebView.TAG, "UCWebView onEmbedViewDestroy " + this.tmpConfig.toString());
                                    if (UCWebView.this.apWebViewListener != null) {
                                        UCWebView.this.apWebViewListener.onEmbedViewDestory(this.tmpConfig.mWidth, this.tmpConfig.mHeight, String.valueOf(this.tmpConfig.mEmbedViewID), this.tmpConfig.mType, this.tmpConfig.mObjectParam);
                                    }
                                }
                            });
                            iEmbedViewContainer.setOnParamChangedListener(new OnParamChangedListener() {
                                EmbedViewConfig tmpConfig = embedViewConfig;

                                public void onParamChanged(String[] name, String[] value) {
                                    H5Log.d(UCWebView.TAG, "UCWebView onEmbedViewParamChanged " + this.tmpConfig.toString());
                                    if (UCWebView.this.apWebViewListener != null) {
                                        UCWebView.this.apWebViewListener.onEmbedViewParamChanged(this.tmpConfig.mWidth, this.tmpConfig.mHeight, String.valueOf(this.tmpConfig.mEmbedViewID), this.tmpConfig.mType, this.tmpConfig.mObjectParam, name, value);
                                    }
                                }
                            });
                            iEmbedViewContainer.setOnVisibilityChangedListener(new OnVisibilityChangedListener() {
                                EmbedViewConfig tmpConfig = embedViewConfig;

                                public void onVisibilityChanged(int reason) {
                                    H5Log.d(UCWebView.TAG, "UCWebView onEmbedViewVisibilityChanged " + this.tmpConfig.toString() + " reason " + reason);
                                    if (UCWebView.this.apWebViewListener != null) {
                                        UCWebView.this.apWebViewListener.onEmbedViewVisibilityChanged(this.tmpConfig.mWidth, this.tmpConfig.mHeight, String.valueOf(this.tmpConfig.mEmbedViewID), this.tmpConfig.mType, this.tmpConfig.mObjectParam, reason);
                                    }
                                }
                            });
                            H5Log.d(UCWebView.TAG, "UCWebView onEmbedView getEmbedView " + embedViewConfig.toString());
                            View embedView = null;
                            if (UCWebView.this.apWebViewListener != null) {
                                embedView = UCWebView.this.apWebViewListener.getEmbedView(embedViewConfig.mWidth, embedViewConfig.mHeight, String.valueOf(embedViewConfig.mEmbedViewID), embedViewConfig.mType, embedViewConfig.mObjectParam);
                            }
                            return new UCEmbededViewWrapper(embedView, UCWebView.this.apWebViewListener, embedViewConfig);
                        } catch (Throwable e) {
                            H5Log.e(UCWebView.TAG, "UCWebView onEmbedView getEmbedView catch exception ", e);
                            return null;
                        }
                    }

                    public boolean onCheckSelfPermission(String permission) {
                        return GeneralPermissionsManager.getInstance().onCheckSelfPermission(UCWebView.this.context, permission);
                    }

                    public void onCheckSelfPermission(String s, final ValueCallback<Boolean> valueCallback) {
                        boolean grant = GeneralPermissionsManager.getInstance().onCheckSelfPermission(UCWebView.this.context, "android.permission.CAMERA");
                        H5Log.d(UCWebView.TAG, "onCheckSelfPermission " + s + " grant: " + grant);
                        if (!"android.permission.CAMERA".equals(s)) {
                            valueCallback.onReceiveValue(Boolean.valueOf(false));
                        } else if (!grant) {
                            LauncherApplicationAgent.getInstance().getMicroApplicationContext().requestPermissions(new String[]{"android.permission.CAMERA"}, 20000196, new RequestPermissionsResultCallback() {
                                public void onRequestPermissionsResult(int i, String[] strings, int[] ints) {
                                    boolean success = PermissionUtils.verifyPermissions(ints);
                                    H5Log.d(UCWebView.TAG, "onCheckSelfPermission permission: " + success);
                                    if (valueCallback != null) {
                                        valueCallback.onReceiveValue(Boolean.valueOf(success));
                                    }
                                }
                            });
                        } else {
                            valueCallback.onReceiveValue(Boolean.valueOf(true));
                        }
                    }

                    public boolean checkIsTinyApp() {
                        boolean rs = false;
                        H5Service h5Service = H5ServiceUtils.getH5Service();
                        if (h5Service != null) {
                            H5Page h5Page = h5Service.getTopH5Page();
                            if (h5Page != null) {
                                String parentAppId = h5Page.getParams().getString("parentAppId");
                                try {
                                    if (((WebView) h5Page.getContentView()) != UCWebView.this.webView) {
                                        return false;
                                    }
                                    if (parentAppId != null && !parentAppId.isEmpty()) {
                                        rs = true;
                                    }
                                } catch (Exception e) {
                                    return false;
                                }
                            }
                        }
                        return rs;
                    }

                    private void getWebARPermission(final ValueCallback<Map<String, String>> callback) {
                        String str;
                        GeneralPermissionsManager instance = GeneralPermissionsManager.getInstance();
                        Context access$1600 = UCWebView.this.context;
                        AnonymousClass5 r3 = new IGeneralPermissions() {
                            public void onAllow() {
                                Map results = new HashMap();
                                results.put("allow", "yes");
                                callback.onReceiveValue(results);
                            }

                            public void onDeny() {
                                Map results = new HashMap();
                                results.put("allow", BQCCameraParam.VALUE_NO);
                                callback.onReceiveValue(results);
                            }
                        };
                        if (UCWebView.this.webView != null) {
                            str = UCWebView.this.webView.getUrl();
                        } else {
                            str = null;
                        }
                        instance.showGeneralPermissionsPrompt(access$1600, r3, str);
                    }

                    public void onGeneralPermissionsShowPrompt(Map<String, String> params, final ValueCallback<Map<String, String>> callback) {
                        if (params != null) {
                            String type = params.get("type");
                            if (type == null || !type.equals(WalletTinyappUtils.CONST_SCOPE_CAMERA)) {
                                H5Log.d(UCWebView.TAG, "not camera request, do none");
                            } else if (callback == null) {
                                H5Log.e((String) UCWebView.TAG, (String) "onGeneralPermissionsShowPrompt fail!");
                            } else if (checkIsTinyApp()) {
                                H5Service h5Service = H5ServiceUtils.getH5Service();
                                H5Page h5Page = null;
                                if (h5Service != null) {
                                    h5Page = h5Service.getTopH5Page();
                                }
                                H5ApiManager h5TinyAppService = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
                                String pageUrl = UCWebView.this.webView != null ? UCWebView.this.webView.getUrl() : "";
                                if (h5TinyAppService.hasWebARPermission("initWebAR", pageUrl, h5Page)) {
                                    h5TinyAppService.hasWebARCameraPermission(pageUrl, h5Page, new H5PermissionCallBack() {
                                        public void allow() {
                                            Map results = new HashMap();
                                            results.put("allow", "yes");
                                            callback.onReceiveValue(results);
                                        }

                                        public void deny() {
                                            Map results = new HashMap();
                                            results.put("allow", BQCCameraParam.VALUE_NO);
                                            callback.onReceiveValue(results);
                                        }
                                    });
                                    return;
                                }
                                Map results = new HashMap();
                                results.put("allow", BQCCameraParam.VALUE_NO);
                                callback.onReceiveValue(results);
                            } else {
                                getWebARPermission(callback);
                            }
                        } else if (callback != null) {
                            Map results2 = new HashMap();
                            results2.put("allow", BQCCameraParam.VALUE_NO);
                            callback.onReceiveValue(results2);
                        } else {
                            H5Log.e((String) UCWebView.TAG, (String) "params is null and callback is null");
                        }
                    }
                });
                this.webView.getUCExtension().setInjectJSProvider(new InjectJSProvider() {
                    public String getJS(int type) {
                        if (type == 1) {
                            Long time = Long.valueOf(System.currentTimeMillis());
                            H5Log.d(UCWebView.TAG, "begin load AlipayJSBridge type " + type);
                            H5Trace.sessionBegin("UcLoadBridge_IO", null, new String[0]);
                            String js = apWebViewClient.getJSBridge();
                            if (UCWebView.this.mH5NumInputKeyboard != null) {
                                js = js + ";" + UCWebView.this.mH5NumInputKeyboard.getInjectJS();
                            }
                            H5Trace.sessionEnd("UcLoadBridge_IO", null, new String[0]);
                            H5Log.d(UCWebView.TAG, "begin load AlipayJSBridge type cost " + (System.currentTimeMillis() - time.longValue()));
                            return js;
                        } else if (type == 16) {
                            return H5Utils.loadJSScriptTag();
                        } else {
                            return "";
                        }
                    }
                }, 17);
                try {
                    if (isH5Activity(this.context)) {
                        if (this.context instanceof MutableContextWrapper) {
                            kbContext = ((MutableContextWrapper) this.context).getBaseContext();
                        } else {
                            kbContext = this.context;
                        }
                        this.h5NativeInputPlugin = new H5NativeInputPlugin(kbContext, this.webView);
                        if (H5Utils.isUCM57()) {
                            this.mH5NumInputKeyboard = new H5NumInputKeyboardM57(kbContext, this.webView, this);
                        } else {
                            this.mH5NumInputKeyboard = new H5NumInputKeyboardM40(kbContext, this.webView, this);
                        }
                        this.mH5NumInputKeyboard.setFallback(H5InputBoardProviderImpl.class);
                        this.mH5NumInputKeyboard.setH5NativeOnSoftKeyboardListener(this.h5NativeInputPlugin);
                        this.webView.getUCExtension().setSoftKeyboardListener(this.mH5NumInputKeyboard);
                    }
                } catch (Exception e) {
                    H5Log.e((String) TAG, "setSoft error" + e);
                }
            }
        }
    }

    public void setDownloadListener(APDownloadListener apDownloadListener) {
        if (apDownloadListener == null) {
            this.webView.setDownloadListener(null);
        } else {
            this.webView.setDownloadListener(new UCDownloadListener(apDownloadListener));
        }
    }

    public void setWebChromeClient(APWebChromeClient apWebChromeClient) {
        if (apWebChromeClient == null) {
            this.webView.setWebChromeClient(null);
        } else {
            this.webView.setWebChromeClient(new UCWebChromeClient(this, apWebChromeClient));
        }
    }

    public APWebSettings getSettings() {
        return this.webSettings;
    }

    public View getView() {
        return this.webView;
    }

    public APHitTestResult getHitTestResult() {
        HitTestResult hitTestResult = this.webView.getHitTestResult();
        if (hitTestResult == null) {
            return null;
        }
        return new ProxyHitTestResult(hitTestResult);
    }

    public void setOnScrollChangedCallback(H5ScrollChangedCallback changedCallback) {
        this.mH5ScrollChangedCallback = changedCallback;
    }

    public int getScrollY() {
        if (this.webView == null || this.webView.getCoreView() == null) {
            return 0;
        }
        return this.webView.getCoreView().getScrollY();
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return this.webView.dispatchKeyEvent(keyEvent);
    }

    public boolean getCurrentPageSnapshot(Rect dstRect, Rect srcRect, Bitmap bitmap, boolean clipByView, int coordinate) {
        return this.webView.getUCExtension().getCurrentPageSnapshot(dstRect, srcRect, bitmap, clipByView, coordinate);
    }

    /* access modifiers changed from: private */
    public static void reportMultiProcessLaunchStatus(final int mode, final boolean success, final boolean isStaticWebview) {
        if (sNeedReportTrace) {
            sNeedReportTrace = false;
            H5Utils.executeOrdered(UcSetupTracing.TAG, new Runnable() {
                public void run() {
                    H5LogData networkData;
                    if (success) {
                        if (UcServiceSetup.sProcessMode == mode) {
                            networkData = H5LogData.seedId("H5_UC_MULTI_PROCESS_LAUNCH_SUCCESS");
                            networkData.param3().add("multiProcessMode", Integer.valueOf(mode));
                            UcSetupTracing.setTrace("renderProcessSuccess");
                            UcSetupTracing.addCommonInfo("multiProcessMode", String.valueOf(mode));
                        } else {
                            networkData = H5LogData.seedId("H5_UC_MULTI_PROCESS_LAUNCH_FALLBACK");
                            networkData.param3().add("configMultiProcessMode", Integer.valueOf(UcServiceSetup.sProcessMode));
                            networkData.param3().add("actualMultiProcessMode", Integer.valueOf(mode));
                            UcSetupTracing.setTrace("renderProcessFallback");
                            UcSetupTracing.addCommonInfo("configMultiProcessMode", String.valueOf(UcServiceSetup.sProcessMode));
                            UcSetupTracing.addCommonInfo("actualMultiProcessMode", String.valueOf(mode));
                        }
                        APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), "multi_process");
                        if (preferences != null) {
                            preferences.putBoolean("launch_failed", false);
                            H5Log.d(UCWebView.TAG, "multi process launch failed sp status: " + preferences.commit());
                        }
                    } else {
                        UCWebView.sHasReportRenderProcessLaunchFailed = true;
                        UcSetupTracing.setTrace("renderProcessFailed");
                        networkData = H5LogData.seedId("H5_UC_MULTI_PROCESS_LAUNCH_FAILED");
                        networkData.param3().add("configMultiProcessMode", Integer.valueOf(UcServiceSetup.sProcessMode));
                        UcSetupTracing.addCommonInfo("configMultiProcessMode", String.valueOf(UcServiceSetup.sProcessMode));
                        APSharedPreferences preferences2 = SharedPreferencesManager.getInstance(H5Utils.getContext(), "multi_process");
                        if (preferences2 != null) {
                            preferences2.putBoolean("launch_failed", true);
                            H5Log.d(UCWebView.TAG, "multi process launch failed sp status: " + preferences2.commit());
                        }
                    }
                    networkData.param3().add("isStaticWebView", Boolean.valueOf(isStaticWebview));
                    UcServiceSetup.addCommonInfoForMultiProcess(networkData);
                    H5LogUtil.logNebulaTech(networkData);
                    UcSetupTracing.addCommonInfo("isStaticWebView", String.valueOf(isStaticWebview));
                    if (!(UCWebView.sWebviewForMultiProcess == null || UCWebView.sWebviewForMultiProcess.getView() == null)) {
                        WebView webview = (WebView) UCWebView.sWebviewForMultiProcess.getView();
                        if (webview.getUCExtension() != null) {
                            webview.getUCExtension().getCoreStatus(2, new ValueCallback<Object>() {
                                public void onReceiveValue(Object value) {
                                    if (value != null && (value instanceof HashMap)) {
                                        for (Entry entry : ((HashMap) value).entrySet()) {
                                            UcSetupTracing.addCommonInfo("uc_" + entry.getKey().toString(), entry.getValue().toString());
                                        }
                                    }
                                }
                            });
                        }
                    }
                    UcSetupTracing.report();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static void reportCreateIsolateStaticView() {
        H5Utils.executeOrdered(UcSetupTracing.TAG, new Runnable() {
            public void run() {
                H5LogUtil.logNebulaTech(H5LogData.seedId("H5_UC_CREATE_ISOLATE_STATIC_VIEW_SUCCESS"));
            }
        });
    }

    /* access modifiers changed from: private */
    public static void reportReleaseNonIsolateStaticView() {
        if (!sHasDestroyNonIsolateStaticWebView) {
            H5Utils.executeOrdered(UcSetupTracing.TAG, new Runnable() {
                public void run() {
                    H5LogUtil.logNebulaTech(H5LogData.seedId("H5_UC_INIT_FROM_NON_ISOLATE_TO_ISOLATE_SUCCESS"));
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void setWebViewClient(UCWebViewClient webViewClient) {
        if (webViewClient != null && this.webView != null) {
            this.webView.setWebViewClient(webViewClient);
        }
    }

    private static boolean isH5Activity(Context context2) {
        if (context2 == null) {
            return false;
        }
        if (context2 instanceof MutableContextWrapper) {
            context2 = ((MutableContextWrapper) context2).getBaseContext();
        }
        try {
            Class h5ActivityClz = Class.forName("com.alipay.mobile.nebulacore.ui.H5Activity");
            H5Log.d(TAG, "isH5Activity: " + context2.getClass() + " isAssignableFrom " + h5ActivityClz);
            if (h5ActivityClz != null) {
                return h5ActivityClz.isAssignableFrom(context2.getClass());
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    private void redirectDNS4UC() {
        if (TextUtils.equals(this.ifRedirectDNS4UCConfigStr, "YES") && ifRedirectDNS4UC) {
            H5Log.d(TAG, "ucwebview loadUrl change dns");
            HashMap dns_info = new HashMap();
            dns_info.put("host", "uc.ucweb.com");
            dns_info.put(OnNativeInvokeListener.ARG_IP, "127.0.0.1");
            dns_info.put("ttl", "300000");
            UCCore.notifyCoreEvent(5, dns_info, null);
            HashMap dns_info2 = new HashMap();
            dns_info2.put("host", "u.uc123.com");
            dns_info2.put(OnNativeInvokeListener.ARG_IP, "127.0.0.1");
            dns_info2.put("ttl", "300000");
            UCCore.notifyCoreEvent(5, dns_info2, null);
            HashMap dns_info3 = new HashMap();
            dns_info3.put("host", "u.ucfly.com");
            dns_info3.put(OnNativeInvokeListener.ARG_IP, "127.0.0.1");
            dns_info3.put("ttl", "300000");
            UCCore.notifyCoreEvent(5, dns_info3, null);
            ifRedirectDNS4UC = false;
        }
    }

    /* access modifiers changed from: private */
    public static void renderProcessReady(WebView webView2, final boolean isStaticWebView) {
        if (!sRenderProcessReady) {
            H5Log.d(TAG, "renderProcessReady");
            sRenderProcessReady = true;
            webView2.getUCExtension().getCoreStatus(1, new ValueCallback<Object>() {
                public void onReceiveValue(Object value) {
                    if (value instanceof Integer) {
                        UCWebView.sActualProcessMode = ((Integer) value).intValue();
                        H5Log.d(UCWebView.TAG, "onRenderProcessReady config mode: " + UcServiceSetup.sProcessMode + " current mode: " + UCWebView.sActualProcessMode);
                        UCWebView.reportMultiProcessLaunchStatus(UCWebView.sActualProcessMode, true, isStaticWebView);
                    }
                }
            });
        }
    }

    private static void registerAppXResourceLoadedReceiver() {
        if (sAppxResourceLoadedReceiver == null) {
            sAppxResourceLoadedReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    try {
                        UCWebView.preloadAppXJs();
                        LocalBroadcastManager.getInstance(H5Utils.getContext()).unregisterReceiver(UCWebView.sAppxResourceLoadedReceiver);
                    } catch (Throwable thr) {
                        H5Log.e(UCWebView.TAG, "preloadAppXJs error!", thr);
                    }
                }
            };
            LocalBroadcastManager.getInstance(H5Utils.getContext()).registerReceiver(sAppxResourceLoadedReceiver, new IntentFilter(H5Param.APPX_PRELOAD_SUCCESS));
            H5Log.d(TAG, "registerAppXResourceLoadedReceiver");
        }
    }

    /* access modifiers changed from: private */
    public static void preloadAppXJs() {
        try {
            H5Log.d(TAG, "preloadAppXJs enter");
            if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5ConfigUtil.getConfig("h5_preloadAppxJs"))) {
                H5Log.d(TAG, "preloadAppXJs canceled config off");
            } else if (sPool.size() == 0) {
                H5Log.d(TAG, "preloadAppXJs canceled webview pool empty");
            } else if (!ActivityHelper.isBackgroundRunning()) {
                H5Log.d(TAG, "preloadAppXJs canceled running foreground");
            } else if (JsPreloadWebviewClient.getResource("https://appx/af-appx.min.js") == null) {
                H5Log.d(TAG, "preloadAppXJs canceled appx resource not loaded");
                registerAppXResourceLoadedReceiver();
            } else {
                UCWebView ucWebView = sPool.poll();
                if (ucWebView != null) {
                    ucWebView.setWebContentsDebuggingEnabled(H5Utils.isDebug());
                    ucWebView.getSettings().setJavaScriptEnabled(true);
                    ucWebView.setWebViewClient((APWebViewClient) new JsPreloadWebviewClient());
                    ucWebView.loadDataWithBaseURL("ext:init_mini_framework", H5ResourceManager.getRaw(R.raw.tinydemo), "text/html", "utf-8", null);
                    H5Utils.runOnMain(new Runnable(ucWebView) {
                        final /* synthetic */ UCWebView val$ucWebView;

                        {
                            this.val$ucWebView = r1;
                        }

                        public void run() {
                            try {
                                this.val$ucWebView.destroy();
                                H5Log.d(UCWebView.TAG, "preloadAppXJs webview destroyed");
                            } catch (Throwable thr) {
                                H5Log.e(UCWebView.TAG, "destroy js preload ucwebview error!", thr);
                            }
                        }
                    }, 5000);
                }
            }
        } catch (Throwable thr) {
            H5Log.e(TAG, "preload appx js error!", thr);
        }
    }

    public void setMultiProcessMode() {
        if (this.webView != null) {
            this.webView.getUCExtension().getCoreStatus(1, new ValueCallback<Object>() {
                public void onReceiveValue(Object value) {
                    if (value instanceof Integer) {
                        int mode = ((Integer) value).intValue();
                        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
                        if (h5Service != null && h5Service.getTopH5Page() != null && h5Service.getTopH5Page().getPageData() != null) {
                            h5Service.getTopH5Page().getPageData().setMultiProcessMode(mode);
                        }
                    }
                }
            });
        }
    }

    public void clearPageStartUnCalled() {
        if (this.mPageStartUnCalled && H5Utils.isMainProcess()) {
            this.mPageStartUnCalled = false;
            H5Utils.executeOrdered(UcSetupTracing.TAG, new Runnable() {
                public void run() {
                    APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), "multi_process");
                    if (preferences != null) {
                        preferences.putInt(NewHtcHomeBadger.COUNT, 0);
                        H5Log.d(UCWebView.TAG, "clear pageStart uncalled status: " + preferences.commit());
                    }
                }
            });
        }
    }

    public void setMultiProcessPreCreate() {
        this.mIsMultiProcessPreCreate = true;
    }

    public boolean isMultiProcessPreCreate() {
        return this.mIsMultiProcessPreCreate;
    }
}
