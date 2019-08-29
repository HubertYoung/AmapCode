package com.xiaomi.stats;

import com.xiaomi.channel.commonutils.network.d;
import com.xiaomi.channel.commonutils.stats.a.C0075a;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.ba;
import com.xiaomi.push.thrift.b;
import com.xiaomi.push.thrift.c;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.apache.thrift.protocol.e;

public class f {
    private String a;
    private boolean b = false;
    private int c;
    private long d;
    private e e;
    private com.xiaomi.channel.commonutils.stats.a f = com.xiaomi.channel.commonutils.stats.a.a();

    static class a {
        static final f a = new f();
    }

    private b a(C0075a aVar) {
        if (aVar.a != 0) {
            b f2 = f();
            f2.a(com.xiaomi.push.thrift.a.CHANNEL_STATS_COUNTER.a());
            f2.c(aVar.a);
            f2.c(aVar.b);
            return f2;
        } else if (aVar.c instanceof b) {
            return (b) aVar.c;
        } else {
            return null;
        }
    }

    public static f a() {
        return a.a;
    }

    public static e b() {
        e eVar;
        synchronized (a.a) {
            try {
                eVar = a.a.e;
            }
        }
        return eVar;
    }

    private void g() {
        if (this.b && System.currentTimeMillis() - this.d > ((long) this.c)) {
            this.b = false;
            this.d = 0;
        }
    }

    public void a(int i) {
        if (i > 0) {
            int i2 = i * 1000;
            if (i2 > 604800000) {
                i2 = 604800000;
            }
            if (this.c != i2 || !this.b) {
                this.b = true;
                this.d = System.currentTimeMillis();
                this.c = i2;
                StringBuilder sb = new StringBuilder("enable dot duration = ");
                sb.append(i2);
                sb.append(" start = ");
                sb.append(this.d);
                com.xiaomi.channel.commonutils.logger.b.c(sb.toString());
            }
        }
    }

    public synchronized void a(XMPushService xMPushService) {
        this.e = new e(xMPushService);
        this.a = "";
        ba.a().a((com.xiaomi.push.service.ba.a) new g(this));
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(b bVar) {
        this.f.a(bVar);
    }

    public boolean c() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        g();
        return this.b && this.f.b() > 0;
    }

    /* access modifiers changed from: 0000 */
    public synchronized c e() {
        c cVar;
        cVar = null;
        try {
            if (d()) {
                int i = 750;
                if (!d.e(this.e.a)) {
                    i = 375;
                }
                cVar = b(i);
            }
        }
        return cVar;
    }

    /* access modifiers changed from: 0000 */
    public synchronized b f() {
        b bVar;
        try {
            bVar = new b();
            bVar.a(d.k(this.e.a));
            bVar.a = 0;
            bVar.c = 1;
            bVar.d((int) (System.currentTimeMillis() / 1000));
            if (this.e.b != null) {
                bVar.e(this.e.b.g());
            }
        }
        return bVar;
    }

    private c b(int i) {
        ArrayList arrayList = new ArrayList();
        c cVar = new c(this.a, arrayList);
        if (!d.e(this.e.a)) {
            cVar.a(com.xiaomi.channel.commonutils.android.d.i(this.e.a));
        }
        org.apache.thrift.transport.b bVar = new org.apache.thrift.transport.b(i);
        e a2 = new org.apache.thrift.protocol.k.a().a(bVar);
        try {
            cVar.b(a2);
        } catch (org.apache.thrift.f unused) {
        }
        LinkedList<C0075a> c2 = this.f.c();
        while (c2.size() > 0) {
            try {
                b a3 = a(c2.getLast());
                if (a3 != null) {
                    a3.b(a2);
                }
                if (bVar.a.size() > i) {
                    break;
                }
                if (a3 != null) {
                    arrayList.add(a3);
                }
                c2.removeLast();
            } catch (NoSuchElementException | org.apache.thrift.f unused2) {
            }
        }
        return cVar;
    }
}
