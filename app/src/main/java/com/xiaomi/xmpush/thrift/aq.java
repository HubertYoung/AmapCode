package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import java.io.Serializable;
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

public class aq implements Serializable, Cloneable, org.apache.thrift.a<aq, a> {
    public static final Map<a, b> k;
    private static final j l = new j("XmPushActionUnRegistration");
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("debug", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("target", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 2);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("appId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("regId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 5);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("appVersion", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 6);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 7);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("token", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 8);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("deviceId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 9);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("aliasName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 10);
    public String a;
    public x b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        REG_ID(5, "regId"),
        APP_VERSION(6, "appVersion"),
        PACKAGE_NAME(7, "packageName"),
        TOKEN(8, "token"),
        DEVICE_ID(9, "deviceId"),
        ALIAS_NAME(10, "aliasName");
        
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
        enumMap.put(a.REG_ID, new b("regId", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APP_VERSION, new b("appVersion", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TOKEN, new b("token", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.DEVICE_ID, new b("deviceId", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.ALIAS_NAME, new b("aliasName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        k = Collections.unmodifiableMap(enumMap);
        b.a(aq.class, k);
    }

    public aq a(String str) {
        this.c = str;
        return this;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r4) {
        /*
            r3 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r4.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x008c
            short r1 = r0.c
            r2 = 11
            switch(r1) {
                case 1: goto L_0x0080;
                case 2: goto L_0x006d;
                case 3: goto L_0x0062;
                case 4: goto L_0x0057;
                case 5: goto L_0x004c;
                case 6: goto L_0x0041;
                case 7: goto L_0x0036;
                case 8: goto L_0x002b;
                case 9: goto L_0x0020;
                case 10: goto L_0x0015;
                default: goto L_0x000f;
            }
        L_0x000f:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r4, r0)
            goto L_0x0000
        L_0x0015:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.j = r0
            goto L_0x0000
        L_0x0020:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.i = r0
            goto L_0x0000
        L_0x002b:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.h = r0
            goto L_0x0000
        L_0x0036:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.g = r0
            goto L_0x0000
        L_0x0041:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.f = r0
            goto L_0x0000
        L_0x004c:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.e = r0
            goto L_0x0000
        L_0x0057:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.d = r0
            goto L_0x0000
        L_0x0062:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.c = r0
            goto L_0x0000
        L_0x006d:
            byte r1 = r0.b
            r2 = 12
            if (r1 != r2) goto L_0x000f
            com.xiaomi.xmpush.thrift.x r0 = new com.xiaomi.xmpush.thrift.x
            r0.<init>()
            r3.b = r0
            com.xiaomi.xmpush.thrift.x r0 = r3.b
            r0.a(r4)
            goto L_0x0000
        L_0x0080:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.a = r0
            goto L_0x0000
        L_0x008c:
            r3.k()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.aq.a(org.apache.thrift.protocol.e):void");
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(aq aqVar) {
        if (aqVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = aqVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(aqVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = aqVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.a(aqVar.b))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = aqVar.c();
        if ((c2 || c3) && (!c2 || !c3 || !this.c.equals(aqVar.c))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = aqVar.d();
        if ((d2 || d3) && (!d2 || !d3 || !this.d.equals(aqVar.d))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = aqVar.e();
        if ((e2 || e3) && (!e2 || !e3 || !this.e.equals(aqVar.e))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = aqVar.f();
        if ((f2 || f3) && (!f2 || !f3 || !this.f.equals(aqVar.f))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = aqVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.g.equals(aqVar.g))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = aqVar.h();
        if ((h2 || h3) && (!h2 || !h3 || !this.h.equals(aqVar.h))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = aqVar.i();
        if ((i2 || i3) && (!i2 || !i3 || !this.i.equals(aqVar.i))) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = aqVar.j();
        return (!j2 && !j3) || (j2 && j3 && this.j.equals(aqVar.j));
    }

    /* renamed from: b */
    public int compareTo(aq aqVar) {
        if (!getClass().equals(aqVar.getClass())) {
            return getClass().getName().compareTo(aqVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(aqVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, aqVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(aqVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((Comparable) this.b, (Comparable) aqVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(aqVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c()) {
            int a4 = org.apache.thrift.b.a(this.c, aqVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(aqVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d()) {
            int a5 = org.apache.thrift.b.a(this.d, aqVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(aqVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e()) {
            int a6 = org.apache.thrift.b.a(this.e, aqVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(aqVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f()) {
            int a7 = org.apache.thrift.b.a(this.f, aqVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(aqVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g()) {
            int a8 = org.apache.thrift.b.a(this.g, aqVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(aqVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h()) {
            int a9 = org.apache.thrift.b.a(this.h, aqVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(aqVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i()) {
            int a10 = org.apache.thrift.b.a(this.i, aqVar.i);
            if (a10 != 0) {
                return a10;
            }
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(aqVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (j()) {
            int a11 = org.apache.thrift.b.a(this.j, aqVar.j);
            if (a11 != 0) {
                return a11;
            }
        }
        return 0;
    }

    public aq b(String str) {
        this.d = str;
        return this;
    }

    public void b(e eVar) {
        k();
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
        if (this.e != null && e()) {
            eVar.a(q);
            eVar.a(this.e);
        }
        if (this.f != null && f()) {
            eVar.a(r);
            eVar.a(this.f);
        }
        if (this.g != null && g()) {
            eVar.a(s);
            eVar.a(this.g);
        }
        if (this.h != null && h()) {
            eVar.a(t);
            eVar.a(this.h);
        }
        if (this.i != null && i()) {
            eVar.a(u);
            eVar.a(this.i);
        }
        if (this.j != null && j()) {
            eVar.a(v);
            eVar.a(this.j);
        }
        eVar.a();
    }

    public boolean b() {
        return this.b != null;
    }

    public aq c(String str) {
        this.e = str;
        return this;
    }

    public boolean c() {
        return this.c != null;
    }

    public aq d(String str) {
        this.g = str;
        return this;
    }

    public boolean d() {
        return this.d != null;
    }

    public aq e(String str) {
        this.h = str;
        return this;
    }

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof aq)) {
            return a((aq) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f != null;
    }

    public boolean g() {
        return this.g != null;
    }

    public boolean h() {
        return this.h != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.i != null;
    }

    public boolean j() {
        return this.j != null;
    }

    public void k() {
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
        StringBuilder sb = new StringBuilder("XmPushActionUnRegistration(");
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
        if (e()) {
            sb.append(", ");
            sb.append("regId:");
            sb.append(this.e == null ? "null" : this.e);
        }
        if (f()) {
            sb.append(", ");
            sb.append("appVersion:");
            sb.append(this.f == null ? "null" : this.f);
        }
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.g == null ? "null" : this.g);
        }
        if (h()) {
            sb.append(", ");
            sb.append("token:");
            sb.append(this.h == null ? "null" : this.h);
        }
        if (i()) {
            sb.append(", ");
            sb.append("deviceId:");
            sb.append(this.i == null ? "null" : this.i);
        }
        if (j()) {
            sb.append(", ");
            sb.append("aliasName:");
            sb.append(this.j == null ? "null" : this.j);
        }
        sb.append(")");
        return sb.toString();
    }
}
