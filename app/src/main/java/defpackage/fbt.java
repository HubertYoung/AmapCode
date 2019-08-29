package defpackage;

/* renamed from: fbt reason: default package */
/* compiled from: MessageReaderWriter */
final class fbt extends fcu {
    int a;
    boolean b;
    int c;

    public fbt(int i, boolean z, int i2) {
        this.T = 10;
        this.b = z;
        this.a = i;
        this.c = i2;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(m);
        stringBuffer.append(" : ");
        stringBuffer.append(this.T);
        stringBuffer.append(v);
        stringBuffer.append(" : ");
        stringBuffer.append(this.a);
        stringBuffer.append(H);
        stringBuffer.append(" : ");
        stringBuffer.append(this.b);
        stringBuffer.append(w);
        stringBuffer.append(" : ");
        stringBuffer.append(this.c);
        return stringBuffer.toString();
    }
}
