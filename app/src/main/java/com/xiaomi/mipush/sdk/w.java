package com.xiaomi.mipush.sdk;

import android.text.TextUtils;
import com.xiaomi.channel.commonutils.android.d;
import com.xiaomi.channel.commonutils.android.f;
import com.xiaomi.xmpush.thrift.a;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.u;
import java.util.HashMap;
import java.util.Map;

final class w implements Runnable {
    w() {
    }

    public final void run() {
        if (f.e() && d.b(MiPushClient.sContext) != null) {
            ai aiVar = new ai();
            aiVar.b(c.a(MiPushClient.sContext).c());
            aiVar.c("client_info_update");
            aiVar.a(MiPushClient.generatePacketID());
            aiVar.a((Map<String, String>) new HashMap<String,String>());
            String a = com.xiaomi.channel.commonutils.string.d.a(d.b(MiPushClient.sContext));
            String d = d.d(MiPushClient.sContext);
            if (!TextUtils.isEmpty(d)) {
                StringBuilder sb = new StringBuilder();
                sb.append(a);
                sb.append(",");
                sb.append(d);
                a = sb.toString();
            }
            aiVar.i().put(Constants.EXTRA_KEY_IMEI_MD5, a);
            int b = d.b();
            if (b >= 0) {
                aiVar.i().put("space_id", Integer.toString(b));
            }
            aj.a(MiPushClient.sContext).a(aiVar, a.Notification, false, (u) null);
        }
    }
}
