package com.xiaomi.push.service;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.module.a;
import java.util.Iterator;

class bs implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ br d;

    bs(br brVar, Context context, String str, String str2) {
        this.d = brVar;
        this.a = context;
        this.b = str;
        this.c = str2;
    }

    public void run() {
        StringBuilder sb;
        String str;
        Iterator<a> it = j.a(this.a).c(this.b).iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (XMPushService.a(next.e(), this.c)) {
                if (next.a() >= System.currentTimeMillis()) {
                    byte[] d2 = next.d();
                    if (d2 == null) {
                        str = "Geo canBeShownMessage content null";
                    } else {
                        Intent a2 = x.a(d2, System.currentTimeMillis());
                        if (a2 == null) {
                            str = "Geo canBeShownMessage intent null";
                        } else {
                            x.a(this.d.a, (String) null, d2, a2, true);
                            if (j.a((Context) this.d.a).a(next.b()) == 0) {
                                sb = new StringBuilder("show some exit geofence message. then remove this message failed. message_id:");
                            }
                        }
                    }
                    b.a(str);
                } else if (j.a(this.a).a(next.b()) == 0) {
                    sb = new StringBuilder("XMPushService remove some geofence message failed. message_id:");
                }
                sb.append(next.b());
                str = sb.toString();
                b.a(str);
            }
        }
    }
}
