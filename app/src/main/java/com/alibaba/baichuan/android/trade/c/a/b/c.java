package com.alibaba.baichuan.android.trade.c.a.b;

import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.c.a.a.b.b;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;

public class c implements b {
    j a;

    public boolean a(com.alibaba.baichuan.android.trade.c.a.a.b.c cVar) {
        if (!AlibcConfig.getInstance().shouldUseAlipay(true)) {
            return false;
        }
        this.a = new j(cVar.e.c.e, AlibcContext.TRADE_APLIPAY_URLS);
        return this.a.a(cVar.d, cVar.d());
    }
}
