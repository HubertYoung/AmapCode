package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import com.xiaomi.channel.commonutils.android.d;
import com.xiaomi.channel.commonutils.android.g;
import com.xiaomi.channel.commonutils.misc.h;
import com.xiaomi.push.service.an;
import com.xiaomi.push.service.p;
import com.xiaomi.push.service.p.a;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.r;
import com.xiaomi.xmpush.thrift.u;
import java.util.HashMap;
import java.util.Map;

public class as implements a {
    public as(Context context) {
        p.a(context).a((a) this);
    }

    private void b(String str, Context context) {
        ai aiVar = new ai();
        aiVar.c(r.ClientMIIDUpdate.W);
        aiVar.b(c.a(context).c());
        aiVar.a(MiPushClient.generatePacketID());
        HashMap hashMap = new HashMap();
        g.a((Map<String, String>) hashMap, (String) Constants.EXTRA_KEY_MIID, str);
        aiVar.a((Map<String, String>) hashMap);
        int b = d.b();
        if (b >= 0) {
            aiVar.i().put("space_id", Integer.toString(b));
        }
        aj.a(context).a(aiVar, com.xiaomi.xmpush.thrift.a.Notification, true, (u) null);
    }

    public void a(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        long j = sharedPreferences.getLong("last_sync_miid_time", -1);
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        int a = an.a(context).a(com.xiaomi.xmpush.thrift.g.SyncMIIDFrequency.a(), 21600);
        if (j == -1) {
            sharedPreferences.edit().putLong("last_sync_miid_time", currentTimeMillis).commit();
            return;
        }
        if (Math.abs(currentTimeMillis - j) > ((long) a)) {
            h.a(context).a((h.a) new at(context), a);
            sharedPreferences.edit().putLong("last_sync_miid_time", currentTimeMillis).commit();
        }
    }

    public void a(String str, Context context) {
        b(str, context);
    }
}
