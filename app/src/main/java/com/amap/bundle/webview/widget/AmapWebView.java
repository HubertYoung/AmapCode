package com.amap.bundle.webview.widget;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.webview.MultiTabWebView;
import com.autonavi.widget.webview.android.SafeWebView.WebViewClientEx;
import com.autonavi.widget.webview.inner.SafeWebView.b;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import com.uc.webview.export.DownloadListener;
import com.uc.webview.export.JsResult;
import com.uc.webview.export.SslErrorHandler;
import com.uc.webview.export.WebView;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

public final class AmapWebView extends MultiTabWebView {
    public static final String ERROR_URL_404 = "file:///android_asset/not_found_error.html";
    public static final String ERROR_URL_OTHER = "file:///android_asset/connect_error.html";
    private static final String MEMBER_WHITE_LIST_WEBVIEW = "https://wap.amap.com/member/index.html#!/";
    private static final String MOBIKE_URL_WHITE_LIST = "https://h5.mobike.com";
    /* access modifiers changed from: private */
    public static String NOT_SUPPORT_APP_SCHEME = null;
    private static final String NOT_SUPPORT_APP_SCHEME_KEY = "not_support_app_scheme.html";
    private static String sDefaultPackageUri = "file:///data/data/com.autonavi.minimap/";
    private static String sPackageUri;
    /* access modifiers changed from: private */
    public boolean isRequestFocusOnPageFinished;
    /* access modifiers changed from: private */
    public boolean isWebViewAlive;
    private boolean mIsBaichuanMode;
    /* access modifiers changed from: private */
    public defpackage.aiz.a mSslHandleListener;

    public static class a extends Handler {
        private WeakReference<Object> a;

        /* renamed from: com.amap.bundle.webview.widget.AmapWebView$a$a reason: collision with other inner class name */
        class C0008a {
            public Method a;
            public String[] b;

            C0008a() {
            }
        }

        public a(Object obj) {
            this.a = new WeakReference<>(obj);
        }

