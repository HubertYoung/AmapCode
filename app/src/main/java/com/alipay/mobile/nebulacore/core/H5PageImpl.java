package com.alipay.mobile.nebulacore.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ValueCallback;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.api.H5AvailablePageData;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5Context;
import com.alipay.mobile.h5container.api.H5ErrorCode;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Page.H5ErrorHandler;
import com.alipay.mobile.h5container.api.H5Page.H5PageHandler;
import com.alipay.mobile.h5container.api.H5Page.H5TitleBarReadyCallback;
import com.alipay.mobile.h5container.api.H5PageCount;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5PluginManager;
import com.alipay.mobile.h5container.api.H5Scenario;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5TitleBar;
import com.alipay.mobile.h5container.service.H5ConfigService;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.appcenter.apphandler.loadingview.H5NebulaAppCacheManager;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.embedviewcommon.H5NewEmbedBaseViewListener;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5MainLinkMonitor;
import com.alipay.mobile.nebula.newembedview.H5NewEmbedViewProvider;
import com.alipay.mobile.nebula.provider.H5ABTestProvider;
import com.alipay.mobile.nebula.provider.H5AllowFileAccessProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5EmbededViewProvider;
import com.alipay.mobile.nebula.provider.H5InPageRenderProvider;
import com.alipay.mobile.nebula.provider.H5LottieViewProvider;
import com.alipay.mobile.nebula.provider.H5PreConnectProvider;
import com.alipay.mobile.nebula.provider.H5SharePanelProvider;
import com.alipay.mobile.nebula.provider.H5UrlDownloadProvider;
import com.alipay.mobile.nebula.search.H5InputCallback;
import com.alipay.mobile.nebula.search.H5InputListen;
import com.alipay.mobile.nebula.util.H5CdpAdvertisementController;
import com.alipay.mobile.nebula.util.H5KeepAliveUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5NetworkUtil;
import com.alipay.mobile.nebula.util.H5NetworkUtil.Network;
import com.alipay.mobile.nebula.util.H5NetworkUtil.NetworkListener;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5LoadingView;
import com.alipay.mobile.nebula.view.H5TitleView;
import com.alipay.mobile.nebula.view.IH5EmbedViewJSCallback;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import com.alipay.mobile.nebula.webview.APDownloadListener;
import com.alipay.mobile.nebula.webview.APWebViewClient;
import com.alipay.mobile.nebula.webview.WebViewType;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.bridge.H5BridgeImpl;
import com.alipay.mobile.nebulacore.config.H5PluginConfigManager;
import com.alipay.mobile.nebulacore.data.H5MemData;
import com.alipay.mobile.nebulacore.embedview.H5EmbededViewProviderImpl;
import com.alipay.mobile.nebulacore.embedview.H5NewEmbedViewProviderImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.plugin.H5ActionSheetPlugin;
import com.alipay.mobile.nebulacore.plugin.H5AlertPlugin;
import com.alipay.mobile.nebulacore.plugin.H5ApkLoadPlugin;
import com.alipay.mobile.nebulacore.plugin.H5BridgePlugin;
import com.alipay.mobile.nebulacore.plugin.H5CameraPreviewSizesPlugin;
import com.alipay.mobile.nebulacore.plugin.H5DatePlugin;
import com.alipay.mobile.nebulacore.plugin.H5EmbedViewPlugin;
import com.alipay.mobile.nebulacore.plugin.H5HttpPlugin;
import com.alipay.mobile.nebulacore.plugin.H5JSInjectPlugin;
import com.alipay.mobile.nebulacore.plugin.H5LoadingPlugin;
import com.alipay.mobile.nebulacore.plugin.H5LongClickPlugin;
import com.alipay.mobile.nebulacore.plugin.H5NewJSAPIPermissionPlugin;
import com.alipay.mobile.nebulacore.plugin.H5NotifyPlugin;
import com.alipay.mobile.nebulacore.plugin.H5PPDownloadPlugin;
import com.alipay.mobile.nebulacore.plugin.H5PagePlugin;
import com.alipay.mobile.nebulacore.plugin.H5PermissionPlugin;
import com.alipay.mobile.nebulacore.plugin.H5ScreenBrightnessPlugin;
import com.alipay.mobile.nebulacore.plugin.H5ShakePlugin;
import com.alipay.mobile.nebulacore.plugin.H5StartParamPlugin;
import com.alipay.mobile.nebulacore.plugin.H5ToastPlugin;
import com.alipay.mobile.nebulacore.plugin.H5UrlInterceptPlugin;
import com.alipay.mobile.nebulacore.search.H5SearchPlugin;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.nebulacore.ui.H5Fragment;
import com.alipay.mobile.nebulacore.ui.H5MainProcTinyActivity;
import com.alipay.mobile.nebulacore.ui.H5MainProcTinyTransActivity;
import com.alipay.mobile.nebulacore.ui.H5ViewHolder;
import com.alipay.mobile.nebulacore.util.H5ParamCheckUtil;
import com.alipay.mobile.nebulacore.util.H5TimeUtil;
import com.alipay.mobile.nebulacore.util.NebulaUtil;
import com.alipay.mobile.nebulacore.wallet.H5AutoClickPlugin;
import com.alipay.mobile.nebulacore.wallet.H5LoggerPlugin;
import com.alipay.mobile.nebulacore.wallet.H5WalletPageNotifyPlugin;
import com.alipay.mobile.nebulacore.web.H5ScriptLoader;
import com.alipay.mobile.nebulacore.web.H5WebChromeClient;
import com.alipay.mobile.nebulacore.web.H5WebView;
import com.alipay.mobile.nebulacore.web.H5WebViewClient;
import com.alipay.mobile.nebulacore.web.ResourceInfo;
import com.alipay.sdk.cons.c;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.bundle.featureguide.widget.SplashyFragment;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class H5PageImpl extends H5CoreTarget implements H5Page {
    public static final String LOG_NOT_SHOW_LOADINGVIEW = "Don't show loading view : ";
    public static final String LOTTIE_LAUNCH_FILE_PATH = "_animation/launch/";
    public static final String LOTTIE_LAUNCH_MANUAL_HIDE = "manualHide";
    public static final String LOTTIE_PUSH_WINDOW_FILE_PATH = "_animation/pushWindow/";
    public static final String TAG = "H5PageImpl";
    public static Boolean sIsLowerDevice = null;
    private ViewGroup A;
    private H5NewEmbedBaseViewListener B;
    private H5LottieViewProvider C;
    private View D;
    private String E = LOTTIE_LAUNCH_FILE_PATH;
    private String F = "";
    private H5TitleView G;
    private H5ViewHolder H;
    private int I;
    private int J;
    /* access modifiers changed from: private */
    public boolean K;
    private boolean L;
    private boolean M = true;
    private boolean N;
    private boolean O;
    /* access modifiers changed from: private */
    public H5CollectJsApiHandler P;
    /* access modifiers changed from: private */
    public boolean Q;
    private boolean R;
    private boolean S = false;
    private boolean T = false;
    private boolean U;
    private boolean V;
    private Map<String, Object> W;
    private OnTouchListener X = new OnTouchListener() {
        @SuppressLint({"ClickableViewAccessibility"})
        public boolean onTouch(View view, MotionEvent event) {
            H5PageImpl.this.u.onTouchEvent(event);
            return false;
        }
    };
    private APDownloadListener Y = new APDownloadListener() {
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
            H5UrlDownloadProvider downloadProvider = (H5UrlDownloadProvider) H5Utils.getProvider(H5UrlDownloadProvider.class.getName());
            if (downloadProvider != null) {
                downloadProvider.handleDownload(url, userAgent, contentDisposition, mimeType, contentLength, H5PageImpl.this);
                return;
            }
            Nebula.openInBrowser(H5PageImpl.this, url, null);
        }
    };
    BroadcastReceiver b = null;
    /* access modifiers changed from: private */
    public int c = 0;
    private Activity d;
    private H5SessionImpl e;
    /* access modifiers changed from: private */
    public Bundle f;
    private H5WebView g;
    private H5BridgeImpl h;
    private H5PageHandler i;
    private H5ErrorHandler j;
    private H5TitleBarReadyCallback k;
    /* access modifiers changed from: private */
    public H5Context l;
    private boolean m;
    private H5PageData n;
    private H5AvailablePageData o;
    private H5Fragment p;
    private H5WebChromeClient q;
    private H5WebViewClient r;
    private H5ScriptLoader s;
    /* access modifiers changed from: private */
    public long t;
    /* access modifiers changed from: private */
    public GestureDetector u;
    private View v;
    private String w;
    private NetworkListener x;
    private H5EmbededViewProvider y;
    private H5NewEmbedViewProvider z;

    private class H5CollectJsApiHandler implements H5CallBack {
        public boolean exitTabScene = false;
        public boolean waiting = false;

        public H5CollectJsApiHandler() {
        }

        public void onCallBack(JSONObject param) {
            this.waiting = false;
            H5Log.d(H5PageImpl.TAG, "collectJsApi param : " + param);
            JSONArray apiArray = H5Utils.getJSONArray(param, "syncJsApis", null);
            if (apiArray != null && !apiArray.isEmpty()) {
                for (int i = 0; i < apiArray.size(); i++) {
                    JSONObject jsApi = apiArray.getJSONObject(i);
                    if (jsApi != null) {
                        H5PageImpl.this.sendEvent(H5Utils.getString(jsApi, (String) c.n), H5Utils.getJSONObject(jsApi, "params", null));
                    }
                }
            }
            if (H5PageImpl.this.f()) {
                H5PageImpl.this.a(this.exitTabScene);
            } else {
                H5PageImpl.this.doExitPage(this.exitTabScene);
            }
        }
    }

    public H5PageImpl(Activity activity, Bundle params, H5ViewHolder h5ViewHolder) {
        long startTime = System.currentTimeMillis();
        H5Log.d(TAG, "h5startParamTime currentTimeMillis " + startTime);
        this.H = h5ViewHolder;
        H5Environment.setContext(activity);
        this.N = false;
        this.O = false;
        this.l = new H5Context(activity);
        this.d = activity;
        this.m = false;
        this.n = new H5PageData();
        this.n.setCreate(System.currentTimeMillis(), 4);
        this.o = new H5AvailablePageData();
        this.t = 0;
        this.K = false;
        this.L = true;
        this.Q = false;
        this.R = false;
        this.W = new ConcurrentHashMap();
        if (Nebula.DEBUG) {
            H5Log.d(TAG, "h5 page host in activity " + H5Utils.getClassName(activity));
        }
        this.f = params;
        if (this.f == null) {
            try {
                this.f = activity.getIntent().getExtras();
            } catch (Exception e2) {
                H5Log.e(TAG, "startParams getExtras Exception", e2);
            }
        }
        if (this.f == null) {
            this.f = new Bundle();
        }
        a(this.f);
        a();
        H5ParamParser.parseMagicOptions(this.f, TAG);
        this.f = H5ParamParser.parse(this.f, true);
        H5ParamCheckUtil.checkParams(this.f);
        c();
        if (H5Utils.getBoolean(this.f, (String) Nebula.HAS_H5_PKG, false)) {
            H5Log.debug(TAG, "setContentBeforeRedirect true");
            setContentBeforeRedirect(true);
        }
        this.a = new H5MemData();
        String bizType = TextUtils.isEmpty(TextUtils.isEmpty(H5Utils.getString(this.f, (String) "bizType", (String) "")) ? H5Utils.getString(params, (String) H5Param.PUBLIC_ID, (String) "") : H5Utils.getString(this.f, (String) "bizType", (String) "")) ? H5Utils.getString(params, (String) "appId") : TextUtils.isEmpty(H5Utils.getString(this.f, (String) "bizType", (String) "")) ? H5Utils.getString(params, (String) H5Param.PUBLIC_ID, (String) "") : H5Utils.getString(this.f, (String) "bizType", (String) "");
        Bundle webViewConfig = new Bundle();
        webViewConfig.putString("bizType", bizType);
        this.g = new H5WebView(activity, this, webViewConfig);
        H5Log.d(TAG, "h5_create_webview appId={} params={}");
        boolean allowAccessFromFileURL = d();
        H5Log.d(TAG, "allow webview access from file URL " + allowAccessFromFileURL);
        if (this.g != null) {
            this.g.init(allowAccessFromFileURL);
            this.g.setDownloadListener(this.Y);
            this.h = new H5BridgeImpl(this.g, this);
            this.q = new H5WebChromeClient(this);
            this.g.setWebChromeClient(this.q);
            this.r = new H5WebViewClient(this);
            this.g.setWebViewClient(this.r);
        }
        this.s = new H5ScriptLoader(this);
        i();
        this.c++;
        H5PageCount.addUrl(H5Utils.getString(params, (String) "url"));
        H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                if (H5PageImpl.this.l != null && H5PageImpl.this.l.getContext() != null) {
                    try {
                        String totalMem = H5PageCount.totalRamMemorySize(H5PageImpl.this.l.getContext());
                        String recordUrls = H5PageCount.getAll();
                        H5Log.d(H5PageImpl.TAG, "H5PAGE_INDEX create " + H5PageImpl.this.c + " walletMem " + "0" + " urls " + recordUrls + " totalRam " + totalMem);
                        if (H5PageImpl.this.c > 8) {
                            H5Log.d(H5PageImpl.TAG, "H5PAGE_INDEX > 8 send monitor");
                            JSONObject monitorInfo = new JSONObject();
                            monitorInfo.put((String) "urls", (Object) recordUrls);
                            monitorInfo.put((String) "usedMemory", (Object) "0");
                            monitorInfo.put((String) "totalMemory", (Object) totalMem);
                            H5PageImpl.this.sendEvent(CommonEvents.H5_VC_OVERLIMIT, monitorInfo);
                        }
                    } catch (Throwable e) {
                        H5Log.e((String) H5PageImpl.TAG, e);
                    }
                }
            }
        });
        j();
        try {
            applyParams();
        } catch (Throwable t2) {
            H5Log.e((String) TAG, t2);
        }
        this.u = new GestureDetector(activity, new SimpleOnGestureListener() {
            public boolean onDown(MotionEvent e) {
                H5PageImpl.this.t = System.currentTimeMillis();
                H5Flag.lastTouchTime = H5PageImpl.this.t;
                H5Log.d(H5PageImpl.TAG, "onDown " + H5PageImpl.this.t);
                return false;
            }
        });
        if (this.g != null) {
            this.g.getView().setOnTouchListener(this.X);
        }
        this.y = new H5EmbededViewProviderImpl(activity, this);
        this.z = new H5NewEmbedViewProviderImpl(activity, this);
        this.n.setUcFistWebView();
        H5TimeUtil.timeLog(H5TimeUtil.CREATE_PAGE, startTime);
        boolean useSysWebView = "true".equalsIgnoreCase(H5Utils.getString(this.f, (String) H5AlertPlugin.STARTUP_PARAM_USE_SYS_WEBVIEW));
        H5Flag.useSysWebView = useSysWebView;
        if (Nebula.isDSL && getWebView().getType() == WebViewType.SYSTEM_BUILD_IN && !useSysWebView) {
            Nebula.isDSL = false;
            sendEvent(H5AlertPlugin.showUCFailDialog, null);
            b();
        }
    }

    private void a() {
        if (this.b == null) {
            this.b = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    if (intent != null && intent.getAction() != null && "com.eg.android.AlipayGphone.action.CDP_CLOSE_ANNOUNCEMENT_VIEW_ACTION".equals(intent.getAction())) {
                        H5CdpAdvertisementController.closeAdvertisement(intent, H5PageImpl.this);
                        H5Log.d(H5PageImpl.TAG, "CLoseAnnouncementReceiver onReceive");
                    }
                }
            };
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.eg.android.AlipayGphone.action.CDP_CLOSE_ANNOUNCEMENT_VIEW_ACTION");
            H5Context h5Context = getContext();
            if (h5Context != null && h5Context.getContext() != null) {
                LocalBroadcastManager.getInstance(getContext().getContext()).registerReceiver(this.b, filter);
            }
        }
    }

    public H5PageImpl(Activity activity, H5ViewHolder h5ViewHolder) {
        long startTime = System.currentTimeMillis();
        H5Log.d(TAG, "h5startParamTime currentTimeMillis " + startTime);
        this.H = h5ViewHolder;
        H5Environment.setContext(activity);
        this.N = false;
        this.O = false;
        this.l = new H5Context(activity);
        this.d = activity;
        this.m = false;
        this.n = new H5PageData();
        this.n.setCreate(System.currentTimeMillis(), 4);
        this.o = new H5AvailablePageData();
        this.t = 0;
        this.K = false;
        this.L = true;
        this.Q = false;
        this.R = false;
        this.W = new ConcurrentHashMap();
        if (Nebula.DEBUG) {
            H5Log.d(TAG, "h5 page host in activity " + H5Utils.getClassName(activity));
        }
        this.f = new Bundle();
        this.f.putBoolean("isTinyApp", true);
        this.a = new H5MemData();
        Bundle webViewConfig = new Bundle();
        webViewConfig.putString("bizType", "tiny_app");
        this.g = new H5WebView(activity, this, webViewConfig);
        if (this.g != null) {
            this.g.setDownloadListener(this.Y);
            this.q = new H5WebChromeClient(this);
            this.g.setWebChromeClient(this.q);
            this.r = new H5WebViewClient(this);
            this.g.setWebViewClient(this.r);
        }
        this.c++;
        this.u = new GestureDetector(activity, new SimpleOnGestureListener() {
            public boolean onDown(MotionEvent e) {
                H5PageImpl.this.t = System.currentTimeMillis();
                H5Flag.lastTouchTime = H5PageImpl.this.t;
                H5Log.d(H5PageImpl.TAG, "onDown " + H5PageImpl.this.t);
                return false;
            }
        });
        if (this.g != null) {
            this.g.getView().setOnTouchListener(this.X);
        }
        this.n.setUcFistWebView();
        this.y = new H5EmbededViewProviderImpl(activity, this);
        this.z = new H5NewEmbedViewProviderImpl(activity, this);
        H5TimeUtil.timeLog(H5TimeUtil.CREATE_PAGE, "precreate", startTime);
        a();
    }

    public void setUpPage(Bundle params) {
        long time = System.currentTimeMillis();
        this.f = params;
        if (this.f == null) {
            try {
                this.f = this.d.getIntent().getExtras();
            } catch (Exception e2) {
                H5Log.e(TAG, "startParams getExtras Exception", e2);
            }
        }
        if (this.f == null) {
            this.f = new Bundle();
        }
        a(this.f);
        H5ParamParser.parseMagicOptions(this.f, TAG);
        this.f = H5ParamParser.parse(this.f, true);
        H5ParamCheckUtil.checkParams(this.f);
        c();
        if (H5Utils.getBoolean(this.f, (String) Nebula.HAS_H5_PKG, false)) {
            H5Log.debug(TAG, "setContentBeforeRedirect true");
            setContentBeforeRedirect(true);
        }
        if (this.g != null) {
            boolean allowAccessFromFileURL = d();
            H5Log.d(TAG, "allow webview access from file URL " + allowAccessFromFileURL);
            this.g.init(allowAccessFromFileURL);
            this.h = new H5BridgeImpl(this.g, this);
        }
        getPageData().setPageUrl(H5Utils.getString(getParams(), (String) "url"));
        this.s = new H5ScriptLoader(this);
        i();
        H5PageCount.addUrl(H5Utils.getString(params, (String) "url"));
        H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                if (H5PageImpl.this.l != null && H5PageImpl.this.l.getContext() != null) {
                    try {
                        String totalMem = H5PageCount.totalRamMemorySize(H5PageImpl.this.l.getContext());
                        String recordUrls = H5PageCount.getAll();
                        H5Log.d(H5PageImpl.TAG, "H5PAGE_INDEX create " + H5PageImpl.this.c + " walletMem " + "0" + " urls " + recordUrls + " totalRam " + totalMem);
                        if (H5PageImpl.this.c > 8) {
                            H5Log.d(H5PageImpl.TAG, "H5PAGE_INDEX > 8 send monitor");
                            JSONObject monitorInfo = new JSONObject();
                            monitorInfo.put((String) "urls", (Object) recordUrls);
                            monitorInfo.put((String) "usedMemory", (Object) "0");
                            monitorInfo.put((String) "totalMemory", (Object) totalMem);
                            H5PageImpl.this.sendEvent(CommonEvents.H5_VC_OVERLIMIT, monitorInfo);
                        }
                    } catch (Throwable e) {
                        H5Log.e((String) H5PageImpl.TAG, e);
                    }
                }
            }
        });
        j();
        try {
            applyParams();
        } catch (Throwable t2) {
            H5Log.e((String) TAG, t2);
        }
        boolean useSysWebView = "true".equalsIgnoreCase(H5Utils.getString(this.f, (String) H5AlertPlugin.STARTUP_PARAM_USE_SYS_WEBVIEW));
        H5Flag.useSysWebView = useSysWebView;
        if (Nebula.isDSL && getWebView().getType() == WebViewType.SYSTEM_BUILD_IN && !useSysWebView) {
            Nebula.isDSL = false;
            sendEvent(H5AlertPlugin.showUCFailDialog, null);
            b();
        }
        H5TimeUtil.timeLog(H5TimeUtil.CREATE_PAGE, "setUpPage", time);
    }

    private void b() {
        if (getPageData() != null && NebulaUtil.isLogBlankScreen(getPageData().getAppId())) {
            JSONObject param = new JSONObject();
            param.put((String) "errorType", (Object) "errorRender");
            param.put((String) "errorCode", (Object) Integer.valueOf(H5ErrorCode.BLANK_SCREEN_NEBULA_ERROR));
            H5Log.d(TAG, "send page abnormal event : " + param);
            sendEvent(CommonEvents.H5_PAGE_ABNORMAL, param);
        }
    }

    private void a(final H5Bridge bridge) {
        this.x = new NetworkListener() {
            public void onNetworkChanged(Network ot, Network nt) {
                if (!H5Utils.getBoolean(H5PageImpl.this.f, (String) "isTinyApp", false) || (H5PageImpl.this.getSession() != null && H5PageImpl.this.getSession().getTopPage() == H5PageImpl.this)) {
                    H5PageImpl.this.a(nt, bridge);
                } else {
                    H5Log.d(H5PageImpl.TAG, "H5_NETWORK_CHANGE but do not send from non-top page in tiny");
                }
            }
        };
        H5NetworkUtil.getInstance().addListener(this.x);
    }

    /* access modifiers changed from: private */
    public void a(Network nt, H5Bridge bridge) {
        if (getBridge() != null) {
            H5Log.d(TAG, "H5_NETWORK_CHANGE");
            String networkType = H5NetworkUtil.TransferNetworkType(nt);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "isConnected", (Object) Boolean.valueOf(!Constants.ANIMATOR_NONE.equals(networkType)));
            jsonObject.put((String) "networkType", (Object) networkType);
            if (bridge != null) {
                bridge.sendDataWarpToWeb(CommonEvents.H5_NETWORK_CHANGE, jsonObject, null);
            }
        }
    }

    private void a(Bundle param) {
        this.n.setAppId(H5Utils.getString(param, (String) "appId"));
        this.n.setPublicId(H5Utils.getString(param, (String) H5Param.PUBLIC_ID));
        this.n.setAppVersion(H5Utils.getString(param, (String) "appVersion"));
        this.n.setOpenAppId(H5Utils.getString(param, (String) H5Param.OPEN_APP_ID));
        this.n.setShopId(H5Utils.getString(param, (String) "shopId"));
        this.n.setCustomParams(H5Utils.getString(param, (String) H5Param.CUSTOM_PARAMS));
        String releaseType = H5Utils.getString(param, (String) "release_type");
        this.n.setReleaseType(releaseType);
        this.n.setSessionId(H5Utils.getString(param, (String) "sessionId"));
        this.n.putStringExtra(H5Param.ONLINE_HOST, H5Utils.getString(param, (String) H5Param.ONLINE_HOST));
        if (H5Utils.getBoolean(param, (String) "isTinyApp", false)) {
            this.n.setIsTinyApp("YES");
            H5Flag.sInjectDebugConsoleJS = Boolean.valueOf("DEBUG".equals(releaseType));
        } else {
            this.n.setIsTinyApp("NO");
        }
        if (H5Utils.getBoolean(param, (String) H5Param.LONG_ISPRERENDER, false)) {
            this.n.setPreRender(1);
        } else {
            this.n.setPreRender(0);
        }
    }

    private void c() {
        StringBuilder builder = new StringBuilder();
        builder.append("H5 start params:");
        for (String key : this.f.keySet()) {
            builder.append("\n[").append(key).append(" ==> ").append(this.f.get(key)).append("]");
        }
        H5Log.d(TAG, builder.toString());
    }

    public H5ScriptLoader getScriptLoader() {
        return this.s;
    }

    private boolean d() {
        String urlStr = H5Utils.getString(this.f, (String) "url");
        Uri uri = H5UrlHelper.parseUrl(urlStr);
        if (uri == null || !"file".equals(uri.getScheme())) {
            return false;
        }
        String path = uri.getPath();
        H5Log.d(TAG, "uri path : " + path);
        if (TextUtils.isEmpty(path) || path.contains("..") || path.contains("\\") || path.contains("%")) {
            return false;
        }
        if (path.startsWith("/android_asset") || path.startsWith("/android_res")) {
            return true;
        }
        H5AllowFileAccessProvider fileAccessProvider = (H5AllowFileAccessProvider) H5Utils.getProvider(H5AllowFileAccessProvider.class.getName());
        if (fileAccessProvider != null) {
            return fileAccessProvider.allowFileAccess(urlStr);
        }
        try {
            String fileDir = H5Utils.getContext().getFilesDir().getAbsolutePath();
            H5Log.d(TAG, "fileDir : " + fileDir);
            if (!path.startsWith(fileDir)) {
                return false;
            }
            String checkPath = path.substring(fileDir.length());
            H5Log.d(TAG, "checkPath : " + checkPath);
            return NebulaUtil.enableAllowFileAccess(checkPath);
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
            return false;
        }
    }

    private static boolean e() {
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_useNewSearchPageRemove"))) {
            return false;
        }
        return true;
    }

    public void onRelease() {
        if ((this.G instanceof H5TitleBar) && ((H5TitleBar) this.G).isSearchPage() && e()) {
            Nebula.getProviderManager().removeProvider(H5InputCallback.class.getName());
            Nebula.getProviderManager().removeProvider(H5InputListen.class.getName());
        }
        if (!e()) {
            Nebula.getProviderManager().removeProvider(H5InputCallback.class.getName());
            Nebula.getProviderManager().removeProvider(H5InputListen.class.getName());
        }
        if (this.r != null) {
            this.r.onRelease();
        }
        this.r = null;
        if (this.q != null) {
            this.q.onRelease();
        }
        this.q = null;
        if (this.h != null) {
            this.h.onRelease();
        }
        this.h = null;
        this.f = null;
        this.d = null;
        this.e = null;
        if (this.g != null) {
            this.g.setDownloadListener(null);
            this.g.onRelease();
        }
        this.g = null;
        this.l = null;
        this.s = null;
        this.i = null;
        H5NetworkUtil.getInstance().removeListener(this.x);
        this.x = null;
        this.P = null;
        this.C = null;
        this.D = null;
        super.onRelease();
    }

    public H5Context getContext() {
        return this.l;
    }

    public H5Session getSession() {
        return this.e;
    }

    public String getUrl() {
        if (this.r != null) {
            return this.r.getPageUrl();
        }
        return "";
    }

    public String getShareUrl() {
        if (TextUtils.equals("NEW", H5Environment.getConfig("h5_ShareUrlConfig"))) {
            if (this.g != null) {
                return this.g.getUrl();
            }
            return "";
        } else if (this.r != null) {
            return this.r.getShareUrl();
        } else {
            return "";
        }
    }

    public String getRedirectUrl() {
        if (this.r != null) {
            return this.r.getRedirectUrl();
        }
        return "";
    }

    public Bundle getParams() {
        return this.f;
    }

    public String getTitle() {
        if (this.g != null) {
            return TextUtils.isEmpty(this.g.getTitle()) ? this.F : this.g.getTitle();
        }
        return this.F;
    }

    public H5Bridge getBridge() {
        return this.h;
    }

    public H5WebView getWebView() {
        return this.g;
    }

    public H5WebViewClient getWebViewClient() {
        return this.r;
    }

    public H5WebChromeClient getWebChromeClient() {
        return this.q;
    }

    public boolean exitPage() {
        try {
            String createPageScene = H5Utils.getString(getParams(), (String) H5Param.CREATEPAGESENCE);
            if (!TextUtils.equals("H5Activity", createPageScene) && Nebula.needPageKeepAlive(this, this.d) && Nebula.doKeepAlive(this.d, getParams())) {
                H5Log.d(H5KeepAliveUtil.TAG, "needPageKeepAlive true with createPageScene: " + createPageScene);
                return false;
            } else if (g()) {
                b(false);
                return true;
            } else if (!f()) {
                return doExitPage(false);
            } else {
                a(false);
                return true;
            }
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
            return true;
        }
    }

    public boolean exitTabPage() {
        if (g()) {
            b(true);
            return true;
        } else if (!f()) {
            return doExitPage(true);
        } else {
            a(true);
            return true;
        }
    }

    /* access modifiers changed from: private */
    public boolean f() {
        if (!H5Utils.getBoolean(getParams(), (String) "isTinyApp", false) || !NebulaUtil.isCloseCheckDsl() || getWebView() == null || getWebView().getType() != WebViewType.THIRD_PARTY) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void a(final boolean exitTabScene) {
        int filterTime;
        if (!this.Q) {
            JSONObject jsonObject = H5Utils.parseObject(H5Environment.getConfig("h5_logNewBlankScreenConfig"));
            if (jsonObject == null || jsonObject.isEmpty() || this.n == null) {
                doExitPage(exitTabScene);
                return;
            }
            String enable = H5Utils.getString(jsonObject, (String) "enable");
            String regex = H5Utils.getString(jsonObject, (String) "appId");
            if (TextUtils.isEmpty(NebulaUtil.dslJs)) {
                NebulaUtil.dslJs = H5Utils.getString(jsonObject, (String) "script");
            }
            if (H5Utils.getInt(jsonObject, (String) "testFilter") != 0) {
                filterTime = H5Utils.getInt(jsonObject, (String) "testFilter");
            } else {
                filterTime = 1;
            }
            long currentTime = System.currentTimeMillis();
            long startTime = this.n.getStart();
            boolean filter = (currentTime - startTime) / 1000 < ((long) filterTime);
            H5Log.d(TAG, "check dsl currentTime : " + currentTime + " startTime : " + startTime + " filterTime : " + filterTime + " filter : " + filter);
            if (TextUtils.isEmpty(enable) || TextUtils.isEmpty(regex) || TextUtils.isEmpty(this.n.getAppId()) || !"yes".equalsIgnoreCase(enable) || !NebulaUtil.isAppIdMatch(regex, this.n.getAppId()) || filter || getWebView() == null || TextUtils.isEmpty(NebulaUtil.dslJs)) {
                doExitPage(exitTabScene);
                return;
            }
            this.Q = true;
            getWebView().evaluateJavascript(NebulaUtil.dslJs, new ValueCallback<String>() {
                public void onReceiveValue(String value) {
                    H5PageImpl.this.Q = false;
                    H5Log.d(H5PageImpl.TAG, "check dsl result : " + value);
                    JSONObject dslObject = H5Utils.parseObject(value);
                    if (dslObject != null) {
                        try {
                            if (dslObject.containsKey("isDSLError") && "true".equalsIgnoreCase(String.valueOf(dslObject.get("isDSLError")))) {
                                H5PageImpl.this.sendEvent(H5LoggerPlugin.DSL_ERROR_LOG, null);
                            }
                        } catch (Exception e) {
                            H5Log.e((String) H5PageImpl.TAG, (Throwable) e);
                        }
                    }
                    H5PageImpl.this.doExitPage(exitTabScene);
                }
            });
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    if (H5PageImpl.this.Q) {
                        H5Log.d(H5PageImpl.TAG, "check dsl overtime : " + H5PageImpl.this.Q);
                        H5PageImpl.this.doExitPage(exitTabScene);
                    }
                }
            }, 200);
        }
    }

    private boolean g() {
        JSONObject jsonObject = H5Utils.parseObject(H5Environment.getConfig("h5_isCollectDestroyJsApi"));
        if (jsonObject == null || jsonObject.isEmpty() || this.n == null) {
            return false;
        }
        String enable = H5Utils.getString(jsonObject, (String) "enable");
        String regex = H5Utils.getString(jsonObject, (String) "appId");
        if (!"yes".equalsIgnoreCase(enable) || !NebulaUtil.isAppIdMatch(regex, this.n.getAppId())) {
            return false;
        }
        return true;
    }

    private void b(final boolean exitTabScene) {
        if (this.P == null && this.h != null) {
            this.P = new H5CollectJsApiHandler();
            this.P.exitTabScene = exitTabScene;
            this.P.waiting = true;
            this.h.sendToWeb("collectDestroyJsApi", null, this.P);
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    if (H5PageImpl.this.P != null && H5PageImpl.this.P.waiting) {
                        H5Log.d(H5PageImpl.TAG, "collectJsApiHandler overtime, do exit");
                        H5PageImpl.this.doExitPage(exitTabScene);
                    }
                }
            }, 1000);
        }
    }

    public boolean onInterceptError(String errorUrl, int statusCode) {
        boolean intercepted = false;
        if (this.j != null) {
            intercepted = this.j.shouldInterceptError(errorUrl, statusCode);
            if (intercepted) {
                H5Log.w(TAG, "page error intercepted: " + errorUrl + ", " + statusCode);
            }
        }
        return intercepted;
    }

    public boolean doExitPage(boolean exitTabScene) {
        if (this.i != null && !this.i.shouldExit()) {
            H5Log.w(TAG, "page exit intercepted by host!");
            return false;
        } else if (this.m) {
            H5Log.d(TAG, "page already exited!");
            return false;
        } else {
            this.m = true;
            if (!(this.l == null || this.l.getContext() == null)) {
                LocalBroadcastManager.getInstance(this.l.getContext()).unregisterReceiver(this.b);
            }
            h();
            if (this.h != null) {
                sendExitEvent();
            }
            H5SharePanelProvider h5SharePanelProvider = (H5SharePanelProvider) Nebula.getProviderManager().getProvider(H5SharePanelProvider.class.getName());
            if (h5SharePanelProvider != null) {
                h5SharePanelProvider.removeMenuList(hashCode());
            }
            H5PreConnectProvider provider = (H5PreConnectProvider) H5Utils.getProvider(H5PreConnectProvider.class.getName());
            if (provider != null) {
                provider.clearPreRequest(this);
            }
            H5Log.d("H5SharePanelProviderImp", "h5page quit , hashCode = " + hashCode());
            this.c--;
            H5PageCount.removeUrl(H5Utils.getString(this.f, (String) "url"));
            H5Log.d(TAG, "H5PAGE_INDEX exit " + this.c);
            H5Log.d(TAG, "exitPage");
            sendEvent(CommonEvents.H5_PAGE_CLOSED, null);
            if (this.g != null) {
                try {
                    this.g.getSettings().setJavaScriptEnabled(false);
                    ((InputMethodManager) H5Environment.getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.g.getView().getWindowToken(), 0);
                } catch (Exception e2) {
                    H5Log.e((String) TAG, (Throwable) e2);
                }
            }
            if (this.d != null) {
                boolean finish = true;
                if ((this.d instanceof H5Activity) && this.p != null) {
                    try {
                        H5Session h5Session = getSession();
                        if (h5Session != null) {
                            h5Session.getData().set(Nebula.H5_PAGE_RESUME, Nebula.H5_PAGE_RESUME);
                        }
                        finish = !((H5Activity) this.d).getH5FragmentManager().removeFragment(this.p, getParams(), exitTabScene);
                    } catch (Throwable e3) {
                        H5Log.e(TAG, "exception detail", e3);
                    }
                }
                if (finish && (this.d instanceof H5Activity) && !TextUtils.equals("H5Activity", H5Utils.getString(getParams(), (String) H5Param.CREATEPAGESENCE))) {
                    this.d.finish();
                }
            }
            return this.e.removePage(this);
        }
    }

    private void h() {
        final Map resourceInfoMap = new ConcurrentHashMap();
        if (!(this.r == null || this.r.getRequestMap() == null)) {
            resourceInfoMap.putAll(this.r.getRequestMap());
        }
        if (!resourceInfoMap.isEmpty()) {
            final H5LogData networkData = H5LogData.seedId("H5_AL_NETWORK_UNFINISH").param4().addUniteParam(getPageData());
            H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
                public void run() {
                    String requestInfo = "";
                    long currentTime = System.currentTimeMillis();
                    for (Entry entry : resourceInfoMap.entrySet()) {
                        requestInfo = requestInfo + H5UrlHelper.purifyUrl((String) entry.getKey()) + "(" + (currentTime - ((ResourceInfo) entry.getValue()).mStart) + ")|";
                    }
                    if (requestInfo.length() > 0 && requestInfo.endsWith(MergeUtil.SEPARATOR_KV)) {
                        requestInfo = requestInfo.substring(0, requestInfo.length() - 1);
                    }
                    networkData.param3().add("unfinishedUrls", "[" + requestInfo + "]");
                    H5LogUtil.logNebulaTech(networkData);
                }
            });
        }
    }

    private void i() {
        long timeMillis = System.currentTimeMillis();
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        H5ConfigService configService = (H5ConfigService) H5Utils.findServiceByInterface(H5ConfigService.class.getName());
        if (!(configService == null || h5ConfigProvider == null || "yes".equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_disableConfigServiceOpt")))) {
            configService.addExternalPlugins();
        }
        H5PluginManager pm = getPluginManager();
        pm.register((H5Plugin) new H5AlertPlugin());
        pm.register((H5Plugin) new H5NotifyPlugin());
        pm.register((H5Plugin) new H5ShakePlugin());
        pm.register((H5Plugin) new H5BridgePlugin(this));
        pm.register((H5Plugin) new H5PagePlugin(this));
        pm.register((H5Plugin) new H5DatePlugin());
        pm.register((H5Plugin) new H5LongClickPlugin(this));
        pm.register((H5Plugin) new H5HttpPlugin());
        pm.register((H5Plugin) new H5UrlInterceptPlugin());
        pm.register((H5Plugin) new H5PPDownloadPlugin());
        pm.register((H5Plugin) new H5ActionSheetPlugin());
        if (H5Utils.isInWallet()) {
            pm.register((H5Plugin) new H5ApkLoadPlugin());
        }
        pm.register((H5Plugin) new H5PermissionPlugin());
        if (this.g != null) {
            H5Plugin numInputPlugin = this.g.getH5NumInputKeyboard();
            if (numInputPlugin != null) {
                pm.register(numInputPlugin);
            }
            H5Plugin nativeInputPlugin = this.g.getH5NativeInput();
            if (nativeInputPlugin != null) {
                pm.register(nativeInputPlugin);
            }
        }
        pm.register((H5Plugin) new H5ScreenBrightnessPlugin());
        if (!(this.d instanceof H5Activity)) {
            pm.register((H5Plugin) new H5WalletPageNotifyPlugin());
        }
        pm.register((H5Plugin) new H5LoggerPlugin());
        pm.register((H5Plugin) new H5AutoClickPlugin());
        pm.register((H5Plugin) new H5SearchPlugin(this));
        pm.register((H5Plugin) new H5LoadingPlugin(this));
        pm.register((H5Plugin) new H5StartParamPlugin());
        pm.register((H5Plugin) new H5EmbedViewPlugin());
        pm.register((H5Plugin) new H5CameraPreviewSizesPlugin());
        if (!H5Utils.isInWallet()) {
            pm.register((H5Plugin) new H5ToastPlugin());
        }
        H5Plugin extPagePlugin = H5PluginConfigManager.getInstance().createPlugin("page", pm);
        if (extPagePlugin != null) {
            pm.register(extPagePlugin);
        }
        if (Nebula.DEBUG) {
            pm.register((H5Plugin) new H5JSInjectPlugin());
        }
        pm.register((H5Plugin) new H5NewJSAPIPermissionPlugin());
        H5TimeUtil.timeLog(H5TimeUtil.CREATE_PAGE, H5TimeUtil.INIT_PLUGIN, timeMillis);
    }

    private void j() {
        this.e = (H5SessionImpl) Nebula.getService().getSession(H5Utils.getString(this.f, (String) "sessionId"));
        if (this.e != null) {
            H5Scenario h5Scenario = this.e.getScenario();
            String scenarioName = H5Utils.getString(this.f, (String) H5Param.LONG_BIZ_SCENARIO);
            if (!TextUtils.isEmpty(scenarioName) && h5Scenario == null) {
                H5Log.d(TAG, "set session scenario " + scenarioName);
                this.e.setScenario(new H5ScenarioImpl(scenarioName));
            }
        }
    }

    public boolean isFirstPage() {
        return this.S;
    }

    private void k() {
        JSONObject param = new JSONObject();
        String url = H5Utils.getString(this.f, (String) "url");
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!(this.d instanceof H5Activity) || !Nebula.enableOpenScheme(url, this.f)) {
            if ("YES".equalsIgnoreCase(H5Utils.getString(this.f, (String) "enableInPageRender"))) {
                H5InPageRenderProvider provider = (H5InPageRenderProvider) H5Utils.getProvider(H5InPageRenderProvider.class.getName());
                if (provider != null) {
                    provider.addInPageRender(url);
                }
            }
            H5MainLinkMonitor.triggerHandleUrlLink(this);
            H5Utils.handleTinyAppKeyEvent((String) "main", (String) "H5PageImpl.loadUrl()");
            if (!H5Utils.isInTinyProcess() || this.g == null || !TextUtils.isEmpty(this.g.getUrl()) || !p()) {
                if (TextUtils.isEmpty(H5AppUtil.matchAppId(url))) {
                    H5ABTestProvider provider2 = (H5ABTestProvider) H5Utils.getProvider(H5ABTestProvider.class.getName());
                    if (provider2 != null) {
                        String replacedUrl = provider2.handleURL(this, url);
                        if (!TextUtils.isEmpty(replacedUrl)) {
                            url = replacedUrl;
                        }
                    }
                }
                param.put((String) "url", (Object) url);
                param.put((String) "requestPreAuth", (Object) Boolean.valueOf(H5Utils.getBoolean(this.f, (String) "requestPreAuth", false)));
                if (this.f.containsKey(H5Param.REFERER)) {
                    param.put((String) H5Param.REFERER, (Object) H5Utils.getString(this.f, (String) H5Param.REFERER));
                }
                param.put((String) H5Param.PUBLIC_ID, (Object) H5Utils.getString(this.f, (String) H5Param.PUBLIC_ID, (String) ""));
                if (!TextUtils.isEmpty(CommonEvents.H5_PAGE_LOAD_URL)) {
                    sendEvent(CommonEvents.H5_PAGE_LOAD_URL, param);
                }
            } else if (!H5UrlInterceptPlugin.interceptXiaoChengXu(url, H5Utils.getString(this.f, (String) "appId"), this, false)) {
                a(url);
            }
        } else {
            exitPage();
        }
    }

    private void b(Bundle startParams) {
        if (H5Utils.isInTinyProcess() && TextUtils.isEmpty(H5Utils.getString(startParams, (String) "showLoading")) && isFirstPage() && !Nebula.isTinyWebView(startParams)) {
            String appId = H5Utils.getString(startParams, (String) "appId");
            JSONArray jsonArray = H5Utils.parseArray(H5Environment.getConfig("h5_show_tiny_loading"));
            if (jsonArray == null || (!jsonArray.contains(appId) && !jsonArray.contains("all"))) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put((String) "isTinyApp", (Object) Boolean.valueOf(true));
                sendEvent("showLoading", jsonObject);
                return;
            }
            H5Log.d(TAG, appId + " not show TinyLoading");
        }
    }

    public void applyParams() {
        if (Nebula.DEBUG) {
            H5Utils.getBoolean(this.f, (String) "ifCreatePage", false);
        }
        H5Log.d(TAG, "very important step applyParams!!!!!");
        if (this.T) {
            H5Log.d(TAG, "applyParams only invoke once");
            return;
        }
        this.T = true;
        this.e.addPage(this);
        Stack h5Pages = this.e.getPages();
        if (h5Pages != null && h5Pages.size() == 1) {
            H5Log.d(TAG, " is First Page");
            this.S = true;
        }
        a(getBridge());
        this.r.setWebProvider(this.e.getWebProvider());
        k();
        b(this.f);
        for (String key : this.f.keySet()) {
            String intentName = null;
            JSONObject param = new JSONObject();
            if ("url".equals(key)) {
                H5Log.d(TAG, key + " already sendEvent use loadUrlEvent");
            } else {
                if ("showLoading".equals(key)) {
                    if (H5Utils.getBoolean(this.f, key, false)) {
                        intentName = "showLoading";
                    }
                } else if ("backgroundColor".equals(key)) {
                    param.put(key, (Object) Integer.valueOf(H5Utils.getInt(this.f, key, -16777216) | -16777216));
                    intentName = CommonEvents.H5_PAGE_BACKGROUND;
                }
                if (!TextUtils.isEmpty(intentName)) {
                    sendEvent(intentName, param);
                }
            }
        }
        if (this.f != null) {
            this.f.remove("requestPreAuth");
        }
        if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_initTextSizeAsync"))) {
            H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                public void run() {
                    H5PageImpl.this.m();
                }
            });
        } else {
            l();
        }
        H5Log.d(TAG, "H5pageImpl applyParam");
    }

    private void l() {
        if (this.e != null) {
            H5Scenario h5Scenario = this.e.getScenario();
            if (h5Scenario != null && h5Scenario.getData() != null) {
                String sizeStr = h5Scenario.getData().get(H5Param.FONT_SIZE);
                if (!TextUtils.isEmpty(sizeStr)) {
                    try {
                        setTextSize(Integer.parseInt(sizeStr));
                    } catch (Exception e2) {
                        H5Log.e(TAG, "failed to parse scenario font size.", e2);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void m() {
        if (this.e != null) {
            H5Scenario h5Scenario = this.e.getScenario();
            if (h5Scenario != null && h5Scenario.getData() != null) {
                String sizeStr = h5Scenario.getData().get(H5Param.FONT_SIZE);
                if (!TextUtils.isEmpty(sizeStr)) {
                    try {
                        final int size = Integer.parseInt(sizeStr);
                        H5Log.d(TAG, "initTextSizeAsync");
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                H5PageImpl.this.setTextSize(size);
                            }
                        });
                    } catch (Exception e2) {
                        H5Log.e(TAG, "failed to parse scenario font size.", e2);
                    }
                }
            }
        }
    }

    public void showLoadingView() {
        if (this.L && !this.K) {
            H5Log.d(TAG, "show web loading view");
            if (this.H != null && this.H.getH5WebContainer() != null) {
                ViewGroup webContent = (ViewGroup) this.H.getH5WebContainer().getContent();
                if (webContent != null && this.C != null && this.D != null) {
                    if (this.n != null) {
                        this.n.setLottieLoadingAnimStart(System.currentTimeMillis());
                    }
                    this.K = true;
                    webContent.addView(this.D);
                    this.C.playAnimation(this.D);
                }
            }
        }
    }

    public void hideLoadingView() {
        H5Log.d(TAG, "hide web loading view");
        this.L = false;
        if (this.H != null && this.H.getH5WebContainer() != null) {
            ViewGroup webContent = (ViewGroup) this.H.getH5WebContainer().getContent();
            if (webContent != null && this.C != null && this.D != null) {
                this.K = false;
                webContent.removeView(this.D);
                this.C.stopAnimation(this.D);
                if (this.n != null) {
                    this.n.setLottieLoadingAnimEnd(System.currentTimeMillis());
                }
            }
        }
    }

    private boolean n() {
        if (!enableNebulaAppLoadingView()) {
            return false;
        }
        if (!H5Utils.getBoolean(this.f, (String) H5Param.IS_NEBULA_APP, false)) {
            H5Log.d(TAG, "Don't show loading view : isn't nebula app");
            return false;
        } else if (H5Utils.getBoolean(this.f, (String) H5Param.LONG_PACKAGE_LOADING_SHOWN, false)) {
            H5Log.d(TAG, "Don't show loading view : packageLoadingShown");
            return false;
        } else if (H5Utils.getBoolean(this.f, (String) H5Param.LONG_UC_INIT_LOADING_SHOWN, false)) {
            H5Log.d(TAG, "Don't show loading view : UCInitLoadingShown");
            return false;
        } else {
            if (H5Utils.getBoolean(this.f, (String) H5Param.LONG_ISPRERENDER, false)) {
                H5Log.d(TAG, "Don't show loading view : is pre render page");
            }
            if (this.l == null || this.l.getContext() == null || !H5Utils.NETWORK_TYPE_NOTREACHABLE.equals(H5Utils.getNetworkType(this.l.getContext()))) {
                return true;
            }
            H5Log.d(TAG, "Don't show loading view : network is unreachable");
            return false;
        }
    }

    private JSONObject o() {
        JSONObject lottieConfig = H5Utils.parseObject(this.f.getString(H5AppUtil.lottie_animation));
        if (lottieConfig == null || lottieConfig.isEmpty()) {
            H5Log.d(TAG, "Don't show loading view : lack lottie config");
            return null;
        }
        JSONObject loadingConfig = null;
        String fromTypeStr = H5Utils.getString(this.f, (String) H5Param.FROM_TYPE, (String) "");
        if (H5PageData.FROM_TYPE_START_APP.equals(fromTypeStr)) {
            loadingConfig = H5Utils.getJSONObject(lottieConfig, "launchConfig", null);
        } else if ("pushWindow".equals(fromTypeStr)) {
            this.E = LOTTIE_PUSH_WINDOW_FILE_PATH;
            loadingConfig = H5Utils.getJSONObject(lottieConfig, "pushWindowConfig", null);
        }
        if (loadingConfig == null || loadingConfig.size() <= 0) {
            return loadingConfig;
        }
        Pattern pattern = H5PatternHelper.compile(H5Utils.getString(loadingConfig, (String) "matchUrl", (String) ""));
        String url = H5Utils.getString(this.f, (String) "url");
        if (pattern == null || url == null || pattern.matcher(url).find()) {
            return loadingConfig;
        }
        H5Log.d(TAG, "Don't show loading view : not match url");
        return null;
    }

    public void setLoadingConfig(JSONObject loadingConfig) {
        if (LOTTIE_LAUNCH_MANUAL_HIDE.equals(H5Utils.getString(loadingConfig, (String) com.taobao.accs.common.Constants.KEY_MODE, (String) ""))) {
            this.M = false;
        }
        if (this.C != null && this.D != null) {
            int width = H5Utils.getInt(loadingConfig, (String) "width", 0);
            int height = H5Utils.getInt(loadingConfig, (String) "height", 0);
            int locationX = H5Utils.getInt(loadingConfig, (String) "locationX", 0);
            int locationY = H5Utils.getInt(loadingConfig, (String) "locationY", 0);
            if (width > 0 || height > 0 || locationX > 0 || locationY > 0) {
                this.C.setWidthAndHeight(this.D, width, height);
                this.C.setLocationXY(this.D, locationX, locationY);
            }
            String bgColorStr = H5Utils.getString(loadingConfig, (String) SplashyFragment.INTENT_bgColor, (String) "");
            if (!TextUtils.isEmpty(bgColorStr)) {
                try {
                    this.C.setBackgroundColor(this.D, Color.parseColor(bgColorStr));
                } catch (Throwable throwable) {
                    H5Log.e(TAG, "loadingview setBgColor failed", throwable);
                }
            }
        }
    }

    public void checkIfShowLoadingView() {
        if (n()) {
            JSONObject loadingConfig = o();
            if (loadingConfig != null && !loadingConfig.isEmpty()) {
                if (this.C == null) {
                    this.C = (H5LottieViewProvider) Nebula.getProviderManager().getProvider(H5LottieViewProvider.class.getName());
                    if (this.C != null) {
                        this.D = this.C.getLottieView(this.d);
                    }
                }
                setLoadingConfig(loadingConfig);
                if (this.e != null) {
                    H5ContentProviderImpl h5ContentProvider = (H5ContentProviderImpl) this.e.getWebProvider();
                    if (h5ContentProvider != null) {
                        byte[] jsonBytes = h5ContentProvider.getLocalResource(this.E + "loading.json");
                        if (jsonBytes != null && jsonBytes.length != 0 && this.p != null) {
                            if (this.C != null) {
                                this.C.setMainJson(this.D, jsonBytes);
                            }
                            int loadingImgCount = h5ContentProvider.getLottieAnimationImgsCount(this.E + "images");
                            H5Log.d(TAG, "loading img count " + loadingImgCount);
                            Map map = new HashMap();
                            for (int i2 = 0; i2 < loadingImgCount; i2++) {
                                String name = this.E + "images/img_" + i2 + ".png";
                                byte[] bytes = h5ContentProvider.getLocalResource(name);
                                if (bytes != null && bytes.length > 0) {
                                    map.put(name, bytes);
                                }
                            }
                            if (this.C != null && map.size() > 0) {
                                this.C.setImgs(this.D, map);
                            }
                            try {
                                showLoadingView();
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    public void run() {
                                        try {
                                            if (H5PageImpl.this.K) {
                                                H5Log.d(H5PageImpl.TAG, "time is up, hide LoadingView");
                                                H5PageImpl.this.hideLoadingView();
                                            }
                                        } catch (Throwable throwable) {
                                            H5Log.e(H5PageImpl.TAG, "hideLoadingView failed", throwable);
                                        }
                                    }
                                }, (long) (this.M ? 3000 : 10000));
                            } catch (Throwable e2) {
                                H5Log.e(TAG, "play lottie loading animation failed", e2);
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean enableNebulaAppLoadingView() {
        JSONObject jsonObject = H5Utils.parseObject(H5Environment.getConfigWithProcessCache("h5_enableNebulaAppLoadingView"));
        if (jsonObject != null && !jsonObject.isEmpty()) {
            String enable = H5Utils.getString(jsonObject, (String) "enable");
            if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(enable)) {
                return false;
            }
            if ("yes".equalsIgnoreCase(enable)) {
                String supportLower = H5Utils.getString(jsonObject, (String) "supportLower");
                if (isLowerDevice() && BQCCameraParam.VALUE_NO.equalsIgnoreCase(supportLower)) {
                    H5Log.d(TAG, "Don't show loading view : device not support");
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isLowerDevice() {
        if (sIsLowerDevice == null) {
            sIsLowerDevice = Boolean.valueOf(LoggerFactory.getLogContext().getDevicePerformanceScore() < 2015);
        }
        return sIsLowerDevice.booleanValue();
    }

    public void setHandler(H5PageHandler handler) {
        this.i = handler;
    }

    public void setH5ErrorHandler(H5ErrorHandler h5ErrorHandler) {
        this.j = h5ErrorHandler;
    }

    public View getContentView() {
        if (this.g != null) {
            return this.g.getView();
        }
        return null;
    }

    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        JSONObject param = new JSONObject();
        param.put((String) "baseUrl", (Object) baseUrl);
        param.put((String) "data", (Object) data);
        param.put((String) "mimeType", (Object) mimeType);
        param.put((String) "encoding", (Object) encoding);
        param.put((String) "historyUrl", (Object) historyUrl);
        sendEvent(CommonEvents.H5_PAGE_SHOULD_LOAD_DATA, param);
    }

    public void loadUrl(String url) {
        if (Nebula.interceptSchemeForTiny(url, this)) {
            H5Log.d(TAG, "interceptSchemeForTiny " + url);
            return;
        }
        JSONObject param = new JSONObject();
        param.put((String) "url", (Object) url);
        sendEvent(CommonEvents.H5_PAGE_LOAD_URL, param);
        H5Log.d(TAG, "page loadurl");
    }

    private void a(String url) {
        if (TextUtils.isEmpty(url)) {
            H5Log.w(TAG, "h5 url isEmpty");
            return;
        }
        H5MainLinkMonitor.triggerLoadUrlLink(this);
        if (this.g != null) {
            this.g.loadUrl(url);
        }
    }

    private static boolean p() {
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_enableLoadUrlWithWebView"))) {
            return false;
        }
        return true;
    }

    public void execJavaScript4EmbedView(String url, IH5EmbedViewJSCallback jsCallback) {
        if (this.g != null) {
            this.g.execJavaScript4EmbedView(url, jsCallback);
        }
    }

    public void setTextSize(int textSize) {
        if (this.g != null) {
            this.g.setTextSize(textSize);
        }
    }

    public H5Fragment getH5Fragment() {
        return this.p;
    }

    public void setH5Fragment(H5Fragment h5Fragment) {
        this.p = h5Fragment;
    }

    public H5PageData getPageData() {
        return this.n;
    }

    public H5AvailablePageData getAvailablePageData() {
        return this.o;
    }

    public String getVersion() {
        if (this.g != null) {
            return this.g.getVersion();
        }
        return "";
    }

    public long getLastTouch() {
        return this.t;
    }

    public View getRootView() {
        return this.v;
    }

    public void setRootView(View view) {
        this.v = view;
    }

    public String getPerformance() {
        return this.w;
    }

    public void setPerformance(String pagePerformance) {
        this.w = pagePerformance;
    }

    public boolean scriptbizLoadedAndBridgeLoaded() {
        return this.s != null && this.s.bizLoaded && this.s.bridgeLoaded;
    }

    public void setTitle(String title) {
        this.F = title;
    }

    public void setH5TitleBar(H5TitleView h5TitleBar) {
        this.G = h5TitleBar;
    }

    public H5TitleView getH5TitleBar() {
        return this.G;
    }

    public H5EmbededViewProvider getEmbededViewProvider() {
        return this.y;
    }

    public H5NewEmbedViewProvider getNewEmbedViewProvider() {
        return this.z;
    }

    public void setPageId(int id) {
        this.I = id;
    }

    public int getPageId() {
        return this.I;
    }

    public void setWebViewId(int i2) {
        this.J = i2;
    }

    public int getWebViewId() {
        return this.J;
    }

    public H5TitleBarReadyCallback getTitleBarReadyCallBack() {
        return this.k;
    }

    public void setTitleBarReadyCallBack(H5TitleBarReadyCallback h5TitleBarReadyCallback) {
        this.k = h5TitleBarReadyCallback;
    }

    public boolean ifContainsEmbedView() {
        return this.N;
    }

    public void setContainsEmbedView(boolean ifContains) {
        this.N = ifContains;
    }

    public boolean ifContainsEmbedSurfaceView() {
        return this.O;
    }

    public void setContainsEmbedSurfaceView(boolean ifContains) {
        this.O = ifContains;
    }

    public APWebViewClient getAPWebViewClient() {
        return this.r;
    }

    public void sendExitEvent() {
        if (this.h != null && !this.R) {
            this.R = true;
            this.h.sendToWeb("beforeunload", null, null);
            this.h.sendToWeb("beforeDestroy", null, null);
        }
    }

    public H5LoadingView getH5LoadingView() {
        if (this.H != null) {
            return this.H.getH5LoadingView();
        }
        return null;
    }

    public boolean isTransparentTitleState() {
        String transparentTitle = H5Utils.getString(this.f, (String) H5Param.LONG_TRANSPARENT_TITLE);
        return TextUtils.equals(transparentTitle, "auto") || TextUtils.equals(transparentTitle, "always") || TextUtils.equals(transparentTitle, "custom");
    }

    public void applyParamsIfNeed() {
        applyParams();
    }

    public void setExtra(String key, Object o2) {
        this.W.put(key, o2);
    }

    public Object getExtra(String key) {
        return this.W.get(key);
    }

    public synchronized void setNewEmbedViewRoot(View view) {
        if (this.B != null) {
            this.B.onNewEmbedBaseViewReady(view);
            this.B = null;
        }
        this.A = (ViewGroup) view;
    }

    public synchronized View getNewEmbedViewRoot(H5NewEmbedBaseViewListener listener) {
        ViewGroup viewGroup;
        if (this.A != null) {
            viewGroup = this.A;
        } else {
            this.B = listener;
            viewGroup = null;
        }
        return viewGroup;
    }

    public boolean hasContentBeforeRedirect() {
        return this.U;
    }

    public void setContentBeforeRedirect(boolean flag) {
        if (!this.V) {
            H5Log.debug(TAG, "setContentBeforeRedirect set only once");
            this.U = flag;
        }
        this.V = true;
    }

    public String getAdvertisementViewTag() {
        return H5Utils.FRAGMENT_ROOT_VIEW_TAG;
    }

    public boolean isTinyApp() {
        if (H5Utils.isInTinyProcess()) {
            return true;
        }
        Context context = getContext().getContext();
        if ((context instanceof H5MainProcTinyActivity) || (context instanceof H5MainProcTinyTransActivity)) {
            return true;
        }
        String appId = H5Utils.getString(this.f, (String) "appId", (String) null);
        if (TextUtils.isEmpty(appId)) {
            appId = H5Utils.getString(this.f, (String) "MINI-PROGRAM-WEB-VIEW-TAG", (String) null);
        }
        return H5NebulaAppCacheManager.NEBULA_H5TINY_APP.equalsIgnoreCase(H5NebulaAppCacheManager.getAppType(appId));
    }

    public boolean pageIsClose() {
        H5Log.d(TAG, "pageIsClose " + this.m);
        return this.m;
    }

    public ViewGroup getViewGroup() {
        if (this.H != null) {
            return this.H.getRootView();
        }
        return null;
    }

    public void replace(String url) {
        loadUrl("javascript:location.replace('" + url + "');");
    }

    public void injectPageReady() {
        if (this.g != null) {
            this.g.loadUrl("javascript:(function(){window.ALIPAYVIEWAPPEARED=1})();");
        }
        if (this.h != null) {
            this.h.sendToWeb("appearAfterPreRender", null, null);
        }
    }

    public boolean getAutoHideLoading() {
        return this.M;
    }
}
