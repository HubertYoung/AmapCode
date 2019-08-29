package com.alibaba.baichuan.android.trade.c.b;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alibaba.baichuan.android.jsbridge.AlibcJsBridge;
import com.alibaba.baichuan.android.jsbridge.AlibcWebview;
import com.alibaba.baichuan.android.jsbridge.a.c;
import com.alibaba.baichuan.android.jsbridge.plugin.AlibcPluginEntryManager;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.b.a;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.utils.i;
import java.util.HashMap;
import java.util.Map;

public class d implements AlibcWebview {
    private static final String e = "d";
    private static final String g;
    private static final String h;
    public WebView a;
    public HashMap b;
    public a c;
    public AlibcPluginEntryManager d;
    private WebViewClient f;
    private String i;
    private String j;
    private boolean k;
    private WebChromeClient l;
    private Context m;

    static {
        StringBuilder sb = new StringBuilder(" AliApp(BC/");
        sb.append(AlibcContext.sdkVersion);
        sb.append(")");
        g = sb.toString();
        StringBuilder sb2 = new StringBuilder(" tae_sdk_");
        sb2.append(AlibcContext.sdkVersion);
        h = sb2.toString();
    }

    public d(Context context, a aVar, WebView webView, Map map, WebViewClient webViewClient, WebChromeClient webChromeClient, boolean z) {
        this.b = new HashMap();
        this.d = null;
        this.a = webView;
        this.f = webViewClient;
        this.l = webChromeClient;
        this.b = (HashMap) map;
        this.c = aVar;
        this.m = context;
        this.k = z;
        f();
    }

    public d(Context context, a aVar, WebView webView, Map map, boolean z) {
        this(context, aVar, webView, map, null, null, z);
    }

    @TargetApi(21)
    private void a(WebView webView, boolean z) {
        WebSettings settings = webView.getSettings();
        if (settings != null) {
            try {
                settings.setJavaScriptEnabled(true);
            } catch (Exception unused) {
            }
            settings.setSavePassword(false);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(false);
            settings.setDomStorageEnabled(true);
            this.j = webView.getContext().getApplicationContext().getDir("cache", 0).getPath();
            settings.setAppCachePath(this.j);
            settings.setAllowFileAccess(true);
            settings.setAppCacheEnabled(true);
            settings.setDefaultTextEncodingName("UTF-8");
            if (com.alibaba.baichuan.android.trade.utils.d.a(webView.getContext())) {
                settings.setCacheMode(-1);
            } else {
                settings.setCacheMode(1);
            }
            settings.setBuiltInZoomControls(false);
            if (z) {
                StringBuilder sb = new StringBuilder();
                this.i = settings.getUserAgentString();
                if (this.i != null) {
                    sb.append(this.i);
                }
                if (!AlibcConfig.getInstance().getLoginDegarade(false)) {
                    sb.append(h);
                }
                sb.append(g);
                sb.append(String.format(" AliBaichuan(%s/%s)", new Object[]{AlibcConfig.getInstance().getWebTTID(), AlibcConfig.getInstance().getIsvVersion()}));
                settings.setUserAgentString(sb.toString());
            }
            if (VERSION.SDK_INT >= 21) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            }
        }
    }

    private void f() {
        a(this.a, true);
        if (this.f == null) {
            this.a.setWebViewClient(new f(this));
        } else {
            this.a.setWebViewClient(new b(this.f, this));
        }
        if (this.l == null) {
            this.a.setWebChromeClient(new c(this, this.k));
        } else {
            this.a.setWebChromeClient(new a(this.l, this));
        }
        this.a.setTag(i.a(AlibcContext.context, "id", "com_taobao_nb_sdk_webview_click"), Boolean.FALSE);
        this.a.setOnTouchListener(new e(this));
        AlibcJsBridge.getInstance().init();
        c.a();
        this.d = new AlibcPluginEntryManager(this.m, this);
    }

    public void a(String str, Map map) {
        if (this.a != null) {
            this.a.loadUrl(str, map);
        }
    }

    public boolean a() {
        if (this.a == null) {
            return false;
        }
        return this.a.canGoBack();
    }

    public Map b() {
        return (Map) this.b.get("ui_contextParams");
    }

    public boolean c() {
        return "true".equals(this.b.get(AlibcConstants.SHOW_BY_H5));
    }

    public void clearCache() {
        this.d.onDestroy();
    }

    public void d() {
        if (this.a != null) {
            this.a.reload();
        }
    }

    public void e() {
        if (this.a != null) {
            this.a.goBack();
        }
    }

    public Context getContext() {
        return this.m;
    }

    public Object getJsObject(String str) {
        if (this.d == null) {
            return null;
        }
        return this.d.getEntry(str);
    }

    public WebView getWebView() {
        return this.a;
    }

    public void loadUrl(String str) {
        if (this.a != null) {
            this.a.loadUrl(str);
        }
    }
}
