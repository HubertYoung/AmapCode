package com.xiaomi.push.service;

import com.xiaomi.push.protobuf.b.C0082b;
import com.xiaomi.smack.b;
import com.xiaomi.smack.e;
import java.util.Map;

class bo extends b {
    final /* synthetic */ XMPushService a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    bo(XMPushService xMPushService, Map map, int i, String str, e eVar) {
        // this.a = xMPushService;
        super(map, i, str, eVar);
    }

    public byte[] a() {
        try {
            C0082b bVar = new C0082b();
            bVar.a(ba.a().c());
            return bVar.c();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("getOBBString err: ");
            sb.append(e.toString());
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            return null;
        }
    }
}
