package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.channel.commonutils.android.g;
import com.xiaomi.channel.commonutils.misc.h.a;
import com.xiaomi.push.service.p;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.r;
import com.xiaomi.xmpush.thrift.u;
import java.util.HashMap;
import java.util.Map;

public class at extends a {
    private Context a;

    public at(Context context) {
        this.a = context;
    }

    public int a() {
        return 10;
    }

    public void run() {
        ai aiVar = new ai(MiPushClient.generatePacketID(), false);
        c a2 = c.a(this.a);
        aiVar.c(r.SyncMIID.W);
        aiVar.b(a2.c());
        aiVar.d(this.a.getPackageName());
        HashMap hashMap = new HashMap();
        g.a((Map<String, String>) hashMap, (String) Constants.EXTRA_KEY_MIID, p.a(this.a).c());
        aiVar.h = hashMap;
        aj.a(this.a).a(aiVar, com.xiaomi.xmpush.thrift.a.Notification, true, (u) null);
    }
}
