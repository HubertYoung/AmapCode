package defpackage;

/* renamed from: fcp reason: default package */
/* compiled from: MessageReaderWriter */
final class fcp extends fcu {
    boolean a;
    boolean b;
    boolean c;

    public fcp(boolean z, boolean z2, boolean z3) {
        this.T = 11;
        this.a = z;
        this.b = z2;
        this.c = z3;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(m);
        stringBuffer.append(" : ");
        stringBuffer.append(this.T);
        stringBuffer.append(M);
        stringBuffer.append(" : ");
        stringBuffer.append(this.a);
        stringBuffer.append(P);
        stringBuffer.append(" : ");
        stringBuffer.append(this.b);
        return stringBuffer.toString();
    }
}
