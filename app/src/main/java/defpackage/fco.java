package defpackage;

/* renamed from: fco reason: default package */
/* compiled from: MessageReaderWriter */
public final class fco extends fcu {
    String a;
    String b;
    String c;

    public fco(String str, String str2, String str3) {
        this.T = 14;
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(m);
        stringBuffer.append(" : ");
        stringBuffer.append(this.T);
        stringBuffer.append(J);
        stringBuffer.append(" : ");
        stringBuffer.append(this.a);
        stringBuffer.append(L);
        stringBuffer.append(" : ");
        stringBuffer.append(this.c);
        stringBuffer.append(K);
        stringBuffer.append(" : ");
        stringBuffer.append(this.b);
        return stringBuffer.toString();
    }
}
