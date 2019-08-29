package com.taobao.applink.util;

import android.webkit.WebView;
import com.taobao.applink.d.a;
import com.taobao.applink.exception.TBAppLinkException;
import com.taobao.applink.f.a.c;

final class b implements c {
    final /* synthetic */ WebView a;

    b(WebView webView) {
        this.a = webView;
    }

    public final void a(String str, com.taobao.applink.f.a.b bVar) {
        try {
            a.a(this.a.getContext(), com.taobao.applink.e.a.a(str), bVar);
        } catch (TBAppLinkException unused) {
        }
    }
}
