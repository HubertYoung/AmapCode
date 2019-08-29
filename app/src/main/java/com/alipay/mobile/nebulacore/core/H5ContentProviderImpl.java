package com.alipay.mobile.nebulacore.core;

import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5PageLoader;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider.ResponseListen;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppScoreList;
import com.alipay.mobile.nebula.appcenter.res.H5ResourceManager;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5MonitorLogConfig;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ResProvider;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandler;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.util.TestDataUtils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.alipay.mobile.nebula.webview.WebViewType;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.appcenter.center.H5GlobalDegradePkg;
import com.alipay.mobile.nebulacore.appcenter.center.H5GlobalPackage;
import com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage;
import com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackagePool;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.view.H5Toast;
import com.alipay.mobile.nebulacore.web.H5InputStream;
import com.alipay.mobile.nebulacore.web.H5InputStream.H5InputListener;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class H5ContentProviderImpl implements H5ContentProvider, H5InputListener {
    public static final String TAG = "H5ContentProviderImpl";
    private JSONObject A;
    private boolean B = true;
    private boolean C = false;
    private String D;
    private String E = "https://render.alipay.com/p/s/h5container/index";
    private boolean F = false;
    private boolean G = false;
    private boolean H = true;
    private boolean I;
    private Boolean J = null;
    private H5ContentPackage a;
    private String b;
    private String c;
    /* access modifiers changed from: private */
    public String d;
    private boolean e;
    private boolean f;
    private boolean g;
    private String h;
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;
    private Map<String, String> l;
    private List<H5InputStream> m;
    private List<InputStream> n;
    /* access modifiers changed from: private */
    public Bundle o;
    /* access modifiers changed from: private */
    public H5Page p;
    private H5ResProvider q;
    private H5ResourceHandler r;
    private HashMap<String, String> s;
    private String t;
    private String u = "YES";
    private boolean v = true;
    private boolean w = false;
    private ExecutorService x = null;
    private boolean y = false;
    private String z;

    H5ContentProviderImpl(H5Page page) {
        this.o = page.getParams();
        this.p = page;
        this.s = new HashMap<>();
        this.d = H5Utils.getString(this.o, (String) "appId");
        this.D = H5Utils.getString(this.o, (String) "sessionId");
        this.m = Collections.synchronizedList(new ArrayList());
        this.n = Collections.synchronizedList(new ArrayList());
        this.l = Collections.synchronizedMap(new HashMap());
        this.a = H5ContentPackagePool.getPackage(this.D);
        this.b = H5Utils.getString(this.o, (String) H5Param.OFFLINE_HOST);
        this.c = H5Utils.getString(this.o, (String) H5Param.ONLINE_HOST);
        this.e = H5Utils.getBoolean(this.o, (String) H5Param.MAP_HOST, false);
        this.f = H5Utils.getBoolean(this.o, (String) H5Param.ENABLE_FALLBACK, true);
        this.g = false;
        H5Log.d(TAG, "mapHost " + this.e + " enableFallback " + this.f);
        H5Log.d(TAG, "appId " + this.d + " offlineHost " + this.b + " sessionId:" + this.D);
        H5Log.d(TAG, "onlineHost " + this.c);
        this.h = H5Utils.getString(this.o, (String) H5Param.CDN_HOST);
        this.t = H5Utils.getString(this.o, (String) "appVersion");
        this.q = (H5ResProvider) Nebula.getProviderManager().getProvider(H5ResProvider.class.getName());
        this.r = (H5ResourceHandler) Nebula.getProviderManager().getProvider(H5ResourceHandler.class.getName());
        H5Log.d(TAG, " cdnHost " + this.h + " version:" + this.t);
        this.A = H5Utils.parseObject(H5Environment.getConfigWithProcessCache("h5_resRedirect"));
        this.G = "YES".equalsIgnoreCase(H5WalletWrapper.getConfig("h5_mainUrlDegrade"));
        this.I = "yes".equalsIgnoreCase(H5WalletWrapper.getConfig("h5_disableResPkgIn4"));
        if ("NO".equals(H5Environment.getConfigWithProcessCache("h5_tryConcatOnlineHost"))) {
            this.B = false;
        }
        this.k = H5Utils.getBoolean(H5Environment.getConfigJSONObject("h5_enableNetworkFallbackAsync"), (String) "enable", false);
        this.C = H5Utils.getBoolean(this.o, (String) H5Param.IS_NEBULA_APP, false);
        if ("NO".equalsIgnoreCase(H5Utils.getString(H5Utils.parseObject(H5WalletWrapper.getConfigWithProcessCache("h5_resManifest")), (String) "matchHeaders", (String) "YES"))) {
            this.H = false;
        }
    }

    public WebResourceResponse getContent(Uri uri, String originUrl, H5Page page, boolean canUseFallback, boolean canAsyncFallback, boolean isMainDoc) {
        try {
            if (Nebula.DEBUG && H5Utils.isMain()) {
                throw new RuntimeException("not invoke on ui thread!!!");
            } else if (TextUtils.isEmpty(originUrl)) {
                H5Log.e((String) TAG, (String) "invalid url parameter");
                return a((String) null, (InputStream) null);
            } else if (originUrl.startsWith("file://") || originUrl.startsWith(AjxHttpLoader.DOMAIN_HTTP) || originUrl.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
                ByteArrayInputStream byteArrayInputStream = null;
                final String pureUrl = H5UrlHelper.purifyUrl(originUrl);
                if (Nebula.DEBUG && pureUrl.startsWith("https://appx")) {
                    String appxHost = H5DevConfig.getStringConfig("h5_appx_host", null);
                    if (!TextUtils.isEmpty(appxHost)) {
                        if (pureUrl.contains("af-appx.min.js")) {
                            final String str = appxHost;
                            H5Utils.runOnMain(new Runnable() {
                                public void run() {
                                    H5Toast.showToast(H5Utils.getContext(), "由 " + str + " 加载appx框架", 0);
                                }
                            });
                        }
                        String replacedUrl = pureUrl.replace("https://appx", appxHost);
                        H5Log.d(TAG, "[debug] appx replacedUrl: " + replacedUrl);
                        return a(uri, pureUrl, replacedUrl);
                    }
                }
                if (this.y && pureUrl.startsWith("https://appx")) {
                    H5Log.d(TAG, "hasTinyGoOnline true use tinyRes again " + pureUrl);
                    if (!TextUtils.isEmpty(a())) {
                        return a(uri, pureUrl, pureUrl.replace(AjxHttpLoader.DOMAIN_HTTPS, a()));
                    }
                }
                if (this.a != null) {
                    byte[] data = this.a.get(pureUrl);
                    if (this.B && data == null && uri != null && this.c != null) {
                        data = this.a.get(this.c.concat(uri.getHost() + uri.getPath()));
                    }
                    if (data != null) {
                        ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(data);
                        if (TextUtils.equals(originUrl, H5PageLoader.mainUrl)) {
                            H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
                                public void run() {
                                    H5LogUtil.monitorLog(H5LogData.seedId("H5_AL_SESSION_MAP_SUCCESS").param2().add("appId", H5ContentProviderImpl.this.d).add("version", H5Utils.getString(H5ContentProviderImpl.this.o, (String) "appVersion")).add("url", pureUrl).add(H5Param.PUBLIC_ID, H5Utils.getString(H5ContentProviderImpl.this.o, (String) H5Param.PUBLIC_ID)).param4().add("appId", H5ContentProviderImpl.this.d).add("version", H5Utils.getString(H5ContentProviderImpl.this.o, (String) "appVersion")), H5MonitorLogConfig.newH5MonitorLogConfig().setLogType(H5MonitorLogConfig.WEBAPP_TYPE).setLogHeader(H5MonitorLogConfig.MONITOR_HEADER));
                                }
                            });
                        } else if (!this.w) {
                            this.w = true;
                            final String str2 = originUrl;
                            H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
                                public void run() {
                                    H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_RESOURCE_FIRST_OFFLINE").param3().add("targetUrl", str2).param4().add("appId", H5ContentProviderImpl.this.d).add("version", H5Utils.getString(H5ContentProviderImpl.this.o, (String) "appVersion")).add("url", H5PageLoader.mainUrl).add("h5SessionToken", new StringBuilder(H5SessionImpl.TAG).append(H5PageLoader.h5SessionToken).toString()));
                                }
                            });
                        }
                        H5Log.d(TAG, "[main_pkg] load response from " + this.d + " version:" + this.a.currentUseVersion + " size:" + data.length + " package " + pureUrl);
                        return a(uri, pureUrl, (InputStream) byteArrayInputStream2);
                    }
                }
                boolean disableResPkg = false;
                if (page != null) {
                    disableResPkg = a(page);
                }
                if (!disableResPkg) {
                    byte[] data2 = a(H5GlobalPackage.getContent(pureUrl, isMainDoc), pureUrl);
                    if (data2 != null) {
                        a(uri);
                        ByteArrayInputStream byteArrayInputStream3 = new ByteArrayInputStream(data2);
                        return a(uri, pureUrl, (InputStream) byteArrayInputStream3);
                    } else if (!isMainDoc || this.G) {
                        byte[] data3 = a(H5GlobalDegradePkg.getInstance().getContent(pureUrl), pureUrl);
                        if (data3 != null) {
                            ByteArrayInputStream byteArrayInputStream4 = new ByteArrayInputStream(data3);
                            return a(uri, pureUrl, (InputStream) byteArrayInputStream4);
                        }
                    } else {
                        H5Log.d(TAG, "[getContent] not use degrade res for url: " + pureUrl + " isMainDoc: " + isMainDoc + " mainUrlCanDegrade: " + this.G);
                    }
                } else {
                    H5Log.d(TAG, "disable respkg by SystemWebView & 4.x");
                }
                String content = this.s.remove(originUrl);
                if (!TextUtils.isEmpty(content)) {
                    H5Log.d(TAG, "load response from map local.");
                    return a((String) "text/html", content);
                }
                boolean unsafe = pureUrl.startsWith("file://") && !pureUrl.startsWith(this.b);
                if (H5ContentProvider.UN_SAFE.equals(pureUrl) || unsafe) {
                    H5Log.w(TAG, "load response forbidden by safe strategy.");
                    return a((Uri) null, this.E, (InputStream) new H5InputStream(this.E, this));
                } else if (H5ContentProvider.REDIRECT_LINK.equals(pureUrl)) {
                    return a((String) "text/html", H5ResourceManager.getRaw(R.raw.redirect_link).replace("####", Uri.parse(originUrl).getQueryParameter("url")));
                } else {
                    if (H5ContentProvider.WHITE_LINK.equals(pureUrl)) {
                        return a(R.raw.white_link);
                    }
                    if (H5ContentProvider.SECURITY_LINK.equals(pureUrl)) {
                        return a((String) "text/html", H5ResourceManager.getRaw(R.raw.security_link).replace("####", Uri.parse(originUrl).getQueryParameter("url")));
                    }
                    if (H5ContentProvider.H5_BRIDGE.equals(pureUrl)) {
                        H5Log.w(TAG, "load response for h5 js bridge");
                        String bridge = null;
                        if (this.p instanceof H5PageImpl) {
                            bridge = ((H5PageImpl) this.p).getScriptLoader().composeBridge();
                        }
                        return a((String) "application/javascript", bridge);
                    } else if (originUrl.endsWith("/favicon.ico") || originUrl.endsWith("/favicon.png") || originUrl.endsWith("/favicon2.ico")) {
                        H5Log.d(TAG, "favicon request intercepted");
                        return a((String) "image/x-icon", (String) "");
                    } else if (this.q == null || !this.q.contains(pureUrl)) {
                        if (pureUrl.startsWith("https://appx")) {
                            if (!TextUtils.isEmpty(a(pureUrl))) {
                                return a(uri, pureUrl, a() + pureUrl);
                            } else if (!TextUtils.isEmpty(a())) {
                                this.y = true;
                                return a(uri, pureUrl, pureUrl.replace(AjxHttpLoader.DOMAIN_HTTPS, a()));
                            }
                        }
                        String fallbackUrl = null;
                        if (this.f) {
                            fallbackUrl = b(originUrl);
                        }
                        if (this.f && this.g) {
                            String mimeType = null;
                            if (!TextUtils.isEmpty(fallbackUrl)) {
                                mimeType = H5FileUtil.getMimeType(H5UrlHelper.getPath(fallbackUrl));
                                byteArrayInputStream = new ByteArrayInputStream(fallbackUrl.getBytes());
                            }
                            WebResourceResponse webResourceResponse = new WebResourceResponse(mimeType, "fallbackUrl", byteArrayInputStream);
                            return webResourceResponse;
                        } else if (TextUtils.isEmpty(fallbackUrl) || !canUseFallback) {
                            if (Nebula.DEBUG && page != null) {
                                if (TextUtils.equals(originUrl, page.getUrl())) {
                                    TestDataUtils.storeJSParams("pageLoad|loadFrom", "online");
                                }
                            }
                            H5Log.d(TAG, "load response from web " + originUrl);
                            if (this.v) {
                                c((String) "NO");
                            }
                            if (this.r != null) {
                                WebResourceResponse webResourceResponse2 = this.r.shouldInterceptRequest(originUrl);
                                if (webResourceResponse2 != null) {
                                    return webResourceResponse2;
                                }
                            }
                            return null;
                        } else if (!canAsyncFallback || !this.k || page == null || page.pageIsClose()) {
                            H5InputStream is = new H5InputStream(fallbackUrl, this);
                            if (is.realStream != null && this.a != null) {
                                return a(is, pureUrl, fallbackUrl, originUrl, page);
                            }
                            H5Log.d(TAG, "fallback realStream or contentPackage is null, statusCode: " + is.statusCode + " fallbackUrl:" + fallbackUrl);
                            if (Nebula.DEBUG && page != null) {
                                if (TextUtils.equals(originUrl, page.getUrl())) {
                                    TestDataUtils.storeJSParams("pageLoad|loadFrom", "fallback");
                                }
                            }
                            return a(fallbackUrl, is.realStream);
                        } else {
                            this.l.put(H5UrlHelper.stripAnchor(originUrl), H5UrlHelper.stripAnchor(fallbackUrl));
                            a(null, page, originUrl, pureUrl, fallbackUrl, true);
                            return null;
                        }
                    } else {
                        H5Log.d(TAG, "load response from resource provider.");
                        return a(uri, pureUrl, this.q.getResource(pureUrl));
                    }
                }
            } else {
                H5Log.d(TAG, "skip load resource for " + originUrl);
                return null;
            }
        } catch (Throwable t2) {
            H5Log.e(TAG, "load response from web catch exception ", t2);
            return null;
        }
    }

    private boolean a(@NonNull H5Page h5Page) {
        H5Log.d(TAG, "disableResPkg switch: " + this.I);
        if (!this.I || h5Page.getWebView().getType() != WebViewType.SYSTEM_BUILD_IN || VERSION.SDK_INT >= 21) {
            return false;
        }
        return true;
    }

    private static byte[] a(byte[] data, String pureUrl) {
        if (data == null || !H5Flag.sInjectDebugConsoleJS.booleanValue() || !"https://appx/af-appx.worker.min.js".equals(pureUrl)) {
            return data;
        }
        return (new String(data) + H5ResourceManager.getRaw(R.raw.h5_debug_console_sw)).getBytes();
    }

    private String a() {
        if (TextUtils.isEmpty(this.z)) {
            H5AppCenterPresetProvider h5AppCenterPresetProvider = (H5AppCenterPresetProvider) H5Utils.getProvider(H5AppCenterPresetProvider.class.getName());
            if (h5AppCenterPresetProvider != null) {
                String tinyCommonApp = h5AppCenterPresetProvider.getTinyCommonApp();
                H5AppProvider appProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                if (appProvider != null) {
                    String version = appProvider.getVersion(tinyCommonApp);
                    this.z = appProvider.getH5AppCdnBaseUrl(tinyCommonApp, version);
                    H5Log.d(TAG, "getTinyResFallbackUrl " + tinyCommonApp + Token.SEPARATOR + version + Token.SEPARATOR + this.z);
                }
            }
        }
        return this.z;
    }

    private String a(String url) {
        if (this.A == null || this.A.isEmpty()) {
            return null;
        }
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Utils.getString(this.A, (String) FunctionSupportConfiger.SWITCH_TAG))) {
            return null;
        }
        JSONObject urlJson = H5Utils.getJSONObject(this.A, "content", null);
        if (urlJson != null && !urlJson.isEmpty()) {
            String online = urlJson.getString(url);
            if (!TextUtils.isEmpty(online)) {
                return online;
            }
        }
        return null;
    }

    public byte[] getLocalResource(String key) {
        if (this.a == null || !this.a.containsKey(key)) {
            return H5GlobalPackage.getContent(key, false);
        }
        return this.a.get(key);
    }

    public int getLottieAnimationImgsCount(String path) {
        if (this.a == null) {
            return 0;
        }
        int count = 0;
        for (String startsWith : this.a.keySet()) {
            if (startsWith.startsWith(path)) {
                count++;
            }
        }
        return count;
    }

    public String getContentPackageStatus() {
        if (this.a == null) {
            return "";
        }
        if (this.a.size() > 0) {
            return new StringBuilder(MergeUtil.SEPARATOR_KV).append(this.a.getAppId()).append("_Y_").append(this.a.getVersion()).append("_").append(H5AppScoreList.getInstance().getAppCredit(this.a.getAppId())).toString();
        }
        return new StringBuilder(MergeUtil.SEPARATOR_KV).append(this.a.getAppId()).append("_N_").append(this.a.getVersion()).append("_").append(H5AppScoreList.getInstance().getAppCredit(this.a.getAppId())).toString();
    }

    private WebResourceResponse a(Uri uri, String pureUrl, String onlineUrl) {
        H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_PAGE_RES_REDIRECT").param1().add(this.d, null).param2().add(this.t, null).param3().add("url", pureUrl).add("targetUrl", onlineUrl));
        H5InputStream is = new H5InputStream(onlineUrl, this);
        H5Log.d(TAG, "load response from tinyRes online " + onlineUrl);
        return a(uri, pureUrl, (InputStream) is);
    }

    private WebResourceResponse a(H5InputStream is, String pureUrl, String fallbackUrl, String originUrl, H5Page page) {
        byte[] bytes = H5IOUtils.inputToByte(is.realStream);
        if (bytes != null) {
            this.a.put(pureUrl, bytes);
        }
        a(is, page, originUrl, pureUrl, fallbackUrl, false);
        try {
            is.close();
        } catch (Throwable throwable) {
            H5Log.d(TAG, "getFallbackStream " + throwable);
        }
        InputStream stream = new ByteArrayInputStream(bytes);
        this.n.add(stream);
        return a(fallbackUrl, stream);
    }

    private void a(H5InputStream is, H5Page page, String originUrl, String pureUrl, String fallbackUrl, boolean isAsync) {
        H5Log.d(TAG, ("load response from fallback fallbackUrl: " + fallbackUrl + ", pureUrl :" + pureUrl + ", originUrl :" + originUrl + " isAsync: " + isAsync) + (is != null ? ", statusCode: " + is.statusCode : ""));
        if (this.v) {
            c((String) "NO");
        }
        if (Nebula.DEBUG && page != null && TextUtils.equals(originUrl, page.getUrl())) {
            TestDataUtils.storeJSParams("pageLoad|loadFrom", "fallback");
        }
        if (isAsync) {
            if (!this.i) {
                this.i = true;
                if (!(page == null || page.getPageData() == null)) {
                    page.getPageData().putIntExtra("fallbackType", 2);
                }
            }
        } else if (!this.j && !this.i) {
            this.j = true;
            if (!(page == null || page.getPageData() == null)) {
                page.getPageData().putIntExtra("fallbackType", 1);
            }
        }
        String startUpUrl = H5Utils.getString(this.o, (String) "url");
        H5Log.d(TAG, "fallback showProgress start_up_url is " + startUpUrl + ", pureUrl is " + pureUrl);
        if (TextUtils.equals(startUpUrl, pureUrl) && this.p != null) {
            H5Log.d(TAG, "fallback showProgress");
            this.p.sendEvent("showProgressBar_fallback", null);
        }
    }

    public WebResourceResponse getContent(String originUrl) {
        return getContent(null, originUrl, null, true, false, false);
    }

    public WebResourceResponse getContent(String originUrl, boolean fallback) {
        return getContent(null, originUrl, null, fallback, false, false);
    }

    public void getContent(final String url, final ResponseListen onGetResponse) {
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public void run() {
                try {
                    if (onGetResponse != null) {
                        WebResourceResponse response = H5ContentProviderImpl.this.getContent(url);
                        if (response != null) {
                            onGetResponse.onGetResponse(response);
                            return;
                        }
                        InputStream netInput = new H5InputStream(url, H5ContentProviderImpl.this);
                        byte[] bytes = H5IOUtils.inputToByte(netInput);
                        H5IOUtils.closeQuietly(netInput);
                        onGetResponse.onGetResponse(H5ContentProviderImpl.this.a(url, new ByteArrayInputStream(bytes)));
                    }
                } catch (Throwable throwable) {
                    H5Log.e((String) H5ContentProviderImpl.TAG, throwable);
                }
            }
        });
    }

    private boolean b() {
        if (this.J == null) {
            if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_enableUseGetContentOnUi"))) {
                this.J = Boolean.valueOf(false);
            } else {
                this.J = Boolean.valueOf(true);
            }
        }
        return this.J.booleanValue();
    }

    public void getContentOnUi(String url, ResponseListen responseListen) {
        if (responseListen == null) {
            try {
                H5Log.d(TAG, "responseListen == null");
            } catch (Throwable throwable) {
                H5Log.e((String) TAG, throwable);
            }
        } else if (!b()) {
            getContent(url, responseListen);
        } else {
            final long time = System.currentTimeMillis();
            byte[] data = null;
            if (this.a != null) {
                data = this.a.get(url);
            }
            if (data != null) {
                responseListen.onGetResponse(a((Uri) null, url, (InputStream) new ByteArrayInputStream(data)));
                H5Log.d(TAG, "getContentOnUi form contentPackage:" + (System.currentTimeMillis() - time) + Token.SEPARATOR + url);
                return;
            }
            final String str = url;
            final ResponseListen responseListen2 = responseListen;
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public void run() {
                    try {
                        final WebResourceResponse response = H5ContentProviderImpl.this.getContent(str);
                        if (response != null) {
                            H5Utils.runOnMain(new Runnable() {
                                public void run() {
                                    if (responseListen2 != null) {
                                        H5Log.d(H5ContentProviderImpl.TAG, "getContentOnUi form getContent 1:" + (System.currentTimeMillis() - time) + Token.SEPARATOR + str);
                                        responseListen2.onGetResponse(response);
                                    }
                                }
                            });
                            return;
                        }
                        InputStream netInput = new H5InputStream(str, H5ContentProviderImpl.this);
                        byte[] bytes = H5IOUtils.inputToByte(netInput);
                        H5IOUtils.closeQuietly(netInput);
                        final InputStream stream = new ByteArrayInputStream(bytes);
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                if (responseListen2 != null) {
                                    H5Log.d(H5ContentProviderImpl.TAG, "getContentOnUi form getContent 2:" + (System.currentTimeMillis() - time) + Token.SEPARATOR + str);
                                    responseListen2.onGetResponse(H5ContentProviderImpl.this.a(str, stream));
                                }
                            }
                        });
                    } catch (Throwable throwable) {
                        H5Log.e((String) H5ContentProviderImpl.TAG, throwable);
                        if (responseListen2 != null) {
                            responseListen2.onGetResponse(null);
                        }
                    }
                }
            });
        }
    }

    public void mapContent(String url, String content) {
        this.s.put(url, content);
    }

    public void setEnableFallbackUrl(boolean enable) {
        this.g = enable;
    }

    public void setFallbackCache(String originUrl, byte[] bytes) {
        if (this.a != null) {
            this.a.put(H5UrlHelper.purifyUrl(originUrl), bytes);
        }
    }

    public String getFallbackUrl(String originUrl) {
        if (TextUtils.isEmpty(originUrl) || this.l == null || !this.l.containsKey(originUrl)) {
            return null;
        }
        H5Log.d(TAG, "getFallbackUrl originUrl is : " + originUrl + ", fallbackUrl is : " + this.l.get(originUrl));
        return this.l.get(originUrl);
    }

    private String b(String url) {
        String host = this.e ? this.c : this.b;
        if (TextUtils.isEmpty(this.h) || TextUtils.isEmpty(host)) {
            H5Log.w(TAG, "[getFallback] cdn url or install host empty!");
            return null;
        } else if (!url.startsWith(host)) {
            H5Log.w(TAG, "[getFallback] url not starts with host");
            return null;
        } else {
            if (this.h != null && host.endsWith("/") && !this.h.endsWith("/")) {
                this.h += "/";
            }
            String finalUrl = url.replace(host, this.h);
            H5Log.d(TAG, "[getFallback] fallback final url " + finalUrl);
            if (this.p == null) {
                return finalUrl;
            }
            String fallbackReason = this.a != null ? this.a.getFallbackReason() : "contentPackageIsNull";
            String unAvailableReason = this.a != null ? this.a.getUnAvailableReason() : "";
            if (TextUtils.equals(url, H5PageLoader.mainUrl)) {
                H5LogUtil.monitorLog(H5LogData.seedId(CommonEvents.H5_AL_SESSION_FALLBACK).param1().add("fallbackReason", fallbackReason).add("unAvailableReason", unAvailableReason).add(H5Param.IS_NEBULA_APP, Boolean.valueOf(this.C)).add("inTinyProcess", Boolean.valueOf(H5Utils.isInTinyProcess())).add("downloadDelaySeconds", Long.valueOf(H5PageData.sAppDownloadDelaySeconds)).param2().add(this.p.getPageData().getPageInfo(), null).param3().add("fallbackUrl", finalUrl).param4().addUniteParam(this.p.getPageData()), H5MonitorLogConfig.newH5MonitorLogConfig().setLogType(H5MonitorLogConfig.WEBAPP_TYPE).setLogHeader(H5MonitorLogConfig.MONITOR_HEADER));
            }
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_SESSION_HTTPPROXY_FAIL").param2().add(this.p.getPageData().getPageInfo(), null).param3().add("fallbackUrl", finalUrl).param4().addUniteParam(this.p.getPageData()));
            return finalUrl;
        }
    }

    private static WebResourceResponse a(int pageId) {
        return new WebResourceResponse("text/html", "UTF-8", H5Environment.getResources().openRawResource(pageId));
    }

    private static WebResourceResponse a(String mimeType, String content) {
        if (content == null) {
            content = "";
        }
        byte[] data = null;
        try {
            data = content.getBytes("utf-8");
        } catch (Exception e2) {
            H5Log.e(TAG, "failed to get byte array", e2);
        }
        return new WebResourceResponse(mimeType, "UTF-8", new ByteArrayInputStream(data));
    }

    private WebResourceResponse a(Uri uri, String url, InputStream inputStream) {
        String mimeType = null;
        if (!TextUtils.isEmpty(url)) {
            if (uri == null) {
                uri = H5UrlHelper.parseUrl(url);
            }
            if (uri != null) {
                mimeType = H5FileUtil.getMimeType(uri.getPath());
            }
            H5Log.d(TAG, "[buildContent] url:" + url + " mimeType:" + mimeType);
        }
        if (this.v) {
            c((String) "YES");
        }
        WebResourceResponse resp = new WebResourceResponse(mimeType, "UTF-8", inputStream);
        if (this.H) {
            a(url, resp);
        }
        return resp;
    }

    private static void a(String url, WebResourceResponse resp) {
        Map headerMap = H5GlobalPackage.getHeader(url);
        if (headerMap != null) {
            String contentType = headerMap.get("Content-Type");
            if (!TextUtils.isEmpty(contentType)) {
                String mimeType = H5FileUtil.getMimeTypeFromContentType(contentType);
                if (!TextUtils.isEmpty(mimeType)) {
                    H5Log.d(TAG, "set content-type " + contentType + " from header.json: " + url);
                    resp.setMimeType(mimeType);
                }
            }
            headerMap.remove("Content-Type");
            if (VERSION.SDK_INT >= 21) {
                resp.setResponseHeaders(headerMap);
            }
        }
    }

    /* access modifiers changed from: private */
    public WebResourceResponse a(String url, InputStream inputStream) {
        String mimeType = null;
        if (!TextUtils.isEmpty(url)) {
            mimeType = H5FileUtil.getMimeType(H5UrlHelper.getPath(url));
            H5Log.d(TAG, "url:" + url + " mimeType:" + mimeType);
        }
        if (this.v) {
            c((String) "YES");
        }
        return new WebResourceResponse(mimeType, "UTF-8", inputStream);
    }

    public void onInputClose(H5InputStream his) {
        H5Log.debug(TAG, "on input stream close.");
        this.m.remove(his);
    }

    public void onInputOpen(H5InputStream his) {
        H5Log.debug(TAG, "on input stream open.");
        this.m.add(his);
    }

    public void onInputException() {
        this.F = true;
        H5Log.d(TAG, "h5InputStream exception");
    }

    public void clearInputException() {
        this.F = false;
    }

    public boolean hasInputException() {
        return this.F;
    }

    public void disconnect() {
        try {
            H5Log.debug(TAG, "disconnect connList " + this.m.size());
            synchronized (this.m) {
                for (int index = 0; index < this.m.size(); index++) {
                    H5InputStream his = this.m.get(index);
                    if (his != null) {
                        try {
                            his.close();
                        } catch (Exception e2) {
                            H5Log.e(TAG, "close connection exception.", e2);
                        }
                    }
                }
                this.m.clear();
            }
            H5Log.debug(TAG, "disconnect inputStreamList " + this.n.size());
            synchronized (this.n) {
                for (InputStream inputStream : this.n) {
                    try {
                        inputStream.close();
                    } catch (Exception e3) {
                        H5Log.e((String) TAG, (Throwable) e3);
                    }
                }
                this.n.clear();
            }
        } catch (Exception e4) {
            H5Log.e((String) TAG, (Throwable) e4);
        }
    }

    private void a(final Uri uri) {
        H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                try {
                    if (uri != null) {
                        H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_SESSION_HTTPPROXY").param3().add("targetUrl", uri.toString()).add("localFile", uri.getPath()).addUniteParam(H5ContentProviderImpl.this.p.getPageData()));
                    }
                } catch (Exception e) {
                    H5Log.e(H5ContentProviderImpl.TAG, "reqEndLog catch exception ", e);
                }
            }
        });
    }

    public void releaseContent() {
        H5Log.d(TAG, "releaseContent");
        if (this.a != null) {
            this.a.releaseContent();
        }
        H5ContentPackagePool.clearPackage(this.D);
        if (this.x != null) {
            try {
                this.x.shutdown();
            } catch (Throwable throwable) {
                H5Log.e((String) TAG, throwable);
            }
        }
        this.p = null;
        if (this.l != null) {
            try {
                this.l.clear();
            } catch (Throwable e2) {
                H5Log.e(TAG, "clear mFallbackUrlMap exception ", e2);
            }
        }
    }

    private void c(String isLocal) {
        this.u = isLocal;
        this.v = false;
    }

    public void reSetLocal() {
        this.u = "YES";
        this.v = true;
    }

    public String getLocal() {
        return this.u;
    }
}
