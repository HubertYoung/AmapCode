package defpackage;

/* renamed from: feh reason: default package */
/* compiled from: ApiLockHelper */
final class feh {
    public String a;
    public long b;
    public long c;

    public feh(String str, long j, long j2) {
        this.a = str;
        this.b = j;
        this.c = j2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("LockedEntity [key=");
        sb.append(this.a);
        sb.append(", lockStartTime=");
        sb.append(this.b);
        sb.append(", lockInterval=");
        sb.append(this.c);
        sb.append("]");
        return sb.toString();
    }
}
