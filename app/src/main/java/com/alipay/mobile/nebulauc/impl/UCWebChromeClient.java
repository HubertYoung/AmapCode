package com.alipay.mobile.nebulauc.impl;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ConsoleMessage.MessageLevel;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import com.alipay.mobile.nebula.webview.APJsPromptResult;
import com.alipay.mobile.nebula.webview.APJsResult;
import com.alipay.mobile.nebula.webview.APWebChromeClient;
import com.alipay.mobile.nebula.webview.APWebView;
import com.uc.webview.export.GeolocationPermissions.Callback;
import com.uc.webview.export.JsPromptResult;
import com.uc.webview.export.JsResult;
import com.uc.webview.export.WebChromeClient;
import com.uc.webview.export.WebChromeClient.CustomViewCallback;
import com.uc.webview.export.WebChromeClient.FileChooserParams;
import com.uc.webview.export.WebView;

class UCWebChromeClient extends WebChromeClient {
    private APWebView mAPView;
    private APWebChromeClient mClient;

    UCWebChromeClient(APWebView apWebView, APWebChromeClient apWebChromeClient) {
        this.mAPView = apWebView;
        this.mClient = apWebChromeClient;
    }

    public void onProgressChanged(WebView view, int i) {
        if (this.mClient != null) {
            this.mClient.onProgressChanged(this.mAPView, i);
        }
    }

    public void onReceivedTitle(WebView view, String s) {
        if (this.mClient != null) {
            this.mClient.onReceivedTitle(this.mAPView, s);
        }
    }

    public void onReceivedIcon(WebView view, Bitmap bitmap) {
        if (this.mClient != null) {
            this.mClient.onReceivedIcon(this.mAPView, bitmap);
        }
    }

    public void onReceivedTouchIconUrl(WebView view, String s, boolean b) {
        if (this.mClient != null) {
            this.mClient.onReceivedTouchIconUrl(this.mAPView, s, b);
        }
    }

    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (this.mClient != null) {
            this.mClient.onShowCustomView(view, (APWebChromeClient.CustomViewCallback) DynamicProxy.dynamicProxy(APWebChromeClient.CustomViewCallback.class, callback));
        }
    }

    public void onHideCustomView() {
        if (this.mClient != null) {
            this.mClient.onHideCustomView();
        }
    }

    public boolean onCreateWindow(WebView view, boolean b, boolean b1, Message message) {
        return this.mClient != null && this.mClient.onCreateWindow(this.mAPView, b, b1, message);
    }

    public void onRequestFocus(WebView webView) {
        if (this.mClient != null) {
            this.mClient.onRequestFocus(this.mAPView);
        }
    }

    public void onCloseWindow(WebView webView) {
        if (this.mClient != null) {
            this.mClient.onCloseWindow(this.mAPView);
        }
    }

    public boolean onJsAlert(WebView view, String s, String s1, JsResult jsResult) {
        return this.mClient != null && this.mClient.onJsAlert(this.mAPView, s, s1, (APJsResult) DynamicProxy.dynamicProxy(APJsResult.class, jsResult));
    }

    public boolean onJsConfirm(WebView view, String s, String s1, JsResult jsResult) {
        return this.mClient != null && this.mClient.onJsConfirm(this.mAPView, s, s1, (APJsResult) DynamicProxy.dynamicProxy(APJsResult.class, jsResult));
    }

    public boolean onJsPrompt(WebView view, String s, String s1, String s2, JsPromptResult jsPR) {
        if (this.mClient != null) {
            if (this.mClient.onJsPrompt(this.mAPView, s, s1, s2, (APJsPromptResult) DynamicProxy.dynamicProxy(APJsPromptResult.class, jsPR))) {
                return true;
            }
        }
        return false;
    }

    public boolean onJsBeforeUnload(WebView view, String s, String s1, JsResult jsResult) {
        return this.mClient != null && this.mClient.onJsBeforeUnload(this.mAPView, s, s1, (APJsResult) DynamicProxy.dynamicProxy(APJsResult.class, jsResult));
    }

    public void onGeolocationPermissionsShowPrompt(String s, Callback cb) {
        if (this.mClient != null) {
            this.mClient.onGeolocationPermissionsShowPrompt(s, (GeolocationPermissions.Callback) DynamicProxy.dynamicProxy(GeolocationPermissions.Callback.class, cb));
        }
    }

    public void onGeolocationPermissionsHidePrompt() {
        if (this.mClient != null) {
            this.mClient.onGeolocationPermissionsHidePrompt();
        }
    }

    public boolean onConsoleMessage(ConsoleMessage cm) {
        return this.mClient != null && this.mClient.onConsoleMessage(new ConsoleMessage(cm.message(), cm.sourceId(), cm.lineNumber(), MessageLevel.valueOf(cm.messageLevel().name())));
    }

    public Bitmap getDefaultVideoPoster() {
        if (this.mClient != null) {
            return this.mClient.getDefaultVideoPoster();
        }
        return null;
    }

    public View getVideoLoadingProgressView() {
        if (this.mClient != null) {
            return this.mClient.getVideoLoadingProgressView();
        }
        return null;
    }

    public void getVisitedHistory(ValueCallback<String[]> valueCallback) {
        if (this.mClient != null) {
            this.mClient.getVisitedHistory(valueCallback);
        }
    }

    public void openFileChooser(ValueCallback<Uri> callback) {
        if (this.mClient != null) {
            this.mClient.openFileChooser(callback, false);
        }
    }

    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
        if (this.mClient == null) {
            return false;
        }
        this.mClient.openFileChooser(valueCallback, true);
        return true;
    }
}
