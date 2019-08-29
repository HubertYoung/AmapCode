package defpackage;

/* renamed from: fcq reason: default package */
/* compiled from: MessageReaderWriter */
public final class fcq extends fcu {
    long a;

    public fcq(long j) {
        this.T = 5;
        this.a = j;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(m);
        stringBuffer.append(" : ");
        stringBuffer.append(this.T);
        stringBuffer.append(t);
        stringBuffer.append(" : ");
        stringBuffer.append(this.a);
        return stringBuffer.toString();
    }
}
