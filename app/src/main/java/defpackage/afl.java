package defpackage;

/* renamed from: afl reason: default package */
/* compiled from: LogHead */
public final class afl {
    public String a = "";
    public byte b = 0;
    public String c;
    public byte d = 0;
    public String e;
    public byte f = 0;
    public String g;
    public byte h = 0;
    public String i;
    public byte j = 0;
    public String k;
    public byte l = 0;
    public String m;
    public byte n = 0;
    public String o;
    public byte p = 0;
    public String q;
    private final String r = "1.0.13.8";

    public final String toString() {
        StringBuilder sb = new StringBuilder("LogHead-");
        sb.append("protocol:1.0.13.8");
        StringBuilder sb2 = new StringBuilder(",aetraffic:");
        sb2.append(this.a);
        sb.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder(",imei:");
        sb3.append(this.c);
        sb.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder(",diu2:");
        sb4.append(this.e);
        sb.append(sb4.toString());
        StringBuilder sb5 = new StringBuilder(",diu3:");
        sb5.append(this.g);
        sb.append(sb5.toString());
        StringBuilder sb6 = new StringBuilder(",dic:");
        sb6.append(this.i);
        sb.append(sb6.toString());
        StringBuilder sb7 = new StringBuilder(",version:");
        sb7.append(this.k);
        sb.append(sb7.toString());
        StringBuilder sb8 = new StringBuilder(",dibv:");
        sb8.append(this.m);
        sb.append(sb8.toString());
        StringBuilder sb9 = new StringBuilder(",imsi:");
        sb9.append(this.o);
        sb.append(sb9.toString());
        StringBuilder sb10 = new StringBuilder(",adiu:");
        sb10.append(this.q);
        sb.append(sb10.toString());
        return sb.toString();
    }
}
