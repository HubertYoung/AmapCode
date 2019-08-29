package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.h;
import com.xiaomi.smack.l;
import com.xiaomi.xmpush.thrift.af;

final class ae extends h {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ af c;
    final /* synthetic */ boolean d;
    final /* synthetic */ boolean e;
    final /* synthetic */ boolean f;
    final /* synthetic */ boolean g;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    ae(int i, XMPushService xMPushService, af afVar, boolean z, boolean z2, boolean z3, boolean z4) {
        // this.b = xMPushService;
        // this.c = afVar;
        // this.d = z;
        // this.e = z2;
        // this.f = z3;
        // this.g = z4;
        super(i);
    }

    public final void a() {
        try {
            af a = x.a((Context) this.b, this.c, this.d, this.e, this.f);
            if (this.g) {
                a.m().a("permission_to_location", aw.b);
            }
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
