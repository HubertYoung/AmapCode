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

public class an implements Serializable, Cloneable, org.apache.thrift.a<an, a> {
    public static final Map<a, b> m;
    private static final j n = new j("XmPushActionSendMessage");
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("debug", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("target", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 2);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("appId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 5);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("topic", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 6);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("aliasName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 7);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("message", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 8);
    private static final org.apache.thrift.protocol.b w = new org.apache.thrift.protocol.b("needAck", 2, 9);
    private static final org.apache.thrift.protocol.b x = new org.apache.thrift.protocol.b("params", 13, 10);
    private static final org.apache.thrift.protocol.b y = new org.apache.thrift.protocol.b("category", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 11);
    private static final org.apache.thrift.protocol.b z = new org.apache.thrift.protocol.b("userAccount", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 12);
    private BitSet A = new BitSet(1);
    public String a;
    public x b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public t h;
    public boolean i = true;
    public Map<String, String> j;
    public String k;
    public String l;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        PACKAGE_NAME(5, "packageName"),
        TOPIC(6, "topic"),
        ALIAS_NAME(7, "aliasName"),
        MESSAGE(8, "message"),
        NEED_ACK(9, "needAck"),
        PARAMS(10, "params"),
        CATEGORY(11, "category"),
        USER_ACCOUNT(12, "userAccount");
        
        private static final Map<String, a> m = null;
        private final short n;
        private final String o;

        static {
            m = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                m.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.n = s;
            this.o = str;
        }

        public final String a() {
            return this.o;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new b("debug", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TARGET, new b("target", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, x.class)));
        enumMap.put(a.ID, new b("id", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APP_ID, new b("appId", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TOPIC, new b("topic", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.ALIAS_NAME, new b("aliasName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.MESSAGE, new b("message", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, t.class)));
        enumMap.put(a.NEED_ACK, new b("needAck", 2, new c(2)));
        enumMap.put(a.PARAMS, new b("params", 2, new e(13, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES), new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES))));
        enumMap.put(a.CATEGORY, new b("category", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.USER_ACCOUNT, new b("userAccount", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        m = Collections.unmodifiableMap(enumMap);
        b.a(an.class, m);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r6) {
        /*
            r5 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r6.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x00d2
            short r1 = r0.c
            r2 = 12
            r3 = 2
            r4 = 11
            switch(r1) {
                case 1: goto L_0x00c6;
                case 2: goto L_0x00b4;
                case 3: goto L_0x00a8;
                case 4: goto L_0x009c;
                case 5: goto L_0x0090;
                case 6: goto L_0x0084;
                case 7: goto L_0x0078;
                case 8: goto L_0x0067;
                case 9: goto L_0x0058;
                case 10: goto L_0x002e;
                case 11: goto L_0x0023;
                case 12: goto L_0x0018;
                default: goto L_0x0012;
            }
        L_0x0012:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r6, r0)
            goto L_0x0000
        L_0x0018:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.l = r0
            goto L_0x0000
        L_0x0023:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.k = r0
            goto L_0x0000
        L_0x002e:
            byte r1 = r0.b
            r2 = 13
            if (r1 != r2) goto L_0x0012
            org.apache.thrift.protocol.d r0 = r6.c()
            java.util.HashMap r1 = new java.util.HashMap
            int r2 = r0.c
            int r2 = r2 * 2
            r1.<init>(r2)
            r5.j = r1
            r1 = 0
        L_0x0044:
            int r2 = r0.c
            if (r1 >= r2) goto L_0x0000
            java.lang.String r2 = r6.l()
            java.lang.String r3 = r6.l()
            java.util.Map<java.lang.String, java.lang.String> r4 = r5.j
            r4.put(r2, r3)
            int r1 = r1 + 1
            goto L_0x0044
        L_0x0058:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0012
            boolean r0 = r6.f()
            r5.i = r0
            r0 = 1
            r5.a(r0)
            goto L_0x0000
        L_0x0067:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0012
            com.xiaomi.xmpush.thrift.t r0 = new com.xiaomi.xmpush.thrift.t
            r0.<init>()
            r5.h = r0
            com.xiaomi.xmpush.thrift.t r0 = r5.h
            r0.a(r6)
            goto L_0x0000
        L_0x0078:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.g = r0
            goto L_0x0000
        L_0x0084:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.f = r0
            goto L_0x0000
        L_0x0090:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.e = r0
            goto L_0x0000
        L_0x009c:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.d = r0
            goto L_0x0000
        L_0x00a8:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.c = r0
            goto L_0x0000
        L_0x00b4:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0012
            com.xiaomi.xmpush.thrift.x r0 = new com.xiaomi.xmpush.thrift.x
            r0.<init>()
            r5.b = r0
            com.xiaomi.xmpush.thrift.x r0 = r5.b
            r0.a(r6)
            goto L_0x0000
        L_0x00c6:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.a = r0
            goto L_0x0000
        L_0x00d2:
            r5.t()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.an.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z2) {
        this.A.set(0, z2);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(an anVar) {
        if (anVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = anVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(anVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = anVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.a(anVar.b))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = anVar.d();
        if ((d2 || d3) && (!d2 || !d3 || !this.c.equals(anVar.c))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = anVar.f();
        if ((f2 || f3) && (!f2 || !f3 || !this.d.equals(anVar.d))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = anVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.e.equals(anVar.e))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = anVar.i();
        if ((i2 || i3) && (!i2 || !i3 || !this.f.equals(anVar.f))) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = anVar.k();
        if ((k2 || k3) && (!k2 || !k3 || !this.g.equals(anVar.g))) {
            return false;
        }
        boolean m2 = m();
        boolean m3 = anVar.m();
        if ((m2 || m3) && (!m2 || !m3 || !this.h.a(anVar.h))) {
            return false;
        }
        boolean n2 = n();
        boolean n3 = anVar.n();
        if ((n2 || n3) && (!n2 || !n3 || this.i != anVar.i)) {
            return false;
        }
        boolean o2 = o();
        boolean o3 = anVar.o();
        if ((o2 || o3) && (!o2 || !o3 || !this.j.equals(anVar.j))) {
            return false;
        }
        boolean q2 = q();
        boolean q3 = anVar.q();
        if ((q2 || q3) && (!q2 || !q3 || !this.k.equals(anVar.k))) {
            return false;
        }
        boolean s2 = s();
        boolean s3 = anVar.s();
        return (!s2 && !s3) || (s2 && s3 && this.l.equals(anVar.l));
    }

    /* renamed from: b */
    public int compareTo(an anVar) {
        if (!getClass().equals(anVar.getClass())) {
            return getClass().getName().compareTo(anVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(anVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, anVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(anVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((Comparable) this.b, (Comparable) anVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(anVar.d()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (d()) {
            int a4 = org.apache.thrift.b.a(this.c, anVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(anVar.f()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (f()) {
            int a5 = org.apache.thrift.b.a(this.d, anVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(anVar.g()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (g()) {
            int a6 = org.apache.thrift.b.a(this.e, anVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(anVar.i()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (i()) {
            int a7 = org.apache.thrift.b.a(this.f, anVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(anVar.k()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (k()) {
            int a8 = org.apache.thrift.b.a(this.g, anVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(anVar.m()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (m()) {
            int a9 = org.apache.thrift.b.a((Comparable) this.h, (Comparable) anVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        int compareTo9 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(anVar.n()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (n()) {
            int a10 = org.apache.thrift.b.a(this.i, anVar.i);
            if (a10 != 0) {
                return a10;
            }
        }
        int compareTo10 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(anVar.o()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (o()) {
            int a11 = org.apache.thrift.b.a((Map) this.j, (Map) anVar.j);
            if (a11 != 0) {
                return a11;
            }
        }
        int compareTo11 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(anVar.q()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (q()) {
            int a12 = org.apache.thrift.b.a(this.k, anVar.k);
            if (a12 != 0) {
                return a12;
            }
        }
        int compareTo12 = Boolean.valueOf(s()).compareTo(Boolean.valueOf(anVar.s()));
        if (compareTo12 != 0) {
            return compareTo12;
        }
        if (s()) {
            int a13 = org.apache.thrift.b.a(this.l, anVar.l);
            if (a13 != 0) {
                return a13;
            }
        }
        return 0;
    }

    public void b(org.apache.thrift.protocol.e eVar) {
        t();
        if (this.a != null && a()) {
            eVar.a(o);
            eVar.a(this.a);
        }
        if (this.b != null && b()) {
            eVar.a(p);
            this.b.b(eVar);
        }
        if (this.c != null) {
            eVar.a(q);
            eVar.a(this.c);
        }
        if (this.d != null) {
            eVar.a(r);
            eVar.a(this.d);
        }
        if (this.e != null && g()) {
            eVar.a(s);
            eVar.a(this.e);
        }
        if (this.f != null && i()) {
            eVar.a(t);
            eVar.a(this.f);
        }
        if (this.g != null && k()) {
            eVar.a(u);
            eVar.a(this.g);
        }
        if (this.h != null && m()) {
            eVar.a(v);
            this.h.b(eVar);
        }
        if (n()) {
            eVar.a(w);
            eVar.a(this.i);
        }
        if (this.j != null && o()) {
            eVar.a(x);
            eVar.a(new d(ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, this.j.size()));
            for (Entry next : this.j.entrySet()) {
                eVar.a((String) next.getKey());
                eVar.a((String) next.getValue());
            }
        }
        if (this.k != null && q()) {
            eVar.a(y);
            eVar.a(this.k);
        }
        if (this.l != null && s()) {
            eVar.a(z);
            eVar.a(this.l);
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

    public String e() {
        return this.d;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof an)) {
            return a((an) obj);
        }
        return false;
    }

    public boolean f() {
        return this.d != null;
    }

    public boolean g() {
        return this.e != null;
    }

    public String h() {
        return this.f;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f != null;
    }

    public String j() {
        return this.g;
    }

    public boolean k() {
        return this.g != null;
    }

    public t l() {
        return this.h;
    }

    public boolean m() {
        return this.h != null;
    }

    public boolean n() {
        return this.A.get(0);
    }

    public boolean o() {
        return this.j != null;
    }

    public String p() {
        return this.k;
    }

    public boolean q() {
        return this.k != null;
    }

    public String r() {
        return this.l;
    }

    public boolean s() {
        return this.l != null;
    }

    public void t() {
        if (this.c == null) {
            StringBuilder sb = new StringBuilder("Required field 'id' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        } else if (this.d == null) {
            StringBuilder sb2 = new StringBuilder("Required field 'appId' was not present! Struct: ");
            sb2.append(toString());
            throw new f(sb2.toString());
        }
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionSendMessage(");
        if (a()) {
            sb.append("debug:");
            sb.append(this.a == null ? "null" : this.a);
            z2 = false;
        } else {
            z2 = true;
        }
        if (b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            if (this.b == null) {
                sb.append("null");
            } else {
                sb.append(this.b);
            }
            z2 = false;
        }
        if (!z2) {
            sb.append(", ");
        }
        sb.append("id:");
        sb.append(this.c == null ? "null" : this.c);
        sb.append(", ");
        sb.append("appId:");
        sb.append(this.d == null ? "null" : this.d);
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.e == null ? "null" : this.e);
        }
        if (i()) {
            sb.append(", ");
            sb.append("topic:");
            sb.append(this.f == null ? "null" : this.f);
        }
        if (k()) {
            sb.append(", ");
            sb.append("aliasName:");
            sb.append(this.g == null ? "null" : this.g);
        }
        if (m()) {
            sb.append(", ");
            sb.append("message:");
            if (this.h == null) {
                sb.append("null");
            } else {
                sb.append(this.h);
            }
        }
        if (n()) {
            sb.append(", ");
            sb.append("needAck:");
            sb.append(this.i);
        }
        if (o()) {
            sb.append(", ");
            sb.append("params:");
            if (this.j == null) {
                sb.append("null");
            } else {
                sb.append(this.j);
            }
        }
        if (q()) {
            sb.append(", ");
            sb.append("category:");
            sb.append(this.k == null ? "null" : this.k);
        }
        if (s()) {
            sb.append(", ");
            sb.append("userAccount:");
            sb.append(this.l == null ? "null" : this.l);
        }
        sb.append(")");
        return sb.toString();
    }
}
