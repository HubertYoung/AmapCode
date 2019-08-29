package com.alibaba.baichuan.android.trade;

import android.app.Activity;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alibaba.baichuan.android.trade.callback.AlibcTaokeTraceCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;

final class a implements AlibcTaokeTraceCallback {
    final /* synthetic */ AlibcBasePage a;
    final /* synthetic */ Activity b;
    final /* synthetic */ WebView c;
    final /* synthetic */ WebViewClient d;
    final /* synthetic */ WebChromeClient e;
    final /* synthetic */ com.alibaba.baichuan.android.trade.b.a f;
    final /* synthetic */ AlibcShowParams g;

    a(AlibcBasePage alibcBasePage, Activity activity, WebView webView, WebViewClient webViewClient, WebChromeClient webChromeClient, com.alibaba.baichuan.android.trade.b.a aVar, AlibcShowParams alibcShowParams) {
        this.a = alibcBasePage;
        this.b = activity;
        this.c = webView;
        this.d = webViewClient;
        this.e = webChromeClient;
        this.f = aVar;
        this.g = alibcShowParams;
    }

    public final void genTaokeUrl(String str) {
        String genOpenUrl = this.a.genOpenUrl();
        if (str == null || TextUtils.isEmpty(str)) {
            str = genOpenUrl;
        }
        AlibcTrade.b(this.b, this.c, this.d, this.e, this.a.getAddParamsUrl(str, AlibcTrade.d, this.f), this.a, this.f, this.g.isProxyWebview());
    }
}
