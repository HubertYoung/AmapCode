package com.alipay.mobile.worker;

import android.graphics.Bitmap;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.ValueCallback;
import com.alipay.mobile.nebula.webview.APJsPromptResult;
import com.alipay.mobile.nebula.webview.APJsResult;
import com.alipay.mobile.nebula.webview.APWebChromeClient;
import com.alipay.mobile.nebula.webview.APWebChromeClient.CustomViewCallback;
import com.alipay.mobile.nebula.webview.APWebView;

public class WorkerWebChromeClient implements APWebChromeClient {
    private WebWorker a;

    public WorkerWebChromeClient(WebWorker worker) {
        this.a = worker;
    }

    public void onProgressChanged(APWebView apWebView, int i) {
    }

    public void onReceivedTitle(APWebView apWebView, String s) {
    }

    public void onReceivedIcon(APWebView apWebView, Bitmap bitmap) {
    }

    public void onReceivedTouchIconUrl(APWebView apWebView, String s, boolean b) {
    }

    public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
    }

    public void onHideCustomView() {
    }

    public boolean onCreateWindow(APWebView apWebView, boolean b, boolean b1, Message message) {
        return false;
    }

    public void onRequestFocus(APWebView apWebView) {
    }

    public void onCloseWindow(APWebView apWebView) {
    }

    public boolean onJsAlert(APWebView apWebView, String s, String s1, APJsResult apJsResult) {
        return false;
    }

    public boolean onJsConfirm(APWebView apWebView, String s, String s1, APJsResult apJsResult) {
        return false;
    }

    public boolean onJsPrompt(APWebView apWebView, String s, String s1, String s2, APJsPromptResult apJsPromptResult) {
        return false;
    }

    public boolean onJsBeforeUnload(APWebView apWebView, String s, String s1, APJsResult apJsResult) {
        return false;
    }

    public void onGeolocationPermissionsShowPrompt(String s, Callback callback) {
    }

    public void onGeolocationPermissionsHidePrompt() {
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String message = consoleMessage.message();
        this.a.getWorkerControllerProvider().handleMsgFromWorker(message);
        PerfLogger.onConsoleMessage(message);
        return false;
    }

    public Bitmap getDefaultVideoPoster() {
        return null;
    }

    public View getVideoLoadingProgressView() {
        return null;
    }

    public void getVisitedHistory(ValueCallback<String[]> valueCallback) {
    }

    public void openFileChooser(ValueCallback valueCallback, boolean b) {
    }
}
