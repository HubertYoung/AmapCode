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

public class ap implements Serializable, Cloneable, org.apache.thrift.a<ap, a> {
    public static final Map<a, b> k;
    private static final j l = new j("XmPushActionSubscriptionResult");
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("debug", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("target", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 2);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("appId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("request", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 5);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("errorCode", 10, 6);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("reason", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 7);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("topic", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 8);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 9);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("category", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 10);
    public String a;
    public x b;
    public String c;
    public String d;
    public ao e;
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
        TOPIC(8, "topic"),
        PACKAGE_NAME(9, "packageName"),
        CATEGORY(10, "category");
        
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
        enumMap.put(a.APP_ID, new b("appId", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.REQUEST, new b("request", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, ao.class)));
        enumMap.put(a.ERROR_CODE, new b("errorCode", 2, new c(10)));
        enumMap.put(a.REASON, new b("reason", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TOPIC, new b("topic", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.CATEGORY, new b("category", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        k = Collections.unmodifiableMap(enumMap);
        b.a(ap.class, k);
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
            com.xiaomi.xmpush.thrift.ao r0 = new com.xiaomi.xmpush.thrift.ao
            r0.<init>()
            r4.e = r0
            com.xiaomi.xmpush.thrift.ao r0 = r4.e
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
            r4.m()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.ap.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.w.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(ap apVar) {
        if (apVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = apVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(apVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = apVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.a(apVar.b))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = apVar.c();
        if ((c2 || c3) && (!c2 || !c3 || !this.c.equals(apVar.c))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = apVar.d();
        if ((d2 || d3) && (!d2 || !d3 || !this.d.equals(apVar.d))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = apVar.e();
        if ((e2 || e3) && (!e2 || !e3 || !this.e.a(apVar.e))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = apVar.f();
        if ((f2 || f3) && (!f2 || !f3 || this.f != apVar.f)) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = apVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.g.equals(apVar.g))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = apVar.i();
        if ((i2 || i3) && (!i2 || !i3 || !this.h.equals(apVar.h))) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = apVar.j();
        if ((j2 || j3) && (!j2 || !j3 || !this.i.equals(apVar.i))) {
            return false;
        }
        boolean l2 = l();
        boolean l3 = apVar.l();
        return (!l2 && !l3) || (l2 && l3 && this.j.equals(apVar.j));
    }

    /* renamed from: b */
    public int compareTo(ap apVar) {
        if (!getClass().equals(apVar.getClass())) {
            return getClass().getName().compareTo(apVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(apVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, apVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(apVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((Comparable) this.b, (Comparable) apVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(apVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c()) {
            int a4 = org.apache.thrift.b.a(this.c, apVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(apVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d()) {
            int a5 = org.apache.thrift.b.a(this.d, apVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(apVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e()) {
            int a6 = org.apache.thrift.b.a((Comparable) this.e, (Comparable) apVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(apVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f()) {
            int a7 = org.apache.thrift.b.a(this.f, apVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(apVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g()) {
            int a8 = org.apache.thrift.b.a(this.g, apVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(apVar.i()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (i()) {
            int a9 = org.apache.thrift.b.a(this.h, apVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        int compareTo9 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(apVar.j()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (j()) {
            int a10 = org.apache.thrift.b.a(this.i, apVar.i);
            if (a10 != 0) {
                return a10;
            }
        }
        int compareTo10 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(apVar.l()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (l()) {
            int a11 = org.apache.thrift.b.a(this.j, apVar.j);
            if (a11 != 0) {
                return a11;
            }
        }
        return 0;
    }

    public void b(e eVar) {
        m();
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
        if (this.d != null && d()) {
            eVar.a(p);
            eVar.a(this.d);
        }
        if (this.e != null && e()) {
            eVar.a(q);
            this.e.b(eVar);
        }
        if (f()) {
            eVar.a(r);
            eVar.a(this.f);
        }
        if (this.g != null && g()) {
            eVar.a(s);
            eVar.a(this.g);
        }
        if (this.h != null && i()) {
            eVar.a(t);
            eVar.a(this.h);
        }
        if (this.i != null && j()) {
            eVar.a(u);
            eVar.a(this.i);
        }
        if (this.j != null && l()) {
            eVar.a(v);
            eVar.a(this.j);
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

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ap)) {
            return a((ap) obj);
        }
        return false;
    }

    public boolean f() {
        return this.w.get(0);
    }

    public boolean g() {
        return this.g != null;
    }

    public String h() {
        return this.h;
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

    public String k() {
        return this.j;
    }

    public boolean l() {
        return this.j != null;
    }

    public void m() {
        if (this.c == null) {
            StringBuilder sb = new StringBuilder("Required field 'id' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        }
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionSubscriptionResult(");
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
        if (d()) {
            sb.append(", ");
            sb.append("appId:");
            sb.append(this.d == null ? "null" : this.d);
        }
        if (e()) {
            sb.append(", ");
            sb.append("request:");
            if (this.e == null) {
                sb.append("null");
            } else {
                sb.append(this.e);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("errorCode:");
            sb.append(this.f);
        }
        if (g()) {
            sb.append(", ");
            sb.append("reason:");
            sb.append(this.g == null ? "null" : this.g);
        }
        if (i()) {
            sb.append(", ");
            sb.append("topic:");
            sb.append(this.h == null ? "null" : this.h);
        }
        if (j()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.i == null ? "null" : this.i);
        }
        if (l()) {
            sb.append(", ");
            sb.append("category:");
            sb.append(this.j == null ? "null" : this.j);
        }
        sb.append(")");
        return sb.toString();
    }
}
