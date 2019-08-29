package com.alipay.mobile.security.zim.gw;

import com.alipay.bis.common.service.facade.gw.zim.ZimValidateGwResponse;

/* compiled from: JsonGwService */
final class f implements Runnable {
    final /* synthetic */ ZimValidateGwResponse a;
    final /* synthetic */ e b;

    f(e eVar, ZimValidateGwResponse zimValidateGwResponse) {
        this.b = eVar;
        this.a = zimValidateGwResponse;
    }

    public final void run() {
        if (this.b.c.mGwListener != null) {
            this.b.c.mGwListener.a(this.a);
        }
    }
}
