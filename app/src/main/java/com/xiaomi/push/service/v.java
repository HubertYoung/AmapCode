package com.xiaomi.push.service;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.service.XMPushService.h;
import com.xiaomi.push.service.aq.b;
import com.xiaomi.push.service.aq.c;
import com.xiaomi.smack.l;
import java.io.IOException;
import java.util.Collection;
import org.json.JSONException;

public class v extends h {
    private XMPushService b;
    private byte[] c;
    private String d;
    private String e;
    private String f;

    public v(XMPushService xMPushService, String str, String str2, String str3, byte[] bArr) {
        super(9);
        this.b = xMPushService;
        this.d = str;
        this.c = bArr;
        this.e = str2;
        this.f = str3;
    }

    public void a() {
        b bVar;
        s a = t.a(this.b);
        if (a == null) {
            try {
                a = t.a(this.b, this.d, this.e, this.f);
            } catch (IOException | JSONException e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
        if (a == null) {
            com.xiaomi.channel.commonutils.logger.b.d("no account for mipush");
            w.a(this.b, ErrorCode.ERROR_AUTHERICATION_ERROR, "no account.");
            return;
        }
        Collection<b> c2 = aq.a().c("5");
        if (c2.isEmpty()) {
            bVar = a.a(this.b);
            af.a(this.b, bVar);
            aq.a().a(bVar);
        } else {
            bVar = c2.iterator().next();
        }
        if (this.b.f()) {
            try {
                if (bVar.m == c.binded) {
                    af.a(this.b, this.d, this.c);
                    return;
                }
                if (bVar.m == c.unbind) {
                    XMPushService xMPushService = this.b;
                    XMPushService xMPushService2 = this.b;
                    xMPushService2.getClass();
                    xMPushService.a((h) new a(bVar));
                }
            } catch (l e3) {
                com.xiaomi.channel.commonutils.logger.b.a((Throwable) e3);
                this.b.a(10, (Exception) e3);
            }
        } else {
            this.b.a(true);
        }
    }

    public String b() {
        return "register app";
    }
}
