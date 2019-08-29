package com.alibaba.baichuan.android.trade.config;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;

class a implements Runnable {
    final /* synthetic */ AlibcConfig a;

    a(AlibcConfig alibcConfig) {
        this.a = alibcConfig;
    }

    public void run() {
        if (this.a.d != null) {
            this.a.d.a();
        }
        this.a.j.postDelayed(this.a.a, TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL);
    }
}
