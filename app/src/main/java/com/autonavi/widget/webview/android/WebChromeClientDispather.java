package com.autonavi.widget.webview.android;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import java.util.ArrayList;
import java.util.List;

public class WebChromeClientDispather extends WebChromeClient {
    private List<WebChromeClient> mWebChromeClients = new ArrayList();

    public void addWebChromeClient(@NonNull WebChromeClient webChromeClient) {
        if (!this.mWebChromeClients.contains(webChromeClient)) {
            this.mWebChromeClients.add(webChromeClient);
        }
    }

    public void removeWebChromeClient(@NonNull WebChromeClient webChromeClient) {
        if (this.mWebChromeClients.contains(webChromeClient)) {
            this.mWebChromeClients.remove(webChromeClient);
        }
    }

    public void close() {
        this.mWebChromeClients.clear();
    }

    public void onProgressChanged(WebView webView, int i) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            this.mWebChromeClients.get(size).onProgressChanged(webView, i);
        }
    }

    public void onReceivedTitle(WebView webView, String str) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            this.mWebChromeClients.get(size).onReceivedTitle(webView, str);
        }
    }

    public void onReceivedIcon(WebView webView, Bitmap bitmap) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            this.mWebChromeClients.get(size).onReceivedIcon(webView, bitmap);
        }
    }

    public void onReceivedTouchIconUrl(WebView webView, String str, boolean z) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            this.mWebChromeClients.get(size).onReceivedTouchIconUrl(webView, str, z);
        }
    }

    public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            this.mWebChromeClients.get(size).onShowCustomView(view, customViewCallback);
        }
    }

    public void onShowCustomView(View view, int i, CustomViewCallback customViewCallback) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            this.mWebChromeClients.get(size).onShowCustomView(view, i, customViewCallback);
        }
    }

    public void onHideCustomView() {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            this.mWebChromeClients.get(size).onHideCustomView();
        }
    }

    public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            if (this.mWebChromeClients.get(size).onCreateWindow(webView, z, z2, message)) {
                return true;
            }
        }
        return false;
    }

    public void onRequestFocus(WebView webView) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            this.mWebChromeClients.get(size).onRequestFocus(webView);
        }
    }

    public void onCloseWindow(WebView webView) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            this.mWebChromeClients.get(size).onCloseWindow(webView);
        }
    }

    public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            if (this.mWebChromeClients.get(size).onJsAlert(webView, str, str2, jsResult)) {
                return true;
            }
        }
        return false;
    }

    public boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            if (this.mWebChromeClients.get(size).onJsConfirm(webView, str, str2, jsResult)) {
                return true;
            }
        }
        return false;
    }

    public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            if (this.mWebChromeClients.get(size).onJsPrompt(webView, str, str2, str3, jsPromptResult)) {
                return true;
            }
        }
        return false;
    }

    public boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            if (this.mWebChromeClients.get(size).onJsBeforeUnload(webView, str, str2, jsResult)) {
                return true;
            }
        }
        return false;
    }

    public void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, QuotaUpdater quotaUpdater) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            this.mWebChromeClients.get(size).onExceededDatabaseQuota(str, str2, j, j2, j3, quotaUpdater);
        }
    }

    public void onReachedMaxAppCacheSize(long j, long j2, QuotaUpdater quotaUpdater) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            this.mWebChromeClients.get(size).onReachedMaxAppCacheSize(j, j2, quotaUpdater);
        }
    }

    public void onGeolocationPermissionsShowPrompt(String str, Callback callback) {
        callback.invoke(str, true, false);
    }

    public void onGeolocationPermissionsHidePrompt() {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            this.mWebChromeClients.get(size).onGeolocationPermissionsHidePrompt();
        }
    }

    @TargetApi(21)
    public void onPermissionRequest(PermissionRequest permissionRequest) {
        super.onPermissionRequest(permissionRequest);
    }

    @TargetApi(21)
    public void onPermissionRequestCanceled(PermissionRequest permissionRequest) {
        super.onPermissionRequestCanceled(permissionRequest);
    }

    public boolean onJsTimeout() {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            if (this.mWebChromeClients.get(size).onJsTimeout()) {
                return true;
            }
        }
        return false;
    }

    public void onConsoleMessage(String str, int i, String str2) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            this.mWebChromeClients.get(size).onConsoleMessage(str, i, str2);
        }
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            if (this.mWebChromeClients.get(size).onConsoleMessage(consoleMessage)) {
                return true;
            }
        }
        return false;
    }

    public Bitmap getDefaultVideoPoster() {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            Bitmap defaultVideoPoster = this.mWebChromeClients.get(size).getDefaultVideoPoster();
            if (defaultVideoPoster != null) {
                return defaultVideoPoster;
            }
        }
        return super.getDefaultVideoPoster();
    }

    public View getVideoLoadingProgressView() {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            View videoLoadingProgressView = this.mWebChromeClients.get(size).getVideoLoadingProgressView();
            if (videoLoadingProgressView != null) {
                return videoLoadingProgressView;
            }
        }
        return super.getVideoLoadingProgressView();
    }

    public void getVisitedHistory(ValueCallback<String[]> valueCallback) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            this.mWebChromeClients.get(size).getVisitedHistory(valueCallback);
        }
    }

    @TargetApi(21)
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
        for (int size = this.mWebChromeClients.size() - 1; size >= 0; size--) {
            if (this.mWebChromeClients.get(size).onShowFileChooser(webView, valueCallback, fileChooserParams)) {
                return true;
            }
        }
        return false;
    }
}
