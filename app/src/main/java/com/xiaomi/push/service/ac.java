package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.h;
import com.xiaomi.smack.l;
import com.xiaomi.xmpush.thrift.af;

final class ac extends h {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ af c;
    final /* synthetic */ String d;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    ac(int i, XMPushService xMPushService, af afVar, String str) {
        // this.b = xMPushService;
        // this.c = afVar;
        // this.d = str;
        super(i);
    }

    public final void a() {
        try {
            af a = x.a((Context) this.b, this.c);
            a.m().a("absent_target_package", this.d);
            af.a(this.b, a);
        } catch (l e) {
            b.a((Throwable) e);
            this.b.a(10, (Exception) e);
        }
    }

    public final String b() {
        return "send app absent ack message for message.";
    }
}
