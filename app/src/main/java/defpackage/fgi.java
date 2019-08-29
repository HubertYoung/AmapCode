package defpackage;

import com.alipay.sdk.util.h;
import java.util.List;
import java.util.Map;
import mtopsdk.network.domain.NetworkStats;
import mtopsdk.network.domain.Request;

/* renamed from: fgi reason: default package */
/* compiled from: Response */
public final class fgi {
    public final Request a;
    public final int b;
    public final String c;
    public final Map<String, List<String>> d;
    public final fgj e;
    public final NetworkStats f;

    /* renamed from: fgi$a */
    /* compiled from: Response */
    public static class a {
        public Request a;
        public int b = -1;
        public String c;
        public Map<String, List<String>> d;
        public fgj e;
        public NetworkStats f;

        public final fgi a() {
            if (this.a != null) {
                return new fgi(this, 0);
            }
            throw new IllegalStateException("request == null");
        }
    }

    /* synthetic */ fgi(a aVar, byte b2) {
        this(aVar);
    }

    private fgi(a aVar) {
        this.a = aVar.a;
        this.b = aVar.b;
        this.c = aVar.c;
        this.d = aVar.d;
        this.e = aVar.e;
        this.f = aVar.f;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("Response{ code=");
        sb.append(this.b);
        sb.append(", message=");
        sb.append(this.c);
        sb.append(", headers");
        sb.append(this.d);
        sb.append(", body");
        sb.append(this.e);
        sb.append(", request");
        sb.append(this.a);
        sb.append(", stat");
        sb.append(this.f);
        sb.append(h.d);
        return sb.toString();
    }
}
