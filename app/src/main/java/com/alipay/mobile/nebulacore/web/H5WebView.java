package com.alipay.mobile.nebulacore.web;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.Rect;
import android.net.http.SslCertificate;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.ValueCallback;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.newembedview.H5NewEmbedViewProvider;
import com.alipay.mobile.nebula.provider.H5AliPayUaProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5AutoLoginProvider;
import com.alipay.mobile.nebula.provider.H5ChannelProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5DevDebugProvider;
import com.alipay.mobile.nebula.provider.H5EmbededViewProvider;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.provider.H5PreConnectProvider;
import com.alipay.mobile.nebula.provider.H5UaProvider;
import com.alipay.mobile.nebula.refresh.H5OverScrollListener;
import com.alipay.mobile.nebula.refresh.H5PullableView;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5NetworkUtil;
import com.alipay.mobile.nebula.util.H5NetworkUtil.Network;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.IH5EmbedViewJSCallback;
import com.alipay.mobile.nebula.webview.APDownloadListener;
import com.alipay.mobile.nebula.webview.APHitTestResult;
import com.alipay.mobile.nebula.webview.APWebBackForwardList;
import com.alipay.mobile.nebula.webview.APWebChromeClient;
import com.alipay.mobile.nebula.webview.APWebSettings;
import com.alipay.mobile.nebula.webview.APWebSettings.PluginState;
import com.alipay.mobile.nebula.webview.APWebSettings.TextSize;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebula.webview.APWebViewClient;
import com.alipay.mobile.nebula.webview.APWebViewListener;
import com.alipay.mobile.nebula.webview.H5ScrollChangedCallback;
import com.alipay.mobile.nebula.webview.WebViewType;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.nebulacore.util.AccessibilityUtil;
import com.alipay.mobile.nebulacore.util.NebulaUtil;
import com.alipay.mobile.nebulacore.wallet.H5WebViewFactory;
import java.util.Map;

public class H5WebView implements H5PullableView, APWebView {
    private static int b = 0;
    public String TAG = "H5WebView";
    protected APWebView a;
    private Bundle c;
    /* access modifiers changed from: private */
    public H5OverScrollListener d;
    private int e = 0;
    private float f = 1.0f;
    private String g;
    private boolean h = false;
    private String i;
    private H5Page j;
    private Bundle k;
    private long l = -1;
    private String m = null;

