package defpackage;

/* renamed from: fbw reason: default package */
/* compiled from: MessageReaderWriter */
final class fbw extends fcu {
    String a;
    String b;
    String c;
    boolean d;
    String e;
    String f;
    String g;
    int h;

    public fbw(String str, String str2, String str3, boolean z, String str4, String str5, String str6) {
        this.T = 1;
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = z;
        this.e = str4;
        this.f = str5;
        this.g = str6;
        this.h = 0;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(m);
        stringBuffer.append(" : ");
        stringBuffer.append(this.T);
        stringBuffer.append(n);
        stringBuffer.append(" : ");
        stringBuffer.append(this.a);
        stringBuffer.append(r);
        stringBuffer.append(" : ");
        stringBuffer.append(this.b);
        stringBuffer.append(o);
        stringBuffer.append(" : ");
        stringBuffer.append(this.c);
        stringBuffer.append(p);
        stringBuffer.append(" : ");
        stringBuffer.append(this.d);
        stringBuffer.append(q);
        stringBuffer.append(" : ");
        stringBuffer.append(this.e);
        stringBuffer.append(E);
        stringBuffer.append(" : ");
        stringBuffer.append(this.f);
        stringBuffer.append(G);
        stringBuffer.append(" : ");
        stringBuffer.append(this.g);
        return stringBuffer.toString();
    }
}
