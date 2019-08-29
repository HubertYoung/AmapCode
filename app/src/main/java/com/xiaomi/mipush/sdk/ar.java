package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.android.a;
import com.xiaomi.channel.commonutils.android.f;
import com.xiaomi.channel.commonutils.android.g;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.string.d;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.r;
import com.xiaomi.xmpush.thrift.u;
import java.util.HashMap;
import java.util.Map;

final class ar implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ boolean b;

    ar(Context context, boolean z) {
        this.a = context;
        this.b = z;
    }

    public final void run() {
        Map<String, String> map;
        String str;
        String b2;
        b.a((String) "do sync info");
        ai aiVar = new ai(MiPushClient.generatePacketID(), false);
        c a2 = c.a(this.a);
        aiVar.c(r.SyncInfo.W);
        aiVar.b(a2.c());
        aiVar.d(this.a.getPackageName());
        aiVar.h = new HashMap();
        g.a(aiVar.h, (String) "app_version", a.a(this.a, this.a.getPackageName()));
        g.a(aiVar.h, (String) Constants.EXTRA_KEY_APP_VERSION_CODE, Integer.toString(a.b(this.a, this.a.getPackageName())));
        g.a(aiVar.h, (String) "push_sdk_vn", (String) "3_6_2");
        g.a(aiVar.h, (String) "push_sdk_vc", Integer.toString(30602));
        g.a(aiVar.h, (String) "token", a2.d());
        if (f.e()) {
            String a3 = d.a(com.xiaomi.channel.commonutils.android.d.b(this.a));
            String d = com.xiaomi.channel.commonutils.android.d.d(this.a);
            if (!TextUtils.isEmpty(d)) {
                StringBuilder sb = new StringBuilder();
                sb.append(a3);
                sb.append(",");
                sb.append(d);
                a3 = sb.toString();
            }
            g.a(aiVar.h, (String) Constants.EXTRA_KEY_IMEI_MD5, a3);
        }
        g.a(aiVar.h, (String) Constants.EXTRA_KEY_REG_ID, a2.e());
        g.a(aiVar.h, (String) Constants.EXTRA_KEY_REG_SECRET, a2.f());
        g.a(aiVar.h, (String) Constants.EXTRA_KEY_ACCEPT_TIME, MiPushClient.getAcceptTime(this.a).replace(",", "-"));
        if (this.b) {
            g.a(aiVar.h, (String) Constants.EXTRA_KEY_ALIASES_MD5, aq.c(MiPushClient.getAllAlias(this.a)));
            g.a(aiVar.h, (String) Constants.EXTRA_KEY_TOPICS_MD5, aq.c(MiPushClient.getAllTopic(this.a)));
            map = aiVar.h;
            str = Constants.EXTRA_KEY_ACCOUNTS_MD5;
            b2 = aq.c(MiPushClient.getAllUserAccount(this.a));
        } else {
            g.a(aiVar.h, (String) Constants.EXTRA_KEY_ALIASES, aq.d(MiPushClient.getAllAlias(this.a)));
            g.a(aiVar.h, (String) Constants.EXTRA_KEY_TOPICS, aq.d(MiPushClient.getAllTopic(this.a)));
            map = aiVar.h;
            str = Constants.EXTRA_KEY_ACCOUNTS;
            b2 = aq.d(MiPushClient.getAllUserAccount(this.a));
        }
        g.a(map, str, b2);
        aj.a(this.a).a(aiVar, com.xiaomi.xmpush.thrift.a.Notification, false, (u) null);
    }
}
