package defpackage;

/* renamed from: fcn reason: default package */
/* compiled from: MessageReaderWriter */
public final class fcn extends fcu {
    String a;
    String b;
    String c;
    String d;

    public fcn(String str, String str2, String str3, String str4) {
        this.T = 15;
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(m);
        stringBuffer.append(" : ");
        stringBuffer.append(this.T);
        stringBuffer.append(I);
        stringBuffer.append(" : ");
        stringBuffer.append(this.a);
        stringBuffer.append(J);
        stringBuffer.append(" : ");
        stringBuffer.append(this.b);
        stringBuffer.append(L);
        stringBuffer.append(" : ");
        stringBuffer.append(this.d);
        stringBuffer.append(K);
        stringBuffer.append(" : ");
        stringBuffer.append(this.c);
        return stringBuffer.toString();
    }
}
