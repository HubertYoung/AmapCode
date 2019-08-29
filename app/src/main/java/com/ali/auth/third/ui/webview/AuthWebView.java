package com.ali.auth.third.ui.webview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.ali.auth.third.core.util.CommonUtils;
import java.util.HashMap;
import java.util.Map;

@SuppressLint({"SetJavaScriptEnabled"})
public class AuthWebView extends WebView {
    private static final String a = "AuthWebView";
    private Map<String, Object> b = new HashMap();
    private HashMap<String, String> c = new HashMap<>();
    private String d;

    public AuthWebView(Context context) {
        super(context);
        a(context, true);
    }

    public AuthWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, true);
    }

    public AuthWebView(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    @TargetApi(21)
    private void a(Context context, boolean z) {
        WebSettings settings = getSettings();
        try {
            settings.setJavaScriptEnabled(true);
        } catch (Exception unused) {
        }
        settings.setSavePassword(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setDomStorageEnabled(true);
        this.d = context.getApplicationContext().getDir("cache", 0).getPath();
        settings.setAppCachePath(this.d);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        if (CommonUtils.isNetworkAvailable(context)) {
            settings.setCacheMode(-1);
        } else {
            settings.setCacheMode(1);
        }
        settings.setBuiltInZoomControls(false);
        try {
            removeJavascriptInterface("searchBoxJavaBridge_");
            removeJavascriptInterface("accessibility");
            removeJavascriptInterface("accessibilityTraversal");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (VERSION.SDK_INT >= 21) {
            try {
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
                settings.setMixedContentMode(0);
            } catch (Throwable unused2) {
            }
        }
    }

    public final void addBridgeObject(String str, Object obj) {
        this.b.put(str, obj);
    }

    public final void addJavascriptInterface(Object obj, String str) {
    }

    public Object getBridgeObj(String str) {
        return this.b.get(str);
    }

    public HashMap<String, String> getContextParameters() {
        return this.c;
    }
}
