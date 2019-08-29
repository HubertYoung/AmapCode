package com.alibaba.baichuan.android.jsbridge;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class AlibcJsCallbackContext {
    private static boolean g = (VERSION.SDK_INT >= 19);
    AlibcWebview a;
    String b;
    String c;
    String d;
    boolean e = false;
    String f = null;

    public AlibcJsCallbackContext(AlibcWebview alibcWebview) {
        this.a = alibcWebview;
    }

    public AlibcJsCallbackContext(AlibcWebview alibcWebview, String str, String str2, String str3) {
        this.a = alibcWebview;
        this.b = str;
        this.c = str2;
        this.d = str3;
    }

    private static String a(String str) {
        if (str.contains(" ")) {
            try {
                str = str.replace(" ", "\\u2028");
            } catch (Exception unused) {
            }
        }
        if (str.contains(" ")) {
            try {
                str = str.replace(" ", "\\u2029");
            } catch (Exception unused2) {
            }
        }
        return str.replace("\\", "\\\\").replace("'", "\\'");
    }

    private static void a(AlibcWebview alibcWebview, String str, String str2) {
        if (AlibcContext.isDebuggable() && !TextUtils.isEmpty(str2)) {
            try {
                new JSONObject(str2);
            } catch (JSONException unused) {
                StringBuilder sb = new StringBuilder("return param is not a valid json!\n");
                sb.append(str);
                sb.append("\n");
                sb.append(str2);
                AlibcLogger.e("AlibcJsCallbackContext", sb.toString());
            }
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = bny.c;
        }
        try {
            alibcWebview.getWebView().post(new b(alibcWebview, String.format(str, new Object[]{a(str2)})));
        } catch (Exception e2) {
            StringBuilder sb2 = new StringBuilder("callback error. ");
            sb2.append(e2.getMessage());
            AlibcLogger.e("AlibcJsCallbackContext", sb2.toString());
        }
    }

    public static void evaluateJavascript(WebView webView, String str) {
        evaluateJavascript(webView, str, null);
    }

    @TargetApi(19)
    public static void evaluateJavascript(WebView webView, String str, ValueCallback valueCallback) {
        while (true) {
            if (str != null && str.length() > 10 && "javascript:".equals(str.substring(0, 11).toLowerCase())) {
                str = str.substring(11);
            }
            if (g) {
                try {
                    webView.evaluateJavascript(str, valueCallback);
                    return;
                } catch (IllegalStateException | NoSuchMethodError unused) {
                    g = false;
                }
            } else {
                if (valueCallback == null) {
                    webView.loadUrl("javascript:".concat(String.valueOf(str)));
                }
                return;
            }
        }
    }

    public static void fireEvent(AlibcWebview alibcWebview, String str, String str2) {
        a(alibcWebview, String.format("window.AliBCBridge && window.AliBCBridge.fireEvent('%s', '%%s', %s);", new Object[]{str, null}), str2);
    }

    public void error(AlibcJsResult alibcJsResult) {
        AlibcLogger.d("AlibcJsCallbackContext", "call error ");
        a(this.a, String.format("javascript:window.AliBCBridge&&window.AliBCBridge.onFailure(%s,'%%s');", new Object[]{this.b}), alibcJsResult.toJsonString());
    }

    public void fireEvent(String str, String str2) {
        a(this.a, String.format("window.AliBCBridge && window.AliBCBridge.fireEvent('%s', '%%s', %s);", new Object[]{str, this.b}), str2);
    }

    public WebView getWebview() {
        return this.a.getWebView();
    }

    public void setNeedfireNativeEvent(String str, boolean z) {
        this.f = str;
        this.e = z;
        AlibcLogger.e("AlibcJsCallbackContext", "setNeedfireNativeEvent : ".concat(String.valueOf(str)));
    }

    public void success(AlibcJsResult alibcJsResult) {
        a(this.a, String.format("javascript:window.AliBCBridge&&window.AliBCBridge.onSuccess(%s,'%%s');", new Object[]{this.b}), alibcJsResult.toJsonString());
    }
}
