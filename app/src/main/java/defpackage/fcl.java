package defpackage;

/* renamed from: fcl reason: default package */
/* compiled from: MessageReaderWriter */
final class fcl extends fcu {
    String a;
    long b;
    long c;
    int d;
    long e;
    String f;

    public fcl(long j, long j2, int i, String str, long j3, String str2) {
        this.T = 7;
        this.b = j;
        this.c = j2;
        this.d = i;
        this.a = str;
        this.e = j3;
        this.f = str2;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(m);
        stringBuffer.append(" : ");
        stringBuffer.append(this.T);
        stringBuffer.append(k);
        stringBuffer.append(" : ");
        stringBuffer.append(this.b);
        stringBuffer.append(j);
        stringBuffer.append(" : ");
        stringBuffer.append(this.c);
        stringBuffer.append(v);
        stringBuffer.append(" : ");
        stringBuffer.append(this.d);
        stringBuffer.append(z);
        stringBuffer.append(" : ");
        stringBuffer.append(this.a);
        stringBuffer.append(D);
        stringBuffer.append(" : ");
        stringBuffer.append(this.e);
        stringBuffer.append(A);
        stringBuffer.append(" : ");
        stringBuffer.append(this.f);
        return stringBuffer.toString();
    }
}
