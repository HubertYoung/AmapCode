package com.ali.auth.third.ui.webview;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.ali.auth.third.core.message.Message;
import com.ali.auth.third.core.message.MessageUtils;
import com.ali.auth.third.core.model.SystemMessageConstants;
import com.ali.auth.third.core.trace.SDKLogger;
import java.lang.reflect.Method;

public class BridgeWebChromeClient extends WebChromeClient {
    private static final String a = "BridgeWebChromeClient";
    /* access modifiers changed from: private */
    public static boolean b = (VERSION.SDK_INT >= 19);

    static class a implements Runnable {
        public WebView a;
        public String b;

        public a(WebView webView, String str) {
            this.a = webView;
            this.b = str;
        }

        @TargetApi(19)
        public void run() {
            try {
                if (BridgeWebChromeClient.b) {
                    try {
                        this.a.evaluateJavascript(this.b, null);
                        return;
                    } catch (Exception e) {
                        StringBuilder sb = new StringBuilder("fail to evaluate the script ");
                        sb.append(e.getMessage());
                        SDKLogger.e("ui", sb.toString(), e);
                    }
                }
                StringBuilder sb2 = new StringBuilder("javascript:");
                sb2.append(this.b);
                String sb3 = sb2.toString();
                if (this.a instanceof AuthWebView) {
                    ((AuthWebView) this.a).loadUrl(sb3);
                } else {
                    this.a.loadUrl(sb3);
                }
            } catch (Exception unused) {
            }
        }
    }

    private d a(String str) {
        Uri parse = Uri.parse(str);
        String host = parse.getHost();
        int port = parse.getPort();
        String lastPathSegment = parse.getLastPathSegment();
        parse.getQuery();
        int indexOf = str.indexOf("?");
        String substring = indexOf == -1 ? null : str.substring(indexOf + 1);
        d dVar = new d();
        dVar.b = lastPathSegment;
        dVar.a = host;
        dVar.c = substring;
        dVar.d = port;
        return dVar;
    }

    private void a(WebView webView, String str) {
        try {
            int indexOf = str.indexOf(58, 9);
            webView.post(new a(webView, String.format("window.WindVane&&window.WindVane.onFailure(%s,'{\"ret\":\"HY_NO_HANDLER\"');", new Object[]{str.substring(indexOf + 1, str.indexOf(47, indexOf))})));
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("fail to handler windvane request, the error message is ");
            sb.append(e.getMessage());
            SDKLogger.e("ui", sb.toString(), e);
        }
    }

    public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
        return false;
    }

    public final boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        String str4;
        Message message;
        String str5;
        StringBuilder sb;
        int i;
        Object[] objArr;
        if (str3 == null) {
            return false;
        }
        if (str3.equals("wv_hybrid:")) {
            a(webView, str2);
        } else if (!TextUtils.equals(str3, "hv_hybrid:") || !(webView instanceof AuthWebView)) {
            return false;
        } else {
            AuthWebView authWebView = (AuthWebView) webView;
            d a2 = a(str2);
            com.ali.auth.third.ui.context.a aVar = new com.ali.auth.third.ui.context.a();
            aVar.b = a2.d;
            aVar.a = authWebView;
            Object bridgeObj = authWebView.getBridgeObj(a2.a);
            if (bridgeObj == null) {
                i = 10000;
                objArr = new Object[]{a2.a};
            } else {
                try {
                    Method method = bridgeObj.getClass().getMethod(a2.b, new Class[]{com.ali.auth.third.ui.context.a.class, String.class});
                    if (method.isAnnotationPresent(BridgeMethod.class)) {
                        try {
                            Object[] objArr2 = new Object[2];
                            objArr2[0] = aVar;
                            objArr2[1] = TextUtils.isEmpty(a2.c) ? bny.c : a2.c;
                            method.invoke(bridgeObj, objArr2);
                        } catch (Exception e) {
                            message = MessageUtils.createMessage(10010, e.getMessage());
                            str4 = a;
                            sb = new StringBuilder();
                            sb.append(message.toString());
                            sb.append(",");
                            str5 = e.toString();
                            sb.append(str5);
                            SDKLogger.e(str4, sb.toString());
                            aVar.a(message.code, message.message);
                            jsPromptResult.confirm("");
                            return true;
                        }
                    } else {
                        i = SystemMessageConstants.JS_BRIDGE_ANNOTATION_NOT_PRESENT;
                        objArr = new Object[]{a2.a, a2.b};
                    }
                } catch (NoSuchMethodException e2) {
                    message = MessageUtils.createMessage(SystemMessageConstants.JS_BRIDGE_METHOD_NOT_FOUND, a2.a, a2.b);
                    str4 = a;
                    sb = new StringBuilder();
                    sb.append(message);
                    sb.append(",");
                    str5 = e2.toString();
                    sb.append(str5);
                    SDKLogger.e(str4, sb.toString());
                    aVar.a(message.code, message.message);
                    jsPromptResult.confirm("");
                    return true;
                }
            }
            Message createMessage = MessageUtils.createMessage(i, objArr);
            SDKLogger.e(a, createMessage.toString());
            aVar.a(createMessage.code, createMessage.message);
        }
        jsPromptResult.confirm("");
        return true;
    }
}
