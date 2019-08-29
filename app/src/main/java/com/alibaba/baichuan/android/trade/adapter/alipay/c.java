package com.alibaba.baichuan.android.trade.adapter.alipay;

import android.app.Activity;
import android.webkit.WebView;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AliPayResult;
import com.alibaba.baichuan.android.trade.model.ResultType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.ui.activity.AlibcWebViewActivity;

class c implements Runnable {
    final /* synthetic */ AliPayResult a;
    final /* synthetic */ AlibcTradeCallback b;
    final /* synthetic */ WebView c;
    final /* synthetic */ AlibcAlipay d;

    c(AlibcAlipay alibcAlipay, AliPayResult aliPayResult, AlibcTradeCallback alibcTradeCallback, WebView webView) {
        this.d = alibcAlipay;
        this.a = aliPayResult;
        this.b = alibcTradeCallback;
        this.c = webView;
    }

    public void run() {
        TradeResult tradeResult = new TradeResult();
        tradeResult.resultType = ResultType.TYPEPAY;
        tradeResult.payResult = this.a;
        if (this.b != null) {
            this.b.onTradeSuccess(tradeResult);
        }
        if (this.c.getContext() instanceof AlibcWebViewActivity) {
            ((AlibcWebViewActivity) this.c.getContext()).finish();
            return;
        }
        if (this.c.getContext() instanceof Activity) {
            ((Activity) this.c.getContext()).finish();
        }
    }
}
