package defpackage;

/* renamed from: cvx reason: default package */
/* compiled from: SmoothBean */
public final class cvx implements cuy {
    public String a;
    public String b;
    public int c;
    public long d;

    public final long a() {
        return this.d;
    }

    public final short b() {
        return cwb.o;
    }

    public final byte[] c() {
        if (this.a == null) {
            this.a = "";
        }
        if (this.b == null) {
            this.b = "";
        }
        return cwd.a(cwd.a((short) this.c), cwd.a(this.a.length()), this.a.getBytes(), cwd.a(this.b.length()), this.b.getBytes());
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("pageName: ");
        sb.append(this.a);
        sb.append(",pageHashCode:");
        sb.append(this.b);
        sb.append(",avgSm:");
        sb.append(this.c);
        return sb.toString();
    }
}
