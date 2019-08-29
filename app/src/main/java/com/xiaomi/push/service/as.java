package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.h;

class as extends h {
    final /* synthetic */ c b;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    as(c cVar, int i) {
        // this.b = cVar;
        super(i);
    }

    public void a() {
        if (this.b.b == this.b.a.t) {
            StringBuilder sb = new StringBuilder("clean peer, chid = ");
            sb.append(this.b.a.h);
            b.b(sb.toString());
            this.b.a.t = null;
        }
    }

    public String b() {
        return "clear peer job";
    }
}
