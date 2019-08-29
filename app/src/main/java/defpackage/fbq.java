package defpackage;

/* renamed from: fbq reason: default package */
/* compiled from: MessageReaderWriter */
final class fbq extends fcu {
    String a;
    boolean b;

    public fbq(boolean z, String str) {
        this.T = 12;
        this.b = z;
        this.a = str;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(m);
        stringBuffer.append(" : ");
        stringBuffer.append(this.T);
        stringBuffer.append(p);
        stringBuffer.append(" : ");
        stringBuffer.append(this.b);
        stringBuffer.append(q);
        stringBuffer.append(" : ");
        stringBuffer.append(this.a);
        return stringBuffer.toString();
    }
}
