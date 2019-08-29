package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import java.io.Serializable;
import java.util.ArrayList;
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

public class ad implements Serializable, Cloneable, org.apache.thrift.a<ad, a> {
    public static final Map<a, b> i;
    private static final j j = new j("XmPushActionCommand");
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("debug", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("target", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 2);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("appId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("cmdName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 5);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("cmdArgs", 15, 6);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 7);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("category", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 9);
    public String a;
    public x b;
    public String c;
    public String d;
    public String e;
    public List<String> f;
    public String g;
    public String h;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        CMD_NAME(5, "cmdName"),
        CMD_ARGS(6, "cmdArgs"),
        PACKAGE_NAME(7, "packageName"),
        CATEGORY(9, "category");
        
        private static final Map<String, a> i = null;
        private final short j;
        private final String k;

        static {
            i = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                i.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.j = s;
            this.k = str;
        }

        public final String a() {
            return this.k;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new b("debug", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TARGET, new b("target", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, x.class)));
        enumMap.put(a.ID, new b("id", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APP_ID, new b("appId", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.CMD_NAME, new b("cmdName", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.CMD_ARGS, new b("cmdArgs", 2, new d(15, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES))));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.CATEGORY, new b("category", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        i = Collections.unmodifiableMap(enumMap);
        b.a(ad.class, i);
    }

    public ad a(String str) {
        this.c = str;
        return this;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r5) {
        /*
            r4 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r5.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x0090
            short r1 = r0.c
            r2 = 11
            switch(r1) {
                case 1: goto L_0x0084;
                case 2: goto L_0x0070;
                case 3: goto L_0x0065;
                case 4: goto L_0x005a;
                case 5: goto L_0x004f;
                case 6: goto L_0x002b;
                case 7: goto L_0x0020;
                case 8: goto L_0x000f;
                case 9: goto L_0x0015;
                default: goto L_0x000f;
            }
        L_0x000f:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r5, r0)
            goto L_0x0000
        L_0x0015:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r5.l()
            r4.h = r0
            goto L_0x0000
        L_0x0020:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r5.l()
            r4.g = r0
            goto L_0x0000
        L_0x002b:
            byte r1 = r0.b
            r2 = 15
            if (r1 != r2) goto L_0x000f
            org.apache.thrift.protocol.c r0 = r5.d()
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = r0.b
            r1.<init>(r2)
            r4.f = r1
            r1 = 0
        L_0x003f:
            int r2 = r0.b
            if (r1 >= r2) goto L_0x0000
            java.lang.String r2 = r5.l()
            java.util.List<java.lang.String> r3 = r4.f
            r3.add(r2)
            int r1 = r1 + 1
            goto L_0x003f
        L_0x004f:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r5.l()
            r4.e = r0
            goto L_0x0000
        L_0x005a:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r5.l()
            r4.d = r0
            goto L_0x0000
        L_0x0065:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r5.l()
            r4.c = r0
            goto L_0x0000
        L_0x0070:
            byte r1 = r0.b
            r2 = 12
            if (r1 != r2) goto L_0x000f
            com.xiaomi.xmpush.thrift.x r0 = new com.xiaomi.xmpush.thrift.x
            r0.<init>()
            r4.b = r0
            com.xiaomi.xmpush.thrift.x r0 = r4.b
            r0.a(r5)
            goto L_0x0000
        L_0x0084:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r5.l()
            r4.a = r0
            goto L_0x0000
        L_0x0090:
            r4.i()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.ad.a(org.apache.thrift.protocol.e):void");
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(ad adVar) {
        if (adVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = adVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(adVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = adVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.a(adVar.b))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = adVar.c();
        if ((c2 || c3) && (!c2 || !c3 || !this.c.equals(adVar.c))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = adVar.d();
        if ((d2 || d3) && (!d2 || !d3 || !this.d.equals(adVar.d))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = adVar.e();
        if ((e2 || e3) && (!e2 || !e3 || !this.e.equals(adVar.e))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = adVar.f();
        if ((f2 || f3) && (!f2 || !f3 || !this.f.equals(adVar.f))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = adVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.g.equals(adVar.g))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = adVar.h();
        return (!h2 && !h3) || (h2 && h3 && this.h.equals(adVar.h));
    }

    /* renamed from: b */
    public int compareTo(ad adVar) {
        if (!getClass().equals(adVar.getClass())) {
            return getClass().getName().compareTo(adVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(adVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, adVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(adVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((Comparable) this.b, (Comparable) adVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(adVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c()) {
            int a4 = org.apache.thrift.b.a(this.c, adVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(adVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d()) {
            int a5 = org.apache.thrift.b.a(this.d, adVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(adVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e()) {
            int a6 = org.apache.thrift.b.a(this.e, adVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(adVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f()) {
            int a7 = org.apache.thrift.b.a((List) this.f, (List) adVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(adVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g()) {
            int a8 = org.apache.thrift.b.a(this.g, adVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(adVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h()) {
            int a9 = org.apache.thrift.b.a(this.h, adVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        return 0;
    }

    public ad b(String str) {
        this.d = str;
        return this;
    }

    public void b(e eVar) {
        i();
        if (this.a != null && a()) {
            eVar.a(k);
            eVar.a(this.a);
        }
        if (this.b != null && b()) {
            eVar.a(l);
            this.b.b(eVar);
        }
        if (this.c != null) {
            eVar.a(m);
            eVar.a(this.c);
        }
        if (this.d != null) {
            eVar.a(n);
            eVar.a(this.d);
        }
        if (this.e != null) {
            eVar.a(o);
            eVar.a(this.e);
        }
        if (this.f != null && f()) {
            eVar.a(p);
            eVar.a(new org.apache.thrift.protocol.c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES, this.f.size()));
            for (String a2 : this.f) {
                eVar.a(a2);
            }
        }
        if (this.g != null && g()) {
            eVar.a(q);
            eVar.a(this.g);
        }
        if (this.h != null && h()) {
            eVar.a(r);
            eVar.a(this.h);
        }
        eVar.a();
    }

    public boolean b() {
        return this.b != null;
    }

    public ad c(String str) {
        this.e = str;
        return this;
    }

    public boolean c() {
        return this.c != null;
    }

    public void d(String str) {
        if (this.f == null) {
            this.f = new ArrayList();
        }
        this.f.add(str);
    }

    public boolean d() {
        return this.d != null;
    }

    public ad e(String str) {
        this.g = str;
        return this;
    }

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ad)) {
            return a((ad) obj);
        }
        return false;
    }

    public ad f(String str) {
        this.h = str;
        return this;
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

    public void i() {
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
        StringBuilder sb = new StringBuilder("XmPushActionCommand(");
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
        if (f()) {
            sb.append(", ");
            sb.append("cmdArgs:");
            if (this.f == null) {
                sb.append("null");
            } else {
                sb.append(this.f);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.g == null ? "null" : this.g);
        }
        if (h()) {
            sb.append(", ");
            sb.append("category:");
            sb.append(this.h == null ? "null" : this.h);
        }
        sb.append(")");
        return sb.toString();
    }
}
