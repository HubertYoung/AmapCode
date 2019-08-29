package defpackage;

/* renamed from: epa reason: default package */
/* compiled from: LMLocation */
public final class epa {
    public byte a = -1;
    public byte b = -1;
    public long c = 0;
    public long d = 0;
    public double e = -999.0d;
    public double f = -999.0d;
    public double g = -999.0d;
    public double h = -999.0d;
    public float i = -999.0f;
    public float j = -999.0f;
    public float k = -999.0f;
    public byte l = -1;
    public boolean m = false;
    public boolean n = false;
    public int o = -1;
    public int p = -1;
    public int q = -1;
    public int r = -1;

    public final String toString() {
        StringBuilder sb = new StringBuilder("LMLocation{status=");
        sb.append(this.a);
        sb.append(", provider=");
        sb.append(this.b);
        sb.append(", locationTime=");
        sb.append(epl.a(this.c));
        sb.append(", systemTime=");
        sb.append(epl.a(this.d));
        sb.append(", latitude=");
        sb.append(this.e);
        sb.append(", longitude=");
        sb.append(this.f);
        sb.append(", matchLatitude=");
        sb.append(this.g);
        sb.append(", matchLongitude=");
        sb.append(this.h);
        sb.append(", speed=");
        sb.append(this.i);
        sb.append(", bearing=");
        sb.append(this.j);
        sb.append(", accuracy=");
        sb.append(this.k);
        sb.append(", checkStatus=");
        sb.append(this.l);
        sb.append(", hasRoute=");
        sb.append(this.m);
        sb.append(", hasRouteMatch=");
        sb.append(this.n);
        sb.append(", locOnRouteState=");
        sb.append(this.o);
        sb.append(", formway=");
        sb.append(this.p);
        sb.append(", linkType=");
        sb.append(this.q);
        sb.append(", roadClass=");
        sb.append(this.r);
        sb.append('}');
        return sb.toString();
    }
}
