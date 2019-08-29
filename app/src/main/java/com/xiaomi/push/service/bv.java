package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.h;
import com.xiaomi.smack.l;

class bv extends h {
    final /* synthetic */ String b;
    final /* synthetic */ byte[] c;
    final /* synthetic */ XMPushService d;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    bv(XMPushService xMPushService, int i, String str, byte[] bArr) {
        // this.d = xMPushService;
        // this.b = str;
        // this.c = bArr;
        super(i);
    }

    public void a() {
        try {
            af.a(this.d, this.b, this.c);
        } catch (l e) {
            b.a((Throwable) e);
            this.d.a(10, (Exception) e);
        }
    }

    public String b() {
        return "send mi push message";
    }
}
