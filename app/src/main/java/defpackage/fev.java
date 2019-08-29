package defpackage;

import java.util.List;
import java.util.Map;

/* renamed from: fev reason: default package */
/* compiled from: MtopHeaderEvent */
public final class fev extends fet {
    public String a;
    private int b;
    private Map<String, List<String>> c;

    public fev(int i, Map<String, List<String>> map) {
        this.b = i;
        this.c = map;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("MtopHeaderEvent [seqNo=");
        sb.append(this.a);
        sb.append(", code=");
        sb.append(this.b);
        sb.append(", headers=");
        sb.append(this.c);
        sb.append("]");
        return sb.toString();
    }
}
