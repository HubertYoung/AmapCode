package com.alibaba.baichuan.android.trade.adapter.alipay;

import android.app.Activity;
import android.webkit.WebView;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.baichuan.android.trade.utils.a.a;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;

class b implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ AlibcTradeCallback b;
    final /* synthetic */ WebView c;
    final /* synthetic */ AlibcAlipay d;

    b(AlibcAlipay alibcAlipay, String str, AlibcTradeCallback alibcTradeCallback, WebView webView) {
        this.d = alibcAlipay;
        this.a = str;
        this.b = alibcTradeCallback;
        this.c = webView;
    }

    public void run() {
        a a2 = com.alibaba.baichuan.android.trade.utils.a.b.a(this.a);
        StringBuilder sb = new StringBuilder("alipay支付失败，信息为：");
        sb.append(a2 != null ? a2.c : null);
        AlibcLogger.i("AlibcAlipay", sb.toString());
        this.d.a((String) UserTrackerConstants.ERRCODE_PAY_FAIL, a2 != null ? a2.c : UserTrackerConstants.EM_PAY_FAILURE);
        if (this.b != null) {
            AlibcTradeCallback alibcTradeCallback = this.b;
            StringBuilder sb2 = new StringBuilder("alipay支付失败，信息为：");
            sb2.append(a2.c);
            alibcTradeCallback.onFailure(10010, sb2.toString());
        }
        if (this.c != null && AlibcContext.MY_ORDERS_URL != null) {
            if (!this.c.getUrl().contains(AlibcContext.MY_ORDERS_URL)) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(AlibcContext.MY_ORDERS_URL);
                sb3.append(AlibcMyOrdersPage.TAB_CODE);
                this.c.loadUrl(String.format(sb3.toString(), new Object[]{AlibcMyOrdersPage.ORDER_TYPE[1]}));
            } else if (this.c.getUrl().contains(AlibcContext.MY_ORDERS_URL)) {
                this.c.reload();
            } else {
                if (this.c.getContext() instanceof Activity) {
                    ((Activity) this.c.getContext()).finish();
                }
            }
        }
    }
}
