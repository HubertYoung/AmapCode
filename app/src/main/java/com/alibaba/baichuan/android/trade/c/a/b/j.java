package com.alibaba.baichuan.android.trade.c.a.b;

import android.webkit.WebView;
import com.alibaba.baichuan.android.trade.adapter.alipay.AlibcAlipay;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;

public class j {
    private static final String a = "j";
    private String[] b;
    private AlibcTradeCallback c;

    public j(AlibcTradeCallback alibcTradeCallback, String[] strArr) {
        this.c = alibcTradeCallback;
        this.b = strArr;
    }

    private boolean b(WebView webView, String str) {
        return AlibcAlipay.getInstance().openAlipay(this.c, webView, str);
    }

    public boolean a(WebView webView, String str) {
        StringBuilder sb = new StringBuilder("PayOverrideHandler.handle()--进入支付流程: url:");
        sb.append(str);
        sb.append(" webview:");
        sb.append(webView != null ? webView.toString() : null);
        AlibcLogger.i("PayOverrideHandler", sb.toString());
        return b(webView, str);
    }
}
