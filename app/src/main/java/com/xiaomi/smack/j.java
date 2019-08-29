package com.xiaomi.smack;

import com.xiaomi.push.service.XMPushService.h;

class j extends h {
    final /* synthetic */ int b;
    final /* synthetic */ Exception c;
    final /* synthetic */ h d;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    j(h hVar, int i, int i2, Exception exc) {
        // this.d = hVar;
        // this.b = i2;
        // this.c = exc;
        super(i);
    }

    public void a() {
        this.d.s.a(this.b, this.c);
    }

    public String b() {
        StringBuilder sb = new StringBuilder("shutdown the connection. ");
        sb.append(this.b);
        sb.append(", ");
        sb.append(this.c);
        return sb.toString();
    }
}
