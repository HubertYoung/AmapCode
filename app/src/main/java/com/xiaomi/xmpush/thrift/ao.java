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

public class ao implements Serializable, Cloneable, org.apache.thrift.a<ao, a> {
    public static final Map<a, b> h;
    private static final j i = new j("XmPushActionSubscription");
    private static final org.apache.thrift.protocol.b j = new org.apache.thrift.protocol.b("debug", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("target", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 2);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("appId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("topic", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 5);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 6);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("category", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 7);
    public String a;
    public x b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        TOPIC(5, "topic"),
        PACKAGE_NAME(6, "packageName"),
        CATEGORY(7, "category");
        
        private static final Map<String, a> h = null;
        private final short i;
        private final String j;

        static {
            h = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                h.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.i = s;
            this.j = str;
        }

        public final String a() {
            return this.j;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new b("debug", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TARGET, new b("target", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, x.class)));
        enumMap.put(a.ID, new b("id", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APP_ID, new b("appId", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TOPIC, new b("topic", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.CATEGORY, new b("category", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        h = Collections.unmodifiableMap(enumMap);
        b.a(ao.class, h);
    }

    public ao a(String str) {
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
            if (r1 == 0) goto L_0x006a
            short r1 = r0.c
            r2 = 11
            switch(r1) {
                case 1: goto L_0x005f;
                case 2: goto L_0x004c;
                case 3: goto L_0x0041;
                case 4: goto L_0x0036;
                case 5: goto L_0x002b;
                case 6: goto L_0x0020;
                case 7: goto L_0x0015;
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
            r3.g = r0
            goto L_0x0000
        L_0x0020:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.f = r0
            goto L_0x0000
        L_0x002b:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.e = r0
            goto L_0x0000
        L_0x0036:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.d = r0
            goto L_0x0000
        L_0x0041:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.c = r0
            goto L_0x0000
        L_0x004c:
            byte r1 = r0.b
            r2 = 12
            if (r1 != r2) goto L_0x000f
            com.xiaomi.xmpush.thrift.x r0 = new com.xiaomi.xmpush.thrift.x
            r0.<init>()
            r3.b = r0
            com.xiaomi.xmpush.thrift.x r0 = r3.b
            r0.a(r4)
            goto L_0x0000
        L_0x005f:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.a = r0
            goto L_0x0000
        L_0x006a:
            r3.h()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.ao.a(org.apache.thrift.protocol.e):void");
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(ao aoVar) {
        if (aoVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = aoVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(aoVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = aoVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.a(aoVar.b))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = aoVar.c();
        if ((c2 || c3) && (!c2 || !c3 || !this.c.equals(aoVar.c))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = aoVar.d();
        if ((d2 || d3) && (!d2 || !d3 || !this.d.equals(aoVar.d))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = aoVar.e();
        if ((e2 || e3) && (!e2 || !e3 || !this.e.equals(aoVar.e))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = aoVar.f();
        if ((f2 || f3) && (!f2 || !f3 || !this.f.equals(aoVar.f))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = aoVar.g();
        return (!g2 && !g3) || (g2 && g3 && this.g.equals(aoVar.g));
    }

    /* renamed from: b */
    public int compareTo(ao aoVar) {
        if (!getClass().equals(aoVar.getClass())) {
            return getClass().getName().compareTo(aoVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(aoVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, aoVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(aoVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((Comparable) this.b, (Comparable) aoVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(aoVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c()) {
            int a4 = org.apache.thrift.b.a(this.c, aoVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(aoVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d()) {
            int a5 = org.apache.thrift.b.a(this.d, aoVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(aoVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e()) {
            int a6 = org.apache.thrift.b.a(this.e, aoVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(aoVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f()) {
            int a7 = org.apache.thrift.b.a(this.f, aoVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(aoVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g()) {
            int a8 = org.apache.thrift.b.a(this.g, aoVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        return 0;
    }

    public ao b(String str) {
        this.d = str;
        return this;
    }

    public void b(e eVar) {
        h();
        if (this.a != null && a()) {
            eVar.a(j);
            eVar.a(this.a);
        }
        if (this.b != null && b()) {
            eVar.a(k);
            this.b.b(eVar);
        }
        if (this.c != null) {
            eVar.a(l);
            eVar.a(this.c);
        }
        if (this.d != null) {
            eVar.a(m);
            eVar.a(this.d);
        }
        if (this.e != null) {
            eVar.a(n);
            eVar.a(this.e);
        }
        if (this.f != null && f()) {
            eVar.a(o);
            eVar.a(this.f);
        }
        if (this.g != null && g()) {
            eVar.a(p);
            eVar.a(this.g);
        }
        eVar.a();
    }

    public boolean b() {
        return this.b != null;
    }

    public ao c(String str) {
        this.e = str;
        return this;
    }

    public boolean c() {
        return this.c != null;
    }

    public ao d(String str) {
        this.f = str;
        return this;
    }

    public boolean d() {
        return this.d != null;
    }

    public ao e(String str) {
        this.g = str;
        return this;
    }

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ao)) {
            return a((ao) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f != null;
    }

    public boolean g() {
        return this.g != null;
    }

    public void h() {
        if (this.c == null) {
            StringBuilder sb = new StringBuilder("Required field 'id' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        } else if (this.d == null) {
            StringBuilder sb2 = new StringBuilder("Required field 'appId' was not present! Struct: ");
            sb2.append(toString());
            throw new f(sb2.toString());
        } else if (this.e == null) {
            StringBuilder sb3 = new StringBuilder("Required field 'topic' was not present! Struct: ");
            sb3.append(toString());
            throw new f(sb3.toString());
        }
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionSubscription(");
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
        sb.append("topic:");
        sb.append(this.e == null ? "null" : this.e);
        if (f()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.f == null ? "null" : this.f);
        }
        if (g()) {
            sb.append(", ");
            sb.append("category:");
            sb.append(this.g == null ? "null" : this.g);
        }
        sb.append(")");
        return sb.toString();
    }
}
