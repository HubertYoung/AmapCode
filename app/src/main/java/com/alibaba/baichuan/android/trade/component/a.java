package com.alibaba.baichuan.android.trade.component;

import android.app.Activity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alibaba.baichuan.android.trade.c.b.d;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.Map;

class a implements Runnable {
    final /* synthetic */ Activity a;
    final /* synthetic */ com.alibaba.baichuan.android.trade.b.a b;
    final /* synthetic */ WebView c;
    final /* synthetic */ Map d;
    final /* synthetic */ WebViewClient e;
    final /* synthetic */ WebChromeClient f;
    final /* synthetic */ String g;
    final /* synthetic */ Map h;
    final /* synthetic */ AlibcComponent i;

    a(AlibcComponent alibcComponent, Activity activity, com.alibaba.baichuan.android.trade.b.a aVar, WebView webView, Map map, WebViewClient webViewClient, WebChromeClient webChromeClient, String str, Map map2) {
        this.i = alibcComponent;
        this.a = activity;
        this.b = aVar;
        this.c = webView;
        this.d = map;
        this.e = webViewClient;
        this.f = webChromeClient;
        this.g = str;
        this.h = map2;
    }

    public void run() {
        d dVar = new d(this.a, this.b, this.c, this.d, this.e, this.f, true);
        String a2 = AlibcComponent.a;
        StringBuilder sb = new StringBuilder("第三方 WebView首次加载的url=");
        sb.append(this.g);
        AlibcLogger.d(a2, sb.toString());
        if (this.h != null) {
            dVar.a(this.g, this.h);
        } else {
            dVar.loadUrl(this.g);
        }
    }
}
