package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.thrift.meta_data.b;
import org.apache.thrift.meta_data.c;
import org.apache.thrift.meta_data.d;
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.e;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.j;

public class ae implements Serializable, Cloneable, org.apache.thrift.a<ae, a> {
    public static final Map<a, b> l;
    private static final j m = new j("XmPushActionCommandResult");
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("debug", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("target", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 2);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("appId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("cmdName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 5);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("request", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 6);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("errorCode", 10, 7);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("reason", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 8);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 9);
    private static final org.apache.thrift.protocol.b w = new org.apache.thrift.protocol.b("cmdArgs", 15, 10);
    private static final org.apache.thrift.protocol.b x = new org.apache.thrift.protocol.b("category", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 12);
    public String a;
    public x b;
    public String c;
    public String d;
    public String e;
    public ad f;
    public long g;
    public String h;
    public String i;
    public List<String> j;
    public String k;
    private BitSet y = new BitSet(1);

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        CMD_NAME(5, "cmdName"),
        REQUEST(6, "request"),
        ERROR_CODE(7, "errorCode"),
        REASON(8, "reason"),
        PACKAGE_NAME(9, "packageName"),
        CMD_ARGS(10, "cmdArgs"),
        CATEGORY(12, "category");
        
        private static final Map<String, a> l = null;
        private final short m;
        private final String n;

        static {
            l = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                l.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.m = s;
            this.n = str;
        }

