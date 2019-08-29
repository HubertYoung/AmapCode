package com.taobao.applink.f;

import android.os.Looper;
import android.webkit.WebView;
import com.taobao.applink.f.a.a;
import com.taobao.applink.f.a.c;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class b implements a {
    long a = 0;
    /* access modifiers changed from: private */
    public Map b = new HashMap();
    /* access modifiers changed from: private */
    public Map c = new HashMap();
    /* access modifiers changed from: private */
    public c d = new a();
    private List e = new ArrayList();

    /* access modifiers changed from: private */
    public void b(WebView webView, f fVar) {
        if (this.e != null) {
            this.e.add(fVar);
        } else {
            a(webView, fVar);
        }
    }

    public void a(WebView webView) {
        if (this.e != null) {
            for (f a2 : this.e) {
                a(webView, a2);
            }
            this.e = null;
        }
    }

    public void a(WebView webView, f fVar) {
        String format = String.format("javascript:TBAppLinkJsBridge._handleMessageFromNative(\"%s\");", new Object[]{fVar.f().replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2").replaceAll("(?<=[^\\\\])(\")", "\\\\\"")});
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            webView.loadUrl(format);
        }
    }

    public void a(WebView webView, String str) {
        String a2 = com.taobao.applink.util.a.a(str);
        com.taobao.applink.f.a.b bVar = (com.taobao.applink.f.a.b) this.b.get(a2);
        String c2 = com.taobao.applink.util.a.c(str);
        if (bVar != null) {
            bVar.a(c2);
            this.b.remove(a2);
        }
    }

    public void a(WebView webView, String str, com.taobao.applink.f.a.b bVar) {
        webView.loadUrl(str);
        this.b.put(com.taobao.applink.util.a.b(str), bVar);
    }

    public void a(WebView webView, String str, c cVar) {
        if (cVar != null) {
            this.c.put(str, cVar);
        }
    }

    public void b(WebView webView) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            a(webView, (String) "javascript:TBAppLinkJsBridge._fetchQueue();", (com.taobao.applink.f.a.b) new c(this, webView));
        }
    }
}
