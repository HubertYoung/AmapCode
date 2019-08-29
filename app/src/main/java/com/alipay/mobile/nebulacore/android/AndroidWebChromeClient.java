package com.alipay.mobile.nebulacore.android;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ConsoleMessage.MessageLevel;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebView;
import com.alipay.mobile.nebula.webview.APJsPromptResult;
import com.alipay.mobile.nebula.webview.APJsResult;
import com.alipay.mobile.nebula.webview.APWebChromeClient;
import com.alipay.mobile.nebula.webview.APWebView;

class AndroidWebChromeClient extends WebChromeClient {
    private APWebView a;
    private APWebChromeClient b;

    AndroidWebChromeClient(APWebView apWebView, APWebChromeClient apWebChromeClient) {
        this.a = apWebView;
        this.b = apWebChromeClient;
    }

    public void onProgressChanged(WebView view, int i) {
        if (this.b != null) {
            this.b.onProgressChanged(this.a, i);
        }
    }

    public void onReceivedTitle(WebView view, String s) {
        if (this.b != null) {
            this.b.onReceivedTitle(this.a, s);
        }
    }

    public void onReceivedIcon(WebView view, Bitmap bitmap) {
        if (this.b != null) {
            this.b.onReceivedIcon(this.a, bitmap);
        }
    }

    public void onReceivedTouchIconUrl(WebView view, String s, boolean b2) {
        if (this.b != null) {
            this.b.onReceivedTouchIconUrl(this.a, s, b2);
        }
    }

    public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
        if (this.b != null) {
            this.b.onShowCustomView(view, (APWebChromeClient.CustomViewCallback) DynamicProxy.dynamicProxy(APWebChromeClient.CustomViewCallback.class, customViewCallback));
        }
    }

    public void onHideCustomView() {
        if (this.b != null) {
            this.b.onHideCustomView();
        }
    }

    public boolean onCreateWindow(WebView view, boolean b2, boolean b1, Message message) {
        return this.b.onCreateWindow(this.a, b2, b1, message);
    }

    public void onRequestFocus(WebView webView) {
        if (this.b != null) {
            this.b.onRequestFocus(this.a);
        }
    }

    public void onCloseWindow(WebView webView) {
        if (this.b != null) {
            this.b.onCloseWindow(this.a);
        }
    }

    public boolean onJsAlert(WebView view, String s, String s1, JsResult jsResult) {
        return this.b.onJsAlert(this.a, s, s1, (APJsResult) DynamicProxy.dynamicProxy(APJsResult.class, jsResult));
    }

    public boolean onJsConfirm(WebView view, String s, String s1, JsResult jsResult) {
        return this.b.onJsConfirm(this.a, s, s1, (APJsResult) DynamicProxy.dynamicProxy(APJsResult.class, jsResult));
    }

    public boolean onJsPrompt(WebView view, String s, String s1, String s2, JsPromptResult jsPromptResult) {
        return this.b.onJsPrompt(this.a, s, s1, s2, (APJsPromptResult) DynamicProxy.dynamicProxy(APJsPromptResult.class, jsPromptResult));
    }

    public boolean onJsBeforeUnload(WebView view, String s, String s1, JsResult jsResult) {
        return this.b.onJsBeforeUnload(this.a, s, s1, (APJsResult) DynamicProxy.dynamicProxy(APJsResult.class, jsResult));
    }

    public void onGeolocationPermissionsShowPrompt(String s, Callback callback) {
        if (this.b != null) {
            this.b.onGeolocationPermissionsShowPrompt(s, (Callback) DynamicProxy.dynamicProxy(Callback.class, callback));
        }
    }

    public void onGeolocationPermissionsHidePrompt() {
        if (this.b != null) {
            this.b.onGeolocationPermissionsHidePrompt();
        }
    }

    public boolean onConsoleMessage(ConsoleMessage cm) {
        return this.b != null && this.b.onConsoleMessage(new ConsoleMessage(cm.message(), cm.sourceId(), cm.lineNumber(), MessageLevel.valueOf(cm.messageLevel().name())));
    }

    public Bitmap getDefaultVideoPoster() {
        if (this.b != null) {
            return this.b.getDefaultVideoPoster();
        }
        return null;
    }

    public View getVideoLoadingProgressView() {
        if (this.b != null) {
            return this.b.getVideoLoadingProgressView();
        }
        return null;
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [android.webkit.ValueCallback<java.lang.String[]>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void getVisitedHistory(android.webkit.ValueCallback<java.lang.String[]> r3) {
        /*
            r2 = this;
            com.alipay.mobile.nebula.webview.APWebChromeClient r1 = r2.b
            java.lang.Class<android.webkit.ValueCallback> r0 = android.webkit.ValueCallback.class
            java.lang.Object r0 = com.alipay.mobile.nebulacore.android.DynamicProxy.dynamicProxy(r0, r3)
            android.webkit.ValueCallback r0 = (android.webkit.ValueCallback) r0
            r1.getVisitedHistory(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.android.AndroidWebChromeClient.getVisitedHistory(android.webkit.ValueCallback):void");
    }

    public void openFileChooser(ValueCallback<Uri> callback) {
        if (this.b != null) {
            this.b.openFileChooser(callback, false);
        }
    }

    public void openFileChooser(ValueCallback callback, String acceptType) {
        if (this.b != null) {
            this.b.openFileChooser(callback, false);
        }
    }

    public void openFileChooser(ValueCallback<Uri> callback, String acceptType, String capture) {
        if (this.b != null) {
            this.b.openFileChooser(callback, false);
        }
    }

    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> callback, FileChooserParams fileChooserParams) {
        if (this.b == null) {
            return false;
        }
        this.b.openFileChooser(callback, true);
        return true;
    }
}
