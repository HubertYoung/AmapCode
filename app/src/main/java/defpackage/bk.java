package defpackage;

/* renamed from: bk reason: default package */
/* compiled from: CountObject */
public final class bk {
    public String a;
    public double b;
    public String c;
    public String d;

    public final String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("[module:");
        sb.append(this.c);
        sb.append(" modulePoint:");
        sb.append(this.d);
        sb.append(" arg:");
        sb.append(this.a);
        sb.append(" value:");
        sb.append(this.b);
        sb.append("]");
        return sb.toString();
    }
}
