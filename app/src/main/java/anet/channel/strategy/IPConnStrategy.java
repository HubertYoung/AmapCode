package anet.channel.strategy;

import android.text.TextUtils;
import java.io.Serializable;

public class IPConnStrategy implements bo, Serializable {
    private static final long serialVersionUID = -2492035520806142510L;
    public final String a;
    public final int b;
    public final ConnProtocol c;
    public volatile int d;
    public volatile int e;
    public volatile int f;
    public volatile int g;
    volatile int h = 1;
    public volatile int i = 1;
    transient boolean j;

    static IPConnStrategy a(String str, a aVar) {
        ConnProtocol valueOf = ConnProtocol.valueOf(aVar);
        if (valueOf == null) {
            return null;
        }
        return a(str, aVar.a, valueOf, aVar.c, aVar.d, aVar.e, aVar.f);
    }

    public static IPConnStrategy a(String str, int i2, ConnProtocol connProtocol, int i3, int i4, int i5, int i6) {
        if (TextUtils.isEmpty(str) || connProtocol == null || i2 <= 0) {
            return null;
        }
        IPConnStrategy iPConnStrategy = new IPConnStrategy(str, i2, connProtocol, i3, i4, i5, i6);
        return iPConnStrategy;
    }

    private IPConnStrategy(String str, int i2, ConnProtocol connProtocol, int i3, int i4, int i5, int i6) {
        this.a = str;
        this.b = i2;
        this.c = connProtocol;
        this.d = i3;
        this.e = i4;
        this.f = i5;
        this.g = i6;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append('{');
        sb.append(this.a);
        if (this.h == 0) {
            sb.append("(*)");
        }
        sb.append(' ');
        sb.append(this.b);
        sb.append(' ');
        sb.append(this.c);
        sb.append('}');
        return sb.toString();
    }

    public final String a() {
        return this.a;
    }

    public final int b() {
        return this.h;
    }

    public final int c() {
        return this.i;
    }

    public final int d() {
        return this.b;
    }

    public final ConnProtocol e() {
        return this.c;
    }

    public final int f() {
        return this.d;
    }

    public final int g() {
        return this.e;
    }

    public final int h() {
        return this.f;
    }

    public final int i() {
        return this.g;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof IPConnStrategy)) {
            return false;
        }
        IPConnStrategy iPConnStrategy = (IPConnStrategy) obj;
        return this.b == iPConnStrategy.b && this.a.equals(iPConnStrategy.a) && this.c.equals(iPConnStrategy.c);
    }

    public int hashCode() {
        return ((((this.a.hashCode() + 527) * 31) + this.b) * 31) + this.c.hashCode();
    }
}
