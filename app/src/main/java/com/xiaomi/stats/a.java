package com.xiaomi.stats;

import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.XMPushService.h;
import com.xiaomi.push.service.aq.b;
import com.xiaomi.push.service.aq.c;

class a implements com.xiaomi.push.service.aq.b.a {
    private XMPushService a;
    private b b;
    private com.xiaomi.smack.a c;
    private c d;
    private int e;
    private boolean f = false;

    a(XMPushService xMPushService, b bVar) {
        this.a = xMPushService;
        this.d = c.binding;
        this.b = bVar;
    }

    private void b() {
        this.b.b((com.xiaomi.push.service.aq.b.a) this);
    }

    /* access modifiers changed from: private */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void c() {
        /*
            r3 = this;
            r3.b()
            boolean r0 = r3.f
            if (r0 != 0) goto L_0x0008
            return
        L_0x0008:
            int r0 = r3.e
            r1 = 11
            if (r0 != r1) goto L_0x000f
            return
        L_0x000f:
            com.xiaomi.stats.f r0 = com.xiaomi.stats.f.a()
            com.xiaomi.push.thrift.b r0 = r0.f()
            int[] r1 = com.xiaomi.stats.c.a
            com.xiaomi.push.service.aq$c r2 = r3.d
            int r2 = r2.ordinal()
            r1 = r1[r2]
            switch(r1) {
                case 1: goto L_0x002e;
                case 2: goto L_0x005b;
                case 3: goto L_0x0025;
                default: goto L_0x0024;
            }
        L_0x0024:
            goto L_0x005b
        L_0x0025:
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.BIND_SUCCESS
        L_0x0027:
            int r1 = r1.a()
            r0.b = r1
            goto L_0x005b
        L_0x002e:
            int r1 = r3.e
            r2 = 17
            if (r1 != r2) goto L_0x0037
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.BIND_TCP_READ_TIMEOUT
            goto L_0x0027
        L_0x0037:
            int r1 = r3.e
            r2 = 21
            if (r1 != r2) goto L_0x0040
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.BIND_TIMEOUT
            goto L_0x0027
        L_0x0040:
            com.xiaomi.stats.e r1 = com.xiaomi.stats.f.b()     // Catch:{ NullPointerException -> 0x005a }
            java.lang.Exception r1 = r1.a()     // Catch:{ NullPointerException -> 0x005a }
            com.xiaomi.stats.d$a r1 = com.xiaomi.stats.d.c(r1)     // Catch:{ NullPointerException -> 0x005a }
            com.xiaomi.push.thrift.a r2 = r1.a     // Catch:{ NullPointerException -> 0x005a }
            int r2 = r2.a()     // Catch:{ NullPointerException -> 0x005a }
            r0.b = r2     // Catch:{ NullPointerException -> 0x005a }
            java.lang.String r1 = r1.b     // Catch:{ NullPointerException -> 0x005a }
            r0.c(r1)     // Catch:{ NullPointerException -> 0x005a }
            goto L_0x005b
        L_0x005a:
            r0 = 0
        L_0x005b:
            if (r0 == 0) goto L_0x0083
            com.xiaomi.smack.a r1 = r3.c
            java.lang.String r1 = r1.e()
            r0.b(r1)
            com.xiaomi.push.service.aq$b r1 = r3.b
            java.lang.String r1 = r1.b
            r0.d(r1)
            r1 = 1
            r0.c = r1
            com.xiaomi.push.service.aq$b r1 = r3.b     // Catch:{ NumberFormatException -> 0x007c }
            java.lang.String r1 = r1.h     // Catch:{ NumberFormatException -> 0x007c }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x007c }
            byte r1 = (byte) r1     // Catch:{ NumberFormatException -> 0x007c }
            r0.a(r1)     // Catch:{ NumberFormatException -> 0x007c }
        L_0x007c:
            com.xiaomi.stats.f r1 = com.xiaomi.stats.f.a()
            r1.a(r0)
        L_0x0083:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stats.a.c():void");
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.b.a((com.xiaomi.push.service.aq.b.a) this);
        this.c = this.a.h();
    }

    public void a(c cVar, c cVar2, int i) {
        if (!this.f && cVar == c.binding) {
            this.d = cVar2;
            this.e = i;
            this.f = true;
        }
        this.a.a((h) new b(this, 4));
    }
}
