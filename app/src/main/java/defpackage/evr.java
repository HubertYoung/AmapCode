package defpackage;

/* renamed from: evr reason: default package */
/* compiled from: AbiInfo */
public final class evr {
    public String a;
    public String b;
    public String c;
    public String d;

    public evr(String str, String str2, String str3, String str4) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AbiInfo{abi='");
        sb.append(this.a);
        sb.append('\'');
        sb.append(", url='");
        sb.append(this.b);
        sb.append('\'');
        sb.append(", version='");
        sb.append(this.c);
        sb.append('\'');
        sb.append(", libName='");
        sb.append(this.d);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
