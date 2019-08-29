package defpackage;

/* renamed from: bj reason: default package */
/* compiled from: AlarmObject */
public final class bj {
    public boolean a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;

    public final String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("[module:");
        sb.append(this.e);
        sb.append(" modulePoint:");
        sb.append(this.f);
        sb.append(" arg:");
        sb.append(this.b);
        sb.append(" isSuccess:");
        sb.append(this.a);
        sb.append(" errorCode:");
        sb.append(this.c);
        sb.append("]");
        return sb.toString();
    }
}
