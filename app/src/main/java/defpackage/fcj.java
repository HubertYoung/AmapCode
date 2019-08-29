package defpackage;

import java.util.List;

/* renamed from: fcj reason: default package */
/* compiled from: MessageReaderWriter */
final class fcj extends fcu {
    List<Long> a;

    public fcj(List<Long> list) {
        this.T = 4;
        this.a = list;
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
