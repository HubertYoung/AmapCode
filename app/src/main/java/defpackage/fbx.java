package defpackage;

/* renamed from: fbx reason: default package */
/* compiled from: MessageReaderWriter */
final class fbx extends fcu {
    String a;
    String b;
    String c;
    boolean d;
    boolean e;
    boolean f;
    int g;

    public fbx(String str, String str2, String str3, boolean z, boolean z2, boolean z3, int i) {
        this.T = 2;
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = z;
        this.e = z2;
        this.f = z3;
        this.g = i;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(m);
        stringBuffer.append(" : ");
        stringBuffer.append(this.T);
        stringBuffer.append(s);
        stringBuffer.append(" : ");
        stringBuffer.append(this.a);
        stringBuffer.append(r);
        stringBuffer.append(" : ");
        stringBuffer.append(this.b);
        stringBuffer.append(E);
        stringBuffer.append(" : ");
        stringBuffer.append(this.c);
        stringBuffer.append(M);
        stringBuffer.append(" : ");
        stringBuffer.append(this.d);
        stringBuffer.append(P);
        stringBuffer.append(" : ");
        stringBuffer.append(this.e);
        return stringBuffer.toString();
    }
}
