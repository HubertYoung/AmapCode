package com.xiaomi.smack;

import com.xiaomi.push.service.XMPushService.h;

class i extends h {
    final /* synthetic */ long b;
    final /* synthetic */ h c;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    i(h hVar, int i, long j) {
        // this.c = hVar;
        // this.b = j;
        super(i);
    }

    public void a() {
        Thread.yield();
        if (this.c.l() && !this.c.a(this.b)) {
            this.c.s.a(22, (Exception) null);
            this.c.s.a(true);
        }
    }

    public String b() {
        StringBuilder sb = new StringBuilder("check the ping-pong.");
        sb.append(this.b);
        return sb.toString();
    }
}
