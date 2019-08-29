package com.uc.webview.export.internal.android;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView.WebViewTransport;
import com.uc.webview.export.GeolocationPermissions.Callback;
import com.uc.webview.export.WebChromeClient.CustomViewCallback;
import com.uc.webview.export.WebView;
import com.uc.webview.export.internal.interfaces.CommonDef;
import com.uc.webview.export.internal.interfaces.IOpenFileChooser;

/* compiled from: ProGuard */
final class i extends WebChromeClientCompatibility implements IOpenFileChooser {

    /* compiled from: ProGuard */
    class a implements CustomViewCallback {
        private WebChromeClient.CustomViewCallback b;

        public a(WebChromeClient.CustomViewCallback customViewCallback) {
            this.b = customViewCallback;
        }

        public final void onCustomViewHidden() {
            if (this.b != null) {
                this.b.onCustomViewHidden();
            }
        }
    }

    /* compiled from: ProGuard */
    class b implements Callback {
        private GeolocationPermissions.Callback b;

        public b(GeolocationPermissions.Callback callback) {
            this.b = callback;
        }

        public final void invoke(String str, boolean z, boolean z2) {
            if (this.b != null) {
                this.b.invoke(str, z, z2);
            }
        }
    }

    public i(WebView webView, com.uc.webview.export.WebChromeClient webChromeClient) {
        this.a = webView;
        this.b = webChromeClient;
    }

    public final void onProgressChanged(android.webkit.WebView webView, int i) {
        this.b.onProgressChanged(this.a, i);
    }

    public final void onReceivedTitle(android.webkit.WebView webView, String str) {
        this.b.onReceivedTitle(this.a, str);
    }

    public final void onReceivedIcon(android.webkit.WebView webView, Bitmap bitmap) {
        this.b.onReceivedIcon(this.a, bitmap);
    }

    public final void onReceivedTouchIconUrl(android.webkit.WebView webView, String str, boolean z) {
        this.b.onReceivedTouchIconUrl(this.a, str, z);
    }

    public final void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
        this.b.onShowCustomView(view, new a(customViewCallback));
    }

    public final void onHideCustomView() {
        this.b.onHideCustomView();
    }

    public final boolean onCreateWindow(android.webkit.WebView webView, boolean z, boolean z2, Message message) {
        WebViewTransport webViewTransport = (WebViewTransport) message.obj;
        WebView webView2 = this.a;
        webView2.getClass();
        WebView.WebViewTransport webViewTransport2 = new WebView.WebViewTransport();
        Message obtain = Message.obtain(new j(this, Looper.getMainLooper()));
        obtain.obj = webViewTransport2;
        CommonDef.sOnCreateWindowType = 1;
        boolean onCreateWindow = this.b.onCreateWindow(this.a, z, z2, obtain);
        CommonDef.sOnCreateWindowType = 0;
        if (webViewTransport2.getWebView() == null) {
            webViewTransport.setWebView(null);
        } else {
            webViewTransport.setWebView((android.webkit.WebView) webViewTransport2.getWebView().getCoreView());
        }
        if (webViewTransport.getWebView() != null) {
            message.sendToTarget();
        }
        return onCreateWindow;
    }

    public final void onRequestFocus(android.webkit.WebView webView) {
        this.b.onRequestFocus(this.a);
    }

    public final void onCloseWindow(android.webkit.WebView webView) {
        this.b.onCloseWindow(this.a);
    }

    public final boolean onJsAlert(android.webkit.WebView webView, String str, String str2, JsResult jsResult) {
        return this.b.onJsAlert(this.a, str, str2, new e(jsResult));
    }

    public final boolean onJsConfirm(android.webkit.WebView webView, String str, String str2, JsResult jsResult) {
        return this.b.onJsConfirm(this.a, str, str2, new e(jsResult));
    }

    public final boolean onJsPrompt(android.webkit.WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        return this.b.onJsPrompt(this.a, str, str2, str3, new d(jsPromptResult));
    }

    public final boolean onJsBeforeUnload(android.webkit.WebView webView, String str, String str2, JsResult jsResult) {
        return this.b.onJsBeforeUnload(this.a, str, str2, new e(jsResult));
    }

    public final void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
        this.b.onGeolocationPermissionsShowPrompt(str, new b(callback));
    }

    public final void onGeolocationPermissionsHidePrompt() {
        this.b.onGeolocationPermissionsHidePrompt();
    }

    public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return this.b.onConsoleMessage(consoleMessage);
    }

    public final Bitmap getDefaultVideoPoster() {
        return this.b.getDefaultVideoPoster();
    }

    public final View getVideoLoadingProgressView() {
        return this.b.getVideoLoadingProgressView();
    }

    public final void getVisitedHistory(ValueCallback<String[]> valueCallback) {
        this.b.getVisitedHistory(valueCallback);
    }

    public final void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
        a(valueCallback, str, str2);
    }

    public final void openFileChooser(ValueCallback<Uri> valueCallback, String str) {
        a(valueCallback, str, null);
    }

    public final void openFileChooser(ValueCallback<Uri> valueCallback) {
        a(valueCallback, null, null);
    }

    private void a(ValueCallback<Uri> valueCallback, String str, String str2) {
        if (!this.b.onShowFileChooser(this.a, new k(this, valueCallback), new l(this, str, str2))) {
            this.b.openFileChooser(valueCallback);
        }
    }
}
