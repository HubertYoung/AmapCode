package com.xiaomi.push.service;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.service.aq.b.a;
import com.xiaomi.push.service.aq.c;

final class ag implements a {
    final /* synthetic */ XMPushService a;

    ag(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    public final void a(c cVar, c cVar2, int i) {
        if (cVar2 == c.binded) {
            w.a(this.a);
            w.b(this.a);
            return;
        }
        if (cVar2 == c.unbind) {
            w.a(this.a, ErrorCode.ERROR_SERVICE_UNAVAILABLE, " the push is not connected.");
        }
    }
}
