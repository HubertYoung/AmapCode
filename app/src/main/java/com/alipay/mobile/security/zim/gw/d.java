package com.alipay.mobile.security.zim.gw;

import com.alipay.bis.common.service.facade.gw.zim.ZimInitGwResponse;

/* compiled from: JsonGwService */
final class d implements Runnable {
    final /* synthetic */ ZimInitGwResponse a;
    final /* synthetic */ c b;

    d(c cVar, ZimInitGwResponse zimInitGwResponse) {
        this.b = cVar;
        this.a = zimInitGwResponse;
    }

    public final void run() {
        if (this.b.b.mGwListener != null) {
            this.b.b.mGwListener.a(this.a);
        }
    }
}
