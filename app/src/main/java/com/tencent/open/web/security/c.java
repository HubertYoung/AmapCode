package com.tencent.open.web.security;

import android.webkit.WebView;
import com.tencent.open.a.C0065a;
import com.tencent.open.a.f;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class c extends C0065a {
    private String d;

    public c(WebView webView, long j, String str, String str2) {
        super(webView, j, str);
        this.d = str2;
    }

    public void a(Object obj) {
        f.a("openSDK_LOG.SecureJsListener", "-->onComplete, result: ".concat(String.valueOf(obj)));
    }

    public void a() {
        f.b("openSDK_LOG.SecureJsListener", "-->onNoMatchMethod...");
    }

    public void a(String str) {
        f.a("openSDK_LOG.SecureJsListener", "-->onCustomCallback, js: ".concat(String.valueOf(str)));
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("result", !com.tencent.open.c.c.a ? -4 : 0);
            jSONObject.put("sn", this.b);
            jSONObject.put("data", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        b(jSONObject.toString());
    }

    private void b(String str) {
        WebView webView = (WebView) this.a.get();
        if (webView != null) {
            StringBuffer stringBuffer = new StringBuffer("javascript:");
            stringBuffer.append("if(!!");
            stringBuffer.append(this.d);
            stringBuffer.append("){");
            stringBuffer.append(this.d);
            stringBuffer.append("(");
            stringBuffer.append(str);
            stringBuffer.append(")}");
            String stringBuffer2 = stringBuffer.toString();
            f.a("openSDK_LOG.SecureJsListener", "-->callback, callback: ".concat(String.valueOf(stringBuffer2)));
            webView.loadUrl(stringBuffer2);
        }
    }
}
