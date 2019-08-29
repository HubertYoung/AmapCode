package com.alipay.mobile.nebulacore.web;

import android.annotation.TargetApi;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.MessageQueue.IdleHandler;
import android.os.Process;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.webkit.ValueCallback;
import android.webkit.WebResourceResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.h5container.api.H5AvailablePageData;
import com.alipay.mobile.h5container.api.H5ErrorCode;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5GetAllResponse;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5WebDriverHelper;
import com.alipay.mobile.monitor.api.ClientMonitor;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.H5RpcFailResult;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.appcenter.res.H5ResourceManager;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.data.H5Trace;
import com.alipay.mobile.nebula.dev.H5BugmeIdGenerator;
import com.alipay.mobile.nebula.io.PoolingByteArrayOutputStream;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5MainLinkMonitor;
import com.alipay.mobile.nebula.networksupervisor.H5NetworkSuScheduler;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5DevDebugProvider;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.provider.H5ErrorPageView;
import com.alipay.mobile.nebula.provider.H5HttpErrorRouterProvider;
import com.alipay.mobile.nebula.provider.H5PreConnectProvider;
import com.alipay.mobile.nebula.provider.H5ReceivedSslErrorHandler;
import com.alipay.mobile.nebula.provider.H5TinyAppProvider;
import com.alipay.mobile.nebula.util.H5DomainUtil;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5NetworkUtil;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.util.H5WarningTipHelper;
import com.alipay.mobile.nebula.util.TestDataUtils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory.DiscardOldestPolicy;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory.H5SingleThreadFactory;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.alipay.mobile.nebula.webview.APHttpAuthHandler;
import com.alipay.mobile.nebula.webview.APSslErrorHandler;
import com.alipay.mobile.nebula.webview.APWebBackForwardList;
import com.alipay.mobile.nebula.webview.APWebResourceRequest;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebula.webview.APWebViewClient;
import com.alipay.mobile.nebula.webview.H5ResContentList;
import com.alipay.mobile.nebula.webview.WebViewType;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.android.AndroidWebView;
import com.alipay.mobile.nebulacore.appcenter.center.H5GlobalPackage;
import com.alipay.mobile.nebulacore.core.H5ContentProviderImpl;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.alipay.mobile.nebulacore.dev.trace.H5PerformanceUtils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import com.alipay.mobile.nebulacore.plugin.H5SessionPlugin;
import com.alipay.mobile.nebulacore.plugin.H5UrlInterceptPlugin;
import com.alipay.mobile.nebulacore.ui.H5Fragment;
import com.alipay.mobile.nebulacore.util.H5ErrorMsgUtil;
import com.alipay.mobile.nebulacore.util.NebulaUtil;
import com.alipay.mobile.nebulacore.wallet.H5LoggerPlugin;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.autonavi.widget.ui.BalloonLayout;
import com.taobao.accs.common.Constants;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class H5WebViewClient implements APWebViewClient {
    public static final int DURATION_ERROR = 60000;
    private static Executor t;
    private static VisitHistoryQueue<String> u = new VisitHistoryQueue<>();
    private int A;
    private int B;
    private boolean C = false;
    public String TAG = "H5WebViewClient";
    H5WarningTipHelper a;
    /* access modifiers changed from: private */
    public H5PageImpl b;
    private H5WebDriverHelper c;
    /* access modifiers changed from: private */
    public H5PageData d;
    private H5AvailablePageData e;
    private String f;
    /* access modifiers changed from: private */
    public H5ContentProviderImpl g;
    private String h;
    private int i;
    /* access modifiers changed from: private */
    public int j;
    private boolean k;
    private String l;
    private Map<String, ResourceInfo> m;
    /* access modifiers changed from: private */
    public boolean n = true;
    private long o;
    private boolean p = false;
    private boolean q = false;
    private boolean r = true;
    private Handler s = null;
    /* access modifiers changed from: private */
    public JSONArray v;
    /* access modifiers changed from: private */
    public List<String> w;
    private boolean x = false;
    private boolean y = false;
    private boolean z = false;

    private static class VisitHistoryQueue<E> extends LinkedList<E> {
        private int a;

        VisitHistoryQueue() {
            this.a = 5;
            this.a = 5;
        }

        public boolean add(E object) {
            boolean result = super.add(object);
            if (size() > this.a) {
                removeFirst();
            }
            return result;
        }
    }

    public H5WebViewClient(H5PageImpl page) {
        this.TAG += hashCode();
        if (page != null && H5Utils.getBoolean(page.getParams(), (String) H5Param.LONG_ISPRERENDER, false)) {
            this.TAG += "_preRender";
        }
        this.b = page;
        this.d = page.getPageData();
        this.e = page.getAvailablePageData();
        this.k = false;
        this.i = Integer.MIN_VALUE;
        this.j = 0;
        this.h = H5BugmeIdGenerator.getBugmeViewId(this.b);
        this.d.setPageUrl(H5Utils.getString(page.getParams(), (String) "url"));
        this.d.setPageToken(UUID.randomUUID().toString());
        this.m = new ConcurrentHashMap();
        this.w = Collections.synchronizedList(new ArrayList());
        page.getContentView().getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            public boolean onPreDraw() {
                if (!(H5WebViewClient.this.b == null || H5WebViewClient.this.b.getWebView() == null)) {
                    if (H5WebViewClient.this.d.getContainerVisible() == 0) {
                        H5WebViewClient.this.d.setContainerVisible(System.currentTimeMillis());
                        H5MainLinkMonitor.triggerAppearLink(H5WebViewClient.this.b);
                    }
                    if (H5WebViewClient.this.d.getAppear() == 0 || H5WebViewClient.this.d.getAppearFromNative() == 0) {
                        int height = H5WebViewClient.this.b.getWebView().getContentHeight();
                        if (H5WebViewClient.this.j != height && H5WebViewClient.this.d.getAppear() == 0) {
                            H5WebViewClient.this.d.setAppear(System.currentTimeMillis() - H5WebViewClient.this.d.getStart());
                            H5Log.d(H5WebViewClient.this.TAG, "onPreDraw page appear " + H5WebViewClient.this.d.getAppear());
                        }
                        if (H5WebViewClient.this.j != height && H5WebViewClient.this.d.getAppearFromNative() == 0) {
                            if (H5WebViewClient.this.n) {
                                H5WebViewClient.this.d.setAppearFromNative(System.currentTimeMillis() - H5PageData.walletServiceStart);
                                H5WebViewClient.this.n = false;
                            } else {
                                H5WebViewClient.this.d.setAppearFromNative(System.currentTimeMillis() - H5WebViewClient.this.d.getStart());
                            }
                            H5Log.d(H5WebViewClient.this.TAG, "onPreDraw page appear native " + H5WebViewClient.this.d.getAppearFromNative());
                        }
                        H5WebViewClient.this.j = height;
                    }
                }
                return true;
            }
        });
        this.c = Nebula.getService().getWebDriverHelper();
        if ("NO".equals(H5Environment.getConfig("h5_asyncSendEvent"))) {
            this.r = false;
        }
        this.v = H5Utils.parseArray(H5Environment.getConfig("h5_fallback_log"));
        this.a = new H5WarningTipHelper();
    }

    public String getPageUrl() {
        return this.d.getPageUrl();
    }

    public String getShareUrl() {
        if (TextUtils.isEmpty(this.l)) {
            return this.d.getPageUrl();
        }
        return this.l;
    }

    public String getRedirectUrl() {
        return this.d.getNavUrl();
    }

    public void setCheckingUrl(String url) {
        this.f = url;
    }

    public void setWebProvider(H5ContentProvider provider) {
        this.g = (H5ContentProviderImpl) provider;
    }

    public boolean shouldOverrideUrlLoading(APWebView view, String url) {
        this.c.shouldOverrideUrlLoading(view, url);
        H5Log.debug(this.TAG, "gao shouldOverrideUrlLoading " + url);
        if (this.g != null) {
            this.g.disconnect();
        }
        if (this.b == null || TextUtils.isEmpty(url)) {
            return true;
        }
        this.d.setNavUrl(url);
        this.f = null;
        JSONObject param = new JSONObject();
        param.put((String) "url", (Object) url);
        if (Nebula.interceptSchemeForTiny(url, this.b)) {
            H5Log.d(this.TAG, "interceptScheme  url " + url);
            return true;
        }
        if (Nebula.enableOpenScheme(url, this.b.getParams())) {
            H5Log.d(this.TAG, "stripLandingURL&Deeplink url " + url + " bingo deeplink");
            return true;
        }
        boolean hasContent = this.b.hasContentBeforeRedirect();
        H5Log.debug(this.TAG, "shouldOverrideUrlLoading hasContent " + hasContent);
        if (H5Utils.isStripLandingURLEnable(url, "locationNormal") && hasContent) {
            String realUrl = H5Utils.getStripLandingURL(url);
            if (!TextUtils.equals(url, realUrl)) {
                H5EnvProvider h5EnvProvider = (H5EnvProvider) Nebula.getProviderManager().getProvider(H5EnvProvider.class.getName());
                if (h5EnvProvider != null) {
                    boolean result = h5EnvProvider.goToSchemeService(realUrl, this.b.getParams());
                    Bundle pageParams = this.b.getParams();
                    H5Utils.landingMonitor(url, realUrl, true, "location", H5Utils.getString(pageParams, (String) "appId"), H5Utils.getString(pageParams, (String) H5Param.PUBLIC_ID), H5Utils.getString(pageParams, (String) H5Param.LONG_BIZ_SCENARIO));
                    if (result) {
                        H5Log.d(this.TAG, "stripLandingURL&Deeplink url " + url + " bingo deeplink in landing");
                        this.b.exitPage();
                        return true;
                    }
                }
            }
        }
        if (view instanceof H5WebView) {
            try {
                if (H5Utils.getBoolean(((H5WebView) view).getWebViewConfig(), (String) "needVerifyUrl", true)) {
                    param.put((String) "needVerifyUrl", (Object) Boolean.valueOf(false));
                }
            } catch (Throwable e2) {
                H5Log.e(this.TAG, "exception detail", e2);
            }
        }
        APWebBackForwardList list = view.copyBackForwardList();
        param.put((String) "historySize", (Object) Integer.valueOf(list == null ? 0 : list.getSize()));
        Bundle startbundle = this.b.getParams();
        if (startbundle != null) {
            param.put((String) "appId", (Object) H5Utils.getString(startbundle, (String) "appId"));
            String preSSOLogin = H5Utils.getString(startbundle, (String) H5Param.LONG_PRESSO_LOGIN);
            String preSSOLoginBindingPage = H5Utils.getString(startbundle, (String) H5Param.LONG_PRESSO_LOGIN_BINDINGPAGE);
            String preSSOLoginUrl = H5Utils.getString(startbundle, (String) H5Param.LONG_PRESSO_LOGIN_URL);
            param.put((String) H5Param.PRESSO_LOGIN, (Object) preSSOLogin);
            param.put((String) H5Param.PRESSO_LOGIN_BINDINGPAGE, (Object) preSSOLoginBindingPage);
            param.put((String) H5Param.PRESSO_LOGIN_URL, (Object) preSSOLoginUrl);
        }
        this.b.sendEvent(CommonEvents.H5_PAGE_SHOULD_LOAD_URL, param);
        boolean handleBySelf = !url.equals(this.f);
        H5Log.d(this.TAG, "shouldOverrideUrlLoading " + handleBySelf);
        if (handleBySelf || this.g == null) {
            return handleBySelf;
        }
        this.g.reSetLocal();
        return handleBySelf;
    }

    public void onReceivedError(APWebView webView, int errorCode, String description, String failingUrl) {
        this.c.onReceivedError(webView, errorCode, description, failingUrl);
        this.d.putStringExtra(H5PageData.WEBVIEW_ERROR_CODE, String.valueOf(errorCode));
        this.d.putStringExtra(H5PageData.WEBVIEW_ERROR_DESC, description);
        this.d.setErrorCode(errorCode);
        H5Log.d(this.TAG, "onReceivedError errorCode " + errorCode + " description " + description + " failingUrl " + failingUrl);
        a(failingUrl, String.valueOf(errorCode), description);
        if (this.d.getErrorCode() < 0) {
            this.d.setNum1000(this.d.getNum1000() + 1);
        }
        if (this.d.getErrorCode() != -97) {
            ResourceInfo info = this.m.get(failingUrl);
            if (info != null && info.mIsMainDoc) {
                if (webView.getType() != WebViewType.THIRD_PARTY) {
                    this.d.setStatusCode(errorCode);
                }
                a(false);
            }
            a(failingUrl, (String) "genericError");
            a(webView, failingUrl, this.d.getStatusCode());
        }
    }

    public void onReceivedHttpError(APWebView view, int statusCode, String requestUrl) {
        H5Log.d(this.TAG, "onReceivedHttpError statusCode : " + statusCode + " requestUrl : " + requestUrl);
        if (!TextUtils.isEmpty(requestUrl) && this.d != null) {
            if (!TextUtils.equals(this.d.getPageUrl(), requestUrl)) {
                H5Log.d(this.TAG, "onReceivedHttpError : " + this.d.getPageUrl());
            } else {
                a(view, requestUrl, this.d.getStatusCode());
            }
        }
    }

    public void onReceivedSslError(APWebView view, APSslErrorHandler handler, SslError error) {
        this.c.onReceivedSslError(view, handler, error);
        if (error != null) {
            H5LogUtil.logNebulaTech(H5LogData.seedId("h5_onReceivedSslError").param1().add(error.getUrl(), null).param4().add(error.toString(), null));
        }
        H5ReceivedSslErrorHandler h5ReceivedSslErrorHandler = (H5ReceivedSslErrorHandler) Nebula.getProviderManager().getProvider(H5ReceivedSslErrorHandler.class.getName());
        if (h5ReceivedSslErrorHandler != null) {
            h5ReceivedSslErrorHandler.onReceivedSslError(view, handler, error);
        } else if (error == null) {
            H5Log.e(this.TAG, (String) "SslError==null");
        } else {
            int errorCode = error.getPrimaryError();
            this.d.setStatusCode(errorCode);
            this.d.setErrorCode(errorCode);
            String failingUrl = null;
            if (VERSION.SDK_INT >= 14) {
                failingUrl = error.getUrl();
            }
            if (TextUtils.isEmpty(failingUrl)) {
                failingUrl = this.d.getPageUrl();
            }
            H5Log.d(this.TAG, "onReceivedSslError " + error + ", url is " + failingUrl);
            if (this.b != null) {
                Uri uri = H5UrlHelper.parseUrl(failingUrl);
                if (uri == null || TextUtils.isEmpty(uri.getScheme()) || TextUtils.isEmpty(uri.getHost()) || TextUtils.equals("file", uri.getScheme())) {
                    H5Log.w(this.TAG, "ignore param check for " + failingUrl);
                    return;
                } else if (TextUtils.equals(uri.getHost(), H5UrlHelper.getHost(H5Utils.getString(this.b.getParams(), (String) H5Param.ONLINE_HOST)))) {
                    handler.proceed();
                    return;
                } else if ("yes".equalsIgnoreCase(H5Environment.getConfig("h5_close_sslError"))) {
                    handler.proceed();
                    return;
                } else if (H5DomainUtil.isSomeDomainInternal(H5DomainUtil.getNewDomainSuffix(failingUrl), H5Environment.getConfig("h5_sslError_WhiteList"))) {
                    handler.proceed();
                    return;
                } else if (System.currentTimeMillis() - this.o < 10000) {
                    handler.cancel();
                    view.stopLoading();
                    a(view, view.getUrl(), errorCode);
                    return;
                } else {
                    this.o = System.currentTimeMillis();
                    a(false);
                    a(failingUrl, (String) "sslError");
                }
            }
            handler.cancel();
            a(view, view.getUrl(), errorCode);
        }
    }

    public void onResourceResponse(APWebView view, HashMap<String, String> hashMap) {
        int statusCode = H5Utils.parseInt(hashMap.get("httpcode"));
        String requestUrl = hashMap.get("url");
        H5Log.d(this.TAG, "onResourceResponse statusCode " + statusCode + " url " + requestUrl);
        if (H5ResContentList.getInstance().contains(requestUrl)) {
            H5ResContentList.getInstance().remove(requestUrl);
            if (this.d != null) {
                this.d.setUcCacheResNum(this.d.getUcCacheResNum() + 1);
            }
        }
        if (this.m.containsKey(requestUrl)) {
            ResourceInfo info = this.m.get(requestUrl);
            if (this.b != null && info != null) {
                a(requestUrl, String.valueOf(statusCode), (String) "");
                if (this.e != null) {
                    this.e.reportReqEnd();
                }
                info.mMimeType = hashMap.get("mimetype");
                info.mStatusCode = statusCode;
                if (info.mIsMainDoc) {
                    if (statusCode == 301 || statusCode == 302 || statusCode >= 400) {
                        this.b.hideLoadingView();
                    }
                    this.d.setErrorCode(statusCode);
                    this.d.setFirstByte(System.currentTimeMillis() - this.d.getStart());
                }
                if (statusCode == 302) {
                    this.d.setNum302(this.d.getNum302() + 1);
                } else if (statusCode == 304) {
                    this.d.setNum304(this.d.getNum304() + 1);
                }
                if (statusCode >= 300 && statusCode < 400 && statusCode != 304) {
                    this.d.setNum300(this.d.getNum300() + 1);
                }
                if (statusCode == 404) {
                    this.d.setNum404(this.d.getNum404() + 1);
                }
                if (statusCode >= 400 && statusCode < 500) {
                    this.d.setNum400(this.d.getNum400() + 1);
                }
                if (statusCode >= 500) {
                    this.d.setNum500(this.d.getNum500() + 1);
                }
                if (info.mStatusCode >= 400) {
                    if (info.mIsMainDoc) {
                        a(info.mUrl, (String) "genericError");
                        a(false);
                    } else if (H5Utils.isJavascript(info.mUrl)) {
                        this.d.setNetErrorJsNum(this.d.getNetErrorJsNum() + 1);
                        this.d.setNetJsReqNum(this.d.getNetJsReqNum() + 1);
                    } else {
                        this.d.setNetErrorOtherNum(this.d.getNetErrorOtherNum() + 1);
                        this.d.setNetOtherReqNum(this.d.getNetOtherReqNum() + 1);
                    }
                    a(info);
                }
            }
        }
    }

    public void onResourceFinishLoad(APWebView view, String url, long size) {
        H5Log.d(this.TAG, "onResourceFinishLoad " + url + " size " + size);
        if (!TextUtils.isEmpty(url) && this.d != null) {
            this.a.showWarningTip(size);
            if (H5Utils.isDebuggable(H5Utils.getContext())) {
                b(url, size);
            }
            boolean isMainDoc = TextUtils.equals(this.d.getPageUrl(), url);
            if (isMainDoc && this.d.isShowErrorPage()) {
                if (size > 0) {
                    this.d.setShowErrorPage(false);
                }
                H5Utils.runOnMain(new Runnable() {
                    public void run() {
                        if (H5WebViewClient.this.d != null && H5WebViewClient.this.b != null) {
                            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_PAGE_REPAIR").param3().add("result", Integer.valueOf(H5WebViewClient.this.d.isShowErrorPage() ? 1 : 0)).add("type", Integer.valueOf(H5WebViewClient.this.d.getReloadType())).param4().addUniteParam(H5WebViewClient.this.d));
                        }
                    }
                }, BalloonLayout.DEFAULT_DISPLAY_DURATION);
            }
            if (isMainDoc) {
                H5PreConnectProvider preConnectProvider = (H5PreConnectProvider) H5Utils.getProvider(H5PreConnectProvider.class.getName());
                if (preConnectProvider != null) {
                    preConnectProvider.clearPreRequest(this.b);
                }
            }
            ResourceInfo info = this.m.remove(url);
            if (info != null) {
                if (info.mIsMainDoc) {
                    this.d.setHtmlSize(this.d.getHtmlSize() + size);
                    this.d.setPageNetLoad(System.currentTimeMillis() - this.d.getStart());
                    H5Log.debug(this.TAG, "url " + url + " pageNetLoad " + this.d.getPageNetLoad());
                }
                this.d.setFunctionHasCallback(3);
                this.d.setPageLoadSize(this.d.getPageLoadSize() + size);
                this.d.setPageSize(this.d.getPageSize() + size);
                if (this.d.getComplete() == 0 && size >= 204800) {
                    this.d.setSizeLimit200(this.d.getSizeLimit200() + 1);
                    if (!H5Utils.isImage(url)) {
                        if (TextUtils.isEmpty(this.d.getSizeLimit200Urls())) {
                            this.d.setSizeLimit200Urls(this.d.getSizeLimit200Urls() + url + "(" + ((int) (size / 1024)) + "KB)");
                        } else {
                            this.d.setSizeLimit200Urls(this.d.getSizeLimit200Urls() + MergeUtil.SEPARATOR_KV + url + "(" + ((int) (size / 1024)) + "KB)");
                        }
                    }
                }
                if (H5Utils.isCss(url)) {
                    if (this.d.getComplete() == 0) {
                        this.d.setCssLoadSize(this.d.getCssLoadSize() + size);
                    }
                    this.d.setCssSize(this.d.getCssSize() + size);
                } else if (H5Utils.isJavascript(url)) {
                    if (this.d.getComplete() == 0) {
                        this.d.setJsLoadSize(this.d.getJsLoadSize() + size);
                    }
                    this.d.setJsSize(this.d.getJsSize() + size);
                } else if (H5Utils.isImage(url)) {
                    if (this.d.getComplete() == 0) {
                        if (size >= 61440) {
                            this.d.setSizeLoadLimit60(this.d.getSizeLoadLimit60() + 1);
                        }
                        if (size >= 204800) {
                            this.d.setSizeLoadLimit200(this.d.getSizeLoadLimit200() + 1);
                        }
                        this.d.setImgLoadSize(this.d.getImgLoadSize() + size);
                    }
                    this.d.setImgSize(this.d.getImgSize() + size);
                    if (size >= 61440) {
                        this.d.setSizeLimit60(this.d.getSizeLimit60() + 1);
                        if (TextUtils.isEmpty(this.d.getImageSizeLimit60Urls())) {
                            this.d.setImageSizeLimit60Urls(this.d.getImageSizeLimit60Urls() + url + "(" + (size / 1024) + "KB)");
                        } else {
                            this.d.setImageSizeLimit60Urls(this.d.getImageSizeLimit60Urls() + MergeUtil.SEPARATOR_KV + url + "(" + (size / 1024) + "KB)");
                        }
                    }
                    H5Log.debug(this.TAG, "pageData.sizeLimit200Urls" + this.d.getSizeLimit200Urls() + Token.SEPARATOR + this.d.getImageSizeLimit60Urls());
                } else {
                    if (this.d.getComplete() == 0) {
                        this.d.setOtherLoadSize(this.d.getOtherLoadSize() + size);
                    }
                    this.d.setOtherSize(this.d.getOtherSize() + size);
                }
                long duration = System.currentTimeMillis() - info.mStart;
                if (H5Utils.isJavascript(url)) {
                    this.d.setNetJsReqNum(this.d.getNetJsReqNum() + 1);
                    this.d.setNetJsSize(this.d.getNetJsSize() + size);
                    this.d.setNetJsTime(this.d.getNetJsTime() + duration);
                } else {
                    this.d.setNetOtherReqNum(this.d.getNetOtherReqNum() + 1);
                    this.d.setNetOtherSize(this.d.getNetOtherSize() + size);
                    this.d.setNetOtherTime(this.d.getNetOtherTime() + duration);
                }
                if (duration < 60000) {
                    return;
                }
                if (info.mIsMainDoc) {
                    a(true);
                } else {
                    a(info);
                }
            }
        }
    }

    public void onReceivedResponseHeader(Map<String, List<String>> responseHeaders) {
        if (responseHeaders != null && responseHeaders.size() != 0 && responseHeaders.containsKey("x-ap-pkg-id")) {
            List pkgIdList = responseHeaders.get("x-ap-pkg-id");
            if (pkgIdList != null && pkgIdList.size() > 0) {
                a((String) pkgIdList.get(0));
            }
        }
    }

    public boolean shouldOverrideUrlLoadingForUC(APWebView apWebView, String url, int nonStandardType) {
        H5Log.d(this.TAG, "shouldOverrideUrlLoading nonStandardType：" + url + Token.SEPARATOR + nonStandardType);
        if (this.b == null || nonStandardType != 1) {
            return false;
        }
        String appId = H5Utils.getString(this.b.getParams(), (String) "appId");
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_shouldOverrideUrlLoading")) || !H5UrlInterceptPlugin.interceptXiaoChengXu(url, appId, this.b, true)) {
            return false;
        }
        return true;
    }

    public Map getRequestMap() {
        return this.m;
    }

    private void a(String appIdList) {
        if (!TextUtils.isEmpty(appIdList)) {
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider != null) {
                JSONObject joConfig = H5Utils.parseObject(h5ConfigProvider.getConfig("h5_pkgresmode"));
                String value = H5Utils.getString(joConfig, (String) "switchheader");
                int limit = 3;
                try {
                    limit = Integer.parseInt(H5Utils.getString(joConfig, (String) H5RpcFailResult.LIMIT, (String) "3"));
                } catch (NumberFormatException e2) {
                    H5Log.e(this.TAG, (Throwable) e2);
                }
                if (!CameraParams.FLASH_MODE_OFF.equalsIgnoreCase(value)) {
                    String[] list = appIdList.split(",");
                    if (list.length != 0 && list.length <= limit) {
                        final List pkgList = Arrays.asList(list);
                        H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
                            public void run() {
                                if (H5WebViewClient.this.d != null) {
                                    for (String appId : pkgList) {
                                        if (!appId.equals(H5WebViewClient.this.d.getAppId())) {
                                            H5GlobalPackage.addResourcePackage(H5WebViewClient.this.d.getAppId(), appId, true, false);
                                        }
                                    }
                                }
                                H5AppProvider h5AppProvider = (H5AppProvider) Nebula.getProviderManager().getProvider(H5AppProvider.class.getName());
                                if (h5AppProvider != null) {
                                    try {
                                        Map map = new HashMap();
                                        for (String appId2 : pkgList) {
                                            if (!H5WebViewClient.this.w.contains(appId2)) {
                                                if (NebulaUtil.enableResDegrade()) {
                                                    map.put(appId2, "*");
                                                } else {
                                                    map.put(appId2, h5AppProvider.getWalletConfigNebulaVersion(appId2));
                                                }
                                                H5WebViewClient.this.w.add(appId2);
                                                H5Log.d(H5WebViewClient.this.TAG, "resourcePkgIdList add : " + appId2);
                                            }
                                        }
                                        if (map.isEmpty()) {
                                            H5Log.d(H5WebViewClient.this.TAG, "map.isEmpty , don't sent rpc");
                                            return;
                                        }
                                        AppReq appReq = new AppReq();
                                        appReq.stableRpc = "NO";
                                        h5AppProvider.updateApp(H5UpdateAppParam.newBuilder().setDownLoadAmr(true).setAppMap(map).setAppReq(appReq).setResPackageList(pkgList).build());
                                    } catch (Throwable throwable) {
                                        H5Log.e(H5WebViewClient.this.TAG, throwable);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void a() {
        H5DevDebugProvider h5DevDebugProvider = (H5DevDebugProvider) H5ProviderManagerImpl.getInstance().getProvider(H5DevDebugProvider.class.getName());
        if (h5DevDebugProvider != null) {
            H5AppProvider appProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            if (appProvider != null && this.b != null && this.d != null) {
                String name = appProvider.getAppName(this.d.getAppId(), this.d.getAppVersion());
                String packageNick = H5Utils.getString(this.b.getParams(), (String) H5AppUtil.package_nick);
                JSONObject obj = new JSONObject();
                obj.put((String) "appId", (Object) this.d.getAppId());
                obj.put((String) "name", (Object) name);
                obj.put((String) "version", (Object) this.d.getAppVersion());
                obj.put((String) "packageNick", (Object) packageNick);
                obj.put((String) "resource", (Object) this.d.getResPkgInfo());
                h5DevDebugProvider.setPkgInfo(this.h, obj);
            }
        }
    }

    private void a(String failingUrl, String errorCode, String description) {
        if (this.b != null && this.b.getBridge() != null && !TextUtils.equals(errorCode, "200") && !TextUtils.equals(errorCode, "302") && !TextUtils.equals(errorCode, "304")) {
            H5Log.debug(this.TAG, "sendErrorResource:" + failingUrl + "  errorCode:" + errorCode + " description:" + description);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "url", (Object) failingUrl);
            jsonObject.put((String) "error", (Object) errorCode);
            jsonObject.put((String) "errorMessage", (Object) description);
            jsonObject.put((String) "client", (Object) "android");
            JSONObject packet = new JSONObject();
            packet.put((String) "data", (Object) jsonObject);
            this.b.getBridge().sendToWeb(CommonEvents.H5_RESOURCE_LOST, packet, null);
        }
    }

    public boolean shouldInterceptResponse(final APWebView view, HashMap<String, String> hashMap) {
        final String requestUrl = hashMap.get("url");
        final int statusCode = H5Utils.parseInt(hashMap.get("httpcode"));
        if ((statusCode > 100 && statusCode < 400) || view == null) {
            return false;
        }
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                H5WebViewClient.this.a(view, requestUrl, statusCode);
            }
        }, 20);
        if (statusCode != 403 && statusCode != 404) {
            return true;
        }
        H5Log.d(this.TAG, "404 or 403,shouldInterceptResponse return false ");
        return false;
    }

    private void a(ResourceInfo info) {
        if (this.b != null) {
            JSONObject param = new JSONObject();
            param.put((String) "targetUrl", (Object) info.mUrl);
            param.put((String) "method", (Object) info.mMethod);
            param.put((String) "type", (Object) info.mMimeType);
            param.put((String) "status", (Object) Integer.valueOf(info.mStatusCode));
            param.put((String) H5PageData.KEY_UC_START, (Object) Long.valueOf(info.mStart));
            param.put((String) "duration", (Object) Long.valueOf(System.currentTimeMillis() - info.mStart));
            param.put((String) "isMainDoc", (Object) info.mIsMainDoc ? "YES" : "NO");
            this.b.sendEvent(CommonEvents.H5_AL_NETWORK_PERFORMANCE_ERROR, param);
        }
    }

    private void a(String failingUrl, String errorType) {
        if (this.b != null) {
            H5Log.d(this.TAG, "reportErrorPage errorCode " + this.d.getErrorCode());
            JSONObject param = new JSONObject();
            param.put((String) "errorCode", (Object) Integer.valueOf(this.d.getErrorCode()));
            param.put((String) "type", (Object) errorType);
            param.put((String) "url", (Object) failingUrl);
            param.put((String) "networkType", (Object) H5Utils.getNetworkType(H5Environment.getContext()));
            param.put((String) "deviceInfo", (Object) H5Log.CURRENT_DEVICE_SPEC);
            this.b.sendEvent(CommonEvents.H5_PAGE_ERROR, param);
        }
    }

    private void a(boolean isLongRender) {
        if (this.b != null) {
            JSONObject param = null;
            if (isLongRender) {
                param = new JSONObject();
                param.put((String) "errorType", (Object) "longRender");
            }
            this.b.sendEvent(CommonEvents.H5_PAGE_ABNORMAL, param);
        }
    }

    /* access modifiers changed from: private */
    public void a(APWebView view, String errorUrl, int statusCode) {
        String backButtonText;
        String html;
        String html2;
        H5Log.d(this.TAG, "loadErrorPage " + errorUrl + " statusCode " + statusCode);
        if (this.b != null) {
            if (this.b.getParams() != null) {
                this.b.getParams().putBoolean(Nebula.DSL_ERROR, true);
            }
            if (!this.b.onInterceptError(errorUrl, statusCode)) {
                H5HttpErrorRouterProvider routerProvider = (H5HttpErrorRouterProvider) H5Utils.getProvider(H5HttpErrorRouterProvider.class.getName());
                if (routerProvider != null && !this.z) {
                    if (routerProvider.enableRoute(view, this.b, statusCode, errorUrl)) {
                        this.z = true;
                        return;
                    }
                }
                if (statusCode == 403 || statusCode == 404) {
                    H5Log.d(this.TAG, "loadErrorPage 404 or 403, return ");
                    return;
                }
                H5MainLinkMonitor.triggerPageFailLink(this.b);
                this.b.sendEvent(CommonEvents.H5_PAGE_ABNORMAL, null);
                String bizType = H5Utils.getString(this.b.getParams(), (String) "bizType");
                H5Log.d(this.TAG, "bizType:" + bizType);
                if (TextUtils.equals(bizType, "Advertisement")) {
                    this.b.getContentView().setVisibility(8);
                } else if (!H5Utils.getBoolean(this.b.getParams(), (String) H5Param.LONG_TRANSPARENT, false) || H5Utils.getBoolean(this.b.getParams(), (String) H5Param.LONG_TRANS_ANIMATE, false)) {
                    this.b.sendEvent("h5PageErrorForTitlebar", null);
                    this.b.getScriptLoader().resetBridge();
                    String pageTitle = H5Environment.getResources().getString(R.string.h5_loading_failed);
                    String errorResult = H5ErrorMsgUtil.getErrorMsg(statusCode, false);
                    String subErrorMsg = H5ErrorMsgUtil.getErrorMsg(statusCode, true);
                    String buttonText = H5Environment.getResources().getString(R.string.h5_menu_refresh);
                    String checkButtonText = H5Environment.getResources().getString(R.string.h5_network_check);
                    if (H5Utils.getBoolean(this.b.getParams(), (String) H5Param.LONG_TRANS_ANIMATE, false)) {
                        backButtonText = H5Environment.getResources().getString(R.string.h5_backward);
                    } else {
                        backButtonText = H5Environment.getResources().getString(R.string.h5_close);
                    }
                    H5Log.d(this.TAG, "load error page for: statusCode:" + statusCode + " errorResult:" + errorResult + " buttonText:" + buttonText);
                    if (this.b.getPageData() != null) {
                        this.b.getPageData().setShowErrorPage(true);
                    }
                    H5ErrorPageView h5ErrorPageView = (H5ErrorPageView) Nebula.getProviderManager().getProvider(H5ErrorPageView.class.getName());
                    if (h5ErrorPageView == null || !h5ErrorPageView.enableShowErrorPage()) {
                        if (NebulaUtil.isShowTransAnimate(this.b.getParams())) {
                            html = H5ResourceManager.getRaw(R.raw.h5_trans_page_error);
                        } else {
                            html = H5ResourceManager.getRaw(R.raw.h5_page_error);
                        }
                        if (html != null) {
                            String html3 = html.replace("####", buttonText).replace("****", checkButtonText).replaceAll("&&&&", errorResult + " (" + statusCode + ")").replace("!!!!", pageTitle).replace("$$$$", String.valueOf(statusCode)).replace("^^^^", backButtonText);
                            if (!TextUtils.isEmpty(errorUrl)) {
                                html2 = html3.replace("%%%%", errorUrl);
                            } else {
                                html2 = html3.replace("%%%%", "");
                            }
                            view.loadDataWithBaseURL(errorUrl, html2.replace("@@@@", H5SessionPlugin.SHOW_NETWORK_CHECK_ACTIVITY), "text/html", "utf-8", errorUrl);
                            if (this.b != null) {
                                JSONObject jsonObject = new JSONObject();
                                JSONObject spm = new JSONObject();
                                spm.put((String) "spmId", (Object) "H5_NONESPM_PAGE");
                                jsonObject.put((String) "spm", (Object) spm);
                                this.b.sendEvent(H5LoggerPlugin.REPORT_DATA, jsonObject);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    h5ErrorPageView.errorPageCallback(this.b, view, errorUrl, statusCode, errorResult, subErrorMsg);
                } else {
                    H5Log.d(this.TAG, "loadErrorPage in transparent case return directly");
                    this.b.sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
                }
            }
        }
    }

    public void onWebViewEvent(APWebView webView, int type, Object object) {
        H5Log.d(this.TAG, "onWebViewEvent " + type + Token.SEPARATOR + object);
        if (this.b == null) {
            H5Log.d(this.TAG, "onWebViewEvent h5Page == null");
            return;
        }
        switch (type) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 13:
            case 14:
                try {
                    a(object, type);
                    return;
                } catch (Throwable throwable) {
                    H5Log.e(this.TAG, throwable);
                }
            case 9:
            case 16:
                a(webView, object, type);
                return;
            case 104:
            case 105:
                a(object);
                return;
            default:
                return;
        }
        H5Log.e(this.TAG, throwable);
    }

    private void a(Object size) {
        if ("yes".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_enableTraceVideoAndAudioTrafficInUC"))) {
            try {
                long dataSize = H5Utils.parseLong((String) size);
                H5Log.e(this.TAG, "onVideoAndAudioTrafficUsed size :" + dataSize);
                ClientMonitor.getInstance().noteTraficConsume(H5UrlHelper.getHost(getPageUrl()), getPageUrl(), 0, dataSize, DataflowID.H5_UCCORE, null);
            } catch (Throwable e2) {
                H5Log.e(this.TAG, "onVideoAndAudioTrafficUsed error :", e2);
            }
        }
    }

    private void a(APWebView webView, Object object, int type) {
        H5Log.d(this.TAG, "onWebViewEvent empty page " + webView.getUrl());
        int blankTime = 0;
        try {
            blankTime = Integer.parseInt(String.valueOf(object));
        } catch (Exception e2) {
            H5Log.e(this.TAG, (Throwable) e2);
        }
        H5Log.d(this.TAG, "onWebViewEvent empty page blankTime is " + blankTime);
        if (blankTime == 3) {
            this.p = true;
            if (Nebula.DEBUG) {
                TestDataUtils.storeJSParams("pageLoad|emptyScreen", Integer.valueOf(3));
            }
        }
        if (blankTime == 6) {
            this.q = true;
            if (Nebula.DEBUG) {
                TestDataUtils.storeJSParams("pageLoad|emptyScreen", Integer.valueOf(6));
            }
            if (!(this.b == null || this.b.getParams() == null)) {
                this.b.getParams().putBoolean(Nebula.DSL_ERROR, true);
            }
            if (ActivityHelper.isBackgroundRunning() || !NebulaUtil.isScreenOn()) {
                H5Log.d(this.TAG, "activity isBackgroundRunning or screenOff");
                return;
            }
            NebulaUtil.whiteScreenSnapshotUpload(this.b);
            if (type != 16 || !H5Utils.getBoolean(this.b.getParams(), (String) "isTinyApp", false) || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5WalletWrapper.getConfigWithProcessCache("h5_enableTinyBaseColorEmptyScreen"))) {
                a(webView);
            }
        }
        if (blankTime == 9) {
            H5Log.d(this.TAG, "onWebViewEvent empty page blankTime 3&6 " + this.p + "&" + this.q);
            if (Nebula.DEBUG) {
                TestDataUtils.storeJSParams("pageLoad|emptyScreen", Integer.valueOf(9));
            }
            if (ActivityHelper.isBackgroundRunning() || !NebulaUtil.isScreenOn()) {
                H5Log.d(this.TAG, "activity isBackgroundRunning or screenOff");
            } else if (this.p && this.q && !H5Flag.hasShowLoading) {
                H5Log.d(this.TAG, "onWebViewEvent empty page blankTime is report monitor");
                JSONObject param = new JSONObject();
                param.put((String) "errorType", (Object) "ucLongRender");
                param.put((String) "errorCode", (Object) Integer.valueOf(type == 9 ? H5ErrorCode.BLANK_SCREEN_UC_EMPTY_SCREEN : H5ErrorCode.BLANK_SCREEN_UC_BASECOLOR_EMPTY_SCREEN));
                this.b.sendEvent(CommonEvents.H5_PAGE_ABNORMAL, param);
            }
        }
    }

    private void a(Object object, int type) {
        String key = null;
        switch (type) {
            case 4:
                this.A = b(object);
                return;
            case 5:
                this.B = b(object);
                return;
            case 6:
                key = H5PageData.KEY_UC_T1;
                break;
            case 7:
                key = H5PageData.KEY_UC_T2;
                break;
            case 8:
                key = H5PageData.KEY_UC_T3;
                break;
            case 13:
                key = H5PageData.KEY_UC_T2_PAINT;
                break;
            case 14:
                key = H5PageData.KEY_UC_T2_TRACE;
                break;
        }
        if (!TextUtils.isEmpty(key)) {
            this.b.getPageData().putIntExtra(key, b(object) - this.A);
            this.b.getPageData().putIntExtra(H5PageData.KEY_UC_T0, this.B - this.A);
        }
    }

    private static int b(Object object) {
        return (int) (Double.valueOf((String) ((HashMap) object).get("time")).doubleValue() * 1000.0d);
    }

    private void a(APWebView webView) {
        H5Log.d(this.TAG, "try checkDSLError");
        if (NebulaUtil.isUcCheckDsl() && NebulaUtil.isLogBlankScreen(this.b.getPageData().getAppId()) && H5Utils.getBoolean(this.b.getParams(), (String) "isTinyApp", false) && !TextUtils.isEmpty(NebulaUtil.dslJs)) {
            webView.evaluateJavascript(NebulaUtil.dslJs, new ValueCallback<String>() {
                public void onReceiveValue(String value) {
                    H5Log.d(H5WebViewClient.this.TAG, "check dsl result : " + value);
                    JSONObject dslObject = H5Utils.parseObject(value);
                    if (dslObject != null) {
                        try {
                            if (dslObject.containsKey("isDSLError") && "true".equalsIgnoreCase(String.valueOf(dslObject.get("isDSLError")))) {
                                JSONObject param = new JSONObject();
                                param.put((String) "errorType", (Object) "errorRender");
                                param.put((String) "errorCode", (Object) Integer.valueOf(H5ErrorCode.BLANK_SCREEN_DSL_ERROR));
                                if (H5WebViewClient.this.b != null && H5WebViewClient.this.b.getParams() != null) {
                                    H5Log.d(H5WebViewClient.this.TAG, "send page abnormal event : " + param);
                                    H5WebViewClient.this.b.sendEvent(CommonEvents.H5_PAGE_ABNORMAL, param);
                                }
                            }
                        } catch (Exception e) {
                            H5Log.e(H5WebViewClient.this.TAG, (Throwable) e);
                        }
                    }
                }
            });
        }
    }

    public void onFirstVisuallyRender(APWebView webview) {
        long time = System.currentTimeMillis();
        H5Log.d(this.TAG, "onFirstVisuallyRender " + time);
        JSONObject param = new JSONObject();
        if (webview != null) {
            param.put((String) "url", (Object) webview.getUrl());
        }
        H5Log.d(this.TAG, "onFirstVisuallyRender " + time);
        if (this.b != null) {
            if (!(this.b.getH5Fragment() == null || !this.b.getH5Fragment().isUseTranslateAnim() || this.b.getWebView() == null)) {
                H5Log.d(this.TAG, "pushwindow animation new webview onPause " + this.b.getWebView());
                this.b.getPageData().setUsePushWindowAnim(true);
                this.b.getH5Fragment().setShouldResumeWebView(true);
                this.b.getWebView().onPause();
                H5Utils.runOnMain(new Runnable() {
                    public void run() {
                        if (H5WebViewClient.this.b != null && H5WebViewClient.this.b.getWebView() != null) {
                            H5Log.d(H5WebViewClient.this.TAG, "pushwindow animation new webview onResume " + H5WebViewClient.this.b.getWebView());
                            H5WebViewClient.this.b.getWebView().onResume();
                        }
                    }
                }, 300);
            }
            this.b.getPageData().setFirstVisuallyRender(time);
            this.b.sendEvent(CommonEvents.H5_PAGE_RENDER, param);
        }
    }

    public void onFormResubmission(APWebView apWebView, Message message, Message message2) {
    }

    public void onReceivedHttpAuthRequest(APWebView apWebView, APHttpAuthHandler apHttpAuthHandler, String s2, String s22) {
    }

    public boolean shouldOverrideKeyEvent(APWebView apWebView, KeyEvent keyEvent) {
        return false;
    }

    public void onUnhandledKeyEvent(APWebView apWebView, KeyEvent keyEvent) {
    }

    public void onScaleChanged(APWebView apWebView, float v2, float v22) {
        if (apWebView != null) {
            apWebView.setScale(v22);
        }
    }

    public void onReceivedLoginRequest(APWebView apWebView, String s2, String s22, String s3) {
    }

    public String getJSBridge() {
        if (this.b == null || this.b.getParams() == null) {
            return null;
        }
        Bundle pageParams = this.b.getParams();
        if (H5Utils.getBoolean(pageParams, (String) "isTinyApp", false) || !TextUtils.isEmpty(H5Utils.getString(pageParams, (String) "MINI-PROGRAM-WEB-VIEW-TAG"))) {
            H5TinyAppProvider h5TinyAppProvider = (H5TinyAppProvider) H5Utils.getProvider(H5TinyAppProvider.class.getName());
            if (h5TinyAppProvider != null) {
                pageParams = h5TinyAppProvider.handlerStartupParams(this.b, pageParams);
            }
        }
        JSONObject params = H5Utils.toJSONObject(pageParams);
        if (params == null || params.isEmpty()) {
            return null;
        }
        Nebula.removeBridgeTimeParam(params);
        HashMap bridgeParams = new HashMap();
        bridgeParams.put(H5ScriptLoader.startupParams, params.toJSONString());
        String bridge = Nebula.loadJsBridge(bridgeParams, 0, this.b != null ? this.b.getWebViewId() : -1);
        H5Log.d(this.TAG, "begin set uc bridge " + bridge);
        return bridge;
    }

    public WebResourceResponse shouldInterceptRequest(APWebView view, APWebResourceRequest wr) {
        return a(view, wr.getUrl(), wr.getMethod());
    }

    @TargetApi(11)
    public WebResourceResponse shouldInterceptRequest(APWebView view, String url) {
        return a(view, H5UrlHelper.parseUrl(url), (String) "GET");
    }

    private WebResourceResponse a(APWebView view, Uri uri, String method) {
        if (uri == null || this.m.containsKey(uri.toString())) {
            return null;
        }
        if (!this.C && Looper.getMainLooper() != Looper.myLooper()) {
            this.C = true;
            try {
                Process.setThreadPriority(-20);
            } catch (Throwable throwable) {
                H5Log.e(this.TAG, "setThreadPriority", throwable);
            }
        }
        WebResourceResponse response = null;
        final String url = uri.toString();
        try {
            this.y = true;
            H5Log.d(this.TAG, "shouldInterceptRequest " + url + " method " + method);
            final boolean isMainDoc = TextUtils.equals(this.d.getPageUrl(), url);
            if (this.g != null) {
                this.g.clearInputException();
                response = this.g.getContent(uri, url, this.b, true, view.getType() == WebViewType.THIRD_PARTY, isMainDoc);
                if (this.g.hasInputException() && isMainDoc && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_handleInputException"))) {
                    final APWebView aPWebView = view;
                    H5Utils.runOnMain(new Runnable() {
                        public void run() {
                            H5Log.d(H5WebViewClient.this.TAG, "handle hasInputException");
                            H5WebViewClient.this.a(aPWebView, url, -1);
                        }
                    }, 20);
                }
                if (response != null && Nebula.DEBUG) {
                    a(url, response);
                }
            }
            final boolean hasResponse = response != null;
            if (!hasResponse) {
                ResourceInfo info = new ResourceInfo(System.currentTimeMillis(), method);
                if (url != null && isMainDoc) {
                    this.m.clear();
                    info.mIsMainDoc = true;
                }
                info.mUrl = url;
                this.m.put(url, info);
            }
            final Uri uri2 = uri;
            final String str = url;
            final String str2 = method;
            getSingleThreadExecutor().execute(new Runnable() {
                public void run() {
                    if (H5WebViewClient.this.b == null) {
                        H5Log.d(H5WebViewClient.this.TAG, "page ==null not log");
                        return;
                    }
                    if (!isMainDoc && TextUtils.isEmpty(H5WebViewClient.this.d.getResPkgInfo())) {
                        String resPkg = H5GlobalPackage.getResPkgInfo(H5WebViewClient.this.d.getAppId());
                        String mainPkg = "";
                        if (H5WebViewClient.this.g != null) {
                            mainPkg = H5WebViewClient.this.g.getContentPackageStatus();
                        }
                        H5WebViewClient.this.d.setResPkgInfo(resPkg + mainPkg);
                        H5WebViewClient.this.a();
                        NativeCrashHandlerApi.addCrashHeadInfo("h5AppVersion", H5WebViewClient.this.d.getAppVersion());
                        NativeCrashHandlerApi.addCrashHeadInfo("h5ResPkgInfo", H5WebViewClient.this.d.getResPkgInfo());
                    }
                    H5WebViewClient.this.a(uri2);
                    H5WebViewClient.this.a(H5WebViewClient.this.d.getPageUrl(), str, str2, isMainDoc);
                    H5DevDebugProvider h5DevDebugProvider = (H5DevDebugProvider) H5ProviderManagerImpl.getInstance().getProviderUseCache(H5DevDebugProvider.class.getName(), true);
                    if (hasResponse) {
                        H5WebViewClient.this.a(h5DevDebugProvider, str, str2);
                    }
                    if (H5WebViewClient.this.v != null && H5WebViewClient.this.v.contains(str)) {
                        H5LogUtil.logNebulaTech(H5LogData.seedId("h5_goto_fallback").param1().add(str, null).param4().add("goto_fallback", hasResponse ? "false" : "true"));
                    }
                }
            });
            return response;
        } catch (Throwable throwable2) {
            H5Log.e(this.TAG, throwable2);
            return null;
        }
    }

    public static synchronized Executor getSingleThreadExecutor() {
        Executor executor;
        synchronized (H5WebViewClient.class) {
            if (t == null) {
                t = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(30), new H5SingleThreadFactory("H5_InterceptRequest_SingleThreadExecutor"), new DiscardOldestPolicy());
            }
            executor = t;
        }
        return executor;
    }

    /* access modifiers changed from: private */
    public void a(H5DevDebugProvider provider, String url, String method) {
        if (provider != null && this.b != null) {
            JSONObject jsonObject = new JSONObject();
            int reqId = H5BugmeIdGenerator.nextRequestId();
            jsonObject.put((String) "reqId", (Object) Integer.valueOf(reqId));
            jsonObject.put((String) "reqUrl", (Object) url);
            jsonObject.put((String) "method", (Object) method);
            jsonObject.put((String) "fromLocalPkg", (Object) Boolean.valueOf(true));
            this.b.sendEvent(CommonEvents.H5_DEV_NETWORK_SRART, jsonObject);
            JSONObject responseEvent = (JSONObject) jsonObject.clone();
            responseEvent.put((String) "reqId", (Object) Integer.valueOf(reqId));
            responseEvent.put((String) "statusCode", (Object) Integer.valueOf(200));
            this.b.sendEvent(CommonEvents.H5_DEV_NETWORK_FINISH, responseEvent);
        }
    }

    /* access modifiers changed from: private */
    public void a(String url, String targetUrl, String method, boolean isMainDoc) {
        try {
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_NETWORK_START").param3().add("url", H5Utils.getMaxLogStr(url)).add("targetUrl", H5Utils.getMaxLogStr(targetUrl)).add("method", method).add("isMainDoc", Boolean.valueOf(isMainDoc)).add(H5PageData.KEY_UC_START, Long.valueOf(System.currentTimeMillis())).param4().addUniteParam(this.d));
        } catch (Exception e2) {
            H5Log.e(this.TAG, "reqStartLog catch exception ", e2);
        }
    }

    private void a(String url, WebResourceResponse response) {
        H5GetAllResponse h5GetAllResponse = (H5GetAllResponse) H5Utils.getProvider(H5GetAllResponse.class.getName());
        if (h5GetAllResponse == null) {
            return;
        }
        if (H5Utils.getConfigBoolean(H5Environment.getContext(), "h5_stamper")) {
            H5Log.debug(this.TAG, "h5GetAllResponse is not null");
            ByteArrayOutputStream baos = null;
            byte[] buffer = H5IOUtils.getBuf(1024);
            try {
                ByteArrayOutputStream baos2 = new PoolingByteArrayOutputStream();
                while (true) {
                    try {
                        int len = response.getData().read(buffer);
                        if (len >= 0) {
                            baos2.write(buffer, 0, len);
                        } else {
                            baos2.flush();
                            InputStream stream1 = new ByteArrayInputStream(baos2.toByteArray());
                            InputStream stream2 = new ByteArrayInputStream(baos2.toByteArray());
                            H5Log.debug(this.TAG, "call h5GetAllResponse.setData");
                            h5GetAllResponse.setData(url, stream1);
                            H5Log.debug(this.TAG, "response.setData(stream2)");
                            response.setData(stream2);
                            H5IOUtils.returnBuf(buffer);
                            H5IOUtils.closeQuietly(baos2);
                            return;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        baos = baos2;
                        try {
                            H5Log.debug(this.TAG, "copyReportMark exception : " + e);
                            H5IOUtils.returnBuf(buffer);
                            H5IOUtils.closeQuietly(baos);
                        } catch (Throwable th) {
                            th = th;
                            H5IOUtils.returnBuf(buffer);
                            H5IOUtils.closeQuietly(baos);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        baos = baos2;
                        H5IOUtils.returnBuf(buffer);
                        H5IOUtils.closeQuietly(baos);
                        throw th;
                    }
                }
            } catch (Exception e3) {
                e = e3;
                H5Log.debug(this.TAG, "copyReportMark exception : " + e);
                H5IOUtils.returnBuf(buffer);
                H5IOUtils.closeQuietly(baos);
            }
        } else {
            H5Log.d(this.TAG, "h5GetAllResponse is null");
        }
    }

    /* access modifiers changed from: private */
    public void a(Uri uri) {
        boolean isLoading = true;
        if (this.e != null) {
            this.e.reportReqStart();
        }
        if (H5Utils.containNebulaAddcors(uri.toString())) {
            this.d.putBooleanExtra("containCORSRes", true);
        }
        String path = uri.getPath();
        this.d.setRequestNum(this.d.getRequestNum() + 1);
        this.d.setFunctionHasCallback(1);
        if (this.d.getComplete() != 0) {
            isLoading = false;
        }
        if (isLoading) {
            this.d.setRequestLoadNum(this.d.getRequestLoadNum() + 1);
        }
        if (H5Utils.isCss(path)) {
            if (isLoading) {
                this.d.setCssLoadNum(this.d.getCssLoadNum() + 1);
            }
            this.d.setCssReqNum(this.d.getCssReqNum() + 1);
        } else if (H5Utils.isJavascript(path)) {
            if (isLoading) {
                this.d.setJsLoadNum(this.d.getJsLoadNum() + 1);
            }
            this.d.setJsReqNum(this.d.getJsReqNum() + 1);
        } else if (H5Utils.isImage(path)) {
            if (isLoading) {
                this.d.setImgLoadNum(this.d.getImgLoadNum() + 1);
            }
            this.d.setImgReqNum(this.d.getImgReqNum() + 1);
        } else {
            if (isLoading) {
                this.d.setOtherLoadNum(this.d.getOtherLoadNum() + 1);
            }
            this.d.setOtherReqNum(this.d.getOtherReqNum() + 1);
        }
    }

    public void onTooManyRedirects(APWebView apWebView, Message message, Message message2) {
    }

    public void onLoadResource(APWebView view, final String url) {
        H5Log.d(this.TAG, "onLoadResource " + url);
        if (this.b != null && !TextUtils.isEmpty(url)) {
            if (!url.startsWith(AjxHttpLoader.DOMAIN_HTTP) && !url.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
                return;
            }
            if (this.r) {
                if (this.s == null) {
                    this.s = new Handler();
                }
                this.s.post(new Runnable() {
                    public void run() {
                        if (H5WebViewClient.this.b != null) {
                            JSONObject param = new JSONObject();
                            param.put((String) "url", (Object) url);
                            Bundle startBundle = H5WebViewClient.this.b.getParams();
                            if (startBundle != null) {
                                param.put((String) "appId", (Object) H5Utils.getString(startBundle, (String) "appId"));
                                String preSSOLogin = H5Utils.getString(startBundle, (String) H5Param.LONG_PRESSO_LOGIN);
                                String preSSOLoginBindingPage = H5Utils.getString(startBundle, (String) H5Param.LONG_PRESSO_LOGIN_BINDINGPAGE);
                                String preSSOLoginUrl = H5Utils.getString(startBundle, (String) H5Param.LONG_PRESSO_LOGIN_URL);
                                param.put((String) H5Param.PRESSO_LOGIN, (Object) preSSOLogin);
                                param.put((String) H5Param.PRESSO_LOGIN_BINDINGPAGE, (Object) preSSOLoginBindingPage);
                                param.put((String) H5Param.PRESSO_LOGIN_URL, (Object) preSSOLoginUrl);
                            }
                            H5WebViewClient.this.b.sendEvent(CommonEvents.H5_PAGE_LOAD_RESOURCE, param);
                        }
                    }
                });
                return;
            }
            JSONObject param = new JSONObject();
            param.put((String) "url", (Object) url);
            Bundle startBundle = this.b.getParams();
            if (startBundle != null) {
                param.put((String) "appId", (Object) H5Utils.getString(startBundle, (String) "appId"));
                String preSSOLogin = H5Utils.getString(startBundle, (String) H5Param.LONG_PRESSO_LOGIN);
                String preSSOLoginBindingPage = H5Utils.getString(startBundle, (String) H5Param.LONG_PRESSO_LOGIN_BINDINGPAGE);
                String preSSOLoginUrl = H5Utils.getString(startBundle, (String) H5Param.LONG_PRESSO_LOGIN_URL);
                param.put((String) H5Param.PRESSO_LOGIN, (Object) preSSOLogin);
                param.put((String) H5Param.PRESSO_LOGIN_BINDINGPAGE, (Object) preSSOLoginBindingPage);
                param.put((String) H5Param.PRESSO_LOGIN_URL, (Object) preSSOLoginUrl);
            }
            this.b.sendEvent(CommonEvents.H5_PAGE_LOAD_RESOURCE, param);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x022e, code lost:
        if (android.text.TextUtils.isEmpty(r15) != false) goto L_0x0230;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onPageStarted(com.alipay.mobile.nebula.webview.APWebView r22, java.lang.String r23, android.graphics.Bitmap r24) {
        /*
            r21 = this;
            r4 = 1
            r0 = r21
            r0.x = r4
            r0 = r21
            java.lang.String r4 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "onPageStarted "
            r5.<init>(r6)
            r0 = r23
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = " originalUrl "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = r22.getOriginalUrl()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)
            java.lang.String r18 = com.alipay.mobile.nebula.util.H5Utils.getStripLandingURL(r23)
            r0 = r23
            r1 = r18
            boolean r4 = android.text.TextUtils.equals(r0, r1)
            if (r4 != 0) goto L_0x007b
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            if (r4 == 0) goto L_0x007b
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            android.os.Bundle r4 = r4.getParams()
            if (r4 == 0) goto L_0x007b
            r5 = 0
            r6 = 0
            r7 = 0
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            android.os.Bundle r4 = r4.getParams()
            java.lang.String r8 = "appId"
            java.lang.String r8 = com.alipay.mobile.nebula.util.H5Utils.getString(r4, r8)
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            android.os.Bundle r4 = r4.getParams()
            java.lang.String r9 = "publicId"
            java.lang.String r9 = com.alipay.mobile.nebula.util.H5Utils.getString(r4, r9)
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            android.os.Bundle r4 = r4.getParams()
            java.lang.String r10 = "bizScenario"
            java.lang.String r10 = com.alipay.mobile.nebula.util.H5Utils.getString(r4, r10)
            r4 = r23
            com.alipay.mobile.nebula.util.H5Utils.landingMonitor(r4, r5, r6, r7, r8, r9, r10)
        L_0x007b:
            r0 = r21
            com.alipay.mobile.h5container.api.H5WebDriverHelper r4 = r0.c
            r0 = r22
            r1 = r23
            r2 = r24
            r4.onPageStarted(r0, r1, r2)
            com.alipay.mobile.nebula.dev.H5BugMeManager r4 = com.alipay.mobile.nebulacore.Nebula.getH5BugMeManager()
            r0 = r23
            r1 = r22
            r4.setWebViewDebugging(r0, r1)
            android.content.Context r4 = com.alipay.mobile.nebulacore.env.H5Environment.getContext()
            if (r4 == 0) goto L_0x00c9
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "H5"
            r4.<init>(r5)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            long r6 = java.lang.System.currentTimeMillis()
            android.content.Context r8 = com.alipay.mobile.nebulacore.env.H5Environment.getContext()
            int r8 = com.alipay.mobile.nebula.util.H5Utils.getUid(r8)
            long r8 = (long) r8
            long r6 = r6 + r8
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            java.lang.String r5 = com.alipay.mobile.nebula.util.H5SecurityUtil.getMD5(r5)
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.alipay.mobile.h5container.api.H5PageLoader.h5Token = r4
        L_0x00c9:
            android.view.View r4 = r22.getView()
            if (r4 == 0) goto L_0x00d7
            android.view.View r4 = r22.getView()
            r5 = 0
            r4.setVisibility(r5)
        L_0x00d7:
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            if (r4 != 0) goto L_0x00de
        L_0x00dd:
            return
        L_0x00de:
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            r5 = 0
            r4.setContainsEmbedView(r5)
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            r5 = 0
            r4.setContainsEmbedSurfaceView(r5)
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            int r5 = com.alipay.mobile.nebulacore.Nebula.getPageId()
            r4.setPageId(r5)
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            com.alipay.mobile.h5container.api.H5Context r4 = r4.getContext()
            if (r4 == 0) goto L_0x0155
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            com.alipay.mobile.h5container.api.H5Context r4 = r4.getContext()
            android.content.Context r4 = r4.getContext()
            boolean r4 = r4 instanceof com.alipay.mobile.nebulacore.ui.H5Activity
            if (r4 == 0) goto L_0x0155
            java.lang.String r4 = "h5_bug_me_show_icon"
            r5 = 0
            boolean r4 = com.alipay.mobile.nebula.dev.H5DevConfig.getBooleanConfig(r4, r5)
            if (r4 == 0) goto L_0x0155
            com.alipay.mobile.nebula.dev.H5BugMeManager r4 = com.alipay.mobile.nebulacore.Nebula.getH5BugMeManager()
            r0 = r23
            boolean r4 = r4.hasAccessToDebug(r0)
            if (r4 == 0) goto L_0x0155
            com.alipay.mobile.nebulacore.dev.provider.H5DebugConsolePool r4 = com.alipay.mobile.nebulacore.dev.provider.H5DebugConsolePool.getInstance()
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r5 = r0.b
            com.alipay.mobile.nebulacore.dev.bugme.H5BugmeConsole r4 = r4.addOrGetConsole(r5)
            r4.startup()
            java.lang.String r4 = "h5_bug_me_debug_switch_keep"
            r5 = 0
            boolean r4 = com.alipay.mobile.nebula.dev.H5DevConfig.getBooleanConfig(r4, r5)
            if (r4 == 0) goto L_0x0155
            java.lang.String r4 = "h5_bug_me_super_user"
            r5 = 0
            boolean r4 = com.alipay.mobile.nebula.dev.H5DevConfig.getBooleanConfig(r4, r5)
            if (r4 == 0) goto L_0x0155
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            com.alipay.mobile.nebulacore.web.H5WebView r4 = r4.getWebView()
            r5 = 1
            r4.clearCache(r5)
        L_0x0155:
            r4 = 0
            r0 = r21
            r0.p = r4
            r4 = 0
            r0 = r21
            r0.q = r4
            r4 = 0
            r0 = r21
            r0.k = r4
            r0 = r21
            com.alipay.mobile.h5container.api.H5AvailablePageData r4 = r0.e
            if (r4 == 0) goto L_0x017c
            r0 = r21
            com.alipay.mobile.h5container.api.H5AvailablePageData r4 = r0.e
            r4.clear()
            r0 = r21
            com.alipay.mobile.h5container.api.H5AvailablePageData r4 = r0.e
            long r6 = java.lang.System.currentTimeMillis()
            r4.setPageStartTime(r6)
        L_0x017c:
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            java.lang.String r4 = r4.getNavUrl()
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x01a8
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            java.lang.String r4 = r4.getNavUrl()
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r5 = r0.d
            java.lang.String r5 = r5.getPageUrl()
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x01a8
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            r5 = 0
            r4.setNavUrl(r5)
        L_0x01a8:
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            long r4 = r4.getStart()
            r6 = 0
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 <= 0) goto L_0x01fe
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "["
            r5.<init>(r6)
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r6 = r0.d
            java.lang.String r6 = r6.getPageUrl()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = "{"
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r6 = r0.d
            long r6 = r6.getPageNetLoad()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = "}->("
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r6 = r0.d
            int r6 = r6.getStatusCode()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = ")]"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.setReferer(r5)
        L_0x01fe:
            r19 = 1
            java.lang.String r15 = ""
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            com.alipay.mobile.h5container.api.H5Context r4 = r4.getContext()
            if (r4 == 0) goto L_0x0232
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            com.alipay.mobile.h5container.api.H5Context r4 = r4.getContext()
            android.content.Context r4 = r4.getContext()
            boolean r4 = r4 instanceof com.alipay.mobile.nebulacore.ui.H5Activity
            if (r4 == 0) goto L_0x0230
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            android.os.Bundle r4 = r4.getParams()
            java.lang.String r5 = "fromType"
            java.lang.String r15 = com.alipay.mobile.nebula.util.H5Utils.getString(r4, r5)
            boolean r4 = android.text.TextUtils.isEmpty(r15)
            if (r4 == 0) goto L_0x0232
        L_0x0230:
            r19 = 0
        L_0x0232:
            r0 = r21
            boolean r4 = r0.r
            if (r4 == 0) goto L_0x03c1
            if (r19 == 0) goto L_0x03c1
            r0 = r21
            android.os.Handler r4 = r0.s
            if (r4 != 0) goto L_0x0249
            android.os.Handler r4 = new android.os.Handler
            r4.<init>()
            r0 = r21
            r0.s = r4
        L_0x0249:
            r0 = r21
            android.os.Handler r4 = r0.s
            com.alipay.mobile.nebulacore.web.H5WebViewClient$10 r5 = new com.alipay.mobile.nebulacore.web.H5WebViewClient$10
            r0 = r21
            r1 = r23
            r5.<init>(r1)
            r4.post(r5)
        L_0x0259:
            com.alibaba.fastjson.JSONObject r17 = new com.alibaba.fastjson.JSONObject
            r17.<init>()
            java.lang.String r4 = "url"
            r0 = r17
            r1 = r23
            r0.put(r4, r1)
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            java.lang.String r5 = "h5PageStartedSync"
            r0 = r17
            r4.sendEvent(r5, r0)
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            r4.clear()
            r20 = 0
            r0 = r22
            boolean r4 = r0 instanceof com.alipay.mobile.nebulacore.web.H5WebView
            if (r4 == 0) goto L_0x0289
            r4 = r22
            com.alipay.mobile.nebulacore.web.H5WebView r4 = (com.alipay.mobile.nebulacore.web.H5WebView) r4
            int r20 = r4.getWebViewIndex()
        L_0x0289:
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            r0 = r20
            r4.setWebViewIndex(r0)
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            r0 = r23
            r4.onPageStarted(r0)
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            r5 = 0
            r4.setFunctionHasCallback(r5)
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            com.alipay.mobile.nebula.log.H5MainLinkMonitor.triggerPageStartedLink(r4)
            com.alipay.mobile.nebula.provider.H5LogProvider r4 = com.alipay.mobile.nebulacore.Nebula.getH5LogHandler()
            if (r4 == 0) goto L_0x02c5
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            long r6 = com.alipay.mobile.h5container.api.H5Flag.lastTouchTime
            java.lang.Long r5 = com.alipay.mobile.nebula.log.H5Logger.getTrackLastClickTime()
            long r8 = r5.longValue()
            long r6 = java.lang.Math.max(r6, r8)
            r4.setSrcClick(r6)
        L_0x02c5:
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            com.alipay.mobile.h5container.api.H5Context r4 = r4.getContext()
            if (r4 == 0) goto L_0x02f9
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            com.alipay.mobile.h5container.api.H5Context r4 = r4.getContext()
            android.content.Context r4 = r4.getContext()
            boolean r4 = r4 instanceof com.alipay.mobile.nebulacore.ui.H5Activity
            if (r4 == 0) goto L_0x0408
            boolean r4 = android.text.TextUtils.isEmpty(r15)
            if (r4 != 0) goto L_0x03e9
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            r4.setFromType(r15)
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            android.os.Bundle r4 = r4.getParams()
            java.lang.String r5 = "fromType"
            r4.remove(r5)
        L_0x02f9:
            com.alipay.mobile.nebula.provider.H5LogProvider r4 = com.alipay.mobile.nebulacore.Nebula.getH5LogHandler()
            if (r4 == 0) goto L_0x030a
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            java.lang.String r5 = com.alipay.mobile.nebula.log.H5Logger.getToken()
            r4.setToken(r5)
        L_0x030a:
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            java.lang.String r5 = com.alipay.mobile.h5container.api.H5PageLoader.h5Token
            r4.setH5Token(r5)
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            java.lang.String r5 = com.alipay.mobile.h5container.api.H5PageLoader.h5SessionToken
            r4.setH5SessionToken(r5)
            java.lang.String r4 = "pageUrl"
            r0 = r23
            com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi.addCrashHeadInfo(r4, r0)
            java.lang.String r4 = "h5PageStartTime"
            long r6 = java.lang.System.currentTimeMillis()
            java.lang.String r5 = java.lang.String.valueOf(r6)
            com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi.addCrashHeadInfo(r4, r5)
            com.alipay.mobile.nebula.webview.APWebBackForwardList r11 = r22.copyBackForwardList()
            if (r11 == 0) goto L_0x0343
            java.lang.String r4 = "h5HistorySize"
            int r5 = r11.getSize()
            java.lang.String r5 = java.lang.String.valueOf(r5)
            com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi.addCrashHeadInfo(r4, r5)
        L_0x0343:
            com.alipay.mobile.nebulacore.web.H5WebViewClient$VisitHistoryQueue<java.lang.String> r4 = u
            int r4 = r4.size()
            if (r4 <= 0) goto L_0x036e
            r0 = r21
            java.lang.String r4 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "sVisitHistoryQueue: "
            r5.<init>(r6)
            com.alipay.mobile.nebulacore.web.H5WebViewClient$VisitHistoryQueue<java.lang.String> r6 = u
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)
            java.lang.String r4 = "h5History"
            com.alipay.mobile.nebulacore.web.H5WebViewClient$VisitHistoryQueue<java.lang.String> r5 = u
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi.addCrashHeadInfo(r4, r5)
        L_0x036e:
            com.alipay.mobile.nebulacore.web.H5WebViewClient$VisitHistoryQueue<java.lang.String> r4 = u
            r0 = r23
            r4.add(r0)
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            if (r4 == 0) goto L_0x03a0
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            android.os.Bundle r4 = r4.getParams()
            r0 = r23
            int r16 = com.alipay.mobile.nebula.util.H5ThirdDisclaimerUtils.needShowDisclaimer(r4, r0)
            com.alibaba.fastjson.JSONObject r14 = new com.alibaba.fastjson.JSONObject
            r14.<init>()
            java.lang.String r4 = "mode"
            java.lang.Integer r5 = java.lang.Integer.valueOf(r16)
            r14.put(r4, r5)
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            java.lang.String r5 = "showDisClaimer"
            r4.sendEvent(r5, r14)
        L_0x03a0:
            java.lang.String r4 = "H5WebViewClient.onPageStarted"
            r0 = r21
            java.lang.String r5 = r0.h
            r6 = 2
            java.lang.String[] r6 = new java.lang.String[r6]
            r7 = 0
            java.lang.String r8 = "url"
            r6[r7] = r8
            r7 = 1
            r6[r7] = r23
            com.alipay.mobile.nebula.data.H5Trace.event(r4, r5, r6)
            r0 = r21
            com.alipay.mobile.nebula.util.H5WarningTipHelper r4 = r0.a
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r5 = r0.b
            r4.initDataExceededConfig(r5)
            goto L_0x00dd
        L_0x03c1:
            com.alibaba.fastjson.JSONObject r17 = new com.alibaba.fastjson.JSONObject
            r17.<init>()
            java.lang.String r4 = "url"
            r0 = r17
            r1 = r23
            r0.put(r4, r1)
            r0 = r21
            com.alipay.mobile.nebulacore.core.H5PageImpl r4 = r0.b
            java.lang.String r5 = "h5PageStarted"
            r0 = r17
            r4.sendEvent(r5, r0)
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            if (r4 == 0) goto L_0x0259
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            r4.resetPageToken()
            goto L_0x0259
        L_0x03e9:
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            java.lang.String r5 = "hrefChange"
            r4.setFromType(r5)
            long r12 = java.lang.System.currentTimeMillis()
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            r5 = -1
            r4.setCreate(r12, r5)
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            r5 = 5
            r4.setCreate(r12, r5)
            goto L_0x02f9
        L_0x0408:
            r0 = r21
            com.alipay.mobile.h5container.api.H5PageData r4 = r0.d
            java.lang.String r5 = "subView"
            r4.setFromType(r5)
            goto L_0x02f9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.web.H5WebViewClient.onPageStarted(com.alipay.mobile.nebula.webview.APWebView, java.lang.String, android.graphics.Bitmap):void");
    }

    public void doUpdateVisitedHistory(APWebView view, String url, boolean isReload) {
        if (!TextUtils.equals(url, "about:blank")) {
            this.c.doUpdateVisitedHistory(view, url, isReload);
        }
        H5Log.d(this.TAG, "doUpdateVisitedHistory " + url + " isReload " + isReload);
        this.k = true;
        this.l = url;
    }

    private String a(APWebView view, String url) {
        String title = view.getTitle();
        if (url == null || title == null) {
            return title;
        }
        if (url.contains(title) || title.contains(".html") || title.contains(".htm")) {
            return null;
        }
        H5Log.d(this.TAG, "!titlePartOfUrl");
        return title;
    }

    public void onPageFinished(APWebView view, String url, long pageSize) {
        H5Log.d(this.TAG, "onPageFinished " + url + " pageSize " + pageSize);
        if (!TextUtils.equals(url, "about:blank")) {
            this.c.onPageFinished(view, url);
        }
        if (this.b != null && view != null) {
            if (url != null) {
                if (url.equals(this.d.getPageUrl()) && this.e != null) {
                    this.e.reportDidFinishedLoadDate(System.currentTimeMillis());
                }
            }
            if (this.b != null && this.d.getAppear() == 0) {
                this.d.setAppear(System.currentTimeMillis() - this.d.getStart());
                H5Log.debug(this.TAG, "page appear " + this.d.getAppear());
            }
            if (this.b != null && this.d.getAppearFromNative() == 0) {
                if (this.n) {
                    this.d.setAppearFromNative(System.currentTimeMillis() - H5PageData.walletServiceStart);
                    this.n = false;
                } else {
                    this.d.setAppearFromNative(System.currentTimeMillis() - this.d.getStart());
                }
                H5Log.debug(this.TAG, "page appear native " + this.d.getAppearFromNative());
            }
            final JSONObject param = new JSONObject();
            param.put((String) "url", (Object) url);
            param.put((String) "title", (Object) a(view, url));
            APWebBackForwardList list = view.copyBackForwardList();
            String originalUrl = view.getOriginalUrl();
            int pageIndex = 0;
            int historySize = 0;
            if (list != null) {
                historySize = list.getSize();
                pageIndex = list.getCurrentIndex();
                boolean urlAsOriginal = TextUtils.equals(originalUrl, url);
                if (pageIndex >= 0 && this.i != pageIndex) {
                    this.k = true;
                }
                if (!this.k && this.i == pageIndex && list.getCurrentItem().getUrl().equals(url)) {
                    this.k = true;
                }
                H5Log.debug(this.TAG, "pageIndex " + pageIndex + " lastPageIndex " + this.i + " urlAsOriginal " + urlAsOriginal + " pageUpdated " + this.k);
                if (this.k) {
                    this.i = pageIndex;
                }
                this.d.setPageIndex(pageIndex);
                param.put((String) "pageIndex", (Object) Integer.valueOf(pageIndex));
            }
            param.put((String) "historySize", (Object) Integer.valueOf(historySize));
            H5Log.debug(this.TAG, "historySize " + historySize);
            param.put((String) H5Param.PAGE_UPDATED, (Object) Boolean.valueOf(this.k));
            if (this.g != null) {
                this.d.setIsLocal(this.g.getLocal());
            }
            if (this.d.getComplete() == 0) {
                this.d.setComplete(System.currentTimeMillis() - this.d.getStart());
            }
            if (Nebula.DEBUG) {
                TestDataUtils.storeJSParams("pageLoad|pageComplete", Long.valueOf(this.d.getComplete()));
                TestDataUtils.storeJSParams("pageLoad|url", url);
                String versionName = "";
                H5EnvProvider provider = (H5EnvProvider) Nebula.getProviderManager().getProvider(H5EnvProvider.class.getName());
                if (provider != null) {
                    versionName = provider.getProductVersion();
                }
                JSONObject testData = new JSONObject();
                testData.put((String) "alipayVersion", (Object) versionName);
                testData.put((String) "brand", (Object) Build.BRAND);
                testData.put((String) "fingerprint", (Object) Build.FINGERPRINT);
                testData.put((String) "manufacturer", (Object) Build.MANUFACTURER);
                testData.put((String) Constants.KEY_MODEL, (Object) Build.MODEL);
                testData.put((String) "network", (Object) H5NetworkUtil.getInstance().getNetworkString());
                testData.put((String) "sdkInt", (Object) Integer.valueOf(VERSION.SDK_INT));
                TestDataUtils.storeJSParams("phone", testData);
                if (!(view instanceof AndroidWebView)) {
                    TestDataUtils.injectJSParams(view);
                }
            }
            if (this.d.getPageSize() == 0) {
                this.d.setPageSize(pageSize);
            }
            this.d.setHtmlLoadSize(pageSize);
            H5Log.debug(this.TAG, "onPageFinished " + url + " originalUrl " + originalUrl + " pageSize " + pageSize + " pageIndex " + pageIndex);
            H5Log.debug(this.TAG, "start=" + this.d.getStart() + "^appear=" + this.d.getAppear() + "^complete=" + this.d.getComplete() + "^pageSize=" + this.d.getPageSize() + "^create=" + this.d.getCreate() + "^appear=" + this.d.getAppear() + "^firstByte=" + this.d.getFirstByte() + "^jsSize=" + this.d.getJsSize() + "^cssSize=" + this.d.getCssSize() + "^imgSize=" + this.d.getImgSize() + "^htmlSize=" + this.d.getHtmlSize() + "^otherSize=" + this.d.getOtherSize() + "^requestNum=" + this.d.getRequestNum() + "^num404=" + this.d.getNum404() + "^num400=" + this.d.getNum400() + "^num500=" + this.d.getNum500() + "^num1000=" + this.d.getNum1000() + "^sizeLimit60=" + this.d.getSizeLimit60());
            H5Trace.event("H5WebViewClient.onPageFinished", this.h, "url", url);
            if (this.b instanceof H5PageImpl) {
                H5Fragment h5Fragment = this.b.getH5Fragment();
                if (h5Fragment != null) {
                    h5Fragment.onPageFinish();
                }
            }
            MessageQueue myQueue = Looper.myQueue();
            AnonymousClass11 r0 = new IdleHandler() {
                public boolean queueIdle() {
                    H5Log.d(H5WebViewClient.this.TAG, "h5netsupervisor exec onPageFinished");
                    H5NetworkSuScheduler.getInstance().exec();
                    return false;
                }
            };
            myQueue.addIdleHandler(r0);
            if (this.r) {
                if (this.s == null) {
                    this.s = new Handler();
                }
                Handler handler = this.s;
                AnonymousClass12 r02 = new Runnable() {
                    public void run() {
                        if (H5WebViewClient.this.b != null) {
                            H5WebViewClient.this.b.sendEvent(CommonEvents.H5_PAGE_FINISHED, param);
                        }
                    }
                };
                handler.post(r02);
            } else {
                this.b.sendEvent(CommonEvents.H5_PAGE_FINISHED, param);
            }
            this.b.sendEvent(CommonEvents.H5_PAGE_FINISHED_SYNC, param);
            H5MainLinkMonitor.triggerPageFinishLink(this.b);
            if (H5Utils.isDebuggable(H5Utils.getContext())) {
                a(url, pageSize);
            }
        }
    }

    private void a(String url, long pageSize) {
        if (this.b != null) {
            JSONObject param = new JSONObject();
            param.put((String) "url", (Object) url);
            param.put((String) "size", (Object) Long.valueOf(pageSize));
            this.b.sendEvent("h5Performance.onPageFinished", param);
        }
    }

    private void b(String url, long pageSize) {
        if (this.b != null) {
            JSONObject param = new JSONObject();
            param.put((String) "url", (Object) url);
            param.put((String) "size", (Object) Long.valueOf(pageSize));
            this.b.sendEvent("h5Performance.onResourceFinishLoad", param);
        }
    }

    public void onRelease() {
        if (this.x && !this.y) {
            H5Log.d(this.TAG, "hasOnPageStarted but no hasShouldInterceptRequest! Dump Thread Infos!");
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public void run() {
                    List<String> traces = H5PerformanceUtils.getAllThreadsTraces();
                    if (traces != null) {
                        H5Log.d(H5WebViewClient.this.TAG, "All Threads Traces: ###" + traces.size());
                        for (String trace : traces) {
                            H5Log.d(H5WebViewClient.this.TAG, trace);
                        }
                    }
                }
            });
        }
        this.b = null;
        H5Log.d(this.TAG, "h5netsupervisor exec onRelease");
        H5NetworkSuScheduler.getInstance().exec();
    }
}
