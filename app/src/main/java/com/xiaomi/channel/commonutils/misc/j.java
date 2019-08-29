package com.xiaomi.channel.commonutils.misc;

import com.xiaomi.channel.commonutils.misc.h.a;

class j extends b {
    final /* synthetic */ h a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    j(h hVar, a aVar) {
        // this.a = hVar;
        super(aVar);
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        synchronized (this.a.d) {
            this.a.c.remove(this.c.a());
        }
    }
}
