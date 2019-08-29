package defpackage;

/* renamed from: etc reason: default package */
public final class etc extends ete {
    public String a;
    public String b;
    public long c;
    public long d;
    public int e;
    public String f = "08:00-22:00";
    public String g;
    public int h = 0;
    public int i = 0;

    public final int a() {
        return 4098;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder("messageID:");
        sb2.append(this.j);
        sb.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder(",taskID:");
        sb3.append(this.l);
        sb.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder(",appPackage:");
        sb4.append(this.k);
        sb.append(sb4.toString());
        StringBuilder sb5 = new StringBuilder(",title:");
        sb5.append(this.a);
        sb.append(sb5.toString());
        StringBuilder sb6 = new StringBuilder(",balanceTime:");
        sb6.append(this.e);
        sb.append(sb6.toString());
        StringBuilder sb7 = new StringBuilder(",startTime:");
        sb7.append(this.c);
        sb.append(sb7.toString());
        StringBuilder sb8 = new StringBuilder(",endTime:");
        sb8.append(this.d);
        sb.append(sb8.toString());
        StringBuilder sb9 = new StringBuilder(",balanceTime:");
        sb9.append(this.e);
        sb.append(sb9.toString());
        StringBuilder sb10 = new StringBuilder(",timeRanges:");
        sb10.append(this.f);
        sb.append(sb10.toString());
        StringBuilder sb11 = new StringBuilder(",forcedDelivery:");
        sb11.append(this.h);
        sb.append(sb11.toString());
        StringBuilder sb12 = new StringBuilder(",distinctBycontent:");
        sb12.append(this.i);
        sb.append(sb12.toString());
        return sb.toString();
    }
}
