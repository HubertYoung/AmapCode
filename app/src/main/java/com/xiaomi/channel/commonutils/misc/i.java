package com.xiaomi.channel.commonutils.misc;

import com.xiaomi.channel.commonutils.misc.h.a;

class i extends b {
    final /* synthetic */ String a;
    final /* synthetic */ h b;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    i(h hVar, a aVar, String str) {
        // this.b = hVar;
        // this.a = str;
        super(aVar);
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        super.a();
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        this.b.e.edit().putLong(this.a, System.currentTimeMillis()).commit();
    }
}
