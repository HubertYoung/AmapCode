package com.xiaomi.metoknlp.devicediscover;

class h implements p {
    final /* synthetic */ n a;
    private String b;
    private long c;
    private long d;

    private h(n nVar) {
        this.a = nVar;
    }

    /* synthetic */ h(n nVar, b bVar) {
        this(nVar);
    }

    public long a() {
        return this.c;
    }

    public void a(String str, long j, long j2) {
        this.b = str;
        this.c = j;
        this.d = j2;
        this.a.j.obtainMessage(1).sendToTarget();
    }

    public long b() {
        return this.d;
    }
}
