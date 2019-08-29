package defpackage;

/* renamed from: etv reason: default package */
/* compiled from: SourceInfo */
public final class etv {
    public final String a;
    public final long b;
    public final String c;

    public etv(String str, long j, String str2) {
        this.a = str;
        this.b = j;
        this.c = str2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SourceInfo{url='");
        sb.append(this.a);
        sb.append('\'');
        sb.append(", length=");
        sb.append(this.b);
        sb.append(", mime='");
        sb.append(this.c);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
