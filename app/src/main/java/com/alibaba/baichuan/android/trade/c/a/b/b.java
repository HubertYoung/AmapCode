package com.alibaba.baichuan.android.trade.c.a.b;

import android.app.Activity;
import android.webkit.WebView;
import com.alibaba.baichuan.android.trade.c.a.a.b.c;
import com.alibaba.baichuan.android.trade.model.ResultType;
import com.alibaba.baichuan.android.trade.model.TradeResult;

class b implements Runnable {
    final /* synthetic */ c a;
    final /* synthetic */ WebView b;
    final /* synthetic */ a c;

    b(a aVar, c cVar, WebView webView) {
        this.c = aVar;
        this.a = cVar;
        this.b = webView;
    }

    public void run() {
        TradeResult tradeResult = new TradeResult();
        tradeResult.resultType = ResultType.TYPECART;
        this.a.e.c.e.onTradeSuccess(tradeResult);
        if (this.b != null && (this.b.getContext() instanceof Activity)) {
            ((Activity) this.b.getContext()).finish();
        }
    }
}
