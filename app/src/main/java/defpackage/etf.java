package defpackage;

/* renamed from: etf reason: default package */
public final class etf extends ete {
    public String a;
    public String b;
    public String c;
    public String d;

    public final int a() {
        return 4103;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("messageID:");
        sb.append(this.j);
        sb.append(",taskID:");
        sb.append(this.l);
        sb.append(",globalID:");
        sb.append(this.a);
        sb.append(",appPackage:");
        sb.append(this.k);
        sb.append(",appID:");
        sb.append(this.d);
        return sb.toString();
    }
}
