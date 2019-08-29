package com.xiaomi.push.service;

import android.util.Base64;
import com.xiaomi.channel.commonutils.android.j;
import com.xiaomi.channel.commonutils.misc.k.b;
import com.xiaomi.channel.commonutils.network.c;
import com.xiaomi.network.HttpUtils;
import com.xiaomi.push.protobuf.a.C0081a;
import com.xiaomi.push.service.ba.a;
import java.util.List;

class bb extends b {
    boolean a = false;
    final /* synthetic */ ba b;

    bb(ba baVar) {
        this.b = baVar;
    }

    public void b() {
        try {
            C0081a b2 = C0081a.b(Base64.decode(HttpUtils.a(j.a(), (String) "http://resolver.msg.xiaomi.net/psc/?t=a", (List<c>) null), 10));
            if (b2 != null) {
                this.b.c = b2;
                this.a = true;
                this.b.i();
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("fetch config failure: ");
            sb.append(e.getMessage());
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
        }
    }

    public void c() {
        a[] aVarArr;
        this.b.d = null;
        if (this.a) {
            synchronized (this.b) {
                aVarArr = (a[]) this.b.b.toArray(new a[this.b.b.size()]);
            }
            for (a a2 : aVarArr) {
                a2.a(this.b.c);
            }
        }
    }
}
