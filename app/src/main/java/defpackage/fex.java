package defpackage;

@Deprecated
/* renamed from: fex reason: default package */
/* compiled from: MtopProgressEvent */
public final class fex extends fet {
    String a;
    int b;
    int c;
    public String d;

    public final String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append("MtopProgressEvent [seqNo=");
        sb.append(this.d);
        sb.append(", desc=");
        sb.append(this.a);
        sb.append(", size=");
        sb.append(this.b);
        sb.append(", total=");
        sb.append(this.c);
        sb.append("]");
        return sb.toString();
    }
}
