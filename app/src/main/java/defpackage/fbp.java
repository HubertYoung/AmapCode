package defpackage;

/* renamed from: fbp reason: default package */
/* compiled from: MessageReaderWriter */
public final class fbp extends fcu {
    int a;
    int b;
    String c;
    int d;
    long e;

    public fbp(int i, String str, int i2, int i3) {
        this.T = 8;
        this.c = str;
        this.b = i2;
        this.a = i;
        this.d = i3;
    }

    public fbp(int i, String str, int i2, int i3, long j) {
        this.T = 8;
        this.c = str;
        this.b = i2;
        this.a = i;
        this.d = i3;
        this.e = j;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(m);
        stringBuffer.append(" : ");
        stringBuffer.append(this.T);
        stringBuffer.append(x);
        stringBuffer.append(" : ");
        stringBuffer.append(this.c);
        stringBuffer.append(y);
        stringBuffer.append(" : ");
        stringBuffer.append(this.b);
        stringBuffer.append(v);
        stringBuffer.append(" : ");
        stringBuffer.append(this.a);
        stringBuffer.append(C);
        stringBuffer.append(" : ");
        stringBuffer.append(this.e);
        stringBuffer.append(B);
        stringBuffer.append(" : ");
        stringBuffer.append(this.d);
        return stringBuffer.toString();
    }
}
