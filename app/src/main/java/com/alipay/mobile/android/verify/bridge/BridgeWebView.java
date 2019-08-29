package com.alipay.mobile.android.verify.bridge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.alipay.mobile.android.verify.logger.Logger;
import com.squareup.otto.Subscribe;
import java.lang.ref.WeakReference;

public class BridgeWebView extends WebView {
    private WeakReference<JsResult> a;
    private WeakReference<JsPromptResult> b;

    public BridgeWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    public BridgeWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public BridgeWebView(Context context) {
        super(context);
        a();
    }

    private void a() {
        b();
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        setWebChromeClient(new a());
        setWebViewClient(new c());
        BusProvider.getInstance().register(this);
    }

    public void destroy() {
        super.destroy();
        try {
            if (this.a != null) {
                ((JsResult) this.a.get()).cancel();
            }
        } catch (Exception unused) {
            Logger.t("BridgeWebView").e("cancel js result got error", new Object[0]);
        }
        try {
            if (this.b != null) {
                ((JsPromptResult) this.b.get()).cancel();
            }
        } catch (Exception unused2) {
            Logger.t("BridgeWebView").e("cancel js prompt result got error", new Object[0]);
        }
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onJsResult(JsResult jsResult) {
        this.a = new WeakReference<>(jsResult);
    }

    @Subscribe
    public void onJsPromptResult(JsPromptResult jsPromptResult) {
        this.b = new WeakReference<>(jsPromptResult);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void b() {
        removeJavascriptInterface("searchBoxJavaBridge_");
        removeJavascriptInterface("accessibility");
        removeJavascriptInterface("accessibilityTraversal");
        WebSettings settings = getSettings();
        settings.setAllowFileAccess(false);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setSupportMultipleWindows(false);
        settings.setDefaultFixedFontSize(16);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        try {
            settings.setJavaScriptEnabled(true);
        } catch (Exception e) {
            Logger.t("BridgeWebView").e(e, "set js enable got error during customSettings", new Object[0]);
        }
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setGeolocationEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        String userAgentString = settings.getUserAgentString();
        StringBuilder sb = new StringBuilder();
        sb.append(userAgentString);
        sb.append(" AlipayVerifySDK/1.0.1 ZOLOZ_PKG_TYPE/FIN_CLOUD");
        settings.setUserAgentString(sb.toString());
        if (VERSION.SDK_INT >= 19) {
            setWebContentsDebuggingEnabled(false);
        }
    }
}
