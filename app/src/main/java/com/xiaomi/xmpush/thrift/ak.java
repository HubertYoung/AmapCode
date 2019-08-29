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
import org.apache.thrift.meta_data.b;
import org.apache.thrift.meta_data.c;
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.e;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.j;

public class ak implements Serializable, Cloneable, org.apache.thrift.a<ak, a> {
    public static final Map<a, b> k;
    private static final j l = new j("XmPushActionRegistrationResult");
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("debug", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("target", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 2);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("appId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("request", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 5);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("errorCode", 10, 6);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("reason", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 7);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("regId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 8);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("regSecret", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 9);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 10);
    public String a;
    public x b;
    public String c;
    public String d;
    public aj e;
    public long f;
    public String g;
    public String h;
    public String i;
    public String j;
    private BitSet w = new BitSet(1);

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        REQUEST(5, "request"),
        ERROR_CODE(6, "errorCode"),
        REASON(7, "reason"),
        REG_ID(8, "regId"),
        REG_SECRET(9, "regSecret"),
        PACKAGE_NAME(10, "packageName");
        
        private static final Map<String, a> k = null;
        private final short l;
        private final String m;

        static {
            k = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                k.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.l = s;
            this.m = str;
        }

        public final String a() {
            return this.m;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new b("debug", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TARGET, new b("target", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, x.class)));
        enumMap.put(a.ID, new b("id", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APP_ID, new b("appId", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.REQUEST, new b("request", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, aj.class)));
        enumMap.put(a.ERROR_CODE, new b("errorCode", 1, new c(10)));
        enumMap.put(a.REASON, new b("reason", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.REG_ID, new b("regId", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.REG_SECRET, new b("regSecret", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        k = Collections.unmodifiableMap(enumMap);
        b.a(ak.class, k);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r5) {
        /*
            r4 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r5.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x0099
            short r1 = r0.c
            r2 = 12
            r3 = 11
            switch(r1) {
                case 1: goto L_0x008d;
                case 2: goto L_0x007b;
                case 3: goto L_0x0070;
                case 4: goto L_0x0065;
                case 5: goto L_0x0054;
                case 6: goto L_0x0043;
                case 7: goto L_0x0038;
                case 8: goto L_0x002d;
                case 9: goto L_0x0022;
                case 10: goto L_0x0017;
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
            r4.j = r0
            goto L_0x0000
        L_0x0022:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.i = r0
            goto L_0x0000
        L_0x002d:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.h = r0
            goto L_0x0000
        L_0x0038:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.g = r0
            goto L_0x0000
        L_0x0043:
            byte r1 = r0.b
            r2 = 10
            if (r1 != r2) goto L_0x0011
            long r0 = r5.j()
            r4.f = r0
            r0 = 1
            r4.a(r0)
            goto L_0x0000
        L_0x0054:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0011
            com.xiaomi.xmpush.thrift.aj r0 = new com.xiaomi.xmpush.thrift.aj
            r0.<init>()
            r4.e = r0
            com.xiaomi.xmpush.thrift.aj r0 = r4.e
            r0.a(r5)
            goto L_0x0000
        L_0x0065:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.d = r0
            goto L_0x0000
        L_0x0070:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.c = r0
            goto L_0x0000
        L_0x007b:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0011
            com.xiaomi.xmpush.thrift.x r0 = new com.xiaomi.xmpush.thrift.x
            r0.<init>()
            r4.b = r0
            com.xiaomi.xmpush.thrift.x r0 = r4.b
            r0.a(r5)
            goto L_0x0000
        L_0x008d:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.a = r0
            goto L_0x0000
        L_0x0099:
            boolean r5 = r4.h()
            if (r5 != 0) goto L_0x00b7
            org.apache.thrift.protocol.f r5 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'errorCode' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r4.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L_0x00b7:
            r4.n()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.ak.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.w.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(ak akVar) {
        if (akVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = akVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(akVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = akVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.a(akVar.b))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = akVar.d();
        if ((d2 || d3) && (!d2 || !d3 || !this.c.equals(akVar.c))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = akVar.e();
        if ((e2 || e3) && (!e2 || !e3 || !this.d.equals(akVar.d))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = akVar.f();
        if (((f2 || f3) && (!f2 || !f3 || !this.e.a(akVar.e))) || this.f != akVar.f) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = akVar.i();
        if ((i2 || i3) && (!i2 || !i3 || !this.g.equals(akVar.g))) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = akVar.j();
        if ((j2 || j3) && (!j2 || !j3 || !this.h.equals(akVar.h))) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = akVar.k();
        if ((k2 || k3) && (!k2 || !k3 || !this.i.equals(akVar.i))) {
            return false;
        }
        boolean m2 = m();
        boolean m3 = akVar.m();
        return (!m2 && !m3) || (m2 && m3 && this.j.equals(akVar.j));
    }

    /* renamed from: b */
    public int compareTo(ak akVar) {
        if (!getClass().equals(akVar.getClass())) {
            return getClass().getName().compareTo(akVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(akVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, akVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(akVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((Comparable) this.b, (Comparable) akVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(akVar.d()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (d()) {
            int a4 = org.apache.thrift.b.a(this.c, akVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(akVar.e()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (e()) {
            int a5 = org.apache.thrift.b.a(this.d, akVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(akVar.f()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (f()) {
            int a6 = org.apache.thrift.b.a((Comparable) this.e, (Comparable) akVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(akVar.h()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (h()) {
            int a7 = org.apache.thrift.b.a(this.f, akVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(akVar.i()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (i()) {
            int a8 = org.apache.thrift.b.a(this.g, akVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(akVar.j()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (j()) {
            int a9 = org.apache.thrift.b.a(this.h, akVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        int compareTo9 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(akVar.k()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (k()) {
            int a10 = org.apache.thrift.b.a(this.i, akVar.i);
            if (a10 != 0) {
                return a10;
            }
        }
        int compareTo10 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(akVar.m()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (m()) {
            int a11 = org.apache.thrift.b.a(this.j, akVar.j);
            if (a11 != 0) {
                return a11;
            }
        }
        return 0;
    }

    public void b(e eVar) {
        n();
        if (this.a != null && a()) {
            eVar.a(m);
            eVar.a(this.a);
        }
        if (this.b != null && b()) {
            eVar.a(n);
            this.b.b(eVar);
        }
        if (this.c != null) {
            eVar.a(o);
            eVar.a(this.c);
        }
        if (this.d != null) {
            eVar.a(p);
            eVar.a(this.d);
        }
        if (this.e != null && f()) {
            eVar.a(q);
            this.e.b(eVar);
        }
        eVar.a(r);
        eVar.a(this.f);
        if (this.g != null && i()) {
            eVar.a(s);
            eVar.a(this.g);
        }
        if (this.h != null && j()) {
            eVar.a(t);
            eVar.a(this.h);
        }
        if (this.i != null && k()) {
            eVar.a(u);
            eVar.a(this.i);
        }
        if (this.j != null && m()) {
            eVar.a(v);
            eVar.a(this.j);
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
        if (obj != null && (obj instanceof ak)) {
            return a((ak) obj);
        }
        return false;
    }

    public boolean f() {
        return this.e != null;
    }

    public long g() {
        return this.f;
    }

    public boolean h() {
        return this.w.get(0);
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.g != null;
    }

    public boolean j() {
        return this.h != null;
    }

    public boolean k() {
        return this.i != null;
    }

    public String l() {
        return this.j;
    }

    public boolean m() {
        return this.j != null;
    }

    public void n() {
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
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionRegistrationResult(");
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
        if (f()) {
            sb.append(", ");
            sb.append("request:");
            if (this.e == null) {
                sb.append("null");
            } else {
                sb.append(this.e);
            }
        }
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.f);
        if (i()) {
            sb.append(", ");
            sb.append("reason:");
            sb.append(this.g == null ? "null" : this.g);
        }
        if (j()) {
            sb.append(", ");
            sb.append("regId:");
            sb.append(this.h == null ? "null" : this.h);
        }
        if (k()) {
            sb.append(", ");
            sb.append("regSecret:");
            sb.append(this.i == null ? "null" : this.i);
        }
        if (m()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.j == null ? "null" : this.j);
        }
        sb.append(")");
        return sb.toString();
    }
}
