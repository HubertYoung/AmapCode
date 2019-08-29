package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.h;
import com.xiaomi.smack.l;
import com.xiaomi.xmpush.thrift.af;

final class ab extends h {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ af c;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    ab(int i, XMPushService xMPushService, af afVar) {
        // this.b = xMPushService;
        // this.c = afVar;
        super(i);
    }

    public final void a() {
        try {
            af a = x.a((Context) this.b, this.c);
            a.m().a("miui_message_unrecognized", "1");
            af.a(this.b, a);
        } catch (l e) {
            b.a((Throwable) e);
            this.b.a(10, (Exception) e);
        }
    }

    public final String b() {
        return "send ack message for unrecognized new miui message.";
    }
}
