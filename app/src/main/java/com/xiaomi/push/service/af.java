package com.xiaomi.push.service;

import android.content.Context;
import android.os.Messenger;
import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.slim.b;
import com.xiaomi.smack.l;
import com.xiaomi.smack.packet.d;
import com.xiaomi.xmpush.thrift.a;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.au;
import com.xiaomi.xmpush.thrift.x;
import java.nio.ByteBuffer;
import org.apache.thrift.f;

final class af {
    static b a(XMPushService xMPushService, byte[] bArr) {
        com.xiaomi.xmpush.thrift.af afVar = new com.xiaomi.xmpush.thrift.af();
        try {
            au.a(afVar, bArr);
            return a(t.a(xMPushService), (Context) xMPushService, afVar);
        } catch (f e) {
            com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
            return null;
        }
    }

    static b a(s sVar, Context context, com.xiaomi.xmpush.thrift.af afVar) {
        try {
            b bVar = new b();
            bVar.a(5);
            bVar.c(sVar.a);
            bVar.b(a(afVar));
            bVar.a((String) "SECMSG", (String) "message");
            String str = sVar.a;
            afVar.g.b = str.substring(0, str.indexOf(AUScreenAdaptTool.PREFIX_ID));
            afVar.g.d = str.substring(str.indexOf("/") + 1);
            bVar.a(au.a(afVar), sVar.c);
            bVar.a(1);
            StringBuilder sb = new StringBuilder("try send mi push message. packagename:");
            sb.append(afVar.f);
            sb.append(" action:");
            sb.append(afVar.a);
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            return bVar;
        } catch (NullPointerException e) {
            com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
            return null;
        }
    }

    static com.xiaomi.xmpush.thrift.af a(String str, String str2) {
        ai aiVar = new ai();
        aiVar.b(str2);
        aiVar.c("package uninstalled");
        aiVar.a(d.j());
        aiVar.a(false);
        return a(str, str2, aiVar, a.Notification);
    }

    static <T extends org.apache.thrift.a<T, ?>> com.xiaomi.xmpush.thrift.af a(String str, String str2, T t, a aVar) {
        byte[] a = au.a(t);
        com.xiaomi.xmpush.thrift.af afVar = new com.xiaomi.xmpush.thrift.af();
        x xVar = new x();
        xVar.a = 5;
        xVar.b = "fakeid";
        afVar.a(xVar);
        afVar.a(ByteBuffer.wrap(a));
        afVar.a(aVar);
        afVar.c(true);
        afVar.b(str);
        afVar.a(false);
        afVar.a(str2);
        return afVar;
    }

    private static String a(com.xiaomi.xmpush.thrift.af afVar) {
        if (!(afVar.h == null || afVar.h.k == null)) {
            String str = afVar.h.k.get("ext_traffic_source_pkg");
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return afVar.f;
    }

    static void a(XMPushService xMPushService) {
        if (t.a(xMPushService.getApplicationContext()) != null) {
            aq.b a = t.a(xMPushService.getApplicationContext()).a(xMPushService);
            a(xMPushService, a);
            aq.a().a(a);
        }
    }

    static void a(XMPushService xMPushService, aq.b bVar) {
        bVar.a((Messenger) null);
        bVar.a((aq.b.a) new ag(xMPushService));
    }

    static void a(XMPushService xMPushService, com.xiaomi.xmpush.thrift.af afVar) {
        com.xiaomi.smack.a h = xMPushService.h();
        if (h == null) {
            throw new l((String) "try send msg while connection is null.");
        } else if (h.b()) {
            b a = a(t.a(xMPushService), (Context) xMPushService, afVar);
            if (a != null) {
                h.b(a);
            }
        } else {
            throw new l((String) "Don't support XMPP connection.");
        }
    }

    static void a(XMPushService xMPushService, String str, byte[] bArr) {
        com.xiaomi.smack.a h = xMPushService.h();
        if (h == null) {
            throw new l((String) "try send msg while connection is null.");
        } else if (h.b()) {
            b a = a(xMPushService, bArr);
            if (a != null) {
                h.b(a);
            } else {
                w.a(xMPushService, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, "not a valid message");
            }
        } else {
            throw new l((String) "Don't support XMPP connection.");
        }
    }
}
