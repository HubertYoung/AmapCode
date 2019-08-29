package com.alibaba.baichuan.android.trade.adapter.alipay;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.AliPayResult;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alipay.sdk.util.h;
import java.util.ArrayList;

class a implements Runnable {
    final /* synthetic */ WebView a;
    final /* synthetic */ AlibcTradeCallback b;
    final /* synthetic */ String c;
    final /* synthetic */ Uri d;
    final /* synthetic */ AlibcAlipay e;

    a(AlibcAlipay alibcAlipay, WebView webView, AlibcTradeCallback alibcTradeCallback, String str, Uri uri) {
        this.e = alibcAlipay;
        this.a = webView;
        this.b = alibcTradeCallback;
        this.c = str;
        this.d = uri;
    }

    public void run() {
        if (this.a.getContext() instanceof Activity) {
            String a2 = this.e.a(this.b, this.a, this.c);
            if (a2 == null || TextUtils.isEmpty(a2)) {
                this.e.a((String) UserTrackerConstants.ERRCODE_PAY_FAIL, (String) "result is null");
            } else {
                String a3 = this.e.a(a2.replace("{", "").replace(h.d, ""), (String) "resultStatus=", (String) ";memo");
                if (TextUtils.equals(AlibcAlipay.PAY_SUCCESS_CODE, a3)) {
                    ArrayList a4 = this.e.a(this.d);
                    AliPayResult a5 = this.e.a(a4);
                    if (a5 == null || (a5.payFailedOrders == null && a5.paySuccessOrders == null)) {
                        AlibcLogger.i("AlibcAlipay", "QueryOrderResultTask.asyncExecute()--pay success but empty order： message:为空的订单");
                        if (this.b != null) {
                            this.b.onFailure(10009, "alipay支付失败，信息为:empty orders");
                        }
                        return;
                    }
                    AlibcLogger.i("AlibcAlipay", "QueryOrderResultTask.asyncExecute()--pay success".concat(String.valueOf(a4)));
                    this.e.a();
                    this.e.a(this.b, this.a, a5);
                    return;
                }
                this.e.a(this.b, a3, this.a);
            }
        }
    }
}
