package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.channel.commonutils.misc.h.a;
import com.xiaomi.push.service.an;
import com.xiaomi.push.service.ao;
import com.xiaomi.xmpush.thrift.ab;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.au;
import com.xiaomi.xmpush.thrift.h;
import com.xiaomi.xmpush.thrift.r;
import com.xiaomi.xmpush.thrift.u;

public class aa extends a {
    private Context a;

    public aa(Context context) {
        this.a = context;
    }

    public int a() {
        return 2;
    }

    public void run() {
        an a2 = an.a(this.a);
        ab abVar = new ab();
        abVar.a(ao.a(a2, h.MISC_CONFIG));
        abVar.b(ao.a(a2, h.PLUGIN_CONFIG));
        ai aiVar = new ai("-1", false);
        aiVar.c(r.DailyCheckClientConfig.W);
        aiVar.a(au.a(abVar));
        aj.a(this.a).a(aiVar, com.xiaomi.xmpush.thrift.a.Notification, (u) null);
    }
}
