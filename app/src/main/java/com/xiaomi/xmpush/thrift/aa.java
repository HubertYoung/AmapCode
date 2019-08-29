package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.thrift.meta_data.b;
import org.apache.thrift.meta_data.c;
import org.apache.thrift.meta_data.e;
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.d;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.j;

public class aa implements Serializable, Cloneable, org.apache.thrift.a<aa, a> {
    public static final Map<a, b> l;
    private static final j m = new j("XmPushActionAckNotification");
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("debug", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("target", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 2);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("appId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("type", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 5);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("request", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 6);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("errorCode", 10, 7);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("reason", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 8);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("extra", 13, 9);
    private static final org.apache.thrift.protocol.b w = new org.apache.thrift.protocol.b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 10);
    private static final org.apache.thrift.protocol.b x = new org.apache.thrift.protocol.b("category", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 11);
    public String a;
    public x b;
    public String c;
    public String d;
    public String e;
    public ai f;
    public long g;
    public String h;
    public Map<String, String> i;
    public String j;
    public String k;
    private BitSet y = new BitSet(1);

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        TYPE(5, "type"),
        REQUEST(6, "request"),
        ERROR_CODE(7, "errorCode"),
        REASON(8, "reason"),
        EXTRA(9, "extra"),
        PACKAGE_NAME(10, "packageName"),
        CATEGORY(11, "category");
        
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
        enumMap.put(a.APP_ID, new b("appId", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TYPE, new b("type", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.REQUEST, new b("request", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, ai.class)));
        enumMap.put(a.ERROR_CODE, new b("errorCode", 1, new c(10)));
        enumMap.put(a.REASON, new b("reason", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.EXTRA, new b("extra", 2, new e(13, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES), new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES))));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.CATEGORY, new b("category", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        l = Collections.unmodifiableMap(enumMap);
        b.a(aa.class, l);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r6) {
        /*
            r5 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r6.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x00c7
            short r1 = r0.c
            r2 = 12
            r3 = 11
            switch(r1) {
                case 1: goto L_0x00bb;
                case 2: goto L_0x00a9;
                case 3: goto L_0x009d;
                case 4: goto L_0x0091;
                case 5: goto L_0x0085;
                case 6: goto L_0x0073;
                case 7: goto L_0x0062;
                case 8: goto L_0x0057;
                case 9: goto L_0x002d;
                case 10: goto L_0x0022;
                case 11: goto L_0x0017;
                default: goto L_0x0011;
            }
        L_0x0011:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r6, r0)
            goto L_0x0000
        L_0x0017:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r6.l()
            r5.k = r0
            goto L_0x0000
        L_0x0022:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r6.l()
            r5.j = r0
            goto L_0x0000
        L_0x002d:
            byte r1 = r0.b
            r2 = 13
            if (r1 != r2) goto L_0x0011
            org.apache.thrift.protocol.d r0 = r6.c()
            java.util.HashMap r1 = new java.util.HashMap
            int r2 = r0.c
            int r2 = r2 * 2
            r1.<init>(r2)
            r5.i = r1
            r1 = 0
        L_0x0043:
            int r2 = r0.c
            if (r1 >= r2) goto L_0x0000
            java.lang.String r2 = r6.l()
            java.lang.String r3 = r6.l()
            java.util.Map<java.lang.String, java.lang.String> r4 = r5.i
            r4.put(r2, r3)
            int r1 = r1 + 1
            goto L_0x0043
        L_0x0057:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r6.l()
            r5.h = r0
            goto L_0x0000
        L_0x0062:
            byte r1 = r0.b
            r2 = 10
            if (r1 != r2) goto L_0x0011
            long r0 = r6.j()
            r5.g = r0
            r0 = 1
            r5.a(r0)
            goto L_0x0000
        L_0x0073:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0011
            com.xiaomi.xmpush.thrift.ai r0 = new com.xiaomi.xmpush.thrift.ai
            r0.<init>()
            r5.f = r0
            com.xiaomi.xmpush.thrift.ai r0 = r5.f
            r0.a(r6)
            goto L_0x0000
        L_0x0085:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r6.l()
            r5.e = r0
            goto L_0x0000
        L_0x0091:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r6.l()
            r5.d = r0
            goto L_0x0000
        L_0x009d:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r6.l()
            r5.c = r0
            goto L_0x0000
        L_0x00a9:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0011
            com.xiaomi.xmpush.thrift.x r0 = new com.xiaomi.xmpush.thrift.x
            r0.<init>()
            r5.b = r0
            com.xiaomi.xmpush.thrift.x r0 = r5.b
            r0.a(r6)
            goto L_0x0000
        L_0x00bb:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r6.l()
            r5.a = r0
            goto L_0x0000
        L_0x00c7:
            boolean r6 = r5.h()
            if (r6 != 0) goto L_0x00e5
            org.apache.thrift.protocol.f r6 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'errorCode' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r5.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r6.<init>(r0)
            throw r6
        L_0x00e5:
            r5.n()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.aa.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.y.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(aa aaVar) {
        if (aaVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = aaVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(aaVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = aaVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.a(aaVar.b))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = aaVar.d();
        if ((d2 || d3) && (!d2 || !d3 || !this.c.equals(aaVar.c))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = aaVar.e();
        if ((e2 || e3) && (!e2 || !e3 || !this.d.equals(aaVar.d))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = aaVar.f();
        if ((f2 || f3) && (!f2 || !f3 || !this.e.equals(aaVar.e))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = aaVar.g();
        if (((g2 || g3) && (!g2 || !g3 || !this.f.a(aaVar.f))) || this.g != aaVar.g) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = aaVar.i();
        if ((i2 || i3) && (!i2 || !i3 || !this.h.equals(aaVar.h))) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = aaVar.k();
        if ((k2 || k3) && (!k2 || !k3 || !this.i.equals(aaVar.i))) {
            return false;
        }
        boolean l2 = l();
        boolean l3 = aaVar.l();
        if ((l2 || l3) && (!l2 || !l3 || !this.j.equals(aaVar.j))) {
            return false;
        }
        boolean m2 = m();
        boolean m3 = aaVar.m();
        return (!m2 && !m3) || (m2 && m3 && this.k.equals(aaVar.k));
    }

    /* renamed from: b */
    public int compareTo(aa aaVar) {
        if (!getClass().equals(aaVar.getClass())) {
            return getClass().getName().compareTo(aaVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(aaVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, aaVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(aaVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((Comparable) this.b, (Comparable) aaVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(aaVar.d()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (d()) {
            int a4 = org.apache.thrift.b.a(this.c, aaVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(aaVar.e()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (e()) {
            int a5 = org.apache.thrift.b.a(this.d, aaVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(aaVar.f()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (f()) {
            int a6 = org.apache.thrift.b.a(this.e, aaVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(aaVar.g()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (g()) {
            int a7 = org.apache.thrift.b.a((Comparable) this.f, (Comparable) aaVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(aaVar.h()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (h()) {
            int a8 = org.apache.thrift.b.a(this.g, aaVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(aaVar.i()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (i()) {
            int a9 = org.apache.thrift.b.a(this.h, aaVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        int compareTo9 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(aaVar.k()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (k()) {
            int a10 = org.apache.thrift.b.a((Map) this.i, (Map) aaVar.i);
            if (a10 != 0) {
                return a10;
            }
        }
        int compareTo10 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(aaVar.l()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (l()) {
            int a11 = org.apache.thrift.b.a(this.j, aaVar.j);
            if (a11 != 0) {
                return a11;
            }
        }
        int compareTo11 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(aaVar.m()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (m()) {
            int a12 = org.apache.thrift.b.a(this.k, aaVar.k);
            if (a12 != 0) {
                return a12;
            }
        }
        return 0;
    }

    public void b(org.apache.thrift.protocol.e eVar) {
        n();
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
        if (this.d != null && e()) {
            eVar.a(q);
            eVar.a(this.d);
        }
        if (this.e != null && f()) {
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
        if (this.i != null && k()) {
            eVar.a(v);
            eVar.a(new d(ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, this.i.size()));
            for (Entry next : this.i.entrySet()) {
                eVar.a((String) next.getKey());
                eVar.a((String) next.getValue());
            }
        }
        if (this.j != null && l()) {
            eVar.a(w);
            eVar.a(this.j);
        }
        if (this.k != null && m()) {
            eVar.a(x);
            eVar.a(this.k);
        }
        eVar.a();
    }

    public boolean b() {
        return this.b != null;
    }

    public String c() {
        return this.c;
    }

    public boolean d() {
        return this.c != null;
    }

    public boolean e() {
        return this.d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof aa)) {
            return a((aa) obj);
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

    public Map<String, String> j() {
        return this.i;
    }

    public boolean k() {
        return this.i != null;
    }

    public boolean l() {
        return this.j != null;
    }

    public boolean m() {
        return this.k != null;
    }

    public void n() {
        if (this.c == null) {
            StringBuilder sb = new StringBuilder("Required field 'id' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        }
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionAckNotification(");
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
        if (e()) {
            sb.append(", ");
            sb.append("appId:");
            sb.append(this.d == null ? "null" : this.d);
        }
        if (f()) {
            sb.append(", ");
            sb.append("type:");
            sb.append(this.e == null ? "null" : this.e);
        }
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
        if (k()) {
            sb.append(", ");
            sb.append("extra:");
            if (this.i == null) {
                sb.append("null");
            } else {
                sb.append(this.i);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.j == null ? "null" : this.j);
        }
        if (m()) {
            sb.append(", ");
            sb.append("category:");
            sb.append(this.k == null ? "null" : this.k);
        }
        sb.append(")");
        return sb.toString();
    }
}
