package defpackage;

/* renamed from: cvt reason: default package */
/* compiled from: MemoryBean */
public final class cvt implements cuy {
    public long a;
    public byte[] b;
    public String c;

    public cvt(long j, cvv cvv, String str) {
        this.a = j;
        this.c = cwf.a(str);
        byte[] bytes = this.c.getBytes();
        this.b = cwd.a(cwd.a(bytes.length), bytes, cwd.a(cvv.a), cwd.a(cvv.b), cwd.a(cvv.d), cwd.a(cvv.c));
    }

    public final long a() {
        return this.a;
    }

    public final short b() {
        return cwb.q;
    }

    public final byte[] c() {
        return this.b;
    }
}
