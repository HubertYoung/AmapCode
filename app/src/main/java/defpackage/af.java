package defpackage;

import anet.channel.entity.ConnType;

/* renamed from: af reason: default package */
/* compiled from: ConnInfo */
public final class af {
    public final bo a;
    public String b;
    public String c;
    public int d = 0;
    public int e = 0;

    public af(String str, String str2, bo boVar) {
        this.a = boVar;
        this.b = str;
        this.c = str2;
    }

    public final String a() {
        if (this.a != null) {
            return this.a.a();
        }
        return null;
    }

    public final int b() {
        if (this.a != null) {
            return this.a.d();
        }
        return 0;
    }

    public final ConnType c() {
        if (this.a != null) {
            return ConnType.a(this.a.e());
        }
        return ConnType.a;
    }

    public final int d() {
        if (this.a != null) {
            return this.a.i();
        }
        return 45000;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ConnInfo [ip=");
        sb.append(a());
        sb.append(",port=");
        sb.append(b());
        sb.append(",type=");
        sb.append(c());
        sb.append(",hb");
        sb.append(d());
        sb.append("]");
        return sb.toString();
    }
}