    public H5WebView(final Activity activity, final H5Page h5Page, Bundle webViewConfig) {
        this.TAG += hashCode();
        this.c = webViewConfig;
        this.j = h5Page;
        this.k = h5Page.getParams();
        if (H5Utils.getBoolean(this.k, (String) H5Param.LONG_ISPRERENDER, false)) {
            this.TAG += "_preRender";
        }
        String bizType = H5Utils.getString(webViewConfig, (String) "bizType");
        this.i = H5Utils.getString(h5Page.getParams(), (String) "appId");
        H5Log.d(this.TAG, "createWebView bizType " + bizType);
        this.a = H5WebViewFactory.instance().createWebView(activity, bizType, activity, h5Page.getParams());
        h5Page.setWebViewId(Nebula.getWebViewId());
        if (this.a == null) {
            H5Log.e(this.TAG, (String) "apWebView == null return");
            return;
        }
        H5Log.d(this.TAG, "webViewVersion is " + this.a.getVersion() + " webViewId " + h5Page.getWebViewId());
        if (!Nebula.disableHWACByUCStyle() && H5HardwarePolicy.disableHardwareAccelerate(this.k, bizType)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5UcService").param4().add("硬件加速关闭了", null));
            View webView = this.a.getView();
            if (webView != null) {
                try {
                    H5Log.d(this.TAG, "diable webview layer hardware accelerate.");
                    webView.setLayerType(1, null);
                } catch (Throwable t) {
                    H5Log.e(this.TAG, "set webview layer exception.", t);
                }
            }
        }
        this.a.setAPWebViewListener(new APWebViewListener() {
            public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY) {
                H5Log.d(H5WebView.this.TAG, "overScrollBy: " + deltaX + ", " + deltaY + ", " + scrollX + ", " + scrollY);
                if (H5WebView.this.d != null) {
                    H5WebView.this.d.onOverScrolled(deltaX, deltaY, scrollX, scrollY);
                }
                return false;
            }

            @SuppressLint({"MissingSuperCall"})
            public void onDetachedFromWindow() {
                if (!(activity instanceof H5Activity) || (h5Page != null && TextUtils.equals("H5Activity", H5Utils.getString(h5Page.getParams(), (String) H5Param.CREATEPAGESENCE)))) {
                    H5Log.d(H5WebView.this.TAG, "onDetachedFromWindow in createPageSence");
                    if (h5Page != null) {
                        h5Page.exitPage();
                    }
                }
            }

            public View getEmbedView(int width, int height, String viewId, String mType, Map<String, String> params) {
                H5Log.d(H5WebView.this.TAG, "H5WebViewClient getEmbedView");
                if (h5Page != null) {
                    h5Page.setContainsEmbedView(true);
                    H5EmbededViewProvider embededViewProvider = h5Page.getEmbededViewProvider();
                    if (embededViewProvider != null) {
                        View view = embededViewProvider.getEmbedView(width, height, viewId, mType, params);
                        if (view == null || !view.getClass().getName().contains("AdapterTextureMapView")) {
                            return view;
                        }
                        h5Page.setContainsEmbedSurfaceView(true);
                        return view;
                    }
                }
                return null;
            }

            public void onEmbedViewAttachedToWebView(int width, int height, String viewId, String mType, Map<String, String> params) {
                H5Log.d(H5WebView.this.TAG, "H5WebViewClient onEmbedViewAttachedToWebView");
                if (h5Page != null) {
                    H5EmbededViewProvider embededViewProvider = h5Page.getEmbededViewProvider();
                    if (embededViewProvider != null) {
                        embededViewProvider.onEmbedViewAttachedToWebView(width, height, viewId, mType, params);
                    }
                }
            }

            public void onEmbedViewDetachedFromWebView(int width, int height, String viewId, String mType, Map<String, String> params) {
                H5Log.d(H5WebView.this.TAG, "H5WebViewClient onEmbedViewDetachedFromWebView");
                if (h5Page != null) {
                    H5EmbededViewProvider embededViewProvider = h5Page.getEmbededViewProvider();
                    if (embededViewProvider != null) {
                        embededViewProvider.onEmbedViewDetachedFromWebView(width, height, viewId, mType, params);
                    }
                }
            }

            public void onEmbedViewDestory(int width, int height, String viewId, String mType, Map<String, String> params) {
                H5Log.d(H5WebView.this.TAG, "H5WebViewClient onEmbedViewDestory");
                if (h5Page != null) {
                    H5EmbededViewProvider embededViewProvider = h5Page.getEmbededViewProvider();
                    if (embededViewProvider != null) {
                        embededViewProvider.onEmbedViewDestory(width, height, viewId, mType, params);
                    }
                }
            }

            public void onEmbedViewParamChanged(int width, int height, String viewId, String mType, Map<String, String> params, String[] name, String[] value) {
                H5Log.d(H5WebView.this.TAG, "H5WebViewClient onEmbedViewParamChanged");
                if (h5Page != null) {
                    H5EmbededViewProvider embededViewProvider = h5Page.getEmbededViewProvider();
                    if (embededViewProvider != null) {
                        embededViewProvider.onEmbedViewParamChanged(width, height, viewId, mType, params, name, value);
                    }
                }
            }

            public void onEmbedViewVisibilityChanged(int width, int height, String viewId, String mType, Map<String, String> params, int reason) {
                H5Log.d(H5WebView.this.TAG, "H5WebViewClient onEmbedViewVisibilityChanged");
                if (h5Page != null) {
                    H5EmbededViewProvider embededViewProvider = h5Page.getEmbededViewProvider();
                    if (embededViewProvider != null) {
                        embededViewProvider.onEmbedViewVisibilityChanged(width, height, viewId, mType, params, reason);
                    }
                }
            }

            public Bitmap getSnapshot(int width, int height, String viewId, String mType, Map<String, String> params) {
                H5Log.d(H5WebView.this.TAG, "H5WebViewClient getSnapshot");
                if (h5Page != null) {
                    H5EmbededViewProvider embededViewProvider = h5Page.getEmbededViewProvider();
                    if (embededViewProvider != null) {
                        return embededViewProvider.getSnapshot(width, height, viewId, mType, params);
                    }
                }
                return null;
            }
        });
        boolean enableScrollBar = H5Utils.getBoolean(h5Page.getParams(), (String) H5Param.LONG_ENABLE_SCROLLBAR, true);
        H5Log.d(this.TAG, "enableScrollBar:" + enableScrollBar);
        if (!enableScrollBar) {
            this.a.setHorizontalScrollBarEnabled(enableScrollBar);
            this.a.setVerticalScrollBarEnabled(enableScrollBar);
        }
        int i2 = b;
        b = i2 + 1;
        this.e = i2;
    }

    public int getWebViewIndex() {
        return this.e;
    }

    public void init(boolean allowAccessFromFileURL) {
        H5Log.d(this.TAG, "initWebView");
        b();
        a();
        a(allowAccessFromFileURL);
        if (this.a == null) {
            H5Log.e(this.TAG, (String) "FATAL ERROR, the internal glue apWebView is null!");
        }
        if (Nebula.DEBUG) {
            setWebContentsDebuggingEnabled(true);
        }
    }

    private void a() {
        boolean startsWithOnlineHost;
        boolean urlWithAppId;
        boolean canAutoLogin;
        if (this.j != null) {
            String url = H5Utils.getString(this.j.getParams(), (String) "url");
            if (!TextUtils.isEmpty(url)) {
                H5PreConnectProvider provider = (H5PreConnectProvider) H5Utils.getProvider(H5PreConnectProvider.class.getName());
                if (provider != null) {
                    provider.preConnect(url, this.j);
                    String onlineHost = H5Utils.getString(this.j.getParams(), (String) H5Param.ONLINE_HOST);
                    if (TextUtils.isEmpty(onlineHost) || !url.startsWith(onlineHost)) {
                        startsWithOnlineHost = false;
                    } else {
                        startsWithOnlineHost = true;
                    }
                    if (!TextUtils.isEmpty(H5AppUtil.matchAppId(url))) {
                        urlWithAppId = true;
                    } else {
                        urlWithAppId = false;
                    }
                    H5AutoLoginProvider autoLoginProvider = (H5AutoLoginProvider) H5Utils.getProvider(H5AutoLoginProvider.class.getName());
                    if (autoLoginProvider == null || !autoLoginProvider.canAutoLogin(url)) {
                        canAutoLogin = false;
                    } else {
                        canAutoLogin = true;
                    }
                    if (!startsWithOnlineHost && !urlWithAppId && !canAutoLogin) {
                        provider.preRequest(url, this.j);
                    }
                }
            }
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    @TargetApi(19)
    private void a(boolean allowAccessFromFileURL) {
        H5Log.d(this.TAG, "applyCustomSettings allowAccessFromFileURL " + allowAccessFromFileURL);
        APWebSettings settings = getSettings();
        settings.setEnableFastScroller(false);
        settings.setPageCacheCapacity(0);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setSupportMultipleWindows(false);
        try {
            settings.setJavaScriptEnabled(true);
        } catch (NullPointerException e2) {
            H5Log.d(this.TAG, "Ignore the exception in AccessibilityInjector when init");
            H5Log.e(this.TAG, "exception detail", e2);
        }
        settings.setDefaultFontSize(16);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setPluginState(PluginState.ON);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(allowAccessFromFileURL);
        String h5Folder = NebulaUtil.getApplicationDir() + "/app_h5container";
        H5FileUtil.mkdirs(h5Folder);
        settings.setDatabaseEnabled(true);
        if (VERSION.SDK_INT < 19) {
            H5FileUtil.mkdirs(h5Folder + "/databases");
            settings.setDatabasePath(h5Folder + "/databases");
        }
        H5FileUtil.mkdirs(h5Folder + "/appcache");
        settings.setAppCachePath(h5Folder + "/appcache");
        settings.setAppCacheEnabled(true);
        settings.getUserAgentString();
        if (H5NetworkUtil.getInstance().getNetworkType() == Network.NETWORK_NO_CONNECTION) {
            settings.setCacheMode(1);
        } else {
            settings.setCacheMode(-1);
        }
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setGeolocationEnabled(true);
        H5FileUtil.mkdirs(h5Folder + "/geolocation");
        settings.setGeolocationDatabasePath(h5Folder + "/geolocation");
        settings.setMediaPlaybackRequiresUserGesture(false);
        if (VERSION.SDK_INT >= 11) {
            settings.setDisplayZoomControls(false);
        }
        settings.setAllowFileAccessFromFileURLs(allowAccessFromFileURL);
        settings.setAllowUniversalAccessFromFileURLs(allowAccessFromFileURL);
        settings.setTextSize(TextSize.NORMAL);
        if (VERSION.SDK_INT > 10 && VERSION.SDK_INT < 17) {
            removeJavascriptInterface("searchBoxJavaBridge_");
            removeJavascriptInterface("accessibility");
            removeJavascriptInterface("accessibilityTraversal");
        }
    }

    private void b() {
        APWebSettings settings = getSettings();
        try {
            String ua = settings.getUserAgentString();
            String originalUA = ua;
            H5ChannelProvider h5ChannelProvider = (H5ChannelProvider) Nebula.getProviderManager().getProvider(H5ChannelProvider.class.getName());
            String channelId = "default";
            if (h5ChannelProvider != null) {
                channelId = h5ChannelProvider.getChannelId();
            }
            if ("5136".equals(channelId)) {
                ua = ua.replace("534.30", "537.36") + " AlipayChannelId/" + channelId;
            }
            if (!ua.contains("UCBS") && ua.contains("UWS")) {
                ua = ua + " UCBS/" + getVersion();
                H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                if (configProvider != null) {
                    boolean enable = !TextUtils.equals("NO", configProvider.getConfig("h5_enableDetectIfHasContent"));
                    if (this.j != null && enable) {
                        ua = ua + " ChannelId(" + this.j.getWebViewId() + ")";
                    }
                }
            }
            String ua2 = ua + " NebulaSDK/1.8.100112 Nebula";
            H5UaProvider uaProvider = (H5UaProvider) Nebula.getProviderManager().getProvider(H5UaProvider.class.getName());
            H5AliPayUaProvider h5AliPayUaProvider = (H5AliPayUaProvider) Nebula.getProviderManager().getProvider(H5AliPayUaProvider.class.getName());
            if (uaProvider != null) {
                this.g = uaProvider.getUa(ua2);
            } else {
                this.g = a(ua2);
                if (h5AliPayUaProvider != null) {
                    this.g = h5AliPayUaProvider.getUa(this.g);
                }
            }
            a(settings, this.g, originalUA);
            H5Log.d(this.TAG, "set final ua " + this.g);
            if (Nebula.DEBUG) {
                H5Log.d(this.TAG, "final user agent " + settings.getUserAgentString());
            }
        } catch (Exception e2) {
            H5Log.e(this.TAG, "setUserAgent exception", e2);
        }
    }

    private void a(APWebSettings settings, String userAgent, String originalUA) {
        JSONArray h5_handle4uaJsonArray = H5Utils.parseArray(H5Environment.getConfig("h5_handle4ua"));
        if (h5_handle4uaJsonArray == null || !h5_handle4uaJsonArray.contains(this.i)) {
            if (Nebula.isTinyWebView(this.k)) {
                userAgent = userAgent + " MiniProgram";
            }
            settings.setUserAgentString(userAgent);
            return;
        }
        settings.setUserAgentString(originalUA);
    }

    private String a(String ua) {
        String ua2;
        String productType;
        String deviceInfo = "";
        try {
            DisplayMetrics displayMetrics = H5Environment.getContext().getResources().getDisplayMetrics();
            if (displayMetrics != null) {
                float density = displayMetrics.density;
                int width = Math.round(((float) displayMetrics.widthPixels) / density);
                int height = Nebula.getHeight(this.j, density, displayMetrics);
                deviceInfo = AccessibilityUtil.getEnabledAccessibilities() != null ? " AlipayDefined(nt:" + H5NetworkUtil.getInstance().getNetworkString() + ",ws:" + width + MergeUtil.SEPARATOR_KV + height + MergeUtil.SEPARATOR_KV + density + AccessibilityUtil.getEnabledAccessibilities() + ")" : " AlipayDefined(nt:" + H5NetworkUtil.getInstance().getNetworkString() + ",ws:" + width + MergeUtil.SEPARATOR_KV + height + MergeUtil.SEPARATOR_KV + density + ")";
            }
        } catch (Exception e2) {
            H5Log.e(this.TAG, "exception detail", e2);
        }
        String versionName = "";
        String language = "";
        boolean isConcaveScreen = false;
        H5EnvProvider provider = (H5EnvProvider) Nebula.getProviderManager().getProvider(H5EnvProvider.class.getName());
        if (provider != null) {
            versionName = provider.getProductVersion();
            language = provider.getLanguage();
            isConcaveScreen = provider.isConcaveScreen();
        }
        String ua3 = ua + deviceInfo + " AliApp(AP/" + versionName + ") AlipayClient/" + versionName + language;
        if (Nebula.useH5StatusBar(this.j)) {
            ua3 = ua3 + " useStatusBar/true";
        }
        if (isConcaveScreen) {
            ua2 = ua3 + " isConcaveScreen/true";
        } else {
            ua2 = ua3 + " isConcaveScreen/false";
        }
        if (Nebula.DEBUG) {
            if (((H5AppProvider) H5ProviderManagerImpl.getInstance().getProvider(H5AppProvider.class.getName())) == null) {
                H5Log.e(this.TAG, (String) "failed to get app info!");
                return ua2;
            }
            JSONObject joHost = H5Utils.parseObject(H5Utils.getString(this.k, (String) "host"));
            if (joHost == null || joHost.isEmpty()) {
                H5Log.w(this.TAG, "can't parse host parameter as json");
                return ua2;
            }
            String rpcUrl = null;
            if (provider != null) {
                rpcUrl = provider.getRpcUrl();
            }
            if (!TextUtils.isEmpty(rpcUrl) && rpcUrl.contains("test.alipay.net")) {
                productType = "test";
            } else if (TextUtils.isEmpty(rpcUrl) || !rpcUrl.contains("mobilegwpre.alipay.com")) {
                productType = "dev";
            } else {
                productType = LogContext.RELEASETYPE_RC;
            }
            ua2 = ua2 + " ProductType/" + productType;
            H5Log.d(this.TAG, "debug ua" + ua2);
        }
        return ua2;
    }

    @TargetApi(14)
    public void setTextSize(int size) {
        APWebSettings settings = getSettings();
        if (VERSION.SDK_INT >= 14) {
            settings.setTextZoom(size);
        } else {
            settings.setTextSize(getTextSize(size));
        }
    }

    public TextSize getTextSize(int textZoom) {
        if (textZoom >= 200) {
            return TextSize.LARGEST;
        }
        if (textZoom >= 150) {
            return TextSize.LARGER;
        }
        if (textZoom >= 100) {
            return TextSize.NORMAL;
        }
        if (textZoom >= 75) {
            return TextSize.SMALLER;
        }
        return TextSize.NORMAL;
    }

    @TargetApi(19)
    public void loadUrl(final String url) {
        if (!TextUtils.isEmpty(url)) {
            if (!url.startsWith("javascript")) {
                this.l = System.currentTimeMillis();
                this.m = url;
            }
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5WebView.this.a(url, (IH5EmbedViewJSCallback) null);
                }
            });
        }
    }

    public void execJavaScript4EmbedView(String url, final IH5EmbedViewJSCallback jsCallback) {
        if (!TextUtils.isEmpty(url)) {
            final String finalUrl = url;
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5WebView.this.a(finalUrl, jsCallback);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void a(String url, final IH5EmbedViewJSCallback jsCallback) {
        boolean z = true;
        try {
            if (url.startsWith("javascript")) {
                boolean meetApiLevel19 = VERSION.SDK_INT >= 19;
                if (this.g == null || (!this.g.contains("UCBS") && !this.g.contains("UWS") && !this.g.contains("U3"))) {
                    z = false;
                }
                if (z) {
                    if (jsCallback == null) {
                        evaluateJavascript(url, null);
                    } else {
                        evaluateJavascript(url, new ValueCallback<String>() {
                            public void onReceiveValue(String value) {
                                jsCallback.onReceiveValue(value);
                            }
                        });
                    }
                } else if (!meetApiLevel19) {
                    this.a.loadUrl(url);
                } else if (jsCallback == null) {
                    evaluateJavascript(url, null);
                } else {
                    evaluateJavascript(url, new ValueCallback<String>() {
                        public void onReceiveValue(String value) {
                            jsCallback.onReceiveValue(value);
                        }
                    });
                }
            } else {
                this.a.loadUrl(url);
            }
        } catch (Throwable t) {
            H5Log.e(this.TAG, "loadUrl exception.", t);
            this.a.loadUrl(url);
        }
    }

    public void setH5OverScrollListener(H5OverScrollListener listener) {
        this.d = listener;
    }

    public final Bundle getWebViewConfig() {
        return this.c;
    }

    public void addJavascriptInterface(Object o, String s) {
        this.a.addJavascriptInterface(o, s);
    }

    public void setWebContentsDebuggingEnabled(boolean b2) {
        this.a.setWebContentsDebuggingEnabled(b2);
    }

    public void flingScroll(int i2, int i22) {
        this.a.flingScroll(i2, i22);
    }

    public boolean zoomIn() {
        return this.a.zoomIn();
    }

    public boolean zoomOut() {
        return this.a.zoomOut();
    }

    public void setHorizontalScrollbarOverlay(boolean b2) {
        this.a.setHorizontalScrollbarOverlay(b2);
    }

    public void setVerticalScrollbarOverlay(boolean b2) {
        this.a.setVerticalScrollbarOverlay(b2);
    }

    public boolean overlayHorizontalScrollbar() {
        return this.a.overlayHorizontalScrollbar();
    }

    public boolean overlayVerticalScrollbar() {
        return this.a.overlayVerticalScrollbar();
    }

    public SslCertificate getCertificate() {
        return this.a.getCertificate();
    }

    public void savePassword(String s, String s2, String s3) {
        this.a.savePassword(s, s2, s3);
    }

    public void setHttpAuthUsernamePassword(String s, String s2, String s3, String s4) {
        this.a.setHttpAuthUsernamePassword(s, s2, s3, s4);
    }

    public String[] getHttpAuthUsernamePassword(String s, String s2) {
        return this.a.getHttpAuthUsernamePassword(s, s2);
    }

    public void destroy() {
        this.a.destroy();
    }

    public void setNetworkAvailable(boolean b2) {
        this.a.setNetworkAvailable(b2);
    }

    public APWebBackForwardList saveState(Bundle bundle) {
        return this.a.saveState(bundle);
    }

    public APWebBackForwardList restoreState(Bundle bundle) {
        return this.a.restoreState(bundle);
    }

    public void loadUrl(String url, Map<String, String> stringStringMap) {
        this.l = System.currentTimeMillis();
        this.m = url;
        this.a.loadUrl(url, stringStringMap);
    }

    public void postUrl(String s, byte[] bytes) {
        this.a.postUrl(s, bytes);
    }

    public void loadData(String s, String s2, String s3) {
        this.a.loadData(s, s2, s3);
    }

    public void loadDataWithBaseURL(String s, String s2, String s3, String s4, String s5) {
        this.a.loadDataWithBaseURL(s, s2, s3, s4, s5);
    }

    public void evaluateJavascript(String s, ValueCallback<String> stringValueCallback) {
        this.a.evaluateJavascript(s, stringValueCallback);
    }

    public void stopLoading() {
        this.a.stopLoading();
    }

    public void reload() {
        this.a.reload();
    }

    public boolean canGoBack() {
        return this.a.canGoBack();
    }

    public void goBack() {
        this.a.goBack();
    }

    public boolean canGoForward() {
        return this.a.canGoForward();
    }

    public void goForward() {
        this.a.goForward();
    }

    public boolean canGoBackOrForward(int i2) {
        return this.a.canGoForward();
    }

    public void goBackOrForward(int i2) {
        this.a.goBackOrForward(i2);
    }

    public boolean pageUp(boolean b2) {
        return this.a.pageUp(b2);
    }

    public boolean pageDown(boolean b2) {
        return this.a.pageDown(b2);
    }

    public void setInitialScale(int i2) {
        this.a.setInitialScale(i2);
    }

    public void invokeZoomPicker() {
        this.a.invokeZoomPicker();
    }

    public String getUrl() {
        return this.a.getUrl();
    }

    public String getOriginalUrl() {
        return this.a.getOriginalUrl();
    }

    public String getTitle() {
        return this.a.getTitle();
    }

    public Bitmap getFavicon() {
        return this.a.getFavicon();
    }

    public int getProgress() {
        return this.a.getProgress();
    }

    public int getContentHeight() {
        return this.a.getContentHeight();
    }

    public int getContentWidth() {
        return this.a.getContentWidth();
    }

    public void onPause() {
        this.a.onPause();
        H5Log.d(this.TAG, "H5WebView onWebViewPause");
        if (this.j != null && this.j.ifContainsEmbedView()) {
            H5EmbededViewProvider embededViewProvider = this.j.getEmbededViewProvider();
            if (embededViewProvider != null) {
                embededViewProvider.onWebViewPause();
            }
        }
    }

    public void onResume() {
        this.a.onResume();
        H5Log.d(this.TAG, "H5WebView onWebViewResume");
        if (this.j != null && this.j.ifContainsEmbedView()) {
            H5EmbededViewProvider embededViewProvider = this.j.getEmbededViewProvider();
            if (embededViewProvider != null) {
                embededViewProvider.onWebViewResume();
            }
        }
    }

    public boolean isPaused() {
        return this.a.isPaused();
    }

    public void freeMemory() {
        this.a.freeMemory();
    }

    public void clearCache(boolean b2) {
        this.a.clearCache(b2);
    }

    public void clearFormData() {
        this.a.clearFormData();
    }

    public void clearHistory() {
        this.a.clearHistory();
    }

    public void clearSslPreferences() {
        this.a.clearSslPreferences();
    }

    public APWebBackForwardList copyBackForwardList() {
        return this.a.copyBackForwardList();
    }

    public void setDownloadListener(APDownloadListener apDownloadListener) {
        this.a.setDownloadListener(apDownloadListener);
    }

    public void setWebViewClient(APWebViewClient client) {
        this.a.setWebViewClient(client);
    }

    public void setWebChromeClient(APWebChromeClient client) {
        this.a.setWebChromeClient(client);
    }

    public void removeJavascriptInterface(String name) {
        H5Log.d(this.TAG, "removeJavascriptInterface " + name);
        if (VERSION.SDK_INT >= 11) {
            this.a.removeJavascriptInterface(name);
        }
    }

    public void onRelease() {
        if (!this.h) {
            this.h = true;
            H5Log.d(this.TAG, "releaseWebView!");
            long elapsed = System.currentTimeMillis() - this.l;
            if (this.l > 0 && this.j.getPageData() != null && !this.j.getPageData().isValid()) {
                H5LogUtil.logH5Exception(H5LogData.seedId(H5Logger.H5_ABNORMAL_ERROR).param1().add("PAGE_STARTED_ABNORMAL", null).param2().add("NO_ON_PAGE_STARTED", null).param3().add("elapsed", Long.valueOf(elapsed)).add("url", this.m));
            }
            if (H5ProviderManagerImpl.getInstance().getProvider(H5DevDebugProvider.class.getName()) != null) {
                Nebula.getService().sendEvent(CommonEvents.H5_DEV_WEBVIEW_RELEASE, null);
            }
            int time = 1000;
            if (H5Utils.isMainProcess() && ((Nebula.useSW(this.k) || H5Utils.getBoolean(this.k, (String) "isTinyApp", false)) && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_webview_release")))) {
                time = 500;
                H5Log.d(this.TAG, "useSw use 500");
            }
            try {
                NativeCrashHandlerApi.addCrashHeadInfo("h5WebViewReleaseUrl", getUrl());
                NativeCrashHandlerApi.addCrashHeadInfo("h5WebViewReleaseTime", String.valueOf(System.currentTimeMillis()));
            } catch (Throwable throwable) {
                H5Log.e(this.TAG, "onRelease addCrashHeadInfo", throwable);
            }
            final int finalTime = time;
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5WebView.this.a(finalTime);
                }
            }, (long) time);
        }
    }

    /* access modifiers changed from: private */
    public void a(int time) {
        try {
            H5Log.d(this.TAG, "loadBlankPage");
            H5Log.d(this.TAG, "H5WebView onWebViewDestory");
            if (this.j != null && this.j.ifContainsEmbedView()) {
                H5EmbededViewProvider embededViewProvider = this.j.getEmbededViewProvider();
                if (embededViewProvider != null) {
                    embededViewProvider.onWebViewDestory();
                }
            }
            loadUrl("about:blank");
            reload();
        } catch (Throwable t) {
            H5Log.e(this.TAG, "loadBlankPage exception.", t);
        }
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                H5WebView.this.c();
            }
        }, (long) time);
    }

    /* access modifiers changed from: private */
    public void c() {
        try {
            H5Log.d(this.TAG, "releaseWebView");
            if (this.j != null && this.j.ifContainsEmbedView()) {
                H5EmbededViewProvider embededViewProvider = this.j.getEmbededViewProvider();
                if (embededViewProvider != null) {
                    embededViewProvider.releaseView();
                }
                H5NewEmbedViewProvider newEmbedViewProvider = this.j.getNewEmbedViewProvider();
                if (newEmbedViewProvider != null) {
                    newEmbedViewProvider.releaseView();
                }
            }
            this.j = null;
            View content = this.a.getView();
            ViewParent parent = content.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(content);
            }
            content.setVisibility(8);
            content.clearFocus();
            content.clearAnimation();
            setDownloadListener(null);
            setWebViewClient(null);
            setWebChromeClient(null);
            stopLoading();
            clearHistory();
            clearSslPreferences();
            content.destroyDrawingCache();
            freeMemory();
        } catch (Throwable t) {
            H5Log.e(this.TAG, "releaseWebView exception.", t);
        } finally {
            destroy();
        }
    }

    public APWebSettings getSettings() {
        return this.a.getSettings();
    }

    public APHitTestResult getHitTestResult() {
        return this.a.getHitTestResult();
    }

    public Picture capturePicture() {
        return this.a.capturePicture();
    }

    public void setAPWebViewListener(APWebViewListener apWebViewListener) {
    }

    public View getView() {
        return this.a.getView();
    }

    public WebViewType getType() {
        return this.a.getType();
    }

    public String getVersion() {
        if (this.a == null) {
            return "(Null webview)";
        }
        return this.a.getVersion();
    }

    public H5Plugin getH5NumInputKeyboard() {
        return this.a.getH5NumInputKeyboard();
    }

    public H5Plugin getH5NativeInput() {
        return this.a.getH5NativeInput();
    }

    public void setScale(float v) {
        this.f = v;
    }

    public float getScale() {
        return this.f;
    }

    public void setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled) {
        this.a.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
    }

    public void setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled) {
        this.a.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled);
    }

    public void setOnScrollChangedCallback(H5ScrollChangedCallback changedCallback) {
        this.a.setOnScrollChangedCallback(changedCallback);
    }

    public int getScrollY() {
        return this.a.getScrollY();
    }

    public boolean dispatchKeyEvent(KeyEvent var1) {
        return this.a.dispatchKeyEvent(var1);
    }

    public boolean getCurrentPageSnapshot(Rect dstRect, Rect srcRect, Bitmap bitmap, boolean clipByView, int coordinate) {
        return this.a.getCurrentPageSnapshot(dstRect, srcRect, bitmap, clipByView, coordinate);
    }

    public H5Page getH5Page() {
        return this.j;
    }

    public APWebView getInternalContentView() {
        return this.a;
    }
}
