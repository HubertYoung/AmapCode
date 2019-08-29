package defpackage;

import java.util.List;
import java.util.Map;

/* renamed from: ffc reason: default package */
/* compiled from: MockResponse */
public final class ffc {
    public String a;
    public int b;
    public Map<String, List<String>> c;
    public byte[] d;

    public final String toString() {
        StringBuilder sb = new StringBuilder("MockResponse{api='");
        sb.append(this.a);
        sb.append('\'');
        sb.append(", statusCode=");
        sb.append(this.b);
        sb.append(", headers=");
        sb.append(this.c);
        sb.append(", byteData=");
        sb.append(new String(this.d));
        sb.append('}');
        return sb.toString();
    }
}
