package com.xiaomi.push.service;

import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.h;
import com.xiaomi.xmpush.thrift.a;
import com.xiaomi.xmpush.thrift.af;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.au;
import com.xiaomi.xmpush.thrift.f;
import com.xiaomi.xmpush.thrift.u;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class o extends h {
    final /* synthetic */ String b;
    final /* synthetic */ List c;
    final /* synthetic */ String d;
    final /* synthetic */ n e;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    o(n nVar, int i, String str, List list, String str2) {
        // this.e = nVar;
        // this.b = str;
        // this.c = list;
        // this.d = str2;
        super(i);
    }

    public void a() {
        String a = this.e.a(this.b);
        ArrayList<ai> a2 = bf.a(this.c, this.b, a, 32768);
        if (a2 != null) {
            Iterator<ai> it = a2.iterator();
            while (it.hasNext()) {
                ai next = it.next();
                next.a("uploadWay", "longXMPushService");
                af a3 = af.a(this.b, a, next, a.Notification);
                if (!TextUtils.isEmpty(this.d) && !TextUtils.equals(this.b, this.d)) {
                    if (a3.m() == null) {
                        u uVar = new u();
                        uVar.a((String) "-1");
                        a3.a(uVar);
                    }
                    a3.m().b("ext_traffic_source_pkg", this.d);
                }
                this.e.a.a(this.b, au.a(a3), true);
            }
            for (f m : this.c) {
                StringBuilder sb = new StringBuilder("TinyData uploaded by TinyDataUploader.");
                sb.append(m.m());
                b.c(sb.toString());
            }
            return;
        }
        b.d("Get a null XmPushActionNotification list when TinyDataHelper.pack() in XMPushService.");
    }

    public String b() {
        return "Send tiny data.";
    }
}
