package defpackage;

/* renamed from: cvk reason: default package */
/* compiled from: CpuBean */
public final class cvk implements cuy {
    public long a;
    public byte[] b;
    public String c;

    public cvk(long j, cvm cvm, String str) {
        this.a = j;
        this.c = cwf.a(str);
        byte[] bytes = this.c.getBytes();
        this.b = cwd.a(cwd.a(bytes.length), bytes, cwd.a(cvm.a), cwd.a(cvm.b), cwd.a(cvm.c));
    }

    public final long a() {
        return this.a;
    }

    public final short b() {
        return cwb.p;
    }

    public final byte[] c() {
        return this.b;
    }
}
