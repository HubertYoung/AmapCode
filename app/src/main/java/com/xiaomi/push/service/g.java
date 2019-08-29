package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.misc.h.a;
import java.util.Iterator;

public class g extends a {
    private XMPushService a;

    public g(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    public int a() {
        return 15;
    }

    public void run() {
        Iterator<com.xiaomi.push.service.module.a> it = j.a((Context) this.a).a().iterator();
        while (it.hasNext()) {
            com.xiaomi.push.service.module.a next = it.next();
            if (next.a() < System.currentTimeMillis()) {
                if (j.a((Context) this.a).a(next.b()) == 0) {
                    StringBuilder sb = new StringBuilder("GeofenceDbCleaner delete a geofence message failed message_id:");
                    sb.append(next.b());
                    b.a(sb.toString());
                }
                x.a(this.a, x.a(next.d()), false, false, true);
            }
        }
    }
}
