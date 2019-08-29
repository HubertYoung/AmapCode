package com.xiaomi.slim;

import android.text.TextUtils;
import com.google.protobuf.micro.a;
import com.google.protobuf.micro.d;
import com.xiaomi.push.protobuf.b.C0082b;
import com.xiaomi.push.protobuf.b.j;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.aq;
import com.xiaomi.push.service.ax;
import com.xiaomi.push.service.ba;
import com.xiaomi.smack.a.C0086a;
import com.xiaomi.smack.b;
import com.xiaomi.smack.h;
import com.xiaomi.smack.l;
import com.xiaomi.smack.util.g;

public class f extends h {
    private Thread w;
    /* access modifiers changed from: private */
    public c x;
    private d y;
    private byte[] z;

    public f(XMPushService xMPushService, b bVar) {
        super(xMPushService, bVar);
    }

    private b c(boolean z2) {
        b bVar = new b();
        bVar.a((String) "PING", (String) null);
        bVar.a(z2 ? "1" : "0");
        j jVar = new j();
        byte[] a = d().a();
        if (a != null) {
            try {
                jVar.a(C0082b.b(a));
            } catch (d unused) {
            }
        }
        byte[] c = com.xiaomi.stats.h.c();
        if (c != null) {
            jVar.a(a.a(c));
        }
        bVar.a(jVar.c(), (String) null);
        return bVar;
    }

    private void x() {
        try {
            this.x = new c(this.q.getInputStream(), this);
            this.y = new d(this.q.getOutputStream(), this);
            StringBuilder sb = new StringBuilder("Blob Reader (");
            sb.append(this.l);
            sb.append(")");
            this.w = new g(this, sb.toString());
            this.w.start();
        } catch (Exception e) {
            throw new l("Error to init reader and writer", e);
        }
    }

    public synchronized void a(int i, Exception exc) {
        if (this.x != null) {
            this.x.b();
            this.x = null;
        }
        if (this.y != null) {
            try {
                this.y.b();
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
            }
            this.y = null;
        }
        this.z = null;
        super.a(i, exc);
    }

    public synchronized void a(aq.b bVar) {
        a.a(bVar, s(), (com.xiaomi.smack.a) this);
    }

    /* access modifiers changed from: 0000 */
    public void a(b bVar) {
        if (bVar != null) {
            if (bVar.d()) {
                StringBuilder sb = new StringBuilder("[Slim] RCV blob chid=");
                sb.append(bVar.c());
                sb.append("; id=");
                sb.append(bVar.h());
                sb.append("; errCode=");
                sb.append(bVar.e());
                sb.append("; err=");
                sb.append(bVar.f());
                com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            }
            if (bVar.c() == 0) {
                if ("PING".equals(bVar.a())) {
                    StringBuilder sb2 = new StringBuilder("[Slim] RCV ping id=");
                    sb2.append(bVar.h());
                    com.xiaomi.channel.commonutils.logger.b.a(sb2.toString());
                    w();
                } else if ("CLOSE".equals(bVar.a())) {
                    c(13, null);
                }
            }
            for (C0086a a : this.g.values()) {
                a.a(bVar);
            }
        }
    }

    @Deprecated
    public void a(com.xiaomi.smack.packet.d dVar) {
        b(b.a(dVar, (String) null));
    }

    public synchronized void a(String str, String str2) {
        a.a(str, str2, (com.xiaomi.smack.a) this);
    }

    public void a(boolean z2) {
        if (this.y != null) {
            b c = c(z2);
            StringBuilder sb = new StringBuilder("[Slim] SND ping id=");
            sb.append(c.h());
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            b(c);
            v();
            return;
        }
        throw new l((String) "The BlobWriter is null.");
    }

    public void a(b[] bVarArr) {
        for (b b : bVarArr) {
            b(b);
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized byte[] a() {
        if (this.z == null && !TextUtils.isEmpty(this.j)) {
            String e = ba.e();
            StringBuilder sb = new StringBuilder();
            sb.append(this.j.substring(this.j.length() / 2));
            sb.append(e.substring(e.length() / 2));
            this.z = ax.a(this.j.getBytes(), sb.toString().getBytes());
        }
        return this.z;
    }

    public void b(b bVar) {
        if (this.y != null) {
            try {
                int a = this.y.a(bVar);
                this.o = System.currentTimeMillis();
                String i = bVar.i();
                if (!TextUtils.isEmpty(i)) {
                    g.a(this.n, i, (long) a, false, System.currentTimeMillis());
                }
                for (C0086a a2 : this.h.values()) {
                    a2.a(bVar);
                }
            } catch (Exception e) {
                throw new l((Throwable) e);
            }
        } else {
            throw new l((String) "the writer is null.");
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(com.xiaomi.smack.packet.d dVar) {
        if (dVar != null) {
            for (C0086a a : this.g.values()) {
                a.a(dVar);
            }
        }
    }

    public boolean b() {
        return true;
    }

    public synchronized void c() {
        x();
        this.y.a();
    }
}
