package com.alibaba.baichuan.android.trade.c.b;

import android.annotation.TargetApi;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import com.alibaba.baichuan.android.jsbridge.AlibcJsBridge;
import com.alibaba.baichuan.android.jsbridge.AlibcNativeCallbackUtil;
import com.alibaba.baichuan.android.jsbridge.AlibcWebview;
import com.alibaba.baichuan.android.trade.ui.activity.a;

public class c extends WebChromeClient {
    private AlibcWebview a;
    private boolean b;

    public c(AlibcWebview alibcWebview, boolean z) {
        this.a = alibcWebview;
        this.b = z;
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String message = consoleMessage.message();
        if (message == null || !message.startsWith("wvNativeCallback")) {
            return super.onConsoleMessage(consoleMessage);
        }
        String substring = message.substring(message.indexOf("/") + 1);
        int indexOf = substring.indexOf("/");
        String substring2 = substring.substring(0, indexOf);
        String substring3 = substring.substring(indexOf + 1);
        ValueCallback nativeCallback = AlibcNativeCallbackUtil.getNativeCallback(substring2);
        if (nativeCallback != null) {
            nativeCallback.onReceiveValue(substring3);
            AlibcNativeCallbackUtil.clearNativeCallback(substring2);
        }
        return true;
    }

    @TargetApi(5)
    public void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, QuotaUpdater quotaUpdater) {
        if (j2 < 20971520) {
            quotaUpdater.updateQuota(j2);
        } else {
            quotaUpdater.updateQuota(j);
        }
    }

    @TargetApi(5)
    public void onGeolocationPermissionsShowPrompt(String str, Callback callback) {
        super.onGeolocationPermissionsShowPrompt(str, callback);
        callback.invoke(str, true, false);
    }

    public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        if (str3 == null || !str3.equals("bc_hybrid:")) {
            return false;
        }
        AlibcJsBridge.getInstance().callMethod(this.a, str2);
        jsPromptResult.confirm("");
        return true;
    }

    public void onProgressChanged(WebView webView, int i) {
        super.onProgressChanged(webView, i);
    }

    public void onReceivedTitle(WebView webView, String str) {
        if (!this.b && webView != null && (webView.getContext() instanceof a)) {
            a aVar = (a) webView.getContext();
            if (aVar.d) {
                aVar.c.setText(str);
            }
        }
    }
}
