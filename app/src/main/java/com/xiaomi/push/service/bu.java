package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService.h;

class bu extends h {
    final /* synthetic */ XMPushService b;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    bu(XMPushService xMPushService, int i) {
        // this.b = xMPushService;
        super(i);
    }

    public void a() {
        if (this.b.k != null) {
            this.b.k.i();
            this.b.k = null;
        }
    }

    public String b() {
        return "disconnect for disable push";
    }
}
