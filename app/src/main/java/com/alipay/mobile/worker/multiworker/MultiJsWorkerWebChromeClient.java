package com.alipay.mobile.worker.multiworker;

import android.graphics.Bitmap;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.ValueCallback;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.webview.APJsPromptResult;
import com.alipay.mobile.nebula.webview.APJsResult;
import com.alipay.mobile.nebula.webview.APWebChromeClient;
import com.alipay.mobile.nebula.webview.APWebChromeClient.CustomViewCallback;
import com.alipay.mobile.nebula.webview.APWebView;

public class MultiJsWorkerWebChromeClient implements APWebChromeClient {
    private MultiJsWorker a;

    public MultiJsWorkerWebChromeClient(MultiJsWorker worker) {
        this.a = worker;
    }

    public void onProgressChanged(APWebView view, int newProgress) {
    }

    public void onReceivedTitle(APWebView view, String title) {
    }

    public void onReceivedIcon(APWebView view, Bitmap icon) {
    }

    public void onReceivedTouchIconUrl(APWebView view, String url, boolean precomposed) {
    }

    public void onShowCustomView(View view, CustomViewCallback callback) {
    }

    public void onHideCustomView() {
    }

    public boolean onCreateWindow(APWebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        return false;
    }

    public void onRequestFocus(APWebView view) {
    }

    public void onCloseWindow(APWebView window) {
    }

    public boolean onJsAlert(APWebView view, String url, String message, APJsResult result) {
        return false;
    }

    public boolean onJsConfirm(APWebView view, String url, String message, APJsResult result) {
        return false;
    }

    public boolean onJsPrompt(APWebView view, String url, String message, String defaultValue, APJsPromptResult result) {
        return false;
    }

    public boolean onJsBeforeUnload(APWebView view, String url, String message, APJsResult result) {
        return false;
    }

    public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
    }

    public void onGeolocationPermissionsHidePrompt() {
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String message = consoleMessage.message();
        H5Log.d("MultiJsWorkerWebChromeClient", "onConsoleMessage..." + message);
        if (this.a != null) {
            MultiJsWorkerMessageTransponder transponder = this.a.getMultiJsWorkerMessageTransponder();
            if (transponder != null) {
                transponder.handleMessageFromWorker(message);
            }
        }
        return false;
    }

    public Bitmap getDefaultVideoPoster() {
        return null;
    }

    public View getVideoLoadingProgressView() {
        return null;
    }

    public void getVisitedHistory(ValueCallback<String[]> callback) {
    }

    public void openFileChooser(ValueCallback callback, boolean array) {
    }
}
