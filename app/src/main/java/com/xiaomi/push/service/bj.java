package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService.h;
import com.xiaomi.slim.b;
import com.xiaomi.smack.f;
import com.xiaomi.smack.packet.d;

class bj implements f {
    final /* synthetic */ XMPushService a;

    bj(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    public void a(b bVar) {
        this.a.a((h) new c(bVar));
    }

    public void b(d dVar) {
        this.a.a((h) new j(dVar));
    }
}
