package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.h;
import com.xiaomi.smack.l;
import com.xiaomi.xmpush.thrift.af;

final class ad extends h {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ af c;
    final /* synthetic */ String d;
    final /* synthetic */ String e;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    ad(int i, XMPushService xMPushService, af afVar, String str, String str2) {
        // this.b = xMPushService;
        // this.c = afVar;
        // this.d = str;
        // this.e = str2;
        super(i);
    }

    public final void a() {
        try {
            af a = x.a((Context) this.b, this.c);
            a.h.a("error", this.d);
            a.h.a("reason", this.e);
            af.a(this.b, a);
        } catch (l e2) {
            b.a((Throwable) e2);
            this.b.a(10, (Exception) e2);
        }
    }

    public final String b() {
        return "send wrong message ack for message.";
    }
}
