package defpackage;

/* renamed from: eoz reason: default package */
/* compiled from: LMHeader */
public final class eoz {
    public String a = "v2.0.3";
    public String b;
    public String c = epl.b();
    public String d = String.valueOf(epl.a());
    public byte e;
    public byte f;
    public byte g;
    public byte h;
    public String i;
    public long j;
    public byte k;
    public long l;
    public long m;
    public long n;
    public long o;
    public String p;

    public eoz() {
        a();
    }

    public final void a() {
        this.b = "";
        this.e = -1;
        this.f = -1;
        this.g = -1;
        this.h = -1;
        this.i = "";
        this.j = 0;
        this.k = -1;
        this.l = 0;
        this.m = 0;
        this.n = 0;
        this.o = 0;
        this.p = "";
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("LMHeader{logVersion='");
        sb.append(this.a);
        sb.append('\'');
        sb.append(", abnumber='");
        sb.append(this.b);
        sb.append('\'');
        sb.append(", mobileModel='");
        sb.append(this.c);
        sb.append('\'');
        sb.append(", systemVersion='");
        sb.append(this.d);
        sb.append('\'');
        sb.append(", amapNaviType=");
        sb.append(this.e);
        sb.append(", pageType=");
        sb.append(this.f);
        sb.append(", naviType=");
        sb.append(this.g);
        sb.append(", routeSource=");
        sb.append(this.h);
        sb.append(", naviID='");
        sb.append(this.i);
        sb.append('\'');
        sb.append(", eventID=");
        sb.append(epl.a(this.j));
        sb.append(", reason=");
        sb.append(this.k);
        sb.append(", startTime=");
        sb.append(epl.a(this.l));
        sb.append(", endTime=");
        sb.append(epl.a(this.m));
        sb.append(", engineNaviTime=");
        sb.append(epl.a(this.n));
        sb.append(", engineRouteTime=");
        sb.append(epl.a(this.o));
        sb.append(", ext='");
        sb.append(this.p);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
