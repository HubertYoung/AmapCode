package defpackage;

/* renamed from: fcr reason: default package */
/* compiled from: MessageReaderWriter */
public final class fcr extends fcu {
    long a;
    long b;
    String c;
    int d;

    public fcr(long j, long j2, int i, String str) {
        this.d = i;
        this.a = j;
        this.b = j2;
        this.c = str;
        this.T = 100;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(m);
        stringBuffer.append(" : ");
        stringBuffer.append(this.T);
        stringBuffer.append(k);
        stringBuffer.append(" : ");
        stringBuffer.append(this.a);
        stringBuffer.append(j);
        stringBuffer.append(" : ");
        stringBuffer.append(this.b);
        stringBuffer.append("cmd : ");
        stringBuffer.append(this.d);
        stringBuffer.append(L);
        stringBuffer.append(" : ");
        stringBuffer.append(this.c);
        return stringBuffer.toString();
    }
}