        @JavascriptInterface
        @com.uc.webview.export.JavascriptInterface
        public final void invokeMethod(String str, String[] strArr) {
            C0008a aVar = new C0008a();
            aVar.b = strArr;
            Class[] clsArr = {String[].class};
            try {
                Object obj = this.a.get();
                if (obj != null) {
                    aVar.a = obj.getClass().getMethod(str, clsArr);
                    Message obtainMessage = obtainMessage(0);
                    obtainMessage.obj = aVar;
                    sendMessage(obtainMessage);
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }

        public final void handleMessage(Message message) {
            C0008a aVar = (C0008a) message.obj;
            if (!(message.what != 0 || aVar == null || aVar.a == null)) {
                try {
                    Object obj = this.a.get();
                    if (obj != null) {
                        aVar.a.invoke(obj, new Object[]{aVar.b});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public AmapWebView(Context context) {
        this(context, null, 0);
    }

    public AmapWebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AmapWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isWebViewAlive = true;
        this.mIsBaichuanMode = false;
        this.isRequestFocusOnPageFinished = true;
        init(false);
    }

    public AmapWebView(Context context, boolean z) {
        super(context, z);
        this.isWebViewAlive = true;
        this.mIsBaichuanMode = false;
        this.isRequestFocusOnPageFinished = true;
        init(z);
    }

    public final void setSslHandleListener(defpackage.aiz.a aVar) {
        this.mSslHandleListener = aVar;
    }

    private void init(boolean z) {
        if (z) {
            initWithAndroid();
            return;
        }
        if (TextUtils.isEmpty(NOT_SUPPORT_APP_SCHEME)) {
            bgx bgx = (bgx) defpackage.esb.a.a.a(bgx.class);
            if (bgx != null) {
                bgx.getUrl(NOT_SUPPORT_APP_SCHEME_KEY, new defpackage.bgx.a() {
                    public final void a(String str) {
                        AmapWebView.NOT_SUPPORT_APP_SCHEME = str;
                    }
                });
            }
        }
        erw erw = new erw();
        erw.b(true);
        erw.a();
        erw.a(false);
        erw.a(getAmapUserAgent());
        setWebSettings(erw);
        setSupportMultiTab(false);
        addWebViewClient(new b() {
            public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (!boe.a(webView, str) && !boe.a(str)) {
                    return super.shouldOverrideUrlLoading(webView, str);
                }
                return true;
            }

            public final void onPageFinished(final WebView webView, String str) {
                super.onPageFinished(webView, str);
                ahl.b(new defpackage.ahl.a() {
                    public final void onError(Throwable th) {
                    }

                    public final Object doBackground() throws Exception {
                        return ahd.a(AmapWebView.this.getContext(), (String) "js/activeEvent.js", Charset.forName("utf-8"));
                    }

                    public final void onFinished(Object obj) {
                        if ((obj instanceof String) && webView != null && !TextUtils.isEmpty((String) obj) && AmapWebView.this.isWebViewAlive) {
                            webView.loadUrl("javascript:".concat(String.valueOf(obj)));
                            if (AmapWebView.this.isRequestFocusOnPageFinished) {
                                webView.requestFocus();
                            }
                        }
                    }
                });
            }

            public final void onReceivedError(WebView webView, int i, String str, String str2) {
                webView.clearCache(true);
                AmapWebView.this.saveWebError(i, str, str2);
                if (i == 404) {
                    AmapWebView.this.loadUrl("file:///android_asset/not_found_error.html");
                } else if (!boe.a(str2) || TextUtils.isEmpty(AmapWebView.NOT_SUPPORT_APP_SCHEME)) {
                    AmapWebView.this.loadUrl("file:///android_asset/connect_error.html");
                } else {
                    AmapWebView.this.loadUrl(AmapWebView.NOT_SUPPORT_APP_SCHEME);
                }
            }

            public final void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                aiz.a(webView, sslErrorHandler, sslError, AmapWebView.this.mSslHandleListener);
            }
        });
        addWebChromeClient(new com.amap.bundle.webview.widget.WebViewEx.a() {
            public final boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
                final bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext == null) {
                    return true;
                }
                com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(pageContext.getActivity());
                aVar.a((CharSequence) str2);
                aVar.a(true);
                aVar.a(17039370, (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        pageContext.dismissViewLayer(alertView);
                    }
                });
                aVar.a(true);
                AlertView a2 = aVar.a();
                pageContext.showViewLayer(a2);
                a2.startAnimation();
                jsResult.confirm();
                return true;
            }

            public final boolean onJsConfirm(WebView webView, String str, String str2, final JsResult jsResult) {
                final bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext == null) {
                    return true;
                }
                com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(pageContext.getActivity());
                aVar.a((CharSequence) str2);
                aVar.a(false);
                aVar.a(17039370, (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        jsResult.confirm();
                        pageContext.dismissViewLayer(alertView);
                    }
                });
                aVar.b(17039360, (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        jsResult.cancel();
                        pageContext.dismissViewLayer(alertView);
                    }
                });
                aVar.a(true);
                AlertView a2 = aVar.a();
                pageContext.showViewLayer(a2);
                a2.startAnimation();
                return true;
            }
        });
        addDownloadListener(new DownloadListener() {
            public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                Activity topActivity = AMapAppGlobal.getTopActivity();
                if (topActivity != null) {
                    topActivity.startActivity(intent);
                }
            }
        });
    }

    private void initWithAndroid() {
        if (TextUtils.isEmpty(NOT_SUPPORT_APP_SCHEME)) {
            bgx bgx = (bgx) defpackage.esb.a.a.a(bgx.class);
            if (bgx != null) {
                bgx.getUrl(NOT_SUPPORT_APP_SCHEME_KEY, new defpackage.bgx.a() {
                    public final void a(String str) {
                        AmapWebView.NOT_SUPPORT_APP_SCHEME = str;
                    }
                });
            }
        }
        erw erw = new erw();
        erw.b(true);
        erw.a();
        erw.a(false);
        erw.a(getAmapUserAgent());
        setWebSettings(erw);
        setSupportMultiTab(false);
        addAndroidWebViewClient(new WebViewClientEx() {
            public void onPageFinished(final android.webkit.WebView webView, String str) {
                super.onPageFinished(webView, str);
                ahl.b(new defpackage.ahl.a() {
                    public final void onError(Throwable th) {
                    }

                    public final Object doBackground() throws Exception {
                        return ahd.a(AmapWebView.this.getContext(), (String) "js/activeEvent.js", Charset.forName("utf-8"));
                    }

                    public final void onFinished(Object obj) {
                        if ((obj instanceof String) && webView != null && !TextUtils.isEmpty((String) obj) && AmapWebView.this.isWebViewAlive) {
                            webView.loadUrl("javascript:".concat(String.valueOf(obj)));
                            if (AmapWebView.this.isRequestFocusOnPageFinished) {
                                webView.requestFocus();
                            }
                        }
                    }
                });
            }

            public void onReceivedError(android.webkit.WebView webView, int i, String str, String str2) {
                webView.clearCache(true);
                AmapWebView.this.saveWebError(i, str, str2);
                if (i == 404) {
                    AmapWebView.this.loadUrl("file:///android_asset/not_found_error.html");
                } else if (!boe.a(str2) || TextUtils.isEmpty(AmapWebView.NOT_SUPPORT_APP_SCHEME)) {
                    AmapWebView.this.loadUrl("file:///android_asset/connect_error.html");
                } else {
                    AmapWebView.this.loadUrl(AmapWebView.NOT_SUPPORT_APP_SCHEME);
                }
            }

            public void onReceivedSslError(android.webkit.WebView webView, android.webkit.SslErrorHandler sslErrorHandler, SslError sslError) {
                aiz.a(webView, sslErrorHandler, sslError, AmapWebView.this.mSslHandleListener);
            }

            public boolean shouldOverrideUrlLoading(android.webkit.WebView webView, String str) {
                if (!defpackage.boe.a.a.a.a(webView, str) && !boe.a(str)) {
                    return super.shouldOverrideUrlLoading(webView, str);
                }
                return true;
            }
        });
        addAndroidWebChromeClient(new WebChromeClient() {
            public boolean onJsAlert(android.webkit.WebView webView, String str, String str2, android.webkit.JsResult jsResult) {
                final bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext == null) {
                    return true;
                }
                com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(pageContext.getActivity());
                aVar.a((CharSequence) str2);
                aVar.a(true);
                aVar.a(17039370, (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        pageContext.dismissViewLayer(alertView);
                    }
                });
                aVar.a(true);
                AlertView a = aVar.a();
                pageContext.showViewLayer(a);
                a.startAnimation();
                jsResult.confirm();
                return true;
            }

            public boolean onJsConfirm(android.webkit.WebView webView, String str, String str2, final android.webkit.JsResult jsResult) {
                final bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext == null) {
                    return true;
                }
                com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(pageContext.getActivity());
                aVar.a((CharSequence) str2);
                aVar.a(false);
                aVar.a(17039370, (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        jsResult.confirm();
                        pageContext.dismissViewLayer(alertView);
                    }
                });
                aVar.b(17039360, (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        jsResult.cancel();
                        pageContext.dismissViewLayer(alertView);
                    }
                });
                aVar.a(true);
                AlertView a = aVar.a();
                pageContext.showViewLayer(a);
                a.startAnimation();
                return true;
            }
        });
        addAndroidDownloadListener(new android.webkit.DownloadListener() {
            public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                Activity topActivity = AMapAppGlobal.getTopActivity();
                if (topActivity != null) {
                    topActivity.startActivity(intent);
                }
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x003a, code lost:
        if (r1.toString().equals(r0) == false) goto L_0x003c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean canGoBack() {
        /*
            r4 = this;
            java.lang.String r0 = r4.getUrl()
            java.lang.String r1 = "file:///android_asset/not_found_error.html"
            boolean r1 = r1.equals(r0)
            if (r1 != 0) goto L_0x0060
            java.lang.String r1 = "file:///android_asset/connect_error.html"
            boolean r1 = r1.equals(r0)
            if (r1 != 0) goto L_0x0060
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x003c
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            com.amap.bundle.blutils.app.ConfigerHelper r2 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()
            java.lang.String r3 = "user_center_url"
            java.lang.String r2 = r2.getKeyValue(r3)
            r1.append(r2)
            java.lang.String r2 = "#!/"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            boolean r1 = r1.equals(r0)
            if (r1 != 0) goto L_0x0060
        L_0x003c:
            java.lang.String r1 = NOT_SUPPORT_APP_SCHEME
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x004c
            java.lang.String r1 = NOT_SUPPORT_APP_SCHEME
            boolean r1 = r1.equals(r0)
            if (r1 != 0) goto L_0x0060
        L_0x004c:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x005b
            java.lang.String r1 = "https://h5.mobike.com"
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L_0x005b
            goto L_0x0060
        L_0x005b:
            boolean r0 = super.canGoBack()
            return r0
        L_0x0060:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.webview.widget.AmapWebView.canGoBack():boolean");
    }

    private String getAmapUserAgent() {
        String str;
        StringBuilder sb = new StringBuilder();
        String str2 = defpackage.ahp.a.a().a;
        String a2 = ajp.a();
        sb.append("AliApp(amap/");
        sb.append(str2);
        sb.append(") ");
        sb.append("amap/");
        sb.append(str2);
        sb.append(Token.SEPARATOR);
        sb.append("Mac=".concat(String.valueOf(a2)));
        sb.append(Token.SEPARATOR);
        String[] stringArray = getResources().getStringArray(R.array.network_type);
        switch (aaw.b(getContext())) {
            case 1:
                str = stringArray[1];
                break;
            case 2:
                str = stringArray[2];
                break;
            case 3:
                str = stringArray[3];
                break;
            case 4:
                str = stringArray[0];
                break;
            default:
                str = null;
                break;
        }
        if (!TextUtils.isEmpty(str)) {
            sb.append("NetType/");
            sb.append(str);
        }
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public void saveWebError(int i, String str, String str2) {
        if (str2 != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("errorCode", i);
                jSONObject.put("description", str);
                jSONObject.put("isHasUrl", ahd.b(str2.replace("file://", "")));
                jSONObject.put("failingUrl", str2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2(LogConstant.POI_ERROR_PAGE_ID, "B003", jSONObject);
        }
    }

    private static void restartApplication() {
        Application application = AMapAppGlobal.getApplication();
        Intent launchIntentForPackage = application.getPackageManager().getLaunchIntentForPackage(application.getPackageName());
        launchIntentForPackage.addFlags(268435456);
        launchIntentForPackage.addFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
        launchIntentForPackage.addFlags(32768);
        application.getApplicationContext().startActivity(launchIntentForPackage);
        System.exit(0);
    }

    public final void goBackOrForward(int i) {
        super.goBackOrForward(i);
        activeEvent(Constants.EVENT_RESUME, "2");
    }

    public final void goBack() {
        if (!TextUtils.isEmpty(NOT_SUPPORT_APP_SCHEME) && NOT_SUPPORT_APP_SCHEME.equals(getUrl())) {
            super.goBack();
        }
        super.goBack();
        activeEvent(Constants.EVENT_RESUME, "2");
    }

    private void activeEvent(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("action", "activeEvent");
            jSONObject.put("type", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("reson", str2);
            jSONObject.put("data", jSONObject2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder("javascript:activeEvent(");
        sb.append(jSONObject.toString());
        sb.append(")");
        loadUrl(sb.toString());
    }

    public final void destroy() {
        super.destroy();
        this.isWebViewAlive = false;
    }

    public final void loadJs(@NonNull String str) {
        if (this.isWebViewAlive) {
            super.loadJs(str);
        }
    }

    public final void loadUrl(@NonNull String str) {
        new zd();
        String a2 = zd.a(str);
        if (this.isWebViewAlive) {
            logInvalidFileUrl(a2);
            super.loadUrl(str);
        }
    }

    public final void loadUrl(@NonNull String str, boolean z) {
        if (this.mIsBaichuanMode && z) {
            StringBuilder sb = new StringBuilder();
            sb.append("load baichuan url with new tab \n");
            StackTraceElement[] stackTrace = new Exception(TextUtils.isEmpty(str) ? "" : str).getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    if (stackTraceElement != null && !TextUtils.isEmpty(stackTraceElement.toString())) {
                        sb.append(stackTraceElement.toString());
                        sb.append("\n");
                    }
                }
            }
            AMapLog.logNormalNative(AMapLog.GROUP_COMMON, "P0018", ALCTtsConstant.EVENT_ID_TTS_JNI_ERROR, sb.toString());
        }
        super.loadUrl(str, z);
    }

    public final void setBaiChuanMode(boolean z) {
        this.mIsBaichuanMode = z;
    }

    public final void loadData(String str, String str2, String str3) {
        if (this.isWebViewAlive) {
            super.loadData(str, str2, str3);
        }
    }

    public final void setIsRequestFocusOnPageFinished(boolean z) {
        if (this.isRequestFocusOnPageFinished != z) {
            this.isRequestFocusOnPageFinished = z;
        }
    }

    private void logInvalidFileUrl(String str) {
        if (!TextUtils.isEmpty(str) && str.startsWith("file")) {
            if (sPackageUri == null) {
                sPackageUri = "";
                File cacheDir = FileUtil.getCacheDir();
                if (cacheDir != null && !TextUtils.isEmpty(cacheDir.getParent())) {
                    StringBuilder sb = new StringBuilder("file://");
                    sb.append(cacheDir.getParent());
                    sPackageUri = sb.toString();
                }
            }
            if (!str.startsWith(sDefaultPackageUri) && (TextUtils.isEmpty(sPackageUri) || !str.startsWith(sPackageUri))) {
                coe.a("P0018", ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, "AmapWebView:".concat(String.valueOf(str)));
            }
        }
    }
}
