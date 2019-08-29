package com.alibaba.baichuan.android.trade.c.a.b;

import android.webkit.WebView;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.c.a.a.b.b;
import com.alibaba.baichuan.android.trade.c.a.a.b.c;
import com.alibaba.baichuan.android.trade.model.ResultType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.ui.activity.AlibcWebViewActivity;

public class a implements b {
    public boolean a(c cVar) {
        if (cVar.b() != 2) {
            return false;
        }
        WebView webView = cVar.d;
        if (webView == null) {
            return false;
        }
        if (webView.getContext() instanceof AlibcWebViewActivity) {
            if (!cVar.e.a()) {
                TradeResult tradeResult = new TradeResult();
                tradeResult.resultType = ResultType.TYPECART;
                ((AlibcWebViewActivity) webView.getContext()).a(tradeResult);
            } else {
                webView.goBack();
            }
        } else if (cVar.e.c.e != null) {
            AlibcContext.executorService.b(new b(this, cVar, webView));
        }
        return true;
    }
}
