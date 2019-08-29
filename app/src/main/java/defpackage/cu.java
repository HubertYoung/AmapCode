package defpackage;

import java.net.Inet6Address;

/* renamed from: cu reason: default package */
/* compiled from: Nat64Prefix */
public final class cu {
    public int a;
    public Inet6Address b;

    public cu(Inet6Address inet6Address, int i) {
        this.a = i;
        this.b = inet6Address;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.getHostAddress());
        sb.append("/");
        sb.append(this.a);
        return sb.toString();
    }
}
