package defpackage;

/* renamed from: bam reason: default package */
/* compiled from: EtaRequestInfo */
public final class bam {
    public double a;
    public double b;
    public double c;
    public double d;
    public String e;
    public int f;

    public final String toString() {
        StringBuilder sb = new StringBuilder("userX=");
        sb.append(this.a);
        sb.append("&userY=");
        sb.append(this.b);
        sb.append("&toX=");
        sb.append(this.c);
        sb.append("&toY=");
        sb.append(this.d);
        sb.append("&policy2=");
        sb.append(this.e);
        sb.append("&multi_routes=");
        sb.append(this.f);
        return sb.toString();
    }
}