        public final String a() {
            return this.n;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new b("debug", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TARGET, new b("target", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, x.class)));
        enumMap.put(a.ID, new b("id", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APP_ID, new b("appId", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.CMD_NAME, new b("cmdName", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.REQUEST, new b("request", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, ad.class)));
        enumMap.put(a.ERROR_CODE, new b("errorCode", 1, new c(10)));
        enumMap.put(a.REASON, new b("reason", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.CMD_ARGS, new b("cmdArgs", 2, new d(15, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES))));
        enumMap.put(a.CATEGORY, new b("category", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        l = Collections.unmodifiableMap(enumMap);
        b.a(ae.class, l);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r5) {
        /*
            r4 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r5.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x00c0
            short r1 = r0.c
            r2 = 12
            r3 = 11
            switch(r1) {
                case 1: goto L_0x00b4;
                case 2: goto L_0x00a2;
                case 3: goto L_0x0096;
                case 4: goto L_0x008a;
                case 5: goto L_0x007e;
                case 6: goto L_0x006d;
                case 7: goto L_0x005c;
                case 8: goto L_0x0051;
                case 9: goto L_0x0046;
                case 10: goto L_0x0022;
                case 11: goto L_0x0011;
                case 12: goto L_0x0017;
                default: goto L_0x0011;
            }
        L_0x0011:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r5, r0)
            goto L_0x0000
        L_0x0017:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.k = r0
            goto L_0x0000
        L_0x0022:
            byte r1 = r0.b
            r2 = 15
            if (r1 != r2) goto L_0x0011
            org.apache.thrift.protocol.c r0 = r5.d()
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = r0.b
            r1.<init>(r2)
            r4.j = r1
            r1 = 0
        L_0x0036:
            int r2 = r0.b
            if (r1 >= r2) goto L_0x0000
            java.lang.String r2 = r5.l()
            java.util.List<java.lang.String> r3 = r4.j
            r3.add(r2)
            int r1 = r1 + 1
            goto L_0x0036
        L_0x0046:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.i = r0
            goto L_0x0000
        L_0x0051:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.h = r0
            goto L_0x0000
        L_0x005c:
            byte r1 = r0.b
            r2 = 10
            if (r1 != r2) goto L_0x0011
            long r0 = r5.j()
            r4.g = r0
            r0 = 1
            r4.a(r0)
            goto L_0x0000
        L_0x006d:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0011
            com.xiaomi.xmpush.thrift.ad r0 = new com.xiaomi.xmpush.thrift.ad
            r0.<init>()
            r4.f = r0
            com.xiaomi.xmpush.thrift.ad r0 = r4.f
            r0.a(r5)
            goto L_0x0000
        L_0x007e:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.e = r0
            goto L_0x0000
        L_0x008a:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.d = r0
            goto L_0x0000
        L_0x0096:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.c = r0
            goto L_0x0000
        L_0x00a2:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0011
            com.xiaomi.xmpush.thrift.x r0 = new com.xiaomi.xmpush.thrift.x
            r0.<init>()
            r4.b = r0
            com.xiaomi.xmpush.thrift.x r0 = r4.b
            r0.a(r5)
            goto L_0x0000
        L_0x00b4:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.a = r0
            goto L_0x0000
        L_0x00c0:
            boolean r5 = r4.h()
            if (r5 != 0) goto L_0x00de
            org.apache.thrift.protocol.f r5 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'errorCode' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r4.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L_0x00de:
            r4.o()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.ae.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.y.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(ae aeVar) {
        if (aeVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = aeVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(aeVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = aeVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.a(aeVar.b))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = aeVar.c();
        if ((c2 || c3) && (!c2 || !c3 || !this.c.equals(aeVar.c))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = aeVar.d();
        if ((d2 || d3) && (!d2 || !d3 || !this.d.equals(aeVar.d))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = aeVar.f();
        if ((f2 || f3) && (!f2 || !f3 || !this.e.equals(aeVar.e))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = aeVar.g();
        if (((g2 || g3) && (!g2 || !g3 || !this.f.a(aeVar.f))) || this.g != aeVar.g) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = aeVar.i();
        if ((i2 || i3) && (!i2 || !i3 || !this.h.equals(aeVar.h))) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = aeVar.j();
        if ((j2 || j3) && (!j2 || !j3 || !this.i.equals(aeVar.i))) {
            return false;
        }
        boolean l2 = l();
        boolean l3 = aeVar.l();
        if ((l2 || l3) && (!l2 || !l3 || !this.j.equals(aeVar.j))) {
            return false;
        }
        boolean n2 = n();
        boolean n3 = aeVar.n();
        return (!n2 && !n3) || (n2 && n3 && this.k.equals(aeVar.k));
    }

    /* renamed from: b */
    public int compareTo(ae aeVar) {
        if (!getClass().equals(aeVar.getClass())) {
            return getClass().getName().compareTo(aeVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(aeVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, aeVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(aeVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((Comparable) this.b, (Comparable) aeVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(aeVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c()) {
            int a4 = org.apache.thrift.b.a(this.c, aeVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(aeVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d()) {
            int a5 = org.apache.thrift.b.a(this.d, aeVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(aeVar.f()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (f()) {
            int a6 = org.apache.thrift.b.a(this.e, aeVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(aeVar.g()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (g()) {
            int a7 = org.apache.thrift.b.a((Comparable) this.f, (Comparable) aeVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(aeVar.h()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (h()) {
            int a8 = org.apache.thrift.b.a(this.g, aeVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(aeVar.i()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (i()) {
            int a9 = org.apache.thrift.b.a(this.h, aeVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        int compareTo9 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(aeVar.j()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (j()) {
            int a10 = org.apache.thrift.b.a(this.i, aeVar.i);
            if (a10 != 0) {
                return a10;
            }
        }
        int compareTo10 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(aeVar.l()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (l()) {
            int a11 = org.apache.thrift.b.a((List) this.j, (List) aeVar.j);
            if (a11 != 0) {
                return a11;
            }
        }
        int compareTo11 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(aeVar.n()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (n()) {
            int a12 = org.apache.thrift.b.a(this.k, aeVar.k);
            if (a12 != 0) {
                return a12;
            }
        }
        return 0;
    }

    public void b(e eVar) {
        o();
        if (this.a != null && a()) {
            eVar.a(n);
            eVar.a(this.a);
        }
        if (this.b != null && b()) {
            eVar.a(o);
            this.b.b(eVar);
        }
        if (this.c != null) {
            eVar.a(p);
            eVar.a(this.c);
        }
        if (this.d != null) {
            eVar.a(q);
            eVar.a(this.d);
        }
        if (this.e != null) {
            eVar.a(r);
            eVar.a(this.e);
        }
        if (this.f != null && g()) {
            eVar.a(s);
            this.f.b(eVar);
        }
        eVar.a(t);
        eVar.a(this.g);
        if (this.h != null && i()) {
            eVar.a(u);
            eVar.a(this.h);
        }
        if (this.i != null && j()) {
            eVar.a(v);
            eVar.a(this.i);
        }
        if (this.j != null && l()) {
            eVar.a(w);
            eVar.a(new org.apache.thrift.protocol.c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES, this.j.size()));
            for (String a2 : this.j) {
                eVar.a(a2);
            }
        }
        if (this.k != null && n()) {
            eVar.a(x);
            eVar.a(this.k);
        }
        eVar.a();
    }

    public boolean b() {
        return this.b != null;
    }

    public boolean c() {
        return this.c != null;
    }

    public boolean d() {
        return this.d != null;
    }

    public String e() {
        return this.e;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ae)) {
            return a((ae) obj);
        }
        return false;
    }

    public boolean f() {
        return this.e != null;
    }

    public boolean g() {
        return this.f != null;
    }

    public boolean h() {
        return this.y.get(0);
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.h != null;
    }

    public boolean j() {
        return this.i != null;
    }

    public List<String> k() {
        return this.j;
    }

    public boolean l() {
        return this.j != null;
    }

    public String m() {
        return this.k;
    }

    public boolean n() {
        return this.k != null;
    }

    public void o() {
        if (this.c == null) {
            StringBuilder sb = new StringBuilder("Required field 'id' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        } else if (this.d == null) {
            StringBuilder sb2 = new StringBuilder("Required field 'appId' was not present! Struct: ");
            sb2.append(toString());
            throw new f(sb2.toString());
        } else if (this.e == null) {
            StringBuilder sb3 = new StringBuilder("Required field 'cmdName' was not present! Struct: ");
            sb3.append(toString());
            throw new f(sb3.toString());
        }
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionCommandResult(");
        if (a()) {
            sb.append("debug:");
            sb.append(this.a == null ? "null" : this.a);
            z = false;
        } else {
            z = true;
        }
        if (b()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("target:");
            if (this.b == null) {
                sb.append("null");
            } else {
                sb.append(this.b);
            }
            z = false;
        }
        if (!z) {
            sb.append(", ");
        }
        sb.append("id:");
        sb.append(this.c == null ? "null" : this.c);
        sb.append(", ");
        sb.append("appId:");
        sb.append(this.d == null ? "null" : this.d);
        sb.append(", ");
        sb.append("cmdName:");
        sb.append(this.e == null ? "null" : this.e);
        if (g()) {
            sb.append(", ");
            sb.append("request:");
            if (this.f == null) {
                sb.append("null");
            } else {
                sb.append(this.f);
            }
        }
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.g);
        if (i()) {
            sb.append(", ");
            sb.append("reason:");
            sb.append(this.h == null ? "null" : this.h);
        }
        if (j()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.i == null ? "null" : this.i);
        }
        if (l()) {
            sb.append(", ");
            sb.append("cmdArgs:");
            if (this.j == null) {
                sb.append("null");
            } else {
                sb.append(this.j);
            }
        }
        if (n()) {
            sb.append(", ");
            sb.append("category:");
            sb.append(this.k == null ? "null" : this.k);
        }
        sb.append(")");
        return sb.toString();
    }
}
