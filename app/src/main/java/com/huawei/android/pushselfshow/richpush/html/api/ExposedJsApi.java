package com.huawei.android.pushselfshow.richpush.html.api;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.huawei.android.pushagent.a.a.c;

public class ExposedJsApi {
    private static final String TAG = "PushSelfShowLog";
    private NativeToJsMessageQueue jsMessageQueue;
    public c pluginManager;

    public ExposedJsApi(Activity activity, WebView webView, String str, boolean z) {
        c.e("PushSelfShowLog", "init ExposedJsApi");
        this.pluginManager = new c(activity, z);
        this.jsMessageQueue = new NativeToJsMessageQueue(activity, webView, str);
    }

    @JavascriptInterface
    public void exec(String str, String str2) {
        try {
            StringBuilder sb = new StringBuilder("ExposedJsApi exec and serviceName is ");
            sb.append(str);
            sb.append(",jsonMsgObject is ");
            sb.append(str2);
            c.a("PushSelfShowLog", sb.toString());
            this.pluginManager.a(str, str2, this.jsMessageQueue);
        } catch (Exception e) {
            c.a((String) "PushSelfShowLog", (String) "ExposedJsApi exec error", (Throwable) e);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        this.pluginManager.a(i, i2, intent);
    }

    public void onDestroy() {
        this.pluginManager.a();
        this.jsMessageQueue.b();
    }

    public void onPause() {
        this.pluginManager.c();
    }

    public void onResume() {
        this.pluginManager.b();
    }

    @JavascriptInterface
    public String retrieveJsMessages() {
        try {
            return this.jsMessageQueue.c();
        } catch (Exception unused) {
            c.a("PushSelfShowLog", "retrieveJsMessages error");
            return "";
        }
    }

    @JavascriptInterface
    public String synExec(String str, String str2) {
        try {
            StringBuilder sb = new StringBuilder("ExposedJsApi exec and serviceName is ");
            sb.append(str);
            sb.append(",jsonMsgObject is ");
            sb.append(str2);
            c.a("PushSelfShowLog", sb.toString());
            return this.pluginManager.a(str, str2);
        } catch (Exception e) {
            c.a((String) "PushSelfShowLog", (String) "ExposedJsApi exec error", (Throwable) e);
            return null;
        }
    }
}
